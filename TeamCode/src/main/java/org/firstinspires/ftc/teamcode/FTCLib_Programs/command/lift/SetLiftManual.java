package org.firstinspires.ftc.teamcode.FTCLib_Programs.command.lift;
import org.firstinspires.ftc.teamcode.FTCLib_Programs.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.FTCLib_Programs.subsystem.ArmSubsystem;


public class SetLiftManual extends SimpleArmCommand {
    boolean getReal;
    public SetLiftManual(ArmSubsystem arm, boolean isGoingUp) {
        super(arm);
        this.getReal = isGoingUp;

    }

    @Override
    public void initialize() {
        arm.setLiftManual(getReal);
    }
}
