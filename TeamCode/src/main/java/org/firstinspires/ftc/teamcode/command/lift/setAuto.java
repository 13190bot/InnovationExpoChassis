package org.firstinspires.ftc.teamcode.command.lift;
import org.firstinspires.ftc.teamcode.command.SimpleArmCommand;


public class setAuto extends SimpleArmCommand {

    public setAuto(ArmSubsystem_OG arm) {
        super(arm);

    }

    @Override
    public void initialize() {
        arm.setLiftAuto();
    }
}
