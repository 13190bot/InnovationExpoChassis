package org.firstinspires.ftc.teamcode.command.lift;

import com.arcrobotics.ftclib.command.CommandBase;

public class SetJunction extends CommandBase {
    private final ArmSubsystem_OG arm;
    private ArmSubsystem_OG.Junction junction;

    public SetJunction(ArmSubsystem_OG arm, ArmSubsystem_OG.Junction junction) {
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