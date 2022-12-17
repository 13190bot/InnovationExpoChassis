package org.firstinspires.ftc.teamcode.opmode.normal;

import android.util.Log;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.ServoImpl;

@TeleOp (name = "Normie TeleOp")
public class NormalTeleOp extends OpMode {

    //define motors
    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackLeft;
    DcMotor motorBackRight;
    DcMotor slideL;
    DcMotor slideR;
    ServoImpl claw;
    boolean clawState;
    // slowmode
    double mul = 1;


    double turnSpeed = 0.5;

    // junctions
    double target = 0;
    boolean goingUp = false;
    boolean liftMoving = false;



    public void init () {
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorBackRight = hardwareMap.dcMotor.get("backRight");

        slideL = hardwareMap.get(DcMotorEx.class, "slideL");
        slideR = hardwareMap.get(DcMotorEx.class, "slideR");

        DcMotor[] motors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight, slideL, slideR};
        for(DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        slideR.setDirection(DcMotorSimple.Direction.REVERSE);

        claw = hardwareMap.get(ServoImpl.class, "claw");

        telemetry.addData("init", "done");
    }

    public void loop () {

        //arm
        if(gamepad2.right_bumper){claw.setPosition(1);}
        if(gamepad2.left_bumper){claw.setPosition(.5);}
        telemetry.addData("claw", claw.getPosition());

        //double slideSpeed = gamepad2.dpad_up ? .5 : (gamepad2.dpad_down ? -.5 : 0) ;

            slideL.setPower(gamepad2.right_stick_y * .5);
            slideR.setPower(gamepad2.right_stick_y * .5);
            telemetry.addData("Left Encoder", slideL.getCurrentPosition());
            telemetry.addData("Right Encoder", slideR.getCurrentPosition());
            telemetry.update();


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