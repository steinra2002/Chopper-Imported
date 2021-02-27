package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PoofHopper;


public class HopperRunBackwardsCommand extends CommandBase {
    private PoofHopper subsystem;

    public HopperRunBackwardsCommand(PoofHopper subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);

    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() { {
        this.subsystem.setMotorSpeed(-0.5, -0.5);
    }

    }

    @Override
    public boolean isFinished() { return false; }


    @Override
    public void end(boolean interrupted) { {
        this.subsystem.setMotorSpeed(0.0, 0.0);
    }

    }
}
