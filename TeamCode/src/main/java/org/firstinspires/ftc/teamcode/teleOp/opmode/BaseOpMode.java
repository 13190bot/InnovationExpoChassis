package org.firstinspires.ftc.teamcode.teleOp.opmode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.hardware.lynx.LynxModule;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.teleOp.subsystem.*;

public class BaseOpMode extends CommandOpMode {

    protected MotorEx leftBack, leftFront, rightBack, rightFront;

    private double loopTime = 0;

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
        telemetry.addData("Mode", "Done initializing");
        telemetry.update();

        PhotonCore.CONTROL_HUB.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        PhotonCore.experimental.setMaximumParallelCommands(4);
        PhotonCore.enable();
    }
    protected void initHardware(){
        leftBack = new MotorEx(hardwareMap, "backLeft");
        leftFront = new MotorEx(hardwareMap, "frontLeft");
        rightBack = new MotorEx(hardwareMap, "backRight");
        rightFront = new MotorEx(hardwareMap, "frontRight");


    }
    protected void setUp(){
        leftBack.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
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
        //speed the refresh rate of a loop from 1000 to 50000
        PhotonCore.CONTROL_HUB.clearBulkCache();
        //write the loop time to the telemetry
        double loop = System.nanoTime();
        tad("Loop Time", 1000000000 / (loop - loopTime));
        loopTime = loop;
    }
    // gamepad button 1 = gb1
    protected GamepadButton gb1(GamepadKeys.Button button){
        return gamepadEx1.getGamepadButton(button);
    }
    protected void tad(String tag, Object data){
        telemetry.addData(tag, data);
    }
}
