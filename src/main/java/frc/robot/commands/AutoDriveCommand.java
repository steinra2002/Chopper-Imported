package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.ChopperDrive;

import java.util.function.BooleanSupplier;


public class AutoDriveCommand extends WaitUntilCommand {
    private final Runnable initialRunnable;
    private final ChopperDrive chopperDrive;

    public AutoDriveCommand(Runnable initialRunnable, BooleanSupplier condition, ChopperDrive chopperDrive) {
        super(condition);
        this.initialRunnable = initialRunnable;
        this.chopperDrive = chopperDrive;
        addRequirements(chopperDrive);
    }

    @Override
    public void initialize() {
        initialRunnable.run();
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {

    }
}
