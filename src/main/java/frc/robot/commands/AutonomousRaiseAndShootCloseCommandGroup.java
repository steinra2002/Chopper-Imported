package frc.robot.commands;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.ChopperDrive;
import frc.robot.subsystems.PoofHopper;
import frc.robot.subsystems.PoofIntake;
import frc.robot.subsystems.PoofballPewPewII;

public class AutonomousRaiseAndShootCloseCommandGroup extends SequentialCommandGroup {
    public AutonomousRaiseAndShootCloseCommandGroup(PoofballPewPewII poofballPewPewII, ChopperDrive chopperDrive, PoofHopper poofHopper, PoofIntake poofIntake) {
        super(new InstantCommand(() -> {
                    poofballPewPewII.setSolenoidValue(DoubleSolenoid.Value.kForward);
                }),
                new ParallelCommandGroup(
                        new MecanumCommand(() -> -0.5, () -> 0.0, () -> 0.0, chopperDrive).withTimeout(1.0).andThen((new InstantCommand(() -> {
                            chopperDrive.stop();
                            poofHopper.setMotorSpeed(-0.6, 0.5);
                        })).withTimeout(4.0)),
                        new InstantCommand(
                                // Start Runnable
                                () -> {
                                    System.out.println("You're fired!");
                                    poofballPewPewII.setMotor(0.40);
                                }).withTimeout(6.0)
                ));
                System.out.println("Command: " + this.getClass());
            }
}