package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WheelOfFortune;

public class ReadIRCommand extends CommandBase {

    private WheelOfFortune wheelOfFortune;

    public ReadIRCommand(WheelOfFortune wheelOfFortune) {
        this.wheelOfFortune = wheelOfFortune;
        addRequirements(wheelOfFortune);
    }

    @Override
    public void execute() {
        double detectedIR = this.wheelOfFortune.getIR();
        SmartDashboard.putNumber("IR", detectedIR);
    }
}
