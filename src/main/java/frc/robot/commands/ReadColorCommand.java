package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WheelOfFortune;

public class ReadColorCommand extends CommandBase {

    private WheelOfFortune wheelOfFortune;

    public ReadColorCommand(WheelOfFortune wheelOfFortune) {

        this.wheelOfFortune = wheelOfFortune;
        addRequirements(wheelOfFortune);
    }

    @Override
    public void execute() {
        Color detectedColor = this.wheelOfFortune.getColor();
        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
    }
}
