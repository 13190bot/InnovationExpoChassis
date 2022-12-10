package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

// FOR EMERGENCY USE

@TeleOp(name = "Emergency TeleOp")
public class Teleop1 extends OpMode {

    DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;

    double driveSpeedMod = 1;

    @Override
    public void init() {
        motorFrontLeft = hardwareMap.dcMotor.get("lf");
        motorBackLeft = hardwareMap.dcMotor.get("lb");
        motorFrontRight = hardwareMap.dcMotor.get("rf");
        motorBackRight = hardwareMap.dcMotor.get("rb");
//        lift = hardwareMap.dcMotor.get("slideMotor");

        DcMotor[] motors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};

        for(DcMotor motor : motors) {

            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        }
//
//        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        double Y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double X = gamepad1.right_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_trigger;
        double lx = gamepad1.left_trigger;


        double denominator = (Math.max(Math.abs(Y) + Math.abs(X) , 1));

        double frontLeftPower = (Y + X) / denominator, backLeftPower= (Y - X) / denominator, frontRightPower = (Y - X) / denominator, backRightPower = (Y + X) / denominator;

        if(rx>0.1) {
            denominator = Math.max(Math.abs(Y) + Math.abs(X) + Math.abs(rx), 1);
            frontLeftPower = (Y + X + rx) / denominator;
            backLeftPower = (Y - X + rx) / denominator;
            frontRightPower = (Y - X - rx) / denominator;
            backRightPower = (Y + X - rx) / denominator;
        }

        if(lx>0.1) {
            denominator = Math.max(Math.abs(Y) + Math.abs(X) + Math.abs(lx), 1);
            frontLeftPower = (Y + X - lx) / denominator;
            backLeftPower = (Y - X - lx) / denominator;
            frontRightPower = (Y - X + lx) / denominator;
            backRightPower = (Y + X + lx) / denominator;
        }

        motorFrontLeft.setPower(frontLeftPower);
        motorBackLeft.setPower(backLeftPower);
        motorFrontRight.setPower(frontRightPower);
        motorBackRight.setPower(backRightPower);



    }
}
