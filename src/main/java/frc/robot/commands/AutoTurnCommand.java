package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;


public class AutoTurnCommand extends PIDCommand {

    public AutoTurnCommand(PIDController controller, DoubleSupplier measurementSource, DoubleSupplier setpointSource, DoubleConsumer useOutput, Subsystem... requirements) {
        super(controller, measurementSource, setpointSource, useOutput, requirements);
    }

    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }
}
