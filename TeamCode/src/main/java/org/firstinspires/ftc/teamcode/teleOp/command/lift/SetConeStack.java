package org.firstinspires.ftc.teamcode.teleOp.command.lift;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.teleOp.subsystem.LiftSubsystem;
import org.firstinspires.ftc.teamcode.util.ConeStack;


public class SetConeStack extends CommandBase {
    private final LiftSubsystem lift;
    private final ConeStack coneStack;

    public SetConeStack(LiftSubsystem lift, ConeStack coneStack) {
        this.lift = lift;
        this.coneStack = coneStack;

        addRequirements(lift);
    }

    @Override
    public void initialize() {
        lift.setConeStack(coneStack);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
