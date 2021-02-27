package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

import javax.swing.*;


@SuppressWarnings("SwitchStatementWithTooFewBranches")
public class JMaster {

    private final String joyType;
    public XboxController lStick;
    public Joystick rStick;
    private final int port1;
    private final int port2;

    public JMaster(int port1, int port2, String jType) {
        lStick = null;
        rStick = null;
        joyType = jType;
        this.port1 = port1;
        this.port2 = port2;
    }

    public static String selectedToText(int selectedVal) {
        switch (selectedVal) {
            case 0:
                return (Constants.GAMEPAD);
            case 1:
                return (Constants.NOJOY);
        }
        return "";
    }

    public void init() {
        switch (joyType) {
            case Constants.GAMEPAD:
                System.out.println("Gamepad Opened");
                lStick = new XboxController(port1);
                rStick = null;
                break;
            case Constants.NOJOY:
                System.out.println("None");
                lStick = null;
                rStick = null;
                break;
            default:
                System.out.println("Unknown");
                lStick = null;
                rStick = null;
                break;
        }
    }

    public double getLY() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return lStick.getRawAxis(0);
                }

        }
        return 0.0;
    }

    public double getRX() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return lStick.getRawAxis(5);
                }

        }
        return 0.0;
    }

    public double getLTriggerAxis() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return lStick.getTriggerAxis(GenericHID.Hand.kLeft);
                }

        }
        return 0.0;
    }

    public double getRTriggerAxis() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return lStick.getTriggerAxis(GenericHID.Hand.kRight);
                }
        }
        return 0.0;
    }

    public double getRY() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return lStick.getRawAxis(4);
                }
        }
        return 0.0;
    }

    public double getLeftTrigger() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return lStick.getTriggerAxis(GenericHID.Hand.kLeft);
                }
        }
        return 0.0;
    }

    public double getRightTrigger() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return lStick.getTriggerAxis(GenericHID.Hand.kRight);
                }
        }
        return 0.0;
    }

    public JoystickButton getLeftRButton() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new JoystickButton(lStick, 6);
                }
        }
        return null;
    }

    public JoystickButton getRightLButton() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new JoystickButton(lStick, 5);
                }
        }
        return null;
    }

    public JoystickButton getLeftLButton() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new JoystickButton(lStick, 5);
                }
        }
        return null;
    }

    public JoystickButton getRightYButton() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new JoystickButton(lStick, 4);
                }
        }
        return null;
    }

    public JoystickButton getLeftYButton() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new JoystickButton(lStick, 4);
                }
        }
        return null;
    }

    public JoystickButton getRightBButton() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new JoystickButton(lStick, 2);
                }
        }
        return null;
    }
//Frantic Comment
    public JoystickButton getLeftBButton() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new JoystickButton(lStick, 2);
                }
        }
        return null;
    }

    public JoystickButton getRightAButton() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new JoystickButton(lStick, 1);
                }
        }
        return null;
    }

    public JoystickButton getLeftAButton() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new JoystickButton(lStick, 1);
                }
        }
        return null;
    }

    public JoystickButton getRightXButton() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new JoystickButton(lStick, 3);
                }
        }
        return null;
    }

    public JoystickButton getLeftXButton() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new JoystickButton(lStick, 3);
                }
        }
        return null;
    }

    public POVButton getDPadUp() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new POVButton(lStick, 0);
                }
        }
        return null;
    }

    public POVButton getDPadRight() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new POVButton(lStick, 90);
                }
        }
        return null;
    }

    public POVButton getDPadDown() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new POVButton(lStick, 180);
                }
        }
        return null;
    }

    public POVButton getDPadLeft() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new POVButton(lStick, 270);
                }
        }
        return null;
    }

    public JoystickButton getStartButton() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new JoystickButton(lStick, 8);
                }
        }
        return null;
    }
    public JoystickButton getBackButton() {
        switch (joyType) {
            case Constants.GAMEPAD:
                if (lStick != null) {
                    return new JoystickButton(lStick, 7);
                }
        }
        return null;
    }
}




