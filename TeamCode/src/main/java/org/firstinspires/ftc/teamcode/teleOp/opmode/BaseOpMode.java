package org.firstinspires.ftc.teamcode.teleOp.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.teleOp.subsystem.*;
import org.firstinspires.ftc.teamcode.util.Junction;

public class BaseOpMode extends CommandOpMode {

    protected MotorEx leftBack, leftFront, rightBack, rightFront;


    protected DriveSubsystem drive;

    protected GamepadEx gamepadEx1;
    protected GamepadEx gamepadEx2;

    @Override
    public void initialize() {
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        initHardware();
        setUp();

        drive = new DriveSubsystem(leftBack, leftFront, rightBack, rightFront);
        //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Mode", "Done initializing");
        telemetry.update();
    }


    protected void initHardware(){
        leftBack = new MotorEx(hardwareMap, "backLeft");
        leftFront = new MotorEx(hardwareMap, "frontLeft");
        rightBack = new MotorEx(hardwareMap, "backRight");
        rightFront = new MotorEx(hardwareMap, "frontRight");


    }

    protected void setUp(){
        rightFront.setInverted(false);
        rightBack.setInverted(false);
        leftBack.setInverted(false);
        leftFront.setInverted(false);

        leftBack.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        /*
        rightFront.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightFront.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         */

    }

    @Override
    public void run() {
        super.run();
        tad("leftBack Power", leftBack.motor.getPower());
        tad("leftFront Power", leftFront.motor.getPower());
        tad("rightBack Power", rightBack.motor.getPower());
        tad("rightFront Power", rightFront.motor.getPower());

        tad("leftBack pos", leftBack.motor.getCurrentPosition());
        tad("leftFront pos", leftFront.motor.getCurrentPosition());
        tad("rightBack pos", rightBack.motor.getCurrentPosition());
        tad("rightFront pos", rightFront.motor.getCurrentPosition());




        telemetry.update();

    }

    // gamepad button 1 = gb1
    protected GamepadButton gb1(GamepadKeys.Button button){
        return gamepadEx1.getGamepadButton(button);
    }

    // gamepad button 2 = gb2
    protected GamepadButton gb2(GamepadKeys.Button button){
        return gamepadEx2.getGamepadButton(button);
    }

    protected void tad(String tag, Object data){
        telemetry.addData(tag, data);
    }
}
