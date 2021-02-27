package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class ComboButton extends JoystickButton {
    private final GenericHID m_joystick;
    private final int m_buttonNumber;

    public ComboButton(GenericHID joystick, int buttonNumber1, int buttonNumber2) {
        super(joystick, buttonNumber1);
        this.m_joystick = joystick;
        m_buttonNumber = buttonNumber2;
    }

    @Override
    public boolean get() {
        return super.get() && m_joystick.getRawButton(m_buttonNumber);
    }
}
