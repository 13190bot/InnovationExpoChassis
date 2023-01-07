package org.firstinspires.ftc.teamcode.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

@TeleOp (name = "Wheels Test")
public class WheelsTest extends OpMode {

    DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;
//    static DcMotor slideL;
//    static DcMotor slideR;
//    ServoImpl claw;

//    // slowmode
//    double mul = 1;
//
//
//    double turnSpeed = 0.5;
//    double slideSpeed = 0.2;
//
//    // junctions
//    double target = 0;
//    boolean goingUp = false;
//    boolean liftMoving = false;
//
//
//    int mediumJunct = 10; //Change these values
//    int smallJunct = 10; //Change these values

    public void init () {
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorBackRight = hardwareMap.dcMotor.get("backRight");


        DcMotor[] motors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};

        for(DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);



//        slideL = hardwareMap.dcMotor.get("slideL");
//        slideR = hardwareMap.dcMotor.get("slideR");
//
//        DcMotor[] slides = {slideL, slideR};
//        for(DcMotor slide : slides) {
//            slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        }
//
//        slideR.setPower(0);
//        slideL.setPower(0);
//
//        claw = hardwareMap.get(ServoImpl.class, "claw");
//
//
//        telemetry.addData("init", "done");

    }
    public void loop () {

        //claw manipulation
//        if(gamepad2.right_bumper){claw.setPosition(1);}
//        if(gamepad2.left_bumper){claw.setPosition(0);}


        //Presets
        if (gamepad2.y) { //Medium junction

            motorFrontLeft.setPower(1);
            motorBackLeft.setPower(1);
            motorFrontRight.setPower(1);
            motorBackRight.setPower(1);

        } else {
            motorFrontLeft.setPower(0);
            motorBackLeft.setPower(0);
            motorFrontRight.setPower(0);
            motorBackRight.setPower(0);
        }

    }

//    private static void moveSlide(int amount) {
//        slideL.setTargetPosition(slideL.getCurrentPosition() + amount);
//        slideR.setTargetPosition(slideR.getCurrentPosition() + amount);
//    }
//
//    private static void slideTarget(int pos) {
//        slideL.setTargetPosition(pos);
//        slideR.setTargetPosition(pos);
//    }

}