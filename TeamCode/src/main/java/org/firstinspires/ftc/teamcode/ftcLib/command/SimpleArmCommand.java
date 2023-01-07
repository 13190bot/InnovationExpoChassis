package org.firstinspires.ftc.teamcode.ftcLib.command;

import android.util.Log;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.ArmSubsystem;

public class SimpleArmCommand extends CommandBase {
    protected final ArmSubsystem arm;

    public SimpleArmCommand(ArmSubsystem arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    @Override
    public boolean isFinished() {
        Log.d(this.getName() + " status", "done");
        return true;
    }
}
