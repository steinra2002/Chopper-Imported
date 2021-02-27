/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.CANSparkMaxSmartVelocity;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class ChopperDrive extends SubsystemBase {
    SpeedController frontRight;
    SpeedController frontLeft;
    SpeedController rearLeft;
    SpeedController rearRight;

    private CANEncoder frontLeftEncoder;
    private CANEncoder rearLeftEncoder;
    private CANEncoder frontRightEncoder;
    private CANEncoder rearRightEncoder;

    private MecanumDrive m_robotDrive;

    private NetworkTableEntry frontRightCurrentEntry;
    private NetworkTableEntry frontLeftCurrentEntry;
    private NetworkTableEntry rearLeftCurrentEntry;
    private NetworkTableEntry rearRightCurrentEntry;
    private NetworkTableEntry frontRightSpeedEntry;
    private NetworkTableEntry frontLeftSpeedEntry;
    private NetworkTableEntry rearLeftSpeedEntry;
    private NetworkTableEntry rearRightSpeedEntry;
    private NetworkTableEntry speedMultiplierEntry;

    private double m_speedMultiplier = 1.0;

    public ChopperDrive() {

        if (Robot.isReal()) {
//            frontLeft = new CANSparkMaxSmartVelocity(Constants.DRIVE_MOTOR_FL);
//            rearLeft = new CANSparkMaxSmartVelocity(Constants.DRIVE_MOTOR_BL);
//            frontRight = new CANSparkMaxSmartVelocity(Constants.DRIVE_MOTOR_FR);
//            rearRight = new CANSparkMaxSmartVelocity(Constants.DRIVE_MOTOR_BR);
            frontLeft = new CANSparkMax(Constants.DRIVE_MOTOR_FL, CANSparkMaxLowLevel.MotorType.kBrushless);
            rearLeft = new CANSparkMax(Constants.DRIVE_MOTOR_BL, CANSparkMaxLowLevel.MotorType.kBrushless);
            frontRight = new CANSparkMax(Constants.DRIVE_MOTOR_FR, CANSparkMaxLowLevel.MotorType.kBrushless);
            rearRight = new CANSparkMax(Constants.DRIVE_MOTOR_BR, CANSparkMaxLowLevel.MotorType.kBrushless);

//            frontLeftEncoder = ((CANSparkMaxSmartVelocity) frontLeft).getEncoder();
//            rearLeftEncoder = ((CANSparkMaxSmartVelocity) rearLeft).getEncoder();
//            frontRightEncoder = ((CANSparkMaxSmartVelocity) frontRight).getEncoder();
//            rearRightEncoder = ((CANSparkMaxSmartVelocity) rearRight).getEncoder();
            frontLeftEncoder = ((CANSparkMax) frontLeft).getEncoder();
            rearLeftEncoder = ((CANSparkMax) rearLeft).getEncoder();
            frontRightEncoder = ((CANSparkMax) frontRight).getEncoder();
            rearRightEncoder = ((CANSparkMax) rearRight).getEncoder();

        } else {
            frontLeft = new PWMSparkMax(Constants.DRIVE_MOTOR_FL);
            rearLeft = new PWMSparkMax(Constants.DRIVE_MOTOR_BL);
            frontRight = new PWMSparkMax(Constants.DRIVE_MOTOR_FR);
            rearRight = new PWMSparkMax(Constants.DRIVE_MOTOR_BR);
        }

        m_robotDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
        m_robotDrive.setSafetyEnabled(false);
        m_robotDrive.setDeadband(0.2);

        if (Robot.isSimulation()) {
            m_robotDrive.setSafetyEnabled(false);
        }

        ShuffleboardTab tab = Shuffleboard.getTab("Chopper Drive");
        addMotorNumbers(tab);
        speedMultiplierEntry = addSpeedMultiplier(tab);
        frontLeftCurrentEntry = getNetworkGraphEntry(tab,"Front Left Current", 0, 0);
        frontRightCurrentEntry = getNetworkGraphEntry(tab,"Fdront Right Current", 2, 0);
        rearLeftCurrentEntry = getNetworkGraphEntry(tab,"Rear Left Current", 0, 2);
        rearRightCurrentEntry = getNetworkGraphEntry(tab,"Rear Right Current", 2, 2);
        frontLeftCurrentEntry.setDouble(0.0);
        frontRightCurrentEntry.setDouble(0.0);
        rearLeftCurrentEntry.setDouble(0.0);
        rearRightCurrentEntry.setDouble(0.0);
        frontLeftSpeedEntry = getNetworkGraphEntry(tab,"Front Left Speed", 4, 0);
        frontRightSpeedEntry = getNetworkGraphEntry(tab,"Front Right Speed", 6, 0);
        rearLeftSpeedEntry = getNetworkGraphEntry(tab,"Rear Left Speed", 4, 2);
        rearRightSpeedEntry = getNetworkGraphEntry(tab,"Rear Right Speed", 6, 2);
        frontLeftSpeedEntry.setDouble(0.0);
        frontRightSpeedEntry.setDouble(0.0);
        rearLeftSpeedEntry.setDouble(0.0);
        rearRightSpeedEntry.setDouble(0.0);
    }

    @Override
    public void periodic() {
        speedMultiplierEntry.setDouble(m_speedMultiplier);
    }

    /** From the MecanumDrive.java comments
     * Angles are measured clockwise from the positive X axis. The robot's speed is independent
     from its angle or rotation rate.
     * ySpeed    The robot's speed along the Y axis [-1.0..1.0]. Right is positive.
     * xSpeed    The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
     * zRotation The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is
     positive.
     * gyroAngle The current angle reading from the gyro in degrees around the Z axis. Use
     this to implement field-oriented controls.
     */
    public void driveWithJoy(double leftY, double leftX, double rightZ) {
        // Lift out driveCartesian code for learning and debugging
        displayCartesian(leftX, leftY, rightZ);

        var angle = Math.toDegrees(Math.atan2(leftX, leftY));
        var mod_angle = angle % 90;

        if (Math.abs(mod_angle) <= 15 || (90 - Math.abs(mod_angle)) <= 15) {
            Vector2d inputVec = new Vector2d(leftY, leftX);
            var mag = inputVec.magnitude();
            if (Math.abs(leftY) > Math.abs(leftX)) {
                leftY = Math.signum(leftY) * mag;
                leftX = 0;
            } else {
                leftY = 0;
                leftX = Math.signum(leftX) * mag;
            }
        }
//        System.out.println("leftY: " + leftY + " leftX: " + leftX + " rightZ: " + rightZ);
        // Caution!!! We swap "X" and "Y"s here because the "Y" stick becomes the "X" direction.

        leftX *= m_speedMultiplier;
        leftY *= m_speedMultiplier;
        rightZ *= m_speedMultiplier;

        m_robotDrive.driveCartesian(
                leftX, // ySpeed: Right is positive.
                leftY, // xSpeed: Forward is positive.
                rightZ); // zRotation: Clockwise is positive.
    }

    public void driveCartesian(double leftY, double leftX, double rightZ) {
        System.out.println("ChopperDrive: driveCartesian: " + leftY);
        m_robotDrive.driveCartesian(
                leftX, // ySpeed: Right is positive.
                leftY, // xSpeed: Forward is positive.
                rightZ); // zRotation: Clockwise is positive.
    }

    public int getDistance() {
        if (Robot.isReal()) {
            return (int) ((frontLeftEncoder.getPosition() + frontRightEncoder.getPosition()) * 0.5);
        } else {
            return 1;
        }
    }

    public void stop() {
        m_robotDrive.stopMotor();
    }

    private void displayCartesian(double leftX, double leftY, double rightZ) {
        double ySpeed = MathUtil.clamp(leftX, -1.0, 1.0);
        ySpeed = applyDeadband(ySpeed, RobotDriveBase.kDefaultDeadband);
        double xSpeed = MathUtil.clamp(leftY, -1.0, 1.0);
        xSpeed = applyDeadband(xSpeed, RobotDriveBase.kDefaultDeadband);

        // Compensate for gyro angle.
        Vector2d input = new Vector2d(ySpeed, xSpeed);
        input.rotate(0.0);

        double[] wheelSpeeds = new double[4];
        wheelSpeeds[RobotDriveBase.MotorType.kFrontLeft.value] = input.x + input.y + rightZ;
        wheelSpeeds[RobotDriveBase.MotorType.kFrontRight.value] = -input.x + input.y - rightZ;
        wheelSpeeds[RobotDriveBase.MotorType.kRearLeft.value] = -input.x + input.y + rightZ;
        wheelSpeeds[RobotDriveBase.MotorType.kRearRight.value] = input.x + input.y - rightZ;

        normalize(wheelSpeeds);

        if (Robot.isReal()) {
            frontRightCurrentEntry.setDouble(((CANSparkMax)frontRight).getOutputCurrent());
            frontLeftCurrentEntry.setDouble(((CANSparkMax)frontLeft).getOutputCurrent());
            rearLeftCurrentEntry.setDouble(((CANSparkMax)rearLeft).getOutputCurrent());
            rearRightCurrentEntry.setDouble(((CANSparkMax)rearRight).getOutputCurrent());
            frontRightSpeedEntry.setDouble(frontRightEncoder.getVelocity());
            frontLeftSpeedEntry.setDouble(frontLeftEncoder.getVelocity());
            rearLeftSpeedEntry.setDouble(rearLeftEncoder.getVelocity());
            rearRightSpeedEntry.setDouble(rearRightEncoder.getVelocity());
        }
    }

    public void turn(double turnPower) {
        m_robotDrive.driveCartesian(
                0.0, // ySpeed: Right is positive.
                0.0, // xSpeed: Forward is positive.
                turnPower); // zRotation: Clockwise is positive.
    }

    /**
     * Code is lifted from RobotDriveBase.java
     * @param value speed
     * @param deadband dead band value
     * @return new speed
     */
    protected double applyDeadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } else {
                return (value + deadband) / (1.0 - deadband);
            }
        } else {
            return 0.0;
        }
    }

    /**
     * Normalize all wheel speeds if the magnitude of any wheel is greater than 1.0.
     */
    protected void normalize(double[] wheelSpeeds) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        for (int i = 1; i < wheelSpeeds.length; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (maxMagnitude < temp) {
                maxMagnitude = temp;
            }
        }
        if (maxMagnitude > 1.0) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
            }
        }
    }

    public void setHalfSpeed() {
        m_speedMultiplier = 0.5;
    }

    public void setFullSpeed() {
        m_speedMultiplier = 1.0;
    }

    public void setClimbSpeed() {
        m_speedMultiplier = 0.4;
    }

    public double getSpeedMultiplier() {
        return m_speedMultiplier;
    }

    private NetworkTableEntry getNetworkGraphEntry(ShuffleboardTab tab, String name, int colNum, int rowNum) {
        return tab.add(name, 0)
                .withWidget(BuiltInWidgets.kGraph)
                .withSize(2, 2)
                .withPosition(colNum, rowNum)
//                .withProperties(Map.of("Label position", "HIDDEN"))
                .getEntry();
    }

    private void addMotorNumbers(ShuffleboardTab tab) {
        tab.add("FrontLeft", Constants.DRIVE_MOTOR_FL)
                .withSize(1, 1)
                .withPosition(0, 7);
        tab.add("FrontRight", Constants.DRIVE_MOTOR_FR)
                .withSize(1, 1)
                .withPosition(1, 7);
        tab.add("RearLeft", Constants.DRIVE_MOTOR_BL)
                .withSize(1, 1)
                .withPosition(2, 7);
        tab.add("RearRight", Constants.DRIVE_MOTOR_BR)
                .withSize(1, 1)
                .withPosition(3, 7);
    }

    private NetworkTableEntry addSpeedMultiplier(ShuffleboardTab tab) {
        return tab.add("Speed Multiplier", 1.0)
                .withSize(2, 1)
                .withPosition(2, 5)
                .getEntry();
    }
}
