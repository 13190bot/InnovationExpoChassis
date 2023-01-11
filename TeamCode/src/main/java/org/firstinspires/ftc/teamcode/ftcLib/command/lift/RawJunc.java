package org.firstinspires.ftc.teamcode.ftcLib.command.lift;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.RawArmSubsystem;

public class RawJunc extends CommandBase {
    private final RawArmSubsystem arm;
    private final RawArmSubsystem.Junction junc;

    public RawJunc(RawArmSubsystem arm, RawArmSubsystem.Junction junc) {
        this.arm = arm;
        this.junc = junc;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.moveToJunction(junc);
    }


}
