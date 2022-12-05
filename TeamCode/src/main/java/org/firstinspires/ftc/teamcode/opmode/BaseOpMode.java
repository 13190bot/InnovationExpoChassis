package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.DriveSubsystem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BaseOpMode extends CommandOpMode {
    protected MotorEx fL, fR, bL, bR;
    protected DcMotor slideLeft, slideRight;
    protected ServoEx claw,arm1,arm2;
    protected DriveSubsystem drive;
    protected ArmSubsystem arm;

    protected RevIMU imu;



    @Override
    public void initialize() {
        initHardware();
        setUpHardwareDevices();
        drive = new DriveSubsystem(fL, fR, bL, bR);
        arm = new ArmSubsystem(claw, arm1, arm2, slideLeft, slideRight);


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

        slideLeft = hardwareMap.dcMotor.get("slideL");
        slideRight = hardwareMap.dcMotor.get("slideR");

        // what the proper min and max?
        claw = new SimpleServo(hardwareMap, "claw", 0, 120);
        arm1 = new SimpleServo(hardwareMap, "arm1", 0, 120);
        arm2 = new SimpleServo(hardwareMap, "arm2", 0, 120);
//        slide2 = new SimpleServo(hardwareMap, "slide2", 0, 120);

    }

    @Override
    public void run() {
        super.run();
        telemetry.addData("leftFront Power", round(fL.motor.getPower()));
        telemetry.addData("leftBack Power", round(bL.motor.getPower()));
        telemetry.addData("rightFront Power", round(fR.motor.getPower()));
        telemetry.addData("rightBack Power", round(bR.motor.getPower()));




        telemetry.addData("claw Position", claw.getPosition());
        telemetry.addData("slide1 Position", claw.getPosition());

        telemetry.update();
    }

    protected void setUpHardwareDevices() {
        // reverse motors
        //fR.setInverted(true);
        //bR.setInverted(true);
        fL.setInverted(true);
        bL.setInverted(true);

    }


    private static double round(double value, @SuppressWarnings("SameParameterValue") int places) {
        if (places < 0) throw new IllegalArgumentException();

        return new BigDecimal(Double.toString(value)).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

    private static double round(double value) {
        return round(value, 4);
    }


}
