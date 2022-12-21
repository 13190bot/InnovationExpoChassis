package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.autonomous.vision.SleeveDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

import static android.os.SystemClock.sleep;

@Autonomous(name = "Preload Auto")
public class newAuto extends OpMode {
    DcMotor lf, lb, rf, rb;
    OpenCvCamera camera;

    String webcamName = "Webcam 1";

    @Override
    public void init() {
        lf = hardwareMap.dcMotor.get("leftFront");
        lb = hardwareMap.dcMotor.get("leftBack");
        rf = hardwareMap.dcMotor.get("rightFront");
        rb = hardwareMap.dcMotor.get("rightBack");

        DcMotor[] motors = {lf, lb, rf, rb};

        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }


    }

    @Override
    public void loop() {



    }

}
