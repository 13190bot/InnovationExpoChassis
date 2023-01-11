package org.firstinspires.ftc.teamcode.ftcLib.teleop.baseOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.ArmSubsystem;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.RawArmSubsystem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BaseRawArmOpMode extends CommandOpMode {

    protected MotorEx slideLeft, slideRight;
    protected ServoEx claw;
    protected RawArmSubsystem arm;
    protected ArmSubsystem oldarm;

    @Override
    public void initialize() {
        initHardware();
        setUpHardwareDevices();

        arm = new RawArmSubsystem(slideLeft, slideRight);

        oldarm = new ArmSubsystem(claw, slideLeft, slideRight);

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
        telemetry.addData("Right Slide Encoder", round(arm.getSlideREncoder()));
        telemetry.addData("Left Slide Encoder", round(arm.getSlideLEncoder()));
        telemetry.addData("Right Slide Velocity", round(arm.getSlideRVelocity()));
        telemetry.addData("Left Slide Velocity", round(arm.getSlideLVelocity()));
        telemetry.addData("Claw Position", round(oldarm.getClawPos()));

        telemetry.update();
    }

    private static double round(double value, @SuppressWarnings("SameParameterValue") int places) {
        if (places < 0) throw new IllegalArgumentException();
        return new BigDecimal(Double.toString(value)).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

    private static double round(double value) {
        return round(value, 4);
    }

}