package org.firstinspires.ftc.teamcode.command.lift;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;

public class SetJunction extends CommandBase {
    private final ArmSubsystem arm;
    private ArmSubsystem.Junction junction;

    public SetJunction(ArmSubsystem arm, ArmSubsystem.Junction junction) {
        this.arm = arm;
        this.junction = junction;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.setLiftAuto();
        arm.setJunction(arm, junction);
    }

    @Override
    public boolean isFinished() {
        // Log.d("SetJunction status", "finished");
        return true;
    }
}