package xd.qwerkyquinoa.ftc;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name = "Final TeleOp Robot Centric")
public class FinalTeleOpRobot extends OpMode {

    DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight, lift;
    Servo claw;

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


        if(gamepad1.left_bumper){
            lift.setPower(-0.8);
        } else if(gamepad1.right_bumper){
            lift.setPower(0.8);
        } else if (!gamepad1.left_bumper && !gamepad1.right_bumper){
            lift.setPower(0);
        }

        //run claw
        if(gamepad1.dpad_up){

            claw.setPosition(1);

        } else if (gamepad1.dpad_down) {

            claw.setPosition(0);

        }


        //run drive
        double Y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double X = gamepad1.right_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_trigger;
        double lx = gamepad1.left_trigger;

        // Read inverse IMU heading, as the IMU heading is CW positive



        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        //TODO please test
        double denominator = Math.max(Math.abs(Y) + Math.abs(X) , 1);

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
