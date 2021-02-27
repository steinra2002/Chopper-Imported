package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.ChopperTurret;

import java.util.function.IntSupplier;


public class RotateTurretCommand extends CommandBase {
    private final IntSupplier encoderTicksSupplier;
    private final ChopperTurret chopperTurret;
    private final boolean commandFinishes;

    public enum TurretCommandState {
        kCreate, kInitialize, kExecute, kEnd
    }

    private TurretCommandState curState = TurretCommandState.kCreate;
    private int m_TestPos;

    public RotateTurretCommand(IntSupplier encoderTicks, ChopperTurret chopperTurret, boolean commandFinishes) {
        this.encoderTicksSupplier = encoderTicks;
        this.chopperTurret = chopperTurret;
        this.commandFinishes = commandFinishes;
        addRequirements(chopperTurret);
    }

    @Override
    public void initialize() {
        curState = TurretCommandState.kInitialize;
        System.out.println("RotateTurretCommand: initialize: " + encoderTicksSupplier.getAsInt());

        if (Robot.isSimulation()) {
            chopperTurret.updateSimVals(encoderTicksSupplier.getAsInt());
        }

        // set is a relative position set function
        chopperTurret.set(encoderTicksSupplier.getAsInt());
    }

    @Override
    public void execute() {
        curState = TurretCommandState.kExecute;
    }

    @Override
    public boolean isFinished() {
        if (commandFinishes) {
            return chopperTurret.isOnTarget();
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        curState = TurretCommandState.kEnd;
        System.out.println("RotateTurretCommand: end:");
        SmartDashboard.putBoolean("RotateTurret Running", false);
        chopperTurret.stop();
    }

    public boolean isInState(TurretCommandState state) {
        return state == curState;
    }
}
