package org.firstinspires.ftc.teamcode.ftcLib.teleop.baseOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.PCArmSubsystem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BasePCArmOpMode extends CommandOpMode {

    protected Motor slideLeft, slideRight;
    protected PCArmSubsystem arm;
    @Override
    public void initialize() {
        initHardware();
        setUpHardwareDevices();

        arm = new PCArmSubsystem(slideLeft, slideRight);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Mode", "Done initializing");
        telemetry.update();
    }

    protected void initHardware() {
        slideLeft = new MotorEx(hardwareMap, "slideL");
        slideRight = new MotorEx(hardwareMap, "slideR");
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
        telemetry.addData("Current Junction", arm.getCurrentGoal());
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