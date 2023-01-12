package org.firstinspires.ftc.teamcode.ftcLib.command.lift;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.Subsystem;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.PCArmSubsystem;

public class PCJunc extends CommandBase {
    private final PCArmSubsystem arm;
    private final PCArmSubsystem.Junction junc;

    public PCJunc(PCArmSubsystem arm, PCArmSubsystem.Junction junc) {
        this.arm = arm;
        this.junc = junc;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.moveToJunction(junc);
    }


}
