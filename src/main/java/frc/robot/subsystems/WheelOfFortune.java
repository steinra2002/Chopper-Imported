/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class WheelOfFortune extends SubsystemBase
{
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private ColorSensorV3 m_colorSensor;
    //DoubleSolenoid upSheGoes = new DoubleSolenoid(0, 1);
    //WPI_TalonSRX spinny;
    /**
     * Creates a new WheelOfFortune.
     */
    public WheelOfFortune()
    {
        //spinny = new WPI_TalonSRX(Constants.VANA_WHITE);
        m_colorSensor = new ColorSensorV3(i2cPort);
        //setSolenoidValue(DoubleSolenoid.Value.kReverse);
    }

    public void setSolenoidValue(DoubleSolenoid.Value value){

        //upSheGoes.set(value);
    }

//    public double getMotorSpeed() {
//        return spinny.getMotorOutputPercent();
//    }

    public void setMotorSpeed(double motorSpeed) {

        //spinny.set(motorSpeed);
    }
    /**
     * Will be called periodically whenever the CommandScheduler runs.
     */
    @Override
    public void periodic()
    {

    }

    public Color getColor() {
        return m_colorSensor.getColor();
    }

    public double getIR() {
        return m_colorSensor.getIR();
    }

    public int getProximity() {
        return m_colorSensor.getProximity();
    }
}
