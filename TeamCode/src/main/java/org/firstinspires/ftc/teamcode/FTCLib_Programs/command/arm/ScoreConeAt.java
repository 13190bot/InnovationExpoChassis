package org.firstinspires.ftc.teamcode.FTCLib_Programs.command.arm;

import org.firstinspires.ftc.teamcode.FTCLib_Programs.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.FTCLib_Programs.subsystem.ArmSubsystem;

public class ScoreConeAt extends SimpleArmCommand {
    int targetPos;
    public ScoreConeAt(ArmSubsystem arm, int targetPos) {
        super(arm);
        this.targetPos = targetPos;
    }

    @Override
    public void initialize() {
        arm.scoreCone(targetPos);
    }
}
