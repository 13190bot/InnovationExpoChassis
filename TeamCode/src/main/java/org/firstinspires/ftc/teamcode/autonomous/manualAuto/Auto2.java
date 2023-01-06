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

import static android.os.SystemClock.sleep;

@Autonomous(name = "Park Auto")
public class Auto2 extends LinearOpMode {

    DcMotor lf, lb, rf, rb;
    SleeveDetection sleeveDetection;
    OpenCvCamera camera;

    String webcamName = "Webcam 1";

    final int SEGMENT_LENGTH = 1350;

    @Override
    public void runOpMode() {
        lf = hardwareMap.dcMotor.get("frontLeft");
        lb = hardwareMap.dcMotor.get("backLeft");
        rf = hardwareMap.dcMotor.get("frontRight");
        rb = hardwareMap.dcMotor.get("backRight");

        DcMotor motors [] = {lf, lb, rf, rb};

        for(DcMotor motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);

        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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

        waitForStart();

        lf.setPower(0.3);
        rf.setPower(0.2);
        lb.setPower(0.2);
        rb.setPower(0.2);

        while (rf.getCurrentPosition() != SEGMENT_LENGTH) {}
//        sleep(1000);

        lf.setPower(0);
        rf.setPower(0);
        rb.setPower(0);
        lb.setPower(0);
        sleep(500);

        Integer targPos;

        switch ( sleeveDetection.getPosition() ) {
            case 1: //left
                targPos = SEGMENT_LENGTH * 2;
                rf.setPower(0.2);
                rb.setPower(-0.2);
                lf.setPower(-0.2);
                lb.setPower(0.2);
                break;

            case 0: //if the middle parkpos
                targPos = SEGMENT_LENGTH;
                break;

            case 2: //right
                rf.setPower(-0.2);
                rb.setPower(0.2);
                lf.setPower(0.2);
                lb.setPower(-0.2);
                targPos = 0;
                break;
            default:
                throw new IllegalStateException( "Unexpected value: " + sleeveDetection.getPosition() );
        }

        while ( rf.getCurrentPosition() != targPos ) {}

        lf.setPower(0);
        rf.setPower(0);
        rb.setPower(0);
        lb.setPower(0);

    }

}
