package org.firstinspires.ftc.teamcode.command.arm;
import org.firstinspires.ftc.teamcode.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;


public class GoScore extends SimpleArmCommand {
    public GoScore(ArmSubsystem arm) {
        super(arm);

    }

    @Override
    public void initialize() {
        arm.armScore();
    }
}