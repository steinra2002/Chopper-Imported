package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TalonSRXSmartPosition extends WPI_TalonSRX {
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    /**
     * Constructor for motor controller
     *
     * @param deviceNumber device ID of motor controller
     */
    public TalonSRXSmartPosition(int deviceNumber) {
        super(deviceNumber);
        configFactoryDefault();
        configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        //configNeutralDeadband(0.001);

        //setSensorPhase(Constants.kSensorPhase);
        //setInverted(Constants.kInverted);

        //setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
        //setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);

        // PID coefficients
        //kP = 5.0 * 1023.0 / 500.0;
//        kI = 0.005;
        //kI = 0.001;
//        kD = 100.0 * 1023.0 / 541.0;
//        kD = 100.0 * 1023.0 / 541.0;
        //kD = 0.0;
//        kIz = 200;
        //kIz = 0.0;
       // kFF = 1023.0 / 285.0;
        //kMaxOutput = 1;
        //kMinOutput = -1;

        //configNominalOutputForward(0);
        //configNominalOutputReverse(0);
        //configPeakOutputForward(kMaxOutput);
        //configPeakOutputReverse(kMinOutput);

        //selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
        //config_kF(Constants.kPIDLoopIdx, kFF);
        //config_kP(Constants.kPIDLoopIdx, kP);
        //config_kI(Constants.kPIDLoopIdx, kI);
        //config_kD(Constants.kPIDLoopIdx, kD);

        //configMotionCruiseVelocity(Constants.kMotionCruiseVelocity);
        //configMotionAcceleration(Constants.kMotionAcceleration);

        //setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    }
}
