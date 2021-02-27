package frc.robot.commands;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.PoofHopper;
import frc.robot.subsystems.PoofIntake;

public class IntakeReverseCommandGroup extends SequentialCommandGroup {
    public IntakeReverseCommandGroup(PoofIntake poofIntake, PoofHopper poofHopper) {
        super(
            new StartEndCommand(() -> {
                System.out.println("Exhale");
                poofIntake.setMotorSpeed(-1.0);
                poofHopper.setMotorSpeed(0.5, -0.4);
            },
                    () -> {
                        poofIntake.setMotorSpeed(0.0);
                        poofHopper.setMotorSpeed(0.0, 0.0);
                    }));
    }
}