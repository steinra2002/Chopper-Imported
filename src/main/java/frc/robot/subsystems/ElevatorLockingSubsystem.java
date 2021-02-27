package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorLockingSubsystem extends SubsystemBase {

    private final Servo stoppy = new Servo(Constants.ELEVATOR_STOPPER);

    public ElevatorLockingSubsystem() {
        releaseServo();
    }

    public void stopServo() {
        stoppy.set(0.2);
    }

    public void releaseServo() {
        stoppy.set(0.0);
    }
}

