package org.firstinspires.ftc.teamcode.command.lift;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;

import java.util.function.DoubleSupplier;

public class ManualLift extends CommandBase {
    private final ArmSubsystem arm;
    private DoubleSupplier input;

    public ManualLift(ArmSubsystem arm, DoubleSupplier input) {
        this.arm = arm;
        this.input = input;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        if(input.getAsDouble() != 0) {
            arm.changeSetPoint(input.getAsDouble());
            arm.loopPID();
        } else {
            arm.loopPID();
        }
    }
}
