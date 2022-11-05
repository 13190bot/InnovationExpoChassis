package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "MechDive")
public class DriveTeleOp extends OpMode {

    private MechDrive drive;
    private DcMotor lf, rf, lb, rb;
    @Override
    public void init() {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        drive = new MechDrive(lf, rf, lb, rb);


        telemetry.addData("init", "done");
        telemetry.update();
    }

    //TODO: make sure the code here lines up with the stuff written up on thursday
    @Override
    public void loop() {
        drive.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
    }

    @Override
    public void stop() {

        drive.stop();

    }
}
