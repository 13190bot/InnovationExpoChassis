package org.firstinspires.ftc.teamcode.FTCLib_Programs.command.lift;
import org.firstinspires.ftc.teamcode.FTCLib_Programs.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.FTCLib_Programs.command.SimpleArmCommand;
import org.firstinspires.ftc.teamcode.FTCLib_Programs.subsystem.ArmSubsystem;


public class SetLiftAuto extends SimpleArmCommand {
    boolean getReal;
    public SetLiftAuto(ArmSubsystem arm) {
        super(arm);


    }

    @Override
    public void initialize() {
        arm.setLiftAuto();
    }
}
