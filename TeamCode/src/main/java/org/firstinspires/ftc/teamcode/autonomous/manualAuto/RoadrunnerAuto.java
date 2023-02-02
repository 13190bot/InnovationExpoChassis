package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.teleOp.subsystem.ClawSubsystem;
import org.firstinspires.ftc.teamcode.teleOp.subsystem.LiftSubsystem;
import org.firstinspires.ftc.teamcode.util.Junction;
import org.firstinspires.ftc.teamcode.autonomous.vision.SleeveDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

import java.util.Vector;

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

@Autonomous(name = "RR-Auto")

public class RoadrunnerAuto extends LinearOpMode {

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
    public void runOpMode() throws InterruptedException {
        //drive setup
        drive = new SampleMecanumDrive(hardwareMap);


        liftL= new MotorEx(hardwareMap, "slideL");
        liftR = new MotorEx(hardwareMap, "slideR");

        clawServo = new SimpleServo(hardwareMap, "claw", 0, 120);


        lift = new LiftSubsystem(liftL, liftR, () -> 1);
        claw = new ClawSubsystem(clawServo);


        StandardTrackingWheelLocalizer localizer = new StandardTrackingWheelLocalizer(hardwareMap);


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

        waitForStart();

        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)

                //PRELOADSTART PRELOAD HIGH


                // go to before cone stack
                //.lineToLinearHeading(new Pose2d(35, -58.333333 + 46.5, Math.toRadians(180)))
                .forward(46.5)

                // go to high junction
                /*
                .back(27 + 11.5)
                .turn(Math.toRadians(90))
                .forward(5)
                 */
                //.lineToLinearHeading(new Pose2d(35 + 27 - (27 + 11.5), -58.333333 + 46.5, Math.toRadians(90)))
                                .strafeLeft(11.5)

                //        .lineToLinearHeading(localizer.getPoseEstimate().plus(new Pose2d(0, 0, Math.toRadians(-90))))
                //.forward(5)

                .addDisplacementMarker(() -> {
                    // set lift height to high junction
                    lift.setJunction(Junction.HIGH);
                })
                .waitSeconds(1)

                .forward(5)

                .waitSeconds(0.5)

                // drop cone
                .addDisplacementMarker(() -> {
                    claw.release();
                })

                .waitSeconds(0.5)

                // go back a bit so we don't put claw on junction
                /*
                .back(5)
                */
                .lineToLinearHeading(new Pose2d(35 + 27 - (27 + 11.5), -58.333333 + 46.5, Math.toRadians(0)))


                .addDisplacementMarker(() -> {
                    //set height to ground
                    lift.setJunction(Junction.NONE);
                })


                // go back to cone stack
                //.turn(Math.toRadians(-90))
                //.forward(27 + 11.5)


                //PRELOADEND














                // CYCLE HIGH

                /*
                1 tile is 18 inches
                 */


                // INIT



                // go to cone stack
                /*
                .forward(46.5)
                .turn(Math.toRadians(-90))
                .forward(27)
                 */

                /*
                //CYCLEHIGHSTART

                .lineToLinearHeading(new Pose2d(35, -58.333333 + 46.5, Math.toRadians(0)))
                .forward(27)
                 */

                //CYCLEHIGHEND

                //.splineToLinearHeading(new Pose2d(35 + 27, -58.333333 + 46.5, Math.toRadians(0)), Math.toRadians(-180))






                // LOOP
                .forward(27 + 11.5)

                .waitSeconds(0.5)
                // grab cone
                        .addDisplacementMarker(() -> {
                            claw.grab();
                        })
                .waitSeconds(0.5)


                // go to high junction
                // /*
                .back(27 + 11.5)
                .turn(Math.toRadians(90))
                // */
                //.lineToLinearHeading(new Pose2d(35 + 27 - (27 + 11.5), -58.333333 + 46.5, Math.toRadians(90)))
//                        .forward(5)

                        .addDisplacementMarker(() -> {
                            // set lift height to high junction
                            lift.setJunction(Junction.HIGH);
                        })
                .waitSeconds(1)

                .forward(5)

                .waitSeconds(0.5)

                // drop cone
                        .addDisplacementMarker(() -> {
                            claw.release();
                        })

                .waitSeconds(0.5)

                // go back a bit so we don't put claw on junction
                /*
                .back(5)
                */
                .lineToLinearHeading(new Pose2d(35 + 27 - (27 + 11.5), -58.333333 + 46.5, Math.toRadians(0)))


                        .addDisplacementMarker(() -> {
                            //set height to ground
                            lift.setJunction(Junction.NONE);
                        })


                // go back to cone stack
                //.turn(Math.toRadians(-90))
                //.forward(27 + 11.5)


                // LOOPEND








                // VISION

                // go back to before cone stack
                //.lineToLinearHeading(new Pose2d(35, -58.333333 + 46.5, Math.toRadians(0)))
                                .forward(11.5)

                // sleeve detection position
                .lineToLinearHeading(new Pose2d(35, -tileSize * 1.5, Math.toRadians(0)))



                // finally go to parkingpos
                .strafeRight(relative.getX())

                .forward(relative.getY())


                .build()
        );
    }
}


