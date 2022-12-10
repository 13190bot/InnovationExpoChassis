package org.firstinspires.ftc.teamcode.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public class DriveSubsystem extends SubsystemBase {
    private final MecanumDrive drive;


    public double slowFactor = 3; //change this to change how much slowmode slows robot down
    public DriveSubsystem(MotorEx fL, MotorEx fR, MotorEx bL, MotorEx bR){
        drive = new MecanumDrive(fL, fR, bL, bR);
    }

    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double turnSpeedL, double turnSpeedR){

        double turnSpeedT = (-1*turnSpeedL)+turnSpeedR;
        drive.driveRobotCentric(strafeSpeed, forwardSpeed, turnSpeedT);
    }
    public void driveRobotCentricSlowMode(double strafeSpeed, double forwardSpeed, double turnSpeedL, double turnSpeedR){
        double turnSpeedT = (-1*turnSpeedL)+turnSpeedR;
        drive.driveRobotCentric(strafeSpeed / slowFactor, forwardSpeed / slowFactor, turnSpeedT / slowFactor);
    }
}
