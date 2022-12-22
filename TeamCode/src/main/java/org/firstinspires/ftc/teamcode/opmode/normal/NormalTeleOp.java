package org.firstinspires.ftc.teamcode.opmode.normal;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp (name = "Normie TeleOp")
public class NormalTeleOp extends OpMode {

    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackLeft;
    DcMotor motorBackRight;
    static DcMotor slideL;
    static DcMotor slideR;
    ServoImpl claw;

    // slowmode
    double mul = 1;


    double turnSpeed = 0.5;
    double slideSpeed = 0.2;

    // junctions
    double target = 0;
    boolean goingUp = false;
    boolean liftMoving = false;


    int mediumJunct = 10; //Change these values
    int smallJunct = 10; //Change these values

    public void init () {
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorBackRight = hardwareMap.dcMotor.get("backRight");


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

        //claw manipulation
        if(gamepad2.right_bumper){claw.setPosition(1);}
        if(gamepad2.left_bumper){claw.setPosition(0);}


        //Presets
        if (gamepad2.y) { //Medium junction
            slideTarget(mediumJunct);
        } else if (gamepad2.x) { //Small junction
            slideTarget(smallJunct);
        }

        
        if (gamepad2.dpad_down) {
            moveSlide(-10);
        } else if (gamepad2.dpad_up){
            moveSlide(+10);
        }
        else {
            moveSlide(0); //Keeps at same pos
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

    private static void moveSlide(int amount) {
        slideL.setTargetPosition(slideL.getCurrentPosition() + amount);
        slideR.setTargetPosition(slideR.getCurrentPosition() + amount);
    }

    private static void slideTarget(int pos) {
        slideL.setTargetPosition(pos);
        slideR.setTargetPosition(pos);
    }

}