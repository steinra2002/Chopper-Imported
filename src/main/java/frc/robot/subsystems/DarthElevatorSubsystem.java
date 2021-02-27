package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class DarthElevatorSubsystem extends SubsystemBase {
    /**
     * The Singleton instance of this DarthElevatorSubsystem. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private static DarthElevatorSubsystem INSTANCE;
    private CANSparkMax darthElevator;

    private final int MAX_HEIGHT = 570;
//    private final int MAX_HEIGHT = 200;

    private NetworkTableEntry speedEntry;
    private NetworkTableEntry currentEntry;
    private NetworkTableEntry posEntry;

    public DarthElevatorSubsystem() {
        darthElevator = new CANSparkMax(Constants.DARTH_ELEVATOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        darthElevator.restoreFactoryDefaults();
        darthElevator.setIdleMode(CANSparkMax.IdleMode.kBrake);
        darthElevator.setSmartCurrentLimit(20);
        darthElevator.getEncoder().setPosition(0.0);

        ShuffleboardTab tab = Shuffleboard.getTab("Elevator");
        addMotorNumber(tab);
        speedEntry = getNetworkGraphEntry(tab, "Elevator Speed", 0, 0);
        currentEntry = getNetworkGraphEntry(tab, "Elevator Current", 2, 0);
        posEntry = getNetworkGraphEntry(tab, "Elevator Position", 4, 0);
    }

    public void MotorPower(double power) {
        if (power > 0 && darthElevator.getEncoder().getPosition() >= MAX_HEIGHT) {
            darthElevator.set(0.0);
            return;
        }
        darthElevator.set(power);
    }

    @Override
    public void periodic() {
        if (Robot.isReal()) {
            speedEntry.setDouble(((CANSparkMax) darthElevator).getEncoder().getVelocity());
            currentEntry.setDouble(((CANSparkMax) darthElevator).getOutputCurrent());
            int curHeight = (int) ((CANSparkMax) darthElevator).getEncoder().getPosition();
            posEntry.setDouble(curHeight);
            SmartDashboard.putBoolean("Elevator at Height", curHeight >= 595);
        }

//        if (darthElevator.getEncoder().getPosition() >= 600) {
//            darthElevator.set(0.0);
//        }
    }

    public static DarthElevatorSubsystem getInstance() {
        // Fast (non-synchronized) check to reduce overhead of acquiring a lock when it's not needed
        if (INSTANCE == null) {
            // Lock to make thread safe 
            synchronized (DarthElevatorSubsystem.class) {
                // check nullness again as multiple threads can reach above null check
                if (INSTANCE == null) {
                    INSTANCE = new DarthElevatorSubsystem();
                }
            }
        }
        return INSTANCE;
    }

    private NetworkTableEntry getNetworkGraphEntry(ShuffleboardTab tab, String name, int colNum, int rowNum) {
        return tab.add(name, 0)
                .withWidget(BuiltInWidgets.kGraph)
                .withSize(2, 2)
                .withPosition(colNum, rowNum)
//                .withProperties(Map.of("Label position", "HIDDEN"))
                .getEntry();
    }

    private void addMotorNumber(ShuffleboardTab tab) {
        tab.add("Motor Number", Constants.DARTH_ELEVATOR)
                .withSize(2, 1)
                .withPosition(0, 7);
    }
}

