/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 * <p>
 * It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int kMinTurretEncoderValue = -2800;
    public static final int kMaxTurretEncoderValue = 2800;
    public static int DRIVE_MOTOR_FR = 2;
    public static int DRIVE_MOTOR_FL = 1;
    public static int DRIVE_MOTOR_BR = 4;
    public static int DRIVE_MOTOR_BL = 3;
    public static int ELEVATOR_STOPPER = 9;
    public static final String GAMEPAD = "Logitech Gamepad";
    public static final String NOJOY = "Un-Logitech Gamepad";
    public static int POOF_BALL_PEWPEWII = 5;
    public static int CHOPPER_TURRET = 12;
    public static int HOPPER_ONE = 8;
    public static int HOPPER_TWO = 7;
    public static int INTAKER_ONE = 10;
    //public static int VANA_WHITE = 12; Not being used. Toasted.
    public static int DARTH_ELEVATOR = 13;
    public static boolean kSensorPhase = false;
    public static int kTimeoutMs = 30;
    public static int kPIDLoopIdx = 0;
    public static boolean kInverted = false;
    public static int kSlotIdx = 0;
    public static int kMotionCruiseVelocity = 285;
    public static int kMotionAcceleration = 285 * 4;
}