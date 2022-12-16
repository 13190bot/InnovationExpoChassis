package org.firstinspires.ftc.teamcode.opmode.normal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import static android.os.SystemClock.sleep;


@Autonomous
public class LiftTest extends OpMode {

    DcMotor slideL, slideR;

    @Override
    public void init() {

        slideL = hardwareMap.dcMotor.get("slideL");
        slideR = hardwareMap.dcMotor.get("slideR");

        slideL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slideR.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void start () {
    }

    @Override
    public void loop() {}
}
