package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
/*
import static android.os.SystemClock.sleep;

public class AutoFake extends OpMode {

    DcMotor lf, lb, rf, rb;

    Camera camera;

    @Override
    public void init() {

        camera = new Camera(hardwareMap);

        lf = hardwareMap.dcMotor.get("lf");
        lb = hardwareMap.dcMotor.get("lb");
        rf= hardwareMap.dcMotor.get("rf");
        rb = hardwareMap.dcMotor.get("rb");
    }

    @Override
    public void start () {
        lf.setPower(.5);
        lb.setPower(.5);
        rf.setPower(.5);
        rb.setPower(.5);

        sleep(3000);

        switch( camera.getParkPos() ){

            case 0:
                lf.setPower(-.5);
                lb.setPower(.5);
                rf.setPower(.5);
                rb.setPower(-.5);

            case 1:
                break;

            case 2:
                lf.setPower(.5);
                lb.setPower(-.5);
                rf.setPower(-.5);
                rb.setPower(.5);

        }

        sleep(3000);

        lf.setPower(0);
        lb.setPower(0);
        rf.setPower(0);
        rb.setPower(0);

    }

    @Override
    public void loop() {
    requestOpModeStop();
    }
}
*/

