package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.autonomous.vision.SleeveDetection;
import org.firstinspires.ftc.teamcode.teleOp.opmode.BaseOpMode;
import org.firstinspires.ftc.teamcode.teleOp.subsystem.ClawSubsystem;
import org.firstinspires.ftc.teamcode.teleOp.subsystem.LiftSubsystem;
import org.firstinspires.ftc.teamcode.util.Junction;
import org.openftc.easyopencv.OpenCvCamera;

/*
README:
!!!: If you are using preload then
UNCOMMENT
starts with //PRELOADSTART
ends with //PRELOADEND
COMMENT
starts with //CYCLEHIGHSTART
ends with //CYCLEHIGHEND
!!!: If you are not using preload then
COMMENT
starts with //PRELOADSTART
ends with //PRELOADEND
UNCOMMENT
starts with //CYCLEHIGHSTART
ends with //CYCLEHIGHEND
 */

@Autonomous(name = "RoadrunnerAutoFTCLIB")

public class RoadrunnerAutoFTCLIB3 extends BaseOpMode {

    Pose2d startingPos = new Pose2d(35,-58.333333,Math.toRadians(90));
    //ServoImpl claw;
    private SampleMecanumDrive drive;
    protected LiftSubsystem lift;
    protected ClawSubsystem claw;
    protected SimpleServo clawServo;

    protected MotorEx liftR, liftL;

    String webcamName = "Webcam 1";

    SleeveDetection sleeveDetection;
    OpenCvCamera camera;
    SleeveDetection.ParkingPosition parkingPosition;


    @Override
    public void initialize() {
        super.initialize();
        //drive setup
        telemetry = new MultipleTelemetry(telemetry);
        drive = new SampleMecanumDrive(hardwareMap);


        liftL= new MotorEx(hardwareMap, "slideL");
        liftR = new MotorEx(hardwareMap, "slideR");

        clawServo = new SimpleServo(hardwareMap, "claw", 0, 120);


        lift = new LiftSubsystem(liftL, liftR, () -> 1);
        claw = new ClawSubsystem(clawServo);


        /*
        //StandardTrackingWheelLocalizer localizer = new StandardTrackingWheelLocalizer(hardwareMap);
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
        parkingPosition = sleeveDetection.getPosition();
        */
        parkingPosition = SleeveDetection.ParkingPosition.RIGHT;

        Pose2d ParkingPos;
        Vector2d relative; //MAKE SURE RELATIVE ISNT EMPTY OR ITLL ERROR
        /*
        relative is from signal cone position
         */
        double tileSize = 24;

        switch(parkingPosition) {
            case NOPOS:
                // bruh
                relative = new Vector2d(0, 0);
            case LEFT:
                relative = new Vector2d(-tileSize, tileSize / 2);
            case RIGHT:
                relative = new Vector2d(tileSize, tileSize / 2);
            case CENTER:
                relative = new Vector2d(0, tileSize / 2);
            default:
                relative = new Vector2d(0, 0);
        }
        ParkingPos = new Pose2d();

        double forwardmul = 1.3; // multiplier for forward
        double leftturnmul = 1.37; // multiplier for left turn
        double rightturnmul = 1.41; // multiplier for right turn


//        liftL.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        liftR.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);


        waitForStart();

        drive.followTrajectorySequence(drive.trajectorySequenceBuilder(startingPos)

                        //PRELOADSTART PRELOAD HIGH

                        .addDisplacementMarker(() -> {
                            claw.grab();
                        })

                        .forward(47 * forwardmul)

                        // go to high junction
                        //.strafeLeft(11.5)
                        //.turn(Math.toRadians(90 * turnmul)).forward(11.5 * mul).turn(Math.toRadians(-90 * turnmul))
                        .turn(Math.toRadians(45 * leftturnmul))

                        // Marker callbacks should never block for extended periods.
                        .addDisplacementMarker(() -> {
                            // set lift height to high junction
//                            telemetry.addData("WORKS", "WORKS");
//                            telemetry.update();

                            lift.setJunction(Junction.HIGH);
                        })

                        .forward(9.5 * forwardmul)

                        .waitSeconds(0.5)

                        // drop cone
                        .addDisplacementMarker(() -> {
                            claw.release();
                        })

                        .waitSeconds(0.5)

                        .back(9.5 * forwardmul)
                        // go back a bit so we don't put



                        .addDisplacementMarker(() -> {
                            // set height to ground
                            lift.setJunction(Junction.NONE);
                        })

                        .turn(Math.toRadians(-45 * rightturnmul))

                        .back(12 * forwardmul)

                        //PRELOADEND

                        .waitSeconds(69420420)








                        // CYCLE HIGH


                        // INIT



                        // go to cone stack

                        /*
                        //CYCLEHIGHSTART
                        .lineToLinearHeading(new Pose2d(35, -58.333333 + 46.5, Math.toRadians(0)))
                        .forward(27)
                         */

                        //CYCLEHIGHEND






                        // LOOP

                        // LOOPEND








                        // VISION

                        // go back to before cone stack
                        //.lineToLinearHeading(new Pose2d(35, -58.333333 + 46.5, Math.toRadians(0)))
                        .forward(11.5)

                        // sleeve detection position
                        .lineToLinearHeading(new Pose2d(35, -tileSize * 1.5, Math.toRadians(0)))



                        // finally go to parkingpos
//                .strafeRight(relative.getX())
//
//                .forward(relative.getY())


                        .build()
        );






    }
}
