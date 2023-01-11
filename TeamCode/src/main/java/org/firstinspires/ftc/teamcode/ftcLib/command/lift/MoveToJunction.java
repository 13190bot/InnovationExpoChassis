package org.firstinspires.ftc.teamcode.ftcLib.command.lift;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.ArmSubsystem;

public class MoveToJunction extends CommandBase {
    private final ArmSubsystem arm;
    private final ArmSubsystem.Junction junc;

    public MoveToJunction(ArmSubsystem arm, ArmSubsystem.Junction junc) {
        this.arm = arm;
        this.junc = junc;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
       arm.moveToJunction(junc);
    }


}
