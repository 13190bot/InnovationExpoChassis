package org.firstinspires.ftc.teamcode.FTCLib_Programs.command.claw;

import org.firstinspires.ftc.teamcode.FTCLib_Programs.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.FTCLib_Programs.subsystem.ArmSubsystem;

public class DropCone extends SimpleArmCommand {
    public DropCone(ArmSubsystem arm) {
        super(arm);
    }

    @Override
    public void initialize() {
        arm.clawOpen();
    }
}
