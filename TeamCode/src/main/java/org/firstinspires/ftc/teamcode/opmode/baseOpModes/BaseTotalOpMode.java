package org.firstinspires.ftc.teamcode.opmode.baseOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.DriveSubsystem;

import java.math.BigDecimal;
import java.math.RoundingMode;

@TeleOp
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
        arm = new ArmSubsystem(claw, slideLeft, slideRight);

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
        //TODO MAKE SURE CORRECT MOTORS ARE REVERSED
        slideRight.setInverted(true);

        slideLeft.resetEncoder();
        slideRight.resetEncoder();

        slideLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        slideRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        //Motor Reversal
        bL.setInverted(true);
        fR.setInverted(true);
        fL.setInverted(true);

        //ask whether or not we should use this (8872 are hypocrites if they tell us not to use this)
        fL.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        fR.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        bL.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);



    }

    @Override
    public void run() {
        super.run();

        telemetry.addData("leftFront Power", round(fL.motor.getPower()));
        telemetry.addData("leftBack Power", round(bL.motor.getPower()));
        telemetry.addData("rightFront Power", round(fR.motor.getPower()));
        telemetry.addData("rightBack Power", round(bR.motor.getPower()));

        telemetry.addData("Left Slide Encoder", arm.getSlideLEncoder());
        telemetry.addData("Right Slide Encoder", arm.getSlideREncoder());
        telemetry.addData("Left Slide Power", arm.getSlideLPower());
        telemetry.addData("Right Slide Power", arm.getSlideRPower());
        telemetry.addData("Left Slide Error", arm.getSlideLError());
        telemetry.addData("Right Slide Error", arm.getSlideRError());
        telemetry.addData("Claw Position", arm.getClawPos());
        telemetry.update();

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