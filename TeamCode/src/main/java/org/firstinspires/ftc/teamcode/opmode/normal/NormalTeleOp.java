package org.firstinspires.ftc.teamcode.opmode.normal;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;

@TeleOp (name = "Normie TeleOp")
public class NormalTeleOp extends OpMode {

    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackLeft;
    DcMotor motorBackRight;
    static DcMotor slideL;
    static DcMotor slideR;
    ServoEx claw;
    double mul = 1;
    double turnSpeed = 0.5;
    double slideSpeed = 0.7;
    public void init () {
        motorFrontLeft = hardwareMap.dcMotor.get("leftFront");
        motorBackLeft = hardwareMap.dcMotor.get("leftBack");
        motorFrontRight = hardwareMap.dcMotor.get("rightFront");
        motorBackRight = hardwareMap.dcMotor.get("rightBack");

        DcMotor[] motors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};

        for(DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        //TODO correct motors reversed?
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);



        slideL = hardwareMap.dcMotor.get("slideL");
        slideR = hardwareMap.dcMotor.get("slideR");

        DcMotor[] slides = {slideL, slideR};
        for(DcMotor slide : slides) {
            slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }


        telemetry.addData("init", "done");

    }
    public void loop () {

        //claw manip
        if(gamepad2.right_bumper){claw.setPosition(1);}
        if(gamepad2.left_bumper){claw.setPosition(0);}

        //lift gonna be manual (if parteek want set junctions, he can write them himself)
        if(gamepad2.dpad_up){
            slideR.setPower(slideSpeed);
            slideL.setPower(slideSpeed);
        }
        if(gamepad2.dpad_down){
            slideR.setPower(-slideSpeed);
            slideL.setPower(-slideSpeed);
        }

        //run drive
        double Y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double X = gamepad1.right_stick_x; // Counteract imperfect strafing
        double rx = gamepad1.right_trigger;
        double lx = gamepad1.left_trigger;

        boolean slowTime = gamepad1.left_bumper;

        if (slowTime) {
            if (mul == 0.5) {
                mul = 1;
            } else {
                mul = 0.5;
            }
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

        motorFrontLeft.setPower(frontLeftPower);
        motorBackLeft.setPower(backLeftPower);
        motorFrontRight.setPower(frontRightPower);
        motorBackRight.setPower(backRightPower);

    }

}