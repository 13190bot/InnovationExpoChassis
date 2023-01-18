package org.firstinspires.ftc.teamcode.ftcLib.teleop.baseOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.ArmSubsystem;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.DriveSubsystem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BaseTotalOpMode extends CommandOpMode {
    protected MotorEx fL, fR, bL, bR;
    protected MotorEx slideLeft, slideRight;
    protected ServoEx claw;

    protected DriveSubsystem drive;
    protected ArmSubsystem arm;

    @Override
    public void initialize() {
        initHardware();
        setUpHardwareDevices();

        drive = new DriveSubsystem(fL, fR, bL, bR);
        arm = new ArmSubsystem(slideLeft, slideRight, claw);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Mode", "Done initializing");
        telemetry.update();
    }

    protected void initHardware() {
        fL = new MotorEx(hardwareMap, "frontLeft");
        fR = new MotorEx(hardwareMap, "frontRight");
        bL = new MotorEx(hardwareMap, "backLeft");
        bR = new MotorEx(hardwareMap, "backRight");

        slideLeft = new MotorEx(hardwareMap, "slideL");
        slideRight = new MotorEx(hardwareMap, "slideR");

        //TODO find min and max
        claw = new SimpleServo(hardwareMap, "claw", 0, 120);
    }

    protected void setUpHardwareDevices() {
        //motor reversal
        slideRight.setInverted(true);
        bR.setInverted(true);

        slideLeft.resetEncoder();
        slideRight.resetEncoder();

        //sets all motors to brake.
        MotorEx[] motors = {slideLeft, slideRight, fL, fR, bL, bR};
        for(MotorEx motor : motors){
            motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        }

    }

    @Override
    public void run() {
        super.run();

        telemetry.addData("leftFront Power", round(fL.motor.getPower()));
        telemetry.addData("leftBack Power", round(bL.motor.getPower()));
        telemetry.addData("rightFront Power", round(fR.motor.getPower()));
        telemetry.addData("rightBack Power", round(bR.motor.getPower()));

        telemetry.addData("Right Slide Encoder", round(arm.getSlideREncoder()));
        telemetry.addData("Right Slide Power", round(arm.getSLideRPower()));

        telemetry.addData("Left Slide Encoder", round(arm.getSlideLEncoder()));
        telemetry.addData("Right Slide Power", round(arm.getSLideLPower()));

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