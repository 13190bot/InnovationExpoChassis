package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.subsystem.DriveSubsystem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BaseDriveOpMode extends CommandOpMode {
    protected MotorEx fL, fR, bL, bR;
    protected DcMotor slideLeft, slideRight;
    protected ServoEx claw,arm1,arm2;
    protected DriveSubsystem drive;


    protected RevIMU imu;



    @Override
    public void initialize() {
        initHardware();
        setUpHardwareDevices();
        drive = new DriveSubsystem(fL, fR, bL, bR);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Mode", "Done initializing");
        telemetry.update();
    }

    protected void initHardware() {
        fL = new MotorEx(hardwareMap, "leftFront");
        fR = new MotorEx(hardwareMap, "rightFront");
        bL = new MotorEx(hardwareMap, "leftBack");
        bR = new MotorEx(hardwareMap, "rightBack");

        imu = new RevIMU(hardwareMap);
        imu.init();

    }
    protected void setUpHardwareDevices() {
        //TODO MAKE SURE CORRECT MOTORS ARE REVERSED
        fL.setInverted(true);
        bL.setInverted(true);

    }

    @Override
    public void run() {
        super.run();
        telemetry.addData("leftFront Power", round(fL.motor.getPower()));
        telemetry.addData("leftBack Power", round(bL.motor.getPower()));
        telemetry.addData("rightFront Power", round(fR.motor.getPower()));
        telemetry.addData("rightBack Power", round(bR.motor.getPower()));

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