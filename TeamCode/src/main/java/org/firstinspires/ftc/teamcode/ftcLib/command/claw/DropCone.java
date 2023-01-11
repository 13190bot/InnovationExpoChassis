package org.firstinspires.ftc.teamcode.ftcLib.command.claw;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.ArmSubsystem;

public class DropCone extends CommandBase {
    private final ArmSubsystem arm;

    public DropCone(ArmSubsystem arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.clawOpen();
    }
}
