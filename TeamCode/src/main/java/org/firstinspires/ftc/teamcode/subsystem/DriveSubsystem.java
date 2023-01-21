package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import java.util.function.DoubleSupplier;


@Config
public class DriveSubsystem extends SubsystemBase {
    private final MecanumDrive drive;

    public static double slowModeFactor = 3;

    public DriveSubsystem(MotorEx leftBack, MotorEx leftFront, MotorEx rightBack, MotorEx rightFront){
        drive = new MecanumDrive(leftFront, rightFront, leftBack, rightBack);
    }

    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double turnSpeedL, double turnSpeedR){
        double turnSpeedT = (-1*turnSpeedL)+turnSpeedR;
        drive.driveRobotCentric(strafeSpeed * 0.8, forwardSpeed * 0.8, turnSpeedT * 0.8);
    }

    public void driveRobotCentricSlowMode(double strafeSpeed, double forwardSpeed, double turnSpeedL, double turnSpeedR) {
        double turnSpeedT = (-1*turnSpeedL)+turnSpeedR;
        drive.driveRobotCentric(strafeSpeed / slowModeFactor,
                forwardSpeed / slowModeFactor,
                turnSpeedT / slowModeFactor);
    }

//    public Command runRobotCentricCommand(DoubleSupplier strafeSpeed, DoubleSupplier forwardSpeed, DoubleSupplier turnSpeed) {
//        return new RunCommand(() -> drive.driveRobotCentric(
//                strafeSpeed.getAsDouble(), forwardSpeed.getAsDouble(), turnSpeed.getAsDouble()), this);
//    }
//
//    public Command runSlowMode(DoubleSupplier strafeSpeed, DoubleSupplier forwardSpeed, DoubleSupplier turnSpeed) {
//        return new RunCommand(() -> driveRobotCentric(strafeSpeed.getAsDouble() / slowModeFactor,
//                forwardSpeed.getAsDouble() / slowModeFactor,
//                turnSpeed.getAsDouble() / slowModeFactor)); // balls pp big balls
//
//    }
}
