package org.firstinspires.ftc.teamcode.ftcLib.command.lift;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.PCArmSubsystem;

import java.util.function.DoubleSupplier;

public class PCLift extends CommandBase {
    private final PCArmSubsystem arm;
    private DoubleSupplier input;

    public PCLift(PCArmSubsystem arm, DoubleSupplier input) {
        this.arm = arm;
        this.input = input;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        arm.manualSlide(input.getAsDouble());
    }

    public boolean isFinished() {
        return false;
    }
}
