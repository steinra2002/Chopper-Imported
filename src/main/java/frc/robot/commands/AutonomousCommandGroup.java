package frc.robot.commands;


import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ChopperDrive;

public class AutonomousCommandGroup extends SequentialCommandGroup {
    public AutonomousCommandGroup(ChopperDrive drive) {
        PIDController turnPIDController = new PIDController(0.0111, 0, 0);
        // Velocity tolerance is set to PositiveInfinity by default. :-(
        turnPIDController.setTolerance(0.05, 100);

        addCommands(
            new AutoDriveCommand(
                    () -> drive.driveCartesian(1.0, 0.0, 0.0),
                    () -> drive.getDistance() >= 2, drive)
                    .andThen(drive::stop, drive),
            new AutoTurnCommand(
                    turnPIDController,
                    RobotContainer::getAngle,
                    () -> -90,
                    drive::turn,
                    drive
            )
        );
        System.out.println("Command: " + this.getClass());
    }
}