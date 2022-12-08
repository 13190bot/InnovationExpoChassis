package org.firstinspires.ftc.teamcode.command.lift;
import org.firstinspires.ftc.teamcode.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;


public class SetLiftDown extends SimpleArmCommand {

    public SetLiftDown(ArmSubsystem arm) {
        super(arm);

    }

    @Override
    public void initialize() {
        arm.setLiftDown();
    }
}
