package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PoofIntake;
import frc.robot.subsystems.WheelOfFortune;


public class SpinnyUpRunnerCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "FieldCanBeLocal"})
    private final WheelOfFortune subsystem;

    /**
     *
     *
     * @param subsystem The subsystem used by this command.
     */

    public SpinnyUpRunnerCommand(WheelOfFortune subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        //this.subsystem.setMotorSpeed(0.5);

    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
