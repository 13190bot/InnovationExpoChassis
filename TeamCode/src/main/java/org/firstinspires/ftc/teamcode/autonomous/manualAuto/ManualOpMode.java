package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import static android.os.SystemClock.sleep;


//TODO: code strafe, test in VirtualBot
@Autonomous
public class ManualOpMode extends OpMode {

    DcMotor lf, lb, rf, rb;

    Camera camera;

    @Override
    public void init() {

        lf = hardwareMap.dcMotor.get("lf");
        lb = hardwareMap.dcMotor.get("lb");
        rf = hardwareMap.dcMotor.get("rf");
        rb = hardwareMap.dcMotor.get("rb");

        DcMotor motors [] = {lf, lb, rf, rb};

        for(DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        telemetry.addData("init", "done");

    }

    @Override
    public void start () {

        //strafe to face park pos
        switch ( camera::getParkPos ) {
            case 1: //left
                rf.setPower(.5);
                rb.setPower(-.5);
                lf.setPower(-.5);
                lb.setPower(.5);
                sleep(3000);
                break;
            case 2: //if the middle parkpos
                break;
            case 3: //right
                rf.setPower(-.5);
                rb.setPower(.5);
                lf.setPower(.5);
                lb.setPower(-.5);
                sleep(3000);
                break;
        }

        //move forwards
        lf.setPower(.5);
        lb.setPower(.5);
        rf.setPower(.5);
        rb.setPower(.5);

        sleep(2000);

    }

    @Override
    public void loop() {}

}
