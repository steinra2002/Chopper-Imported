package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PoofHopper;
import frc.robot.subsystems.PoofIntake;


public class InhaleCommand extends CommandBase {

    private final PoofHopper poofHopper;
    private final PoofIntake poofIntake;

    public InhaleCommand(PoofHopper poofHopper, PoofIntake poofIntake) {
        this.poofHopper = poofHopper;
        this.poofIntake = poofIntake;
        addRequirements(this.poofHopper, this.poofIntake);
    }

    @Override
    public void initialize() {
        System.out.println("Inhale");
        poofIntake.setMotorSpeed(1.0);
        poofHopper.setMotorSpeed(-0.6, 0.5);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        poofIntake.setMotorSpeed(0.0);
        poofHopper.setMotorSpeed(0.0, 0.0);
    }
}
