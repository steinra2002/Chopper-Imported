package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ChopperDrive;
import frc.robot.subsystems.DarthElevatorSubsystem;


public class UpWeGoesCommand extends CommandBase {
    private final DarthElevatorSubsystem darthElevatorSubsystem;
    private final ChopperDrive chopperDrive;

    public UpWeGoesCommand(DarthElevatorSubsystem darthElevatorSubsystem, ChopperDrive chopperDrive) {
        this.darthElevatorSubsystem = darthElevatorSubsystem;
        this.chopperDrive = chopperDrive;
        // each subsystem used by the command must be passed into the addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.darthElevatorSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("weGoesUp");
        darthElevatorSubsystem.MotorPower(1.0);
        chopperDrive.setClimbSpeed();

    }

    @Override
    public void execute() {
        darthElevatorSubsystem.MotorPower(1.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        darthElevatorSubsystem.MotorPower(0.0);
    }
}
