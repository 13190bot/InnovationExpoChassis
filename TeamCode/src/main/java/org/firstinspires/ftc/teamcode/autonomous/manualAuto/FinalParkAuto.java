package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.autonomous.vision.SleeveDetection;
import org.firstinspires.ftc.teamcode.ftcLib.command.drive.DefaultRobotCentricDrive;
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
    private static final int LONG_TIMER = 3000; // 3 sec
    // 1000 = 1 second, can add more constants if necessary

    private static final double DRIVE_POWER = 0.3;
    private static final double STRAFE_POWER = 0.3;

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
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);
        lf.setDirection(DcMotorSimple.Direction.REVERSE);

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
            telemetry.update();
        }

    }

    @Override
    public void start() {
// changed to use the constant DRIVE_POWER
        lf.setPower(DRIVE_POWER);
        rf.setPower(DRIVE_POWER);
        lb.setPower(DRIVE_POWER);
        rb.setPower(DRIVE_POWER);
        sleep(LONG_TIMER);

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
        telemetry.update();
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

    private void strafeRight(double MOTOR_POWER) {
        lf.setPower(-MOTOR_POWER);
        rf.setPower(MOTOR_POWER);
        lb.setPower(MOTOR_POWER);
        rb.setPower(-MOTOR_POWER);
    }

    private void strafeLeft(double MOTOR_POWER) {
        lf.setPower(MOTOR_POWER);
        rf.setPower(-MOTOR_POWER);
        lb.setPower(-MOTOR_POWER);
        rb.setPower(MOTOR_POWER);
    }


}
