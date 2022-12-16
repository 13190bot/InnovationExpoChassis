package org.firstinspires.ftc.teamcode.command.lift;

import org.firstinspires.ftc.teamcode.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;

import java.util.function.DoubleSupplier;

public class RunLiftManualy extends SimpleArmCommand {

    DoubleSupplier speed;
    public RunLiftManualy(ArmSubsystem arm, DoubleSupplier speed) {
        super(arm);
        this.speed = speed;
    }

    @Override
    public void initialize() {
        arm.manualControlLift( speed.getAsDouble() );
    }

    @Override
    public boolean isFinished () {
        return false;
    }
}