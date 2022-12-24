package org.firstinspires.ftc.teamcode.command.arm;
import org.firstinspires.ftc.teamcode.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;


public class PrepToGrabCone extends SimpleArmCommand {
    public PrepToGrabCone(ArmSubsystem arm) {
        super(arm);

    }

    @Override
    public void initialize() {
        arm.grabConePrep();
    }
}