package org.firstinspires.ftc.teamcode.opmode.normal;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ServoImpl;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;

@TeleOp (name = "Normie TeleOp with Lift Run to Pos")
public class NormalTeleOpWithRunToPosLift extends OpMode {

    //hardware
    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackLeft;
    DcMotor motorBackRight;
    static DcMotor slideL;
    static DcMotor slideR;
    ServoImpl claw;

    // slow mode
    double mul = 1;

    double turnSpeed = 0.5;
    double slideSpeed = 0.2;

    // junctions
    final int TALL = ArmSubsystem.HIGH;
    final int MED = ArmSubsystem.MEDIUM;
    final int LOW = ArmSubsystem.LOW;

    public void init () {
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorBackRight = hardwareMap.dcMotor.get("backRight");
        slideL = hardwareMap.dcMotor.get("slideL");


        DcMotor[] motors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight, slideL};

        for(DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        //TODO correct motors reversed?
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);



        slideR = hardwareMap.dcMotor.get("slideR");


        slideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideR.setTargetPosition(0);
        slideR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
        if (gamepad2.left_trigger > .5) {
            slideR.setTargetPosition(slideR.getCurrentPosition() - 20);
        } else if (gamepad2.right_trigger > .5) {
            slideR.setTargetPosition(slideR.getCurrentPosition() + 20);
        } else if (gamepad2.dpad_up) {
            slideR.setTargetPosition(TALL);
        } else if (gamepad2.dpad_left){
            slideR.setTargetPosition(MED);
        } else if (gamepad2.dpad_right){
            slideR.setTargetPosition(LOW);
        } else if (gamepad2.dpad_down){
            slideR.setTargetPosition(0);
        } else {
            slideR.setTargetPosition(slideR.getCurrentPosition());
        }

        slideL.setPower( slideR.getPower() );

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