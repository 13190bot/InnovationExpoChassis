package org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.opmode.Surendra;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.autonomous.vision.SleeveDetection;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "HighJunc_Park")

public class HighJunc_Park extends LinearOpMode {

    private SleeveDetection sleeveDetection;

    private SleeveDetection.ParkingPosition pos;

    private OpenCvCamera camera;

    private String webcamName = "Webcam 1";

    private SampleMecanumDrive drive;

    private ArmSubsystem arm;

    protected MotorEx slideLeft, slideRight;

    protected ServoEx claw;

    Pose2d startingPos = new Pose2d(0,0,Math.toRadians(180)); //figure out correct to rad.

    public enum AutoPhase{
        IDLE,
        GRAB,
        FORWARD,
        SLIDE_UP,
        FACE_POLE,
        MINOR_FORWARD,
        DROP_CONE,
        MINOR_BACKWARD,
        RESET_ROTATION,
        SLIDES_DOWN,
        PARK
    }

    //TODO create timers for arm actions

    private AutoPhase action = AutoPhase.GRAB;

    @Override
    public void runOpMode() throws InterruptedException {

        //drive setup and arm setup
        slideLeft = new MotorEx(hardwareMap, "slideLeft");
        slideRight = new MotorEx(hardwareMap, "slideRight");

        claw = new SimpleServo(hardwareMap, "claw", 0, 180); //TODO figure out max degree

        drive = new SampleMecanumDrive(hardwareMap);
        arm = new ArmSubsystem(claw, slideLeft, slideRight);

        drive .setPoseEstimate(startingPos);


        //camera set up
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeveDetection = new SleeveDetection();
        camera.setPipeline(sleeveDetection);

        //lets us see stream from driver hub (not really necessary)
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });


        while (!isStarted()) {
            pos = sleeveDetection.getPosition();
            telemetry.addData("Position: ", pos);
            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            switch(action){
                case IDLE:
                    break;
                case GRAB:
                    if(!drive.isBusy()){

                    }

                    action = AutoPhase.FORWARD;

                    break;
                case FORWARD:
                    drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                            .lineToConstantHeading(new Vector2d(34.5,-11.6))
                            .build()
                    );

                    action = AutoPhase.SLIDE_UP;

                    break;
                case SLIDE_UP:
                    if(!drive.isBusy()){

                    }

                    action = AutoPhase.FACE_POLE;

                    break;
                case FACE_POLE:
                    drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                            .turn(Math.toRadians(45))
                            .build()
                    );

                    action = AutoPhase.MINOR_FORWARD;

                    break;
                case MINOR_FORWARD:
                    drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                            .lineToConstantHeading(new Vector2d(32,-10))
                            .build()
                    );

                    action = AutoPhase.DROP_CONE;

                    break;
                case DROP_CONE:
                    if(!drive.isBusy()){

                    }

                    action = AutoPhase.MINOR_BACKWARD;

                    break;
                case MINOR_BACKWARD:
                    drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                            .lineToConstantHeading(new Vector2d(34.5,-11.6))
                            .build()
                    );

                    action = AutoPhase.RESET_ROTATION;

                    break;
                case RESET_ROTATION:
                    drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                            .turn(Math.toRadians(-45))
                            .build()
                    );

                    action = AutoPhase.SLIDES_DOWN;

                    break;
                case SLIDES_DOWN:
                    if(!drive.isBusy()){

                    }

                    action = AutoPhase.PARK;

                    break;
                case PARK:
                    switch (pos){
                        case RIGHT:
                            drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                                    .forward(23.333333)
                                    .turn(Math.toRadians(-90))
                                    .forward(23.333333)
                                    .build()
                            );

                            break;
                        case CENTER:
                            drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                                    .forward(23.333333)
                                    .build()
                            );

                            break;
                        case LEFT:drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                                .forward(23.333333)
                                .turn(Math.toRadians(90))
                                .forward(23.333333)
                                .build()
                        );

                            break;

                    }

                    action = AutoPhase.IDLE;

                    break;
            }

            drive.update();
            arm.loopPID();

            Pose2d currentPoseEstimate = drive.getPoseEstimate();
            telemetry.addData("X-pos", currentPoseEstimate.getX());
            telemetry.addData("Y-pos", currentPoseEstimate.getY());
            telemetry.addData("Heading", currentPoseEstimate.getHeading());
            telemetry.addData("Current Action", action);
            telemetry.update();






        }
    }
}



