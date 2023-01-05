package org.firstinspires.ftc.teamcode.teleop.normal;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ServoImpl;

@TeleOp(name = "Height Testing")
public class GetHeight extends OpMode{

    DcMotor slideLeft, slideRight;
    ServoImpl claw;

    double speed = 0.3;
    int TestHeight = 10; //Change for any tests, Activated by B button
    public void init () {

        slideLeft = hardwareMap.dcMotor.get("slideL");
        slideRight = hardwareMap.dcMotor.get("slideR");

        claw = hardwareMap.get(ServoImpl.class, "claw");

        //reverse this one
        slideRight.setDirection(DcMotorSimple.Direction.REVERSE);

        slideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void start () {
        slideRight.setPower(0);
        slideLeft.setPower(0);
    }

    public void loop () {


/*
Player 1
Right bumper : Close claw
Left bumper : Open claw
Dpad up : slide up
Dpad down : slide down
B : test height
A : telemetry encoder values
 */


        if(gamepad1.right_bumper){claw.setPosition(1);} //Claw Manipulation
        if(gamepad1.left_bumper){claw.setPosition(0);}

        if(gamepad1.dpad_down){
            slideRight.setPower(-speed);
            slideLeft.setPower(-speed);
        }

        else if(gamepad1.dpad_up){
            slideRight.setPower(speed);
            slideLeft.setPower(speed);
        } else if(gamepad1.b){ //Position testing
            slideRight.setTargetPosition(TestHeight);
            slideRight.setTargetPosition(TestHeight);
        } else{
            slideRight.setPower(0);
            slideLeft.setPower(0);
        }


        if(gamepad1.a){
            telemetry.addData("SlideR pos", slideRight.getCurrentPosition());
            telemetry.addData("SlideL pos", slideLeft.getCurrentPosition());
            telemetry.update();
        }


    }

}