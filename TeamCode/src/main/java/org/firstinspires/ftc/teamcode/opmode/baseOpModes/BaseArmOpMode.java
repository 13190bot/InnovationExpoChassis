package org.firstinspires.ftc.teamcode.opmode.baseOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;

public class BaseArmOpMode extends CommandOpMode {

    protected MotorEx slideLeft, slideRight;
    protected ServoEx arm1, arm2, claw;
    protected ArmSubsystem arm;

    @Override
    public void initialize() {
        initHardware();
        setUpHardwareDevices();
        arm = new ArmSubsystem(claw, slideLeft, slideRight);


        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Mode", "Done initializing");
        telemetry.update();
    }

    protected void initHardware() {

        slideLeft = new MotorEx(hardwareMap, "slideL");
        slideRight = new MotorEx(hardwareMap, "slideR");

        //TODO find min and max
        claw = new SimpleServo(hardwareMap, "claw", 0, 120);

    }
    protected void setUpHardwareDevices() {
        slideLeft.resetEncoder();
        slideRight.resetEncoder();

        //TODO make sure correct motor is reversed
        slideRight.setInverted(true);

        slideLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        slideRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void run() {
        super.run();
        telemetry.addData("Left Slide Encoder", arm.getSlideLEncoder());
        telemetry.addData("Right Slide Encoder", arm.getSlideREncoder());
        telemetry.addData("Left Slide Power", arm.getSlideLPower());
        telemetry.addData("Right Slide Power", arm.getSlideRPower());
        telemetry.addData("Left Slide Error", arm.getSlideLError());
        telemetry.addData("Right Slide Error", arm.getSlideRError());
        telemetry.update();
    }


}