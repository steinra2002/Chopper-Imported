package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WheelOfFortune;

public class ReadProximityCommand extends CommandBase {
    private WheelOfFortune wheelOfFortune;

    public ReadProximityCommand(WheelOfFortune wheelOfFortune) {

        this.wheelOfFortune = wheelOfFortune;
        addRequirements(wheelOfFortune);
    }

    @Override
    public void execute() {
        double detectedProximity = this.wheelOfFortune.getProximity();
        SmartDashboard.putNumber("Proximity", detectedProximity);
    }
}
