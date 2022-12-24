package org.firstinspires.ftc.teamcode.command.arm;
import org.firstinspires.ftc.teamcode.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;

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