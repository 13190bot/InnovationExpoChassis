package org.firstinspires.ftc.teamcode.command.claw;

import org.firstinspires.ftc.teamcode.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;

public class DropCone extends SimpleArmCommand {
    public DropCone(ArmSubsystem arm) {
        super(arm);
    }

    @Override
    public void initialize() {
        arm.clawOpen();
    }
}
