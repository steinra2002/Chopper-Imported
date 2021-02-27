package frc.robot;

import com.revrobotics.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CANSparkMaxSmartVelocity extends CANSparkMax {
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
    private CANPIDController m_pidController;
    private CANEncoder m_encoder;
    private Method setpointCommandMethod;
    private double m_setPoint;
    /**
     * Create a new SPARK MAX Controller
     *
     * @param deviceID The device ID.
     */
    public CANSparkMaxSmartVelocity(int deviceID) {
        super(deviceID, CANSparkMaxLowLevel.MotorType.kBrushless);
        restoreFactoryDefaults();

        setSmartCurrentLimit(40);
        setIdleMode(IdleMode.kCoast);

        /**
         * In order to use PID functionality for a controller, a CANPIDController object
         * is constructed by calling the getPIDController() method on an existing
         * CANSparkMax object
         */
        m_pidController = getPIDController();

        // PID coefficients
        kP = 2.28e-4;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 1.79e-4;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 5700;

        // Smart Motion Coefficients
        maxVel = 5700; // rpm
        maxAcc = 5700;

        // set PID coefficients
        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);

        /*
         * Smart Motion coefficients are set on a CANPIDController object
         *
         * - setSmartMotionMaxVelocity() will limit the velocity in RPM of
         * the pid controller in Smart Motion mode
         * - setSmartMotionMinOutputVelocity() will put a lower bound in
         * RPM of the pid controller in Smart Motion mode
         * - setSmartMotionMaxAccel() will limit the acceleration in RPM^2
         * of the pid controller in Smart Motion mode
         * - setSmartMotionAllowedClosedLoopError() will set the max allowed
         * error for the pid controller in Smart Motion mode
         */
        int smartMotionSlot = 0;
        m_pidController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        m_pidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        m_pidController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        m_pidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

        try {
            setpointCommandMethod = CANSparkMaxLowLevel.class.getDeclaredMethod("setpointCommand",
                    double.class, ControlType.class);
            setpointCommandMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void set(double speed) {
        if (Math.abs(speed) < 0.25) {
            super.set(speed);
        } else {
            m_setPoint = speed * maxRPM;
            try {
                setpointCommandMethod.invoke(this, m_setPoint, ControlType.kSmartVelocity);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
