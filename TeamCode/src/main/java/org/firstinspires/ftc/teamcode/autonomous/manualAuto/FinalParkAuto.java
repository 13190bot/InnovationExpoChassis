package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.autonomous.vision.SleeveDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

import static android.os.SystemClock.sleep;



//TODO: Test on ACTUAL BOT (virtualbot seems to work for now)
@Autonomous(name = "Manual_ParkAuto")
public class FinalParkAuto extends OpMode {

    DcMotor lf, lb, rf, rb;
    SleeveDetection sleeveDetection;
    OpenCvCamera camera;

    // defining constants for ez editing
    private static final int LONG_TIMER = 1000; // 1 sec

    private static final double DRIVE_POWER = 0.5;
    private static final double STRAFE_POWER = 0.5;

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
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        rf.setDirection(DcMotorSimple.Direction.REVERSE);
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

        telemetry.addData("setPower", 1);
        sleep(LONG_TIMER);

        telemetry.addData("setPower", 2);
        //strafe to face park pos
        switch (lol) {
            case LEFT: //left

                telemetry.addData("Detected left", 1);
                telemetry.update();
                strafeLeft(STRAFE_POWER);
                break;
            case CENTER: //if the middle parkpos

                telemetry.addData("Detected center", 2);
                telemetry.update();
                break;
            case RIGHT: //right
                telemetry.addData("Detected right", 3);
                telemetry.update();
                strafeRight(STRAFE_POWER);
                break;
            default: // error for if no parking pos detected
                telemetry.addData("Error: No Parking Position", "No parkpos detected.");
                telemetry.update();
                break;
        }

        sleep(LONG_TIMER);
        telemetry.addData("Parking", 0);
        stopMotors(); // stops motors

    }

    @Override
    public void stop() {
        stopMotors();
    }

    @Override
    public void loop() {

    }


    private void stopMotors() { // func to stop motors
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
    }

    private void strafeRight(double motorPower) {
        lf.setPower(-motorPower);
        rf.setPower(motorPower);
        lb.setPower(motorPower);
        rb.setPower(-motorPower);
    }

    private void strafeLeft(double motorPower) {
        lf.setPower(motorPower);
        rf.setPower(-motorPower);
        lb.setPower(-motorPower);
        rb.setPower(motorPower);
    }


}
