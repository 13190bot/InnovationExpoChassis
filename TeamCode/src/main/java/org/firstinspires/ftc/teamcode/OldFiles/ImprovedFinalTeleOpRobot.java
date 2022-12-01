package org.firstinspires.ftc.teamcode.OldFiles;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name = "Final TeleOp Robot Centric")
public class ImprovedFinalTeleOpRobot extends OpMode {

    int liftState = 0; // 0: not moving, 1: going up, 2: going down

    boolean clawState = false;
    DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight, lift;
    Servo claw;

//    double driveSpeedMod = 1;

    //TODO use GetHeight to get values (after comp)
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

        }

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        claw = (Servo) hardwareMap.servo.get("clamp");

        // Retrieve the IMU from the hardware map


        telemetry.addData("init", "done");

    }


    public void loop () {
//TODO worry about this after comp
        //set target
        boolean setTarget = true;
        if(gamepad1.dpad_up) {
            target = TALL;
        } else if (gamepad1.dpad_down) {
            target = JUNC;
        } else if (gamepad1.dpad_left) {
            target = SHORT;
        } else if (gamepad1.dpad_right) {
            target = MID;
        } else {
            setTarget = false;
        }

        if (setTarget) {
            if (lift.getCurrentPosition() > target) { // lift is above target, go down
                liftState = 2;
            } else {
                liftState = 1;
            }
        }



        //mock up target position mechanic
        if ((liftState == 1) && (lift.getCurrentPosition() > target)) {
            lift.setPower(-.7);
        }

        else if ((liftState == 2) && (lift.getCurrentPosition() < target)) {
            lift.setPower(.7);
        }


        /*
        // old manual lift
        if(gamepad2.dpad_down ){
            lift.setPower(-0.8);
        } else if(gamepad2.dpad_up){
            lift.setPower(0.8);
        } else{
            lift.setPower(0);
        }
        */

        //run claw
        if(gamepad2.right_bumper) {

            claw.setPosition(0.25);

        } else if (gamepad2.left_bumper) {

            claw.setPosition(0.75);

        }


        //run drive
        double Y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double X = gamepad1.right_stick_x; // Counteract imperfect strafing
        double rx = gamepad1.right_trigger;
        double lx = gamepad1.left_trigger;



        // Read inverse IMU heading, as the IMU heading is CW positive

//        if(gamepad1.left_trigger > 0.6){
//            speed_change = 2;
//        }
//        else {
//            speed_change = 1;
//        }

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        //TODO please test
        double denominator = (Math.max(Math.abs(Y) + Math.abs(X) , 1));

        double frontLeftPower = (Y + X ) / denominator, backLeftPower= (Y - X ) / denominator, frontRightPower = (Y - X ) / denominator, backRightPower = (Y + X ) / denominator;

        double slowConstant = 3;

        if(rx>0.3) {
//            denominator = Math.max(Math.abs(Y) + Math.abs(X) + Math.abs(rx), 1);
//            frontLeftPower = (Y + X + rx) / denominator;
//            backLeftPower = (Y - X + rx) / denominator;
//            frontRightPower = (Y - X - rx) / denominator;
//            backRightPower = (Y + X - rx) / denominator;

            frontLeftPower = backLeftPower = 0.5 * slowConstant;
            frontRightPower = backRightPower = -0.5 * slowConstant;
        }
        if(lx>0.3) {
//            denominator = Math.max(Math.abs(Y) + Math.abs(X) + Math.abs(lx), 1);
//            frontLeftPower = (Y + X - lx) / denominator;
//            backLeftPower = (Y - X - lx) / denominator;
//            frontRightPower = (Y - X + lx) / denominator;
//            backRightPower = (Y + X + lx) / denominator;

            frontLeftPower = backLeftPower = -0.5 * slowConstant;
            frontRightPower = backRightPower = 0.5 * slowConstant;
        }

//        if (gamepad1.right_bumper) {
//            frontLeftPower = frontLeftPower / 2;
//            backLeftPower = backLeftPower / 2;
//            backRightPower = backRightPower / 2;
//            frontRightPower = frontRightPower / 2;
//        } else if (gamepad1.left_bumper) {
//            frontLeftPower = frontLeftPower * 1;
//            backLeftPower = backLeftPower * 1;
//            backRightPower = backRightPower * 1;
//            frontRightPower = frontRightPower * 1;

//        boolean slowState = false;

//        if (gamepad1.left_bumper) {
//            slowState = true;
//            if (slowState) {
//                frontLeftPower /= 4;
//                frontRightPower /= 4;
//                backLeftPower /= 4;
//                backRightPower /= 4;
//            } else {
//                frontLeftPower *= 1;
//                frontRightPower *= 1;
//                backLeftPower *= 1;
//                backRightPower *= 1;
//                slowState = false;
//            }
//
//        }

//

        motorFrontLeft.setPower(frontLeftPower / slowConstant);
        motorBackLeft.setPower(backLeftPower / slowConstant);
        motorFrontRight.setPower(frontRightPower / slowConstant);
        motorBackRight.setPower(backRightPower / slowConstant);



    }



}
