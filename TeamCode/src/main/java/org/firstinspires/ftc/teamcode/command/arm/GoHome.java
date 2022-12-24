package org.firstinspires.ftc.teamcode.command.arm;
import org.firstinspires.ftc.teamcode.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;


public class GoHome extends SimpleArmCommand {
    public GoHome(ArmSubsystem arm) {
        super(arm);

    }

    @Override
    public void initialize() {
        arm.armHome();
    }
}