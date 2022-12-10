package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.DriveSubsystem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BaseArmOpMode extends CommandOpMode {

    protected DcMotor slideLeft, slideRight;
    protected ServoEx claw,arm1,arm2;
    protected ArmSubsystem arm;





    @Override
    public void initialize() {
        initHardware();
        setUpHardwareDevices();
        arm = new ArmSubsystem(claw, arm1, arm2, slideLeft, slideRight);


        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Mode", "Done initializing");
        telemetry.update();
    }

    protected void initHardware() {

        slideLeft = hardwareMap.dcMotor.get("slideL");
        slideRight = hardwareMap.dcMotor.get("slideR");

        //TODO find min and max
        claw = new SimpleServo(hardwareMap, "claw", 0, 120);
        arm1 = new SimpleServo(hardwareMap, "arm1", 0, 120);
        arm2 = new SimpleServo(hardwareMap, "arm2", 0, 120);

    }
    protected void setUpHardwareDevices() {
        //TODO make sure correct motor is reversed
        slideRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void run() {
        super.run();
        telemetry.addData("claw Position", claw.getPosition());

        telemetry.addData("armServo1 Position", arm1.getPosition());
        telemetry.addData("armServo2 Position", arm2.getPosition());

        telemetry.update();
    }


}