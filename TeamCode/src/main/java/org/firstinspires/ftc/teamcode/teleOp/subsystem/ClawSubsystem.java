package org.firstinspires.ftc.teamcode.teleOp.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;

@Config
public class ClawSubsystem extends SubsystemBase {
    private final SimpleServo claw;

    public static double grabPosition = 0.8;
    public static double releasePosition = 0.5;


    public ClawSubsystem(SimpleServo claw) {
        this.claw = claw;
    }

    public void grab(){
        claw.setPosition(grabPosition);
    }

    public void release(){
        claw.setPosition(releasePosition);
    }

}
