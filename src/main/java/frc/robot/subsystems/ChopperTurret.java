/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.TalonSRXSmartPosition;
import frc.robot.commands.RotateTurretCommand;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ChopperTurret extends SubsystemBase
{
    private static final int kMinTurretEncoderValue = -2000;
    private static final int kMaxTurretEncoderValue = 2000;
    private static final int kMaxErrorPosition = 50;
    private static final int kMaxErrorVelocity = 10;

    private TalonSRXSmartPosition motor;
    private double m_targetPosition;
    private double m_currentPosition;
    private double m_currentVelocity;
    private double m_posError;

    private NetworkTableEntry positionEntry;
    private NetworkTableEntry speedEntry;
    private NetworkTableEntry currentEntry;
    private NetworkTableEntry posErrorEntry;
    private NetworkTableEntry cameraXPosEntry;
    private NetworkTableEntry targetEntry;

    private NetworkTableEntry xPosEntry;
    private static final int DO_NOTHING_POS = -9999;
    private int xPos = DO_NOTHING_POS;

    private int commandColPos = 6;
    private int commandRowPos = 0;

    private List<Integer> simEncoderVals;
    private Iterator<Integer> simEncoderValsIter;
    private int simCurrentVal;

    /**
     * Constructor for ChopperTurret.
     */
    public ChopperTurret() {
        motor = new TalonSRXSmartPosition(Constants.CHOPPER_TURRET);
        ShuffleboardTab tab = Shuffleboard.getTab("Turret");
        addMotorNumber(tab);
        targetEntry = addTurretTarget(tab);

        positionEntry = getNetworkGraphEntry(tab,"Turret Position", 0, 0);
        speedEntry = getNetworkGraphEntry(tab,"Turret Speed", 2, 0);
        currentEntry = getNetworkGraphEntry(tab,"Turret Current", 4, 0);
        posErrorEntry = getNetworkGraphEntry(tab,"Turret Pos Error", 0, 2);
        cameraXPosEntry = getNetworkGraphEntry(tab,"Camera X", 2, 2);

        addLimitsList(tab);

        xPosEntry = NetworkTableInstance.getDefault().getTable("datatable").getEntry("Relative Encoder Value");
        xPosEntry.setDouble(DO_NOTHING_POS);

        addTurretCommandToTab("Rotate CW", () -> 420, true);
        addTurretCommandToTab("Rotate CCW", () -> -420, true);
        addTurretCommandToTab("Move by Camera", this::getXPos, false);
    }

    public int getXPos() {
        return xPos;
    }

    public void set(double relativePosition) {
        System.out.println("*********** Setting Turret Relative Position ***********");
        System.out.println("Relative Position: " + relativePosition);
        m_targetPosition = m_currentPosition + relativePosition;
        System.out.println("Target Position: " + m_targetPosition);
        motor.set(ControlMode.MotionMagic, m_targetPosition);
        targetEntry.setDouble(m_targetPosition);
    }

    public void updateSimVals(int deltaSim) {
        int updatedVal = simCurrentVal + Math.abs(deltaSim) + kMaxErrorPosition;

        IntStream simEncoderValStream = IntStream.rangeClosed(simCurrentVal, updatedVal);
        if (deltaSim < 0) {
            simEncoderVals = simEncoderValStream.map((n) -> simCurrentVal - (n - simCurrentVal)).boxed().collect(Collectors.toList());
        } else {
            simEncoderVals = simEncoderValStream.boxed().collect(Collectors.toList());
        }
        simEncoderValsIter = simEncoderVals.iterator();
    }

    public double getEncoderValue() {
        if (Robot.isReal()) {
            return motor.getSelectedSensorPosition();
        }
        else {
            if (simEncoderValsIter.hasNext()) {
                simCurrentVal = simEncoderValsIter.next();
            }
            return simCurrentVal;
        }
    }

    public double getVelocity() {
        if (Robot.isReal()) {
            return motor.getSelectedSensorVelocity();
        }
        else {
            return 1;
        }
    }

    public boolean isOnTarget() {
        m_currentPosition = getEncoderValue();
        m_currentVelocity = getVelocity();
        m_posError = m_currentPosition - m_targetPosition;

        positionEntry.setDouble(m_currentPosition);
        speedEntry.setDouble(m_currentVelocity);
        currentEntry.setDouble(motor.getStatorCurrent());
        posErrorEntry.setDouble(m_posError);

        // If max encoder reached but position error is positive then we can move
        // true here means we can move
        boolean overMaxSendingBack = (!maxEncoderReached() || m_posError > 0);
        // If min encoder reached but position error is negative then we can move
        // true here means we can move
        boolean underMinSendingBack = (!minEncoderReached() || m_posError < 0);

//        System.out.println("Min Encoder Reached: " + minEncoderReached());
//        System.out.println("Max Encoder Reached: " + maxEncoderReached());
//        System.out.println("Current Velocity: " + m_currentVelocity);
//        System.out.println("Position Error: " + m_posError);
//        System.out.println("overMaxSendingVal: " + overMaxSendingBack);
//        System.out.println("underMinSendingBack: " + underMinSendingBack);

        // If either underMinSendingBack or overMaxSendingBack is false then we need to stop command
        return !(underMinSendingBack && overMaxSendingBack) || (minVelErrReached() && maxPosErrReached());
    }

    public void stop() {
        motor.set(ControlMode.PercentOutput, 0.0);
    }

    /**
     * Will be called periodically whenever the CommandScheduler runs.
     */
    @Override
    public void periodic() {
        xPos = ( (int) xPosEntry.getDouble(DO_NOTHING_POS)) * 4;
        cameraXPosEntry.setNumber(xPos);
    }

    public void setPower(double powerLevel) {
        this.motor.set(ControlMode.PercentOutput, powerLevel);
    }

    private boolean minEncoderReached() {
        return m_currentPosition <= kMinTurretEncoderValue;
    }

    private boolean maxEncoderReached() {
        return m_currentPosition >= kMaxTurretEncoderValue;
    }

    private boolean minVelErrReached() {
        return Math.abs(m_currentVelocity) <= kMaxErrorVelocity;
    }

    private boolean maxPosErrReached() {
        return Math.abs(m_posError) <= kMaxErrorPosition;
    }

    private NetworkTableEntry getNetworkGraphEntry(ShuffleboardTab tab, String name, int colNum, int rowNum) {
        return tab.add(name, 0)
                .withWidget(BuiltInWidgets.kGraph)
                .withSize(2, 2)
                .withPosition(colNum, rowNum)
//                .withProperties(Map.of("Label position", "HIDDEN")
                .getEntry();
    }

    private void addTurretCommandToTab(String commandName, IntSupplier encoderTicks, boolean commandFinishes) {
        ShuffleboardTab tab = Shuffleboard.getTab("Turret");
        ShuffleboardLayout layout = tab.getLayout(commandName, BuiltInLayouts.kList)
                .withSize(2, 4)
                .withPosition(commandColPos, commandRowPos)
                .withProperties(Map.of("Label position", "TOP"));

        commandColPos += 2;

        RotateTurretCommand rotateTurretCommand = new RotateTurretCommand(encoderTicks, this, commandFinishes );
        rotateTurretCommand.setName(commandName);
        layout.add(rotateTurretCommand);

        layout.addBoolean("execute",
                () -> rotateTurretCommand.isInState(RotateTurretCommand.TurretCommandState.kExecute));
        layout.addBoolean("end",
                () -> rotateTurretCommand.isInState(RotateTurretCommand.TurretCommandState.kEnd));
    }

    private void addLimitsList(ShuffleboardTab tab) {
        ShuffleboardLayout layout = tab.getLayout("Limits", BuiltInLayouts.kList)
                .withSize(2, 4)
                .withPosition(4, 2)
                .withProperties(Map.of("Label position", "TOP"));

        layout.addBoolean("Min Reached", this::minEncoderReached);
        layout.addBoolean("Max Reached", this::maxEncoderReached);
        layout.addBoolean("Max Pos Error", this::maxPosErrReached);
        layout.addBoolean("Min Vel Error", this::minVelErrReached);
    }

    private NetworkTableEntry addTurretTarget(ShuffleboardTab tab) {
        return tab.add("Turret Target", 0.0)
                .withSize(2, 1)
                .withPosition(0, 4)
                .getEntry();
    }

    private void addMotorNumber(ShuffleboardTab tab) {
        tab.add("Motor Number", Constants.CHOPPER_TURRET)
                .withSize(2, 1)
                .withPosition(0, 7);
    }
}
