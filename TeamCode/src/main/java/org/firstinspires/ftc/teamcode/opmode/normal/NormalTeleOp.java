package org.firstinspires.ftc.teamcode.opmode.normal;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp (name = "Normie TeleOp")
public class NormalTeleOp extends OpMode {

    DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;
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
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection((DcMotorSimple.Direction.REVERSE));

        telemetry.addData("init", "done");

    }
    public void loop () {

        //run drive
        double Y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double X = gamepad1.right_stick_x; // Counteract imperfect strafing
        double rx = gamepad1.right_trigger;
        double lx = gamepad1.left_trigger;

        /*
        a -> slowmode implemented with just multiplying power
        b -> slowmode implemented with clipping joystick output
         */


        boolean SLOWMODE = gamepad1.a;
        double mul = SLOWMODE ? 0.5 : 1;

        boolean SLOWMODE2 = gamepad1.b;

        if (SLOWMODE2) {
            X = Range.clip(X, -.5, .5);
            Y = Range.clip(Y, -.5, .5);
        }



        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = (Math.max(Math.abs(Y) + Math.abs(X) , 1));

        double frontLeftPower = (Y + X ) / denominator, backLeftPower= (Y - X ) / denominator, frontRightPower = (Y - X ) / denominator, backRightPower = (Y + X ) / denominator;

        if(rx>0.3) {
            frontLeftPower = backLeftPower = 0.5;
            frontRightPower = backRightPower = -0.5;
        }
        if(lx>0.3) {

            frontLeftPower = backLeftPower = -0.5;
            frontRightPower = backRightPower = 0.5;
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