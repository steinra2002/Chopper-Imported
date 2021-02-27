package frc.robot.commands;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ChopperDrive;
import frc.robot.subsystems.PoofHopper;
import frc.robot.subsystems.PoofIntake;
import frc.robot.subsystems.PoofballPewPewII;

public class AutonomousRaiseAndShootFarCommandGroup extends SequentialCommandGroup {
    public AutonomousRaiseAndShootFarCommandGroup(PoofballPewPewII poofballPewPewII, ChopperDrive chopperDrive, PoofHopper poofHopper, PoofIntake poofIntake) {
        super(new InstantCommand(() -> {
                    poofballPewPewII.setSolenoidValue(DoubleSolenoid.Value.kReverse);
                }),
                new ParallelCommandGroup(
                        new MecanumCommand(() -> 0.5, () -> 0.0, () -> 0.0, chopperDrive).withTimeout(2.0).andThen((new InstantCommand(() -> {
                                    chopperDrive.stop();
                                    poofHopper.setMotorSpeed(-0.6, 0.5);
                                })).withTimeout(4.0)),
                                new InstantCommand(
                                        // Start Runnable
                                        () -> {
                                            System.out.println("You're fired!");
                                            poofballPewPewII.setMotor(0.60);
                                        }).withTimeout(8.0)
                        ));
                        System.out.println("Command: " + this.getClass());
                    }
}