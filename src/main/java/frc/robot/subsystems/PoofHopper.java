/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;


public class PoofHopper extends SubsystemBase
{

    private CANSparkMax absorption2Motor;
    private CANSparkMax absorption3Motor;
    Solenoid lilPunchyPunch = new Solenoid(3);

//    private NetworkTableEntry speedTopEntry;
    private NetworkTableEntry currentTopEntry;
//    private NetworkTableEntry speedBottomEntry;
    private NetworkTableEntry currentBottomEntry;

    /**
     * Creates a new PoofHopper.
     */
    public PoofHopper()
    {
        absorption2Motor = new CANSparkMax(Constants.HOPPER_ONE, CANSparkMaxLowLevel.MotorType.kBrushless);
        //absorption2Motor.configOpenloopRamp(0.5, 0);
        absorption3Motor = new CANSparkMax(Constants.HOPPER_TWO, CANSparkMaxLowLevel.MotorType.kBrushless);
        absorption2Motor.restoreFactoryDefaults();
        absorption3Motor.restoreFactoryDefaults();
        absorption2Motor.setSmartCurrentLimit(40);
        absorption3Motor.setSmartCurrentLimit(40);
//        absorption3Motor.setInverted(true);
        lilPunchyPunch.set(false);
        ShuffleboardTab tab = Shuffleboard.getTab("Hopper");
//        addMotorNumbers(tab);
//        speedTopEntry = getNetworkGraphEntry(tab,"Hopper Top Speed", 2, 0);
        currentTopEntry = getNetworkGraphEntry(tab,"Hopper Top Current", 4, 0);
//        speedBottomEntry = getNetworkGraphEntry(tab,"Hopper Bottom Speed", 2, 2);
        currentBottomEntry = getNetworkGraphEntry(tab,"Hopper Bottom Current", 4, 2);
    }

    public double getMotor2Speed() {
        return absorption2Motor.get();
    }

    public double getMotor3Speed() {
        return absorption3Motor.get();
    }

    public void solenoidOut() {
        lilPunchyPunch.set(true);
    }

    public void solenoiIn() {
        lilPunchyPunch.set(false);
    }

//    public void setMotorSpeed(double motorSpeed) {
//        absorption2Motor.set(motorSpeed);
//        absorption3Motor.set(motorSpeed);
//    }

    public void setMotorSpeed(double upperSpeed, double lowerSpeed) {
        absorption2Motor.set(lowerSpeed);
        absorption3Motor.set(upperSpeed);
    }

    /**
     * Will always be called periodically whenever the CommandScheduler runs.
     * @see CommandScheduler#run
     */

    @Override
    public void periodic() {
        if (Robot.isReal()) {
//            speedTopEntry.setDouble(absorption2Motor.getEncoder().getVelocity());
            currentTopEntry.setDouble(absorption2Motor.getOutputCurrent());
//            speedBottomEntry.setDouble(absorption3Motor.getEncoder().getVelocity());
            currentBottomEntry.setDouble(absorption3Motor.getOutputCurrent());
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
}
