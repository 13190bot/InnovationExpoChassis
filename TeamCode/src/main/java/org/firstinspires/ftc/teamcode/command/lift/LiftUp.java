package org.firstinspires.ftc.teamcode.command.lift;
import org.firstinspires.ftc.teamcode.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;


public class LiftUp extends SimpleArmCommand {

    public LiftUp(ArmSubsystem arm) {
        super(arm);

    }

    @Override
    public void initialize() {
        arm.setLiftManual();
        arm.LiftUp();
    }
}
