package org.firstinspires.ftc.teamcode.ftcLib.command.claw;

import org.firstinspires.ftc.teamcode.ftcLib.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.ArmSubsystem;

public class GrabCone extends SimpleArmCommand {
    public GrabCone(ArmSubsystem arm) {
        super(arm);
    }

    @Override
    public void initialize() {
        arm.clawClose();
    }
}
