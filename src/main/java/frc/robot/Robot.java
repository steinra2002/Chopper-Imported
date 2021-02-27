/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.IterativeRobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import java.lang.reflect.Field;

public class Robot extends TimedRobot {
    RobotContainer robotContainer;
    CommandBase m_selectedCommand;
    SendableChooser m_autonomousChooser;

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
        robotContainer.sodaPressing.setClosedLoopControl(true);
        m_autonomousChooser = new SendableChooser();
        m_autonomousChooser.setDefaultOption("Drive Straight", robotContainer.getAutonomousCommand());
        m_autonomousChooser.addOption("Drive and Shoot Far", robotContainer.getDriveAndShootFarCommand());
        m_autonomousChooser.addOption("Drive and Shoot Close", robotContainer.getDriveAndShootCloseCommand());
        SmartDashboard.putData(m_autonomousChooser);

        // For the simulator we are going to increase the Robot Watchdog timeout and its spamming messages
        // N.B. we can't easily close or disable it because the IterativeRobotBase loopFunc resets it each time it runs.
        if (Robot.isSimulation()) {
            try {
                Field watchDogField = IterativeRobotBase.class.getDeclaredField("m_watchdog");
                watchDogField.setAccessible(true);
                Watchdog watchdog = (Watchdog) watchDogField.get(this);
                // Down, Fido!
                watchdog.setTimeout(1.0);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        Object selectedItem = m_autonomousChooser.getSelected();
        m_selectedCommand = (selectedItem instanceof CommandBase ? (CommandBase) selectedItem : null);
        if (m_selectedCommand != null) {
            m_selectedCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        if (m_selectedCommand != null) {
            m_selectedCommand.cancel();
        }
    }
}