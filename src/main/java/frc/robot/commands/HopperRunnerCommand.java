package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PoofHopper;

import java.util.concurrent.TimeUnit;

public class HopperRunnerCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "FieldCanBeLocal"})
    private final PoofHopper subsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public HopperRunnerCommand(PoofHopper subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        this.subsystem.setMotorSpeed(0.5, 0.5);
    }

    @Override
    public void end(boolean interrupted) {
        this.subsystem.setMotorSpeed(0.0, 0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
