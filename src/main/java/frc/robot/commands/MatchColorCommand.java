package frc.robot.commands;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WheelOfFortune;

public class MatchColorCommand extends CommandBase {
    private WheelOfFortune wheelOfFortune;
    private final ColorMatch m_colorMatcher = new ColorMatch();

    /**
     * Note: Any example colors should be calibrated as the user needs, these
     * are here as a basic example.
     */
    private final Color kBlueTarget = ColorMatch.makeColor(0.12, 0.42, 0.46);
    private final Color kGreenTarget = ColorMatch.makeColor(0.16, 0.59, 0.25);
    private final Color kRedTarget = ColorMatch.makeColor(0.49, 0.34, 0.14);
    private final Color kYellowTarget = ColorMatch.makeColor(0.31, 0.56, 0.12);

    public MatchColorCommand(WheelOfFortune wheelOfFortune) {

        this.wheelOfFortune = wheelOfFortune;
        addRequirements(wheelOfFortune);
    }

    @Override
    public void initialize() {
        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
    }

    @Override
    public void execute() {
        Color detectedColor = this.wheelOfFortune.getColor();

        String colorString;
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) {
            colorString = "Blue";
        } else if (match.color == kRedTarget) {
            colorString = "Red";
        } else if (match.color == kGreenTarget) {
            colorString = "Green";
        } else if (match.color == kYellowTarget) {
            colorString = "Yellow";
        } else {
            colorString = "Unknown";
        }

        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", colorString);

    }
}
