package org.firstinspires.ftc.teamcode.testing;

import android.util.Log;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Slide Tuner", group = "Testing")
public class slideTuner extends OpMode {

    private DcMotorEx slideL, slideR;

    private final int testHeight = 0;

    @Override
    public void init() {
        Log.d("init", "begin");
        slideL = hardwareMap.get(DcMotorEx.class, "slideL");
        slideR = hardwareMap.get(DcMotorEx.class, "slideR");

        slideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        slideL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slideR.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("init", "complete");
        telemetry.update();

    }


    @Override
    public void loop() {
        Log.d("loop","yes");

        if(gamepad1.right_stick_y>0.3) {
            slideL.setPower(gamepad1.right_stick_y);
            slideR.setPower(gamepad1.right_stick_y);
        }
        else if(gamepad1.a){
            slideL.setTargetPosition(testHeight);
            slideR.setTargetPosition(testHeight);
        }
        else{
            slideL.setPower(0);
            slideR.setPower(0);
        }



        telemetry.addData("Left Encoder", slideL.getCurrentPosition());
        telemetry.addData("Right Encoder", slideR.getCurrentPosition());
        telemetry.update();
    }
}
