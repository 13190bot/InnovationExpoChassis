package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.autonomous.vision.SleeveDetection;
import org.openftc.easyopencv.OpenCvCamera;
//import com.acmerobotics.roadrunner.drive.DriveConstraints;


// 517 ticks per rotation, 316 RPM

@Deprecated
@Autonomous(name = "preloadAuto_WIP")
public class OLDpreloadAuto extends OpMode{
    DcMotor lf, lb, rf, rb;
    SleeveDetection sleeveDetection;
    OpenCvCamera camera;

    int forwardDistance = 12; // inches (thru roadrunner)

    private static final int long_timer = 3000;

    private static final double DRIVE_POWER = 0.5;
    private static final double STRAFE_POWER = 0.5;

    String webcamName = "Webcam 1";


    @Override
    public void init() {
        lf = hardwareMap.dcMotor.get("frontLeft");
        lb = hardwareMap.dcMotor.get("backLeft");
        rf = hardwareMap.dcMotor.get("frontRight");
        rb = hardwareMap.dcMotor.get("backRight");

        DcMotor[] motors = {lf, lb, rf, rb};

        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);


        telemetry.addData("init", "done");




    }

    @Override
    public void start() {
        // Move forward certain distance (use roadrunner for this)
        // Lift claw (preloaded with cone)
        // get cone on pole and then release claw

    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {
        stopMotors();
    }

    private void stopMotors() {
        telemetry.addData("Stopping Motors", "Stopping motors");
        telemetry.update();
        lf.setPower(0);
        lb.setPower(0);
        rf.setPower(0);
        rb.setPower(0);
    }
}
