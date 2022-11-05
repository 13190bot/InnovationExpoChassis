package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="PranavMecannumDriveOne")
public class MecannumDrive extends OpMode {

    DcMotor lf;
    DcMotor rf;
    DcMotor lb;
    DcMotor rb;

    double vertical;
    double horizontal;
    double pivot;

    @Override
    public void init() {

        lf = hardwareMap.dcMotor.get("lf");
        rf = hardwareMap.dcMotor.get("rf");
        lb = hardwareMap.dcMotor.get("lb");
        rb = hardwareMap.dcMotor.get("rb");
        telemetry.addData("Motors", "initialized");

        telemetry.addData("Init ","finished");
    }

    public void start () {

        vertical = gamepad1.left_stick_y;
        horizontal = gamepad1.left_stick_x;
        pivot = gamepad1.right_stick_x;
    }

    @Override
    public void loop() {

        lf.setPower(pivot + (-horizontal - vertical));
        rf.setPower(pivot + (horizontal - vertical));
        lb.setPower(pivot + (horizontal - vertical));
        rb.setPower(pivot + (-horizontal - vertical));

    }
}
