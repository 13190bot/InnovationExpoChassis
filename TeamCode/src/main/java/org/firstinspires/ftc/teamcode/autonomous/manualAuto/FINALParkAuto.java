package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.autonomous.vision.SleeveDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;


//TODO: Test on ACTUAL BOT (virtualbot seems to work for now)
@Config
@Autonomous(name = "FINAL_parkauto")
public class FINALParkAuto extends LinearOpMode {

    DcMotor lf, lb, rf, rb;
    SleeveDetection sleeveDetection;
    OpenCvCamera camera;

    // defining constants for ez editing

    private static final double DRIVE_POWER = 0.25;
    public static int forwardEdit = 0;


    String webcamName = "Webcam 1";

    SleeveDetection.ParkingPosition lol;

    @Override
    public void runOpMode() {

        lf = hardwareMap.dcMotor.get("frontLeft");
        lb = hardwareMap.dcMotor.get("backLeft");
        rf = hardwareMap.dcMotor.get("frontRight");
        rb = hardwareMap.dcMotor.get("backRight");

        DcMotor[] motors = {lf, lb, rf, rb};

        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);


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


        lol = sleeveDetection.getPosition();



        //lol = SleeveDetection.ParkingPosition.RIGHT;

        telemetry.addData("Position: ", lol);
        telemetry.update();

        waitForStart();

        lf.setPower(DRIVE_POWER);
        rf.setPower(DRIVE_POWER);
        lb.setPower(DRIVE_POWER);
        rb.setPower(DRIVE_POWER);

        //sleep(2500); //just barely behind pole (fails >13V)
        sleep(2300);
        switch (lol) {
            case LEFT: //left

                lf.setPower(-DRIVE_POWER);
                rf.setPower(DRIVE_POWER);
                lb.setPower(DRIVE_POWER);
                rb.setPower(-DRIVE_POWER);
                telemetry.addData("Detected left", 1);
                telemetry.update();
                break;

            case CENTER: //if the middle parkpos
                telemetry.addData("Detected center", 2);
                telemetry.update();
                break;

            case RIGHT: //right
                lf.setPower(DRIVE_POWER);
                rf.setPower(-DRIVE_POWER);
                lb.setPower(-DRIVE_POWER);
                rb.setPower(DRIVE_POWER);
                telemetry.addData("Detected right", 3);
                telemetry.update();
                break;

            default: // error for if no parking pos detected
                telemetry.addData("Error: No Parking Position", "No parkpos detected.");
                telemetry.update();
                break;
        }




        sleep(3000);
        telemetry.addData("Parking", 0);
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
    }
}
