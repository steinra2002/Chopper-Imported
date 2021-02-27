/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the Project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.util.Map;


public class PoofIntake extends SubsystemBase
{
    WPI_TalonSRX absorptionMotor;
    private boolean motorActive = false;
    DoubleSolenoid punchy = new DoubleSolenoid(0, 1);

    /**
     * Creates a new PoofIntake.
     */
    public PoofIntake() {
        absorptionMotor = new WPI_TalonSRX(Constants.INTAKER_ONE);
        absorptionMotor.configFactoryDefault();

        absorptionMotor.configOpenloopRamp(0.5, 0);
        setSolenoidValue(DoubleSolenoid.Value.kReverse);
        ShuffleboardTab tab = Shuffleboard.getTab("Intake");
        addIntakeState(tab);
    }

    public double getMotorSpeed() {
        return absorptionMotor.getMotorOutputPercent();
    }

    public void setSolenoidValue(DoubleSolenoid.Value value){
        punchy.set(value);
    }

    public void setMotorSpeed(double motorSpeed) {
        absorptionMotor.set(motorSpeed);
    }

    /**
     * Will always be called periodically whenever the CommandScheduler runs.
     * @see CommandScheduler#run
     */
    @Override
    public void periodic() {
        // Only put contents in here which cannot be run within a Command.
        // A good example is Subsystem debug information.
    }

    private void addIntakeState(ShuffleboardTab tab) {
        ShuffleboardLayout layout = tab.getLayout("Solenoid State", BuiltInLayouts.kList)
                .withSize(2, 2)
                .withPosition(0, 0)
                .withProperties(Map.of("Label Position", "TOP"));

        layout.addBoolean("Punchy Out", () -> punchy.get() == DoubleSolenoid.Value.kForward);
        layout.addBoolean("Punchy In", () -> punchy.get() == DoubleSolenoid.Value.kReverse);
    }
}

