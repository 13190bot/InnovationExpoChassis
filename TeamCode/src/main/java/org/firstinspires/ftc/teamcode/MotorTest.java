package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import static android.os.SystemClock.sleep;

@TeleOp
public class MotorTest extends OpMode {

    DcMotor lf, lb, rf, rb;

    @Override
    public void init() {

        lf = hardwareMap.dcMotor.get("lf");
        lb = hardwareMap.dcMotor.get("lb");
        rf = hardwareMap.dcMotor.get("rf");
        rb = hardwareMap.dcMotor.get("rb");

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

        telemetry.addData("lf pos", lf::getCurrentPosition);
        telemetry.addData("lb pos", lb::getCurrentPosition);
        telemetry.addData("rf pos", rf::getCurrentPosition);
        telemetry.addData("rb pos", rb::getCurrentPosition);

    }
}
