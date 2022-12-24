package org.firstinspires.ftc.teamcode.command.lift.PID_Things;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;

import java.util.function.DoubleSupplier;

public class MoveLiftPID extends CommandBase {
    private final ArmSubsystem arm;
    private DoubleSupplier supplier;

    public MoveLiftPID(ArmSubsystem arm, DoubleSupplier supplier) {
        this.arm = arm;
        this.supplier = supplier;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        if(supplier.getAsDouble() != 0) {
            arm.changeSetPoint(supplier.getAsDouble());
            arm.loopPID();
        } else {
            arm.loopPID();
        }
    }
}
