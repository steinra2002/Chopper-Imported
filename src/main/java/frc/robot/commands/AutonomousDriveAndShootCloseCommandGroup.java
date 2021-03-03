package frc.robot.commands;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.ChopperDrive;
import frc.robot.subsystems.PoofHopper;
import frc.robot.subsystems.PoofIntake;
import frc.robot.subsystems.PoofballPewPewII;

public class AutonomousDriveAndShootCloseCommandGroup extends SequentialCommandGroup {
    public AutonomousDriveAndShootCloseCommandGroup(PoofballPewPewII poofballPewPewII, ChopperDrive chopperDrive, PoofHopper poofHopper, PoofIntake poofIntake) {
        super(new InstantCommand(() -> {
                    poofballPewPewII.setSolenoidValue(DoubleSolenoid.Value.kForward);
                    System.out.println("You're fired!");
                    poofballPewPewII.setMotor(0.26);
                }),
                new RunCommand(() -> {
                    System.out.println("Shooter Delay");
                }).withTimeout(1.0),
                new MecanumCommand(() -> -0.5, () -> 0.0, () -> 0.0, chopperDrive).withTimeout(1.0),
                new RunCommand(() -> {
                            chopperDrive.stop();
                            poofHopper.setMotorSpeed(-0.5, 0.4);
                        }).withTimeout(4.0),
                new InstantCommand(
                        // Start Runnable
                        () -> {
                            poofballPewPewII.setMotor(0.0);
                            poofHopper.setMotorSpeed(0.0, 0.0);
                            poofballPewPewII.setSolenoidValue(DoubleSolenoid.Value.kReverse);
                        })
                );
                System.out.println("Command: " + this.getClass());

            }
}