package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

public class GetHeight extends OpMode{

    DcMotor lift;

    public void init () {

        lift = hardwareMap.dcMotor.get("slideMotor");
        lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void start () {

        lift.setPower(.2);

    }

    public void loop () {

        if(gamepad1.a){
            lift.setPower(0);
        }

        telemetry.addData("lift pos", lift::getCurrentPosition);

    }

}