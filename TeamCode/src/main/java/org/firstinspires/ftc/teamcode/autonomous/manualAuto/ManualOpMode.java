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
@Autonomous(name = "ParkAuto")
public class ManualOpMode extends OpMode {

    DcMotor lf, lb, rf, rb;
    SleeveDetection sleeveDetection;
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

        telemetry.addData("init", "done");


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeveDetection = new SleeveDetection();
        camera.setPipeline(sleeveDetection);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720);
            }

            @Override
            public void onError(int errorCode) {
            }
        });

        telemetry.addData("Position: ", sleeveDetection.getPosition());
        telemetry.update();

    }

    @Override
    public void init_loop() {
        super.init_loop();

        if (lf != null || lb != null || rf != null || rb != null) {
            telemetry.addData("lf", lf::getPower);
            telemetry.addData("lb", lb::getPower);
            telemetry.addData("rf", rf::getPower);
            telemetry.addData("rb", rb::getPower);
        }

    }

    @Override
    public void start() {

        lf.setPower(0.1);
        rf.setPower(0.1);
        lb.setPower(0.1);
        rb.setPower(0.1);
        sleep(3000);

        //strafe to face park pos
        switch (sleeveDetection.getPosition()) {
            case LEFT: //left

                telemetry.addData("Detected left", 1);
                rf.setPower(0.1);
                rb.setPower(-0.1);
                lf.setPower(-0.1);
                lb.setPower(0.1);
                break;
            case CENTER: //if the middle parkpos

                telemetry.addData("Detected center", 2);
                break;
            case RIGHT: //right
                telemetry.addData("Detected right", 3);
                rf.setPower(-0.1);
                rb.setPower(0.1);
                lf.setPower(0.1);
                lb.setPower(-0.1);
                break;
        }
        sleep(3000);
        telemetry.addData("Parking", 0);
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);

    }

    @Override
    public void loop() {
    }


}
