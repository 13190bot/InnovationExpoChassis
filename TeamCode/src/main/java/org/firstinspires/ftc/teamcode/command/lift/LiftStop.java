package org.firstinspires.ftc.teamcode.command.lift;
import org.firstinspires.ftc.teamcode.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;


public class LiftStop extends SimpleArmCommand {

    public LiftStop(ArmSubsystem arm) {
        super(arm);

    }

    @Override
    public void initialize() {
        arm.setLiftManual();
        arm.LiftStop();
    }
}
