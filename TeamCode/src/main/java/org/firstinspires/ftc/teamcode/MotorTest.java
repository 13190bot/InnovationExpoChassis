package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import static android.os.SystemClock.sleep;

@TeleOp (name = "MotorTest")
public class MotorTest extends OpMode {

    DcMotor lf, lb, rf, rb;
    DcMotor[] motors = {lf, lb, rf, rb};

    @Override
    public void init() {

        lf = hardwareMap.dcMotor.get("lf");
        lb = hardwareMap.dcMotor.get("lb");
        rf = hardwareMap.dcMotor.get("rf");
        rb = hardwareMap.dcMotor.get("rb");
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


//        lf.setDirection(DcMotorSimple.Direction.REVERSE);
//        lb.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void loop() {
        lf.setPower(.5);
        lb.setPower(.5);
        rf.setPower(.5);
        rb.setPower(.5);

        sleep(1500);



    }

    @Override
    public void stop() {
        lf.setPower(0);
        lb.setPower(0);
        rf.setPower(0);
        rb.setPower(0);
        telemetry.addData("lf pos", lf.getCurrentPosition());
        telemetry.addData("lb pos", lb.getCurrentPosition());
        telemetry.addData("rf pos", rf.getCurrentPosition());
        telemetry.addData("rb pos", rb.getCurrentPosition());
    }
}

