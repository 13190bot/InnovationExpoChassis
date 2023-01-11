package org.firstinspires.ftc.teamcode.ftcLib.command.claw;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.ArmSubsystem;

public class GrabCone extends CommandBase {
    private final ArmSubsystem arm;

    public GrabCone(ArmSubsystem arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.clawClose();
    }
}
