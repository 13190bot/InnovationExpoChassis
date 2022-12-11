package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.autonomous.vision.SleeveDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

import static android.os.SystemClock.sleep;


//TODO: code strafe, test in VirtualBot
@Autonomous
public class ManualOpMode extends OpMode {

    DcMotor lf, lb, rf, rb;
    SleeveDetection sleeveDetection;
    OpenCvCamera camera;

    String webcamName = "Webcam 1";

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


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeveDetection = new SleeveDetection();
        camera.setPipeline(sleeveDetection);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(1280,720);
            }

            @Override
            public void onError(int errorCode) {}
        });

        telemetry.addData("Position: ", sleeveDetection.getPosition());
        telemetry.update();

    }

    @Override
    public void start () {

        //strafe to face park pos
        switch ( sleeveDetection.getPosition()) {
            case LEFT: //left
                rf.setPower(.5);
                rb.setPower(-.5);
                lf.setPower(-.5);
                lb.setPower(.5);
                sleep(3000);
                break;
            case CENTER: //if the middle parkpos
                break;
            case RIGHT: //right
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
