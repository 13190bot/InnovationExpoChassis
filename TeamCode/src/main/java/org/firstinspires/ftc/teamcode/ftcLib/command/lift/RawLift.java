package org.firstinspires.ftc.teamcode.ftcLib.command.lift;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.ftcLib.subsystem.RawArmSubsystem;

import java.util.function.DoubleSupplier;

public class RawLift extends CommandBase {
    private final RawArmSubsystem arm;
    private DoubleSupplier input;

    public RawLift(RawArmSubsystem arm, DoubleSupplier input) {
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
