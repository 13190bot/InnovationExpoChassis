package org.firstinspires.ftc.teamcode.command.lift;

import org.firstinspires.ftc.teamcode.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;

public class MoveLiftTo extends SimpleArmCommand {
    int targetPos;
    public MoveLiftTo(ArmSubsystem arm, int targetPos) {
        super(arm);
        this.targetPos = targetPos;
    }

    @Override
    public void initialize() {
        arm.setLiftPosition(targetPos);
    }
}
