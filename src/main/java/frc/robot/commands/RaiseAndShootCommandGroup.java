package frc.robot.commands;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.PoofHopper;
import frc.robot.subsystems.PoofballPewPewII;

public class RaiseAndShootCommandGroup extends SequentialCommandGroup {
    public RaiseAndShootCommandGroup(PoofballPewPewII poofballPewPewII, PoofHopper poofHopper) {
        super(new InstantCommand(() -> {
            poofballPewPewII.setSolenoidValue(DoubleSolenoid.Value.kReverse);
                }),
//                new RunCommand(() -> {
//                    () -> {
//                        System.out.println("You're fired!");
//                        poofballPewPewII.setMotor(0.60);
//                    },
//                })
//                ,
                new StartEndCommand(
                        // Start Runnable
                        () -> {
                            System.out.println("You're fired!");
                            poofballPewPewII.setMotor(0.60);
                        },
                        // End Runnable
                        () -> {
                            System.out.println("No. I quit!");
                            poofballPewPewII.setMotor(0.0);
                            poofballPewPewII.setSolenoidValue(DoubleSolenoid.Value.kForward);
                        })
        );
    }
}