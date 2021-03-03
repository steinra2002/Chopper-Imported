/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.IntSupplier;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;

//import java.util.Properties;

import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings({"FieldCanBeLocal", "unused", "DanglingJavadoc"})
public class RobotContainer
{
    // The robot's subsystems and commands are defined here...
    private final PoofballPewPewII m_pewPewII = new PoofballPewPewII();
    public static final ChopperTurret m_headCannon = new ChopperTurret();
    public static final PoofHopper agitationOfPoofBalls = new PoofHopper();
    private final PoofIntake m_absorptionOfPoofBalls = new PoofIntake();
    private final WheelOfFortune m_vanaWhite = new WheelOfFortune();
    private final ChopperDrive m_driveDrive = new ChopperDrive();
    private final ElevatorLockingSubsystem elevLockingSubsystem = new ElevatorLockingSubsystem();
    public final Compressor sodaPressing = new Compressor();
    public final DarthElevatorSubsystem m_darthElevator = new DarthElevatorSubsystem();

//    private final JMaster m_joySticks = new JMaster(1, 2, "Logitech Gamepad");
    private final Joystick m_rightJoystick = new Joystick(0);
    private final Joystick m_leftJoystick = new Joystick(1);
//    private final Joystick m_joyStickO = new Joystick(1);

    private static final AHRS m_navSensor = new AHRS();

    public AHRS getNavSensor() {
        return m_navSensor;
    }

    private final CommandBase m_autonomousCommand = new MecanumCommand(() -> -0.5, () -> 0.0, () -> 0.0, m_driveDrive).withTimeout(2.0);
    private final CommandGroupBase m_driveAndShootFarCommand = new AutonomousDriveAndShootFarCommandGroup(m_pewPewII, m_driveDrive, agitationOfPoofBalls, m_absorptionOfPoofBalls);
    private final CommandGroupBase m_driveAndShootCloseCommand = new AutonomousDriveAndShootCloseCommandGroup(m_pewPewII, m_driveDrive, agitationOfPoofBalls, m_absorptionOfPoofBalls);

    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    public RobotContainer()
    {
        if (Robot.isReal()) {
            m_driveDrive.setDefaultCommand(new MecanumCommand(
                    () -> -1 * Math.signum(m_rightJoystick.getY()) * Math.pow(m_rightJoystick.getY(), 2), // leftY -> driveCartesian.xSpeed: Forward is positive.
                    () -> Math.signum(m_rightJoystick.getX()) * Math.pow(m_rightJoystick.getX(), 2), // leftX -> driveCartesian.ySpeed: Right is positive.
                    () -> Math.signum(m_leftJoystick.getX()) * Math.pow(m_leftJoystick.getX() , 2), // rightZ -> driveCartesian.zRotation: Clockwise is positive.
                    m_driveDrive
            ));

            m_headCannon.setDefaultCommand(new RotateTurretCommand( m_headCannon, false));
        } else {
            // Play with these values to see how the motors respond.
            m_driveDrive.setDefaultCommand(new MecanumCommand(
                    () -> 1.0, // leftY: All wheels are driving straight forward.
                    () -> 0.0, // leftX
                    () -> 0.0, // rightZ
                    m_driveDrive
            ));
        }

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings.  Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick Joystick} or {@link XboxController}), and then passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton JoystickButton}.
     */
    private void configureButtonBindings()
    {
//        final POVButton dpadUp = new POVButton(m_joyStick, 0);
          final JoystickButton highFire = new ComboButton(m_leftJoystick, 1, 3);
//        final POVButton dpadRight = new POVButton(m_joyStick, 90);
          final JoystickButton rightTurretFire = new JoystickButton(m_leftJoystick, 5);
//        final POVButton dpadDown = new POVButton(m_joyStick, 180);
          final JoystickButton lowFire = new ComboButton(m_leftJoystick, 1, 2);
//        final POVButton dpadLeft = new POVButton(m_joyStick, 270);
          final JoystickButton leftTurretFire = new JoystickButton(m_leftJoystick, 4);
//        final JoystickButton l2 = new JoystickButton(m_joyStick, 7);
          final JoystickButton inhale = new JoystickButton(m_rightJoystick, 1);
//        final JoystickButton r2 = new JoystickButton(m_joyStick, 8);
          final JoystickButton antiShinKiller = new JoystickButton(m_rightJoystick, 3);
//        final JoystickButton l1 = new JoystickButton(m_joyStick, 5);
          final JoystickButton exhale = new JoystickButton(m_rightJoystick, 2);
//        final JoystickButton r1 = new JoystickButton(m_joyStick, 6);
//        final JoystickButton climbTime = new JoystickButton(m_joyStick, 8);
          final JoystickButton upWeGoes = new JoystickButton(m_leftJoystick, 6);
          final JoystickButton downWeGoes = new JoystickButton(m_leftJoystick, 7);

          final JoystickButton fullSpeed = new JoystickButton(m_rightJoystick, 5);
          final JoystickButton halfSpeed = new JoystickButton(m_rightJoystick, 4);
          final JoystickButton lockElevator = new JoystickButton(m_rightJoystick, 11);
          final JoystickButton unlockElevator = new JoystickButton(m_rightJoystick, 10);
//        final JoystickButton colorSense = new JoystickButton(m_joyStick, 4);
//        final JoystickButton colorReadIR = new JoystickButton(m_joyStick, 3);
//        final JoystickButton colorReadProximity = new JoystickButton(m_joyStick, 2);
//        final JoystickButton colorMatch = new JoystickButton(m_joyStick, 1);
//        final JoystickButton colorUp = new JoystickButton(m_joyStick, 2);
//        final JoystickButton colorDown = new JoystickButton(m_joyStick, 1);
//        final JoystickButton colorFullRotate = new JoystickButton(m_joyStick, 3);
//        final JoystickButton colorEighthRotate = new JoystickButton(m_joyStick, 4);
//        final JoystickButton HopperRunBackwards = new JoystickButton(m_joyStickO, 6);

/////// Connect the buttons to commands

//        colorSense.whenPressed(new ReadColorCommand(m_vanaWhite));
//        colorReadIR.whenPressed(new ReadIRCommand(m_vanaWhite));
//        colorReadProximity.whenPressed(new ReadProximityCommand(m_vanaWhite));
//        colorMatch.whenPressed(new MatchColorCommand(m_vanaWhite));
        inhale.whileHeld(new IntakeSequenceCommandGroup(m_absorptionOfPoofBalls, agitationOfPoofBalls));
        antiShinKiller.whenPressed(new InstantCommand(() -> m_absorptionOfPoofBalls.setSolenoidValue(DoubleSolenoid.Value.kReverse)));
        highFire.whileHeld(new RaiseAndShootCommandGroup(m_pewPewII, agitationOfPoofBalls));
        lowFire.whileHeld(new StartEndCommand(
                // Start Runnable
                () -> {
                    System.out.println("You're fired low!");
                    m_pewPewII.setMotor(0.26);
                },
                // End Runnable
                () -> {
                    System.out.println("No. I quit low!");
                    m_pewPewII.setMotor(0.0);
                }));
        rightTurretFire.whenPressed(new RotateTurretCommand(() -> 420, m_headCannon, true));
        leftTurretFire.whenPressed(new RotateTurretCommand(() -> -420, m_headCannon, true));
        exhale.whileHeld(new IntakeReverseCommandGroup(m_absorptionOfPoofBalls, agitationOfPoofBalls));
        upWeGoes.whileHeld(new UpWeGoesCommand(m_darthElevator, m_driveDrive));

        downWeGoes.whileHeld(new StartEndCommand(() -> {
            System.out.println("weGoesDown");
            m_darthElevator.MotorPower(-0.8);
        }, () -> {
            System.out.println("weGoesStopAgain");
            m_darthElevator.MotorPower(0.0);
        }, m_darthElevator));

        fullSpeed.whenPressed(new InstantCommand(() -> {
            System.out.println("Full Speed");
            m_driveDrive.setFullSpeed();
        }) {
            @Override
            public boolean runsWhenDisabled() {
                return true;
            }
        });


        halfSpeed.whenPressed(new InstantCommand(() -> {
            System.out.println("Half Speed");
            m_driveDrive.setHalfSpeed();
        }) {
            @Override
            public boolean runsWhenDisabled() {
                return true;
            }
        });

        lockElevator.whenPressed(new InstantCommand(() -> {
            System.out.println("Locking elevator");
            elevLockingSubsystem.stopServo();
        }, elevLockingSubsystem));

        unlockElevator.whenPressed(new InstantCommand(() -> {
            System.out.println("Unlocking elevator");
            elevLockingSubsystem.releaseServo();
        }, elevLockingSubsystem));
   }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public CommandBase getAutonomousCommand()
    {
        return m_autonomousCommand;
    }

    public CommandGroupBase getDriveAndShootFarCommand() {
        return m_driveAndShootFarCommand;
    }

    public CommandGroupBase getDriveAndShootCloseCommand() {
        return m_driveAndShootCloseCommand;
    }

    public static double getAngle() {
        return m_navSensor.getAngle();
    }
}
