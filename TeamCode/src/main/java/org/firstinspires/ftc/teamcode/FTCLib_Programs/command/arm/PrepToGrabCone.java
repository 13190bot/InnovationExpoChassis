package org.firstinspires.ftc.teamcode.FTCLib_Programs.command.arm;
import org.firstinspires.ftc.teamcode.FTCLib_Programs.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.FTCLib_Programs.subsystem.ArmSubsystem;

public class PrepToGrabCone extends SimpleArmCommand {
    public PrepToGrabCone(ArmSubsystem arm) {
        super(arm);

    }

    @Override
    public void initialize() {
        arm.grabConePrep();
    }
}
