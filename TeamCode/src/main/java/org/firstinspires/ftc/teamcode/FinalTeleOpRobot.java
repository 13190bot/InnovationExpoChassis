package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name = "Final TeleOp Robot Centric")
public class FinalTeleOpRobot extends OpMode {

    DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight, lift;
    Servo claw;

    float driveSpeedMod = 1;

    //TODO use GetHeight to get values (after comp)
    //in ticks
    final int JUNC = 0;
    final int SHORT = 2300;
    final int MID = 3821;
    final int TALL = 4926;
    int speed_change  = 1;

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

        // Retrieve the IMU from the hardware map


        telemetry.addData("init", "done");

    }


    public void loop () {
//TODO worry about this after comp
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


        if (gamepad1.left_bumper) {
            driveSpeedMod = .5F;
        } else {
            driveSpeedMod = 1;
        }

        if(gamepad2.left_bumper){
            lift.setPower(-0.8);
        } else if(gamepad2.right_bumper){
            lift.setPower(0.8);
        } else if (!gamepad2.left_bumper && !gamepad2.right_bumper){
            lift.setPower(0);
        }

        //run claw
        if(gamepad2.dpad_up){

            claw.setPosition(0.75);

        } else if (gamepad2.dpad_down) {

            claw.setPosition(0.5);

        }


        //run drive
        double Y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double X = gamepad1.right_stick_x * 2.2; // Counteract imperfect strafing
        double rx = gamepad1.right_trigger;
        double lx = gamepad1.left_trigger;
        


        // Read inverse IMU heading, as the IMU heading is CW positive

        if(gamepad1.left_trigger > 0.6){
            speed_change = 2;
        } 
        else {
            speed_change = 1;
        }
        
        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        //TODO please test
        double denominator = speed_change*(Math.max(Math.abs(Y) + Math.abs(X) , 1));

        double frontLeftPower = (Y + X ) / denominator, backLeftPower= (Y - X ) / denominator, frontRightPower = (Y - X ) / denominator, backRightPower = (Y + X ) / denominator;

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
        motorFrontLeft.setPower(frontLeftPower * driveSpeedMod);
        motorBackLeft.setPower(backLeftPower * driveSpeedMod);
        motorFrontRight.setPower(frontRightPower * driveSpeedMod);
        motorBackRight.setPower(backRightPower * driveSpeedMod);



    }



}
