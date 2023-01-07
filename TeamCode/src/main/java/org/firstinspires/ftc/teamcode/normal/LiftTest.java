package org.firstinspires.ftc.teamcode.normal;

import android.util.Log;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Manual Lift Opmode")
public class LiftTest extends OpMode {

    private DcMotorEx slideL, slideR;


    @Override
    public void init() {
        Log.d("test", "e");
        slideL = hardwareMap.get(DcMotorEx.class, "slideL");
        slideR = hardwareMap.get(DcMotorEx.class, "slideR");

        slideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        slideL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("init", "complete");
        telemetry.update();

        slideR.setDirection(DcMotorSimple.Direction.REVERSE);

    }


    @Override
    public void loop() {
        Log.d("loop","yes");
        slideL.setPower(gamepad1.right_stick_y);
        slideR.setPower(gamepad1.right_stick_y);
        telemetry.addData("Left Encoder", slideL.getCurrentPosition());
        telemetry.addData("Right Encoder", slideR.getCurrentPosition());
        telemetry.update();
    }
}
