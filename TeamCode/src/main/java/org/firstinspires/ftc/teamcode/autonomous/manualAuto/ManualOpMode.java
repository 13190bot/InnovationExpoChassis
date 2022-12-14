package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.autonomous.vision.SleeveDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import static android.os.SystemClock.sleep;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;


//TODO: code strafe, test in VirtualBot
@Autonomous(name = "ParkAuto")
public class ManualOpMode extends LinearOpMode {

    SleeveDetection sleeveDetection;
    OpenCvCamera camera;

    // Name of the Webcam to be set in the config
    String webcamName = "Webcam 1";

    DcMotor lf, rf, lb, rb;

    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.dcMotor.get("frontLeft");
        lb = hardwareMap.dcMotor.get("backLeft");
        rf = hardwareMap.dcMotor.get("frontRight");
        rb = hardwareMap.dcMotor.get("backRight");

        DcMotor[] motors = {lf, lb, rf, rb};

        for(DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        //TODO: need to reverse motors
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection((DcMotorSimple.Direction.REVERSE));


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeveDetection = new SleeveDetection();
        camera.setPipeline(sleeveDetection);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(1280,720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {}
        });

        while (!isStarted()) {
            telemetry.addData("Position: ", sleeveDetection.getPosition());
            telemetry.update();
        }

        waitForStart();

        //strafe to face park pos
        switch ( sleeveDetection.getPosition()) {
            case LEFT: //left
                rf.setPower(0.1);
                rb.setPower(-0.1);
                lf.setPower(-0.1);
                lb.setPower(0.1);
                break;
            case CENTER: //if the middle parkpos
                break;
            case RIGHT: //right
                rf.setPower(-0.1);
                rb.setPower(0.1);
                lf.setPower(0.1);
                lb.setPower(-0.1);
                break;
        }
        sleep(1000);

        lf.setPower(0.1);
        rf.setPower(0.1);
        lb.setPower(0.1);
        rb.setPower(0.1);

        sleep(1250);

        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);

    }

}
