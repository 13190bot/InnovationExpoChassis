package org.firstinspires.ftc.teamcode.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public class DriveTestSubsystem extends SubsystemBase {

    public double slowFactor = 2.5;
    public double balls = -1;
    private final MecanumDrive drive;

    //constructor for a MecanumDrive
    public DriveTestSubsystem(MotorEx fL, MotorEx fR, MotorEx bL, MotorEx bR){
        drive = new MecanumDrive(fL, fR, bL, bR);
    }

    //remember strafeSpeed and forwardSpeed are reversed here, compared to actual drive subsystem
    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double turnSpeed){
        drive.driveRobotCentric(strafeSpeed/balls, forwardSpeed/balls, turnSpeed);
    }

    public void driveRobotCentricSlowMode(double strafeSpeed, double forwardSpeed, double turnSpeed){
        drive.driveRobotCentric(strafeSpeed / slowFactor *balls ,
                              forwardSpeed / slowFactor* balls,
                                   turnSpeed / slowFactor);
    }

}
