package frc.robot.commands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PoofIntake;

import java.util.concurrent.TimeUnit;


public class IntakeRunnerCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "FieldCanBeLocal"})
    private final PoofIntake subsystem;

    /**
     *
     *
     * @param subsystem The subsystem used by this command.
     */

    public IntakeRunnerCommand(PoofIntake subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        this.subsystem.setMotorSpeed(0.5);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
