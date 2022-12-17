package org.firstinspires.ftc.teamcode.command.lift;
import org.firstinspires.ftc.teamcode.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;


public class setAuto extends SimpleArmCommand {

    public setAuto(ArmSubsystem arm) {
        super(arm);

    }

    @Override
    public void initialize() {
        arm.setLiftAuto();
    }
}
