package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.*;

@TeleOp (name = "GuptaOpMode")
public class PositionOpMode extends OpMode {

    DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight, lift;
    Servo claw;

    //TODO use GetHeight to get values
    //in ticks
    final int JUNC = 0;
    final int SHORT = 2300;
    final int MID = 3821;
    final int TALL = 4926;

    int target = 0;

    public void init () {

        motorFrontLeft = hardwareMap.dcMotor.get("lf");
        motorBackLeft = hardwareMap.dcMotor.get("lb");
        motorFrontRight = hardwareMap.dcMotor.get("rf");
        motorBackRight = hardwareMap.dcMotor.get("rb");
        lift = hardwareMap.dcMotor.get("slideMotor");

        DcMotor[] motors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight, lift};

        for(DcMotor motor : motors) {

            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        }

        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        claw = (Servo) hardwareMap.servo.get("clamp");

        telemetry.addData("init", "done");

    }


    public void loop () {

        //set target
//        if(gamepad1.dpad_up) {
//            target = TALL;
//        } else if (gamepad1.dpad_down) {
//            target = JUNC;
//        } else if (gamepad1.dpad_left) {
//            target = SHORT;
//        } else if (gamepad1.dpad_right) {
//            target = MID;
//        }
//
//        //mock up target position mechanic
//        if (lift.getCurrentPosition() > target){
//            lift.setPower(-.7);
//        }
//
//        else if (lift.getCurrentPosition() < target) {
//            lift.setPower(.7);
//        }

        if(gamepad1.left_bumper){
            lift.setPower(-0.7);
        } else if(gamepad1.right_bumper){
            lift.setPower(0.7);
        } else if (gamepad1.a){
            lift.setPower(0);
        }

        //run claw
        if(gamepad1.dpad_up){

            claw.setPosition(1);

        } else if (gamepad1.dpad_down) {

            claw.setPosition(0);

        }


        //run drive
        double y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;


        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        motorFrontLeft.setPower(frontLeftPower);
        motorBackLeft.setPower(backLeftPower);
        motorFrontRight.setPower(frontRightPower);
        motorBackRight.setPower(backRightPower);


    }

//    public void stop () {
//        telemetry.addData("OpMode", "stopping");
//
//        lift.setTargetPosition(0);
//        sleep(3000);
//
//        telemetry.addData("OpMode", "done");
//
//    }

}
