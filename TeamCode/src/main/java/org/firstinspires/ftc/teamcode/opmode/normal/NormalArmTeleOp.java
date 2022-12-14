package org.firstinspires.ftc.teamcode.opmode.normal;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp (name = "Normie Arm TeleOp")
public class NormalArmTeleOp extends OpMode {

    static DcMotor slideL;
    static DcMotor slideR;
    ServoEx claw;

    double slideSpeed = 0.5;
    public void init () {

        slideL = hardwareMap.dcMotor.get("slideL");
        slideR = hardwareMap.dcMotor.get("slideR");

        DcMotor[] slides = {slideL, slideR};
        for(DcMotor slide : slides) {
            slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }


        telemetry.addData("init", "done");

    }
    public void loop () {

        //claw manip
        if(gamepad2.right_bumper){claw.setPosition(1);}
        if(gamepad2.left_bumper){claw.setPosition(0);}

        //lift gonna be manual (if parteek want set junctions, he can write them himself)
        if(gamepad2.dpad_up){
            slideR.setPower(slideSpeed);
            slideL.setPower(slideSpeed);
        }
        if(gamepad2.dpad_down){
            slideR.setPower(-slideSpeed);
            slideL.setPower(-slideSpeed);
        }

    }

}