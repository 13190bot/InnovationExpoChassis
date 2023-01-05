package org.firstinspires.ftc.teamcode.teleop.baseOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BaseArmOpMode extends CommandOpMode {

    protected MotorEx slideLeft, slideRight;
    protected ServoEx claw;
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
        telemetry.addData("Right Slide Encoder", round(arm.getSlideREncoder()));
        telemetry.addData("Left Slide Encoder", round(arm.getSlideLEncoder()));
        telemetry.addData("Left Slide Power", round(arm.getSlideLPower()));
        telemetry.addData("Right Slide Power", round(arm.getSlideRPower()));
        telemetry.addData("Left Slide Error", round(arm.getSlideLError()));
        telemetry.addData("Right Slide Error", round(arm.getSlideRError()));
        telemetry.addData("Claw Position", round(arm.getClawPos()));

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