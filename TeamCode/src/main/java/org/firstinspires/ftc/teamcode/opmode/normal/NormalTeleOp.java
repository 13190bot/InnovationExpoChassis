package org.firstinspires.ftc.teamcode.opmode.normal;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImpl;

@TeleOp (name = "Normie TeleOp")
public class NormalTeleOp extends OpMode {

    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackLeft;
    DcMotor motorBackRight;
    static DcMotor slideL;
    static DcMotor slideR;
    ServoImpl claw;
    static Servo V4b1;
    static Servo V4b2;

    // slowmode
    double mul = 1;


    double turnSpeed = 0.5;
    double slideSpeed = 0.2;

    // junctions
    double target = 0;
    boolean goingUp = false;
    boolean liftMoving = false;


    public void init () {
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorBackRight = hardwareMap.dcMotor.get("backRight");

        V4b1 = hardwareMap.get(Servo.class, "servo_name"); //(Set "servo_name" as it appears in config)
        V4b2 = hardwareMap.get(Servo.class, "servo_name"); //(Set "servo_name" as it appears in config)

        DcMotor[] motors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};

        for(DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        //TODO correct motors reversed?
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);



        slideL = hardwareMap.dcMotor.get("slideL");
        slideR = hardwareMap.dcMotor.get("slideR");

        DcMotor[] slides = {slideL, slideR};
        for(DcMotor slide : slides) {
            slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        slideR.setPower(slideSpeed);
        slideL.setPower(slideSpeed);

        claw = hardwareMap.get(ServoImpl.class, "claw");


        telemetry.addData("init", "done");

    }
    public void loop () {

        //claw manip
        if(gamepad2.right_bumper){claw.setPosition(1);}
        if(gamepad2.left_bumper){claw.setPosition(0);}

        //DR4b Servos
        if (gamepad2.dpad_left) {
            V4b1.setPosition(1);
            V4b2.setPosition(0);
        }

    //(might want to modify these values)
        if(gamepad2.dpad_right)

    {

        V4b1.setPosition(0);
        V4b2.setPosition(1);

    }

        //lift gonna be manual (if parteek want set junctions, he can write them himself)
        if(gamepad2.dpad_up){
            slideL.setTargetPosition(slideL.getCurrentPosition() + 10);
            slideR.setTargetPosition(slideR.getCurrentPosition() + 10);
        }
        if(gamepad2.dpad_down){
            slideL.setTargetPosition(slideL.getCurrentPosition() - 10);
            slideR.setTargetPosition(slideR.getCurrentPosition() - 10);
        }
        else {
            slideR.setTargetPosition(slideR.getCurrentPosition());
            slideL.setTargetPosition(slideL.getCurrentPosition());
        }

        //run drive
        double Y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double X = gamepad1.right_stick_x; // Counteract imperfect strafing
        double rx = gamepad1.right_trigger;
        double lx = gamepad1.left_trigger;

        boolean slowTime = gamepad1.left_bumper;


        if (slowTime) {
            mul = 0.5;
        } else {
            mul = 1;
        }


        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = (Math.max(Math.abs(Y) + Math.abs(X) , 1));

        double frontLeftPower = (Y + X ) / denominator, backLeftPower= (Y - X ) / denominator, frontRightPower = (Y - X ) / denominator, backRightPower = (Y + X ) / denominator;

        if(rx>0.3) {
            frontLeftPower = backLeftPower = turnSpeed;
            frontRightPower = backRightPower = -turnSpeed;
        }
        if(lx>0.3) {

            frontLeftPower = backLeftPower = -turnSpeed;
            frontRightPower = backRightPower = turnSpeed;
        }

        frontLeftPower *= mul;
        backLeftPower *= mul;
        frontRightPower *= mul;
        backRightPower *= mul;

        motorFrontLeft.setPower(-frontLeftPower);
        motorBackLeft.setPower(-backLeftPower);
        motorFrontRight.setPower(-frontRightPower);
        motorBackRight.setPower(-backRightPower);

    }

}