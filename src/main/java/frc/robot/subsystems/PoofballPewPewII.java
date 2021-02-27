/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.*;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.CANSparkMaxSmartVelocity;
import frc.robot.Constants;
import frc.robot.Robot;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


public class PoofballPewPewII extends SubsystemBase {
    SpeedController blastMotor;
    private CANPIDController m_pidController;
    private CANEncoder m_encoder;
    private boolean motorActive = false;
    DoubleSolenoid hoodsolenoid = new DoubleSolenoid(4,5);
    private NetworkTableEntry speedEntry;
    private NetworkTableEntry currentEntry;

    /**
     * Prepare yourself, Jedi! For you shall witness the power of this fully operational PoofBall cannon!
     */

    public PoofballPewPewII() {
        if (Robot.isReal()) {
            blastMotor = new CANSparkMaxShooter(Constants.POOF_BALL_PEWPEWII);
            m_pidController = ((CANSparkMaxShooter) blastMotor).getPIDController();
            m_encoder = ((CANSparkMaxShooter) blastMotor).getEncoder();
        } else {
            blastMotor = new PWMSparkMax(Constants.POOF_BALL_PEWPEWII);
        }
        ShuffleboardTab tab = Shuffleboard.getTab("Shooter");
        addMotorNumber(tab);
        speedEntry = getNetworkGraphEntry(tab,"Shooter Speed", 0, 0);
        currentEntry = getNetworkGraphEntry(tab,"Shooter Current", 2, 0);
        addSolenoidStateList(tab);
    }

    public void setSolenoidValue(DoubleSolenoid.Value value){
        hoodsolenoid.set(value);
    }

    public void setMotor(double value){
        blastMotor.set(value);
    }

    public void setActive(boolean value) {
        motorActive = value;
        System.out.println("shooter subsystem active");
    }

    /**
     * Will be called periodically whenever the CommandScheduler runs.
     */

    @Override
    public void periodic() {
        if (Robot.isReal()) {
            speedEntry.setDouble(((CANSparkMaxShooter) blastMotor).getEncoder().getVelocity());
            currentEntry.setDouble(((CANSparkMaxShooter) blastMotor).getOutputCurrent());
        }
    }

    private NetworkTableEntry getNetworkGraphEntry(ShuffleboardTab tab, String name, int colNum, int rowNum) {
        return tab.add(name, 0)
                .withWidget(BuiltInWidgets.kGraph)
                .withSize(2, 2)
                .withPosition(colNum, rowNum)
//                .withProperties(Map.of("Label position", "HIDDEN"))
                .getEntry();
    }

    private void addMotorNumber(ShuffleboardTab tab) {
        tab.add("Motor Number", Constants.POOF_BALL_PEWPEWII)
                .withSize(2, 1)
                .withPosition(0, 7);
    }

    private void addSolenoidStateList(ShuffleboardTab tab) {
        ShuffleboardLayout layout = tab.getLayout("Solenoid State", BuiltInLayouts.kList)
                .withSize(2, 2)
                .withPosition(4, 0)
                .withProperties(Map.of("Label Position", "TOP"));

        layout.addBoolean("Hood Down", () -> hoodsolenoid.get() == DoubleSolenoid.Value.kForward);
        layout.addBoolean("Hood Up", () -> hoodsolenoid.get() == DoubleSolenoid.Value.kReverse);
    }
}
