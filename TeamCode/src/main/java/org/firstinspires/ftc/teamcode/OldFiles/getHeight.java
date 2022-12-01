package org.firstinspires.ftc.teamcode.OldFiles;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "getHeight")
public class getHeight extends OpMode{

    DcMotor lift;

    public void init () {

        lift = hardwareMap.dcMotor.get("slideMotor");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void start () {

        lift.setPower(0);

    }

    public void loop () {

        if(gamepad1.left_bumper){
            lift.setPower(0.7);
        }

        if(gamepad1.right_bumper){
            lift.setPower(-0.7);
        }

        if(gamepad1.a){
            lift.setPower(0);
        }

//        telemetry.addData("lift pos", lift::getCurrentPosition);

    }

}