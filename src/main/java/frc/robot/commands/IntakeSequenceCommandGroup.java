package frc.robot.commands;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.PoofHopper;
import frc.robot.subsystems.PoofIntake;

public class IntakeSequenceCommandGroup extends SequentialCommandGroup {
    public IntakeSequenceCommandGroup(PoofIntake poofIntake, PoofHopper poofHopper) {
        super(new InstantCommand(() -> {
                    System.out.println("Inhale");
                    poofIntake.setSolenoidValue(DoubleSolenoid.Value.kForward);
                }),
                new InhaleCommand(poofHopper, poofIntake));
    }
}