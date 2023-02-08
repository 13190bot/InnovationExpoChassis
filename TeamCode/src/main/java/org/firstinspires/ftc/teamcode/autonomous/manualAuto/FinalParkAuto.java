package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.autonomous.vision.SleeveDetection;


//TODO: Test on ACTUAL BOT (virtualbot seems to work for now)
@Config
@Autonomous(name = "Manual_ParkAuto")
public class FinalParkAuto extends OpMode {

    DcMotor lf, lb, rf, rb;
//    SleeveDetection sleeveDetection;
//    OpenCvCamera camera;

    // defining constants for ez editing

    private static final double DRIVE_POWER = 0.5;
    public static boolean strafeDone = false;
    public static boolean forwarsdDone = false;
    public static int forwardEdit = 0;


    String webcamName = "Webcam 1";

    SleeveDetection.ParkingPosition lol;

    @Override
    public void init() {

        lf = hardwareMap.dcMotor.get("frontLeft");
        lb = hardwareMap.dcMotor.get("backLeft");
        rf = hardwareMap.dcMotor.get("frontRight");
        rb = hardwareMap.dcMotor.get("backRight");

        DcMotor[] motors = {lf, lb, rf, rb};

        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setTargetPosition(0);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);


        telemetry.addData("init", "done");

/*
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
        */

        telemetry.addData("Position: ", lol);
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
// changed to use the constant DRIVE_POWER


        lf.setPower(DRIVE_POWER);
        rf.setPower(DRIVE_POWER);
        lb.setPower(DRIVE_POWER);
        rb.setPower(DRIVE_POWER);

        //encodervalue:
        lf.setTargetPosition(1170 + forwardEdit);
        lb.setTargetPosition(1070 + forwardEdit);
        rf.setTargetPosition(-1170 - forwardEdit);
        rb.setTargetPosition(1170 + forwardEdit);

        while(!forwarsdDone){}

        //strafe to face park pos
        switch (SleeveDetection.ParkingPosition.RIGHT) {
            case LEFT: //left

                telemetry.addData("Detected left", 1);
                telemetry.update();
                lf.setTargetPosition(-60);
                lb.setTargetPosition(2410);
                rf.setTargetPosition(-107);
                rb.setTargetPosition(-241);
                break;
            case CENTER: //if the middle parkpos

                telemetry.addData("Detected center", 2);
                telemetry.update();
                break;
            case RIGHT: //right
                telemetry.addData("Detected right", 3);
                telemetry.update();
                lf.setTargetPosition(2400);
                lb.setTargetPosition(-170);
                rf.setTargetPosition(107);
                rb.setTargetPosition(2581);
                break;
            default: // error for if no parking pos detected
                telemetry.addData("Error: No Parking Position", "No parkpos detected.");
                telemetry.update();
                break;
        }

        while(!strafeDone){}
        telemetry.addData("Parking", 0);

    }

    @Override
    public void stop() {
    }

    @Override
    public void loop() {
    }
}
