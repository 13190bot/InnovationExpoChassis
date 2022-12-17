package org.firstinspires.ftc.teamcode.opmode.baseOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.subsystem.DriveSubsystem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BaseDriveOpMode extends CommandOpMode {
    protected MotorEx fL, fR, bL, bR;
    protected DriveSubsystem drive;

    @Override
    public void initialize() {
        initHardware();

        drive = new DriveSubsystem(fL, fR, bL, bR);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Mode", "Done initializing");
        telemetry.update();
    }

    protected void initHardware() {
        fL = new MotorEx(hardwareMap, "frontLeft");
        fR = new MotorEx(hardwareMap, "frontRight");
        bL = new MotorEx(hardwareMap, "backLeft");
        bR = new MotorEx(hardwareMap, "backRight");

        //Motor Reversal
        bR.setInverted(true);

        //ask whether or not we should use this (8872 are hypocrites if they tell us not to use this)
        fL.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        fR.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        bL.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        MotorEx[] motors = new MotorEx[]{fL, fR, bL, bR};

        for(MotorEx motor_ : motors) {
            motor_.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor_.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor_.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        }

    }
    @Override
    public void run() {
        super.run();
        telemetry.addData("leftFront Power", round(fL.motor.getPower()));
        telemetry.addData("leftFront pos", fL.motor.getCurrentPosition());

        telemetry.addData("leftBack Power", round(bL.motor.getPower()));
        telemetry.addData("leftBack pos", bL.motor.getCurrentPosition());

        telemetry.addData("rightFront Power", round(fR.motor.getPower()));
        telemetry.addData("rightFront pos", fR.motor.getCurrentPosition());

        telemetry.addData("rightBack Power", round(bR.motor.getPower()));
        telemetry.addData("rightBack pos", bR.motor.getCurrentPosition());


        telemetry.update();
    }

    private static double round(double value, @SuppressWarnings("SameParameterValue") int places) {
        if (places < 0) throw new IllegalArgumentException();
        return new BigDecimal(Double.toString(value)).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

    private static double round(double value) {
        return round(value, 4);
    }


}