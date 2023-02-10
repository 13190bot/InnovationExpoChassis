package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.autonomous.vision.SleeveDetection;
import org.firstinspires.ftc.teamcode.teleOp.subsystem.ClawSubsystem;
import org.firstinspires.ftc.teamcode.teleOp.subsystem.LiftSubsystem;
import org.firstinspires.ftc.teamcode.util.Junction;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.function.DoubleSupplier;


@Autonomous(name = "ONEPLUSZERO")
public class Final_OnePlusZero extends LinearOpMode {

        private SleeveDetection sleeveDetection;

        private SleeveDetection.ParkingPosition pos;

        private OpenCvCamera camera;

        private String webcamName = "Webcam 1";

        private SampleMecanumDrive drive;

        private LiftSubsystem lift;

        private ClawSubsystem claw;

        private DoubleSupplier lol;

        protected MotorEx slideLeft, slideRight;

        protected ServoEx Claw;

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
            SLIDE_DOWN,
            PARK
        }

        private ElapsedTime grab;

        private ElapsedTime slideUp;

        private ElapsedTime drop;

        private ElapsedTime slideDown;


        private double grabTime;

        private double slideUpTime;

        private double dropTime;

        private double slideDownTime;
        Pose2d startingPos = new Pose2d(35,-58.333333,Math.toRadians(90));
        //TODO TUNE THE VALUES

        private AutoPhase action = AutoPhase.GRAB;

        //TODO Tune These Positions

        //path positions
        public int rotAngle = 45;
        @Override
        public void runOpMode() throws InterruptedException {

            //drive setup and arm setup
            slideLeft = new MotorEx(hardwareMap, "slideL");
            slideRight = new MotorEx(hardwareMap, "slideR");

            Claw = new SimpleServo(hardwareMap, "claw", 0, 180); //TODO figure out max degree

            drive = new SampleMecanumDrive(hardwareMap);

            lift = new LiftSubsystem(slideLeft, slideRight, lol);


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

            //TODO tune timer values

            grabTime = 1;
            grab = new ElapsedTime();

            slideUpTime = 1;
            slideUp = new ElapsedTime();

            dropTime = 1;
            drop = new ElapsedTime();

            slideDownTime = 1;
            slideDown = new ElapsedTime();


            while (!isStarted()) {
                pos = sleeveDetection.getPosition();
                telemetry.addData("Position: ", pos);
                telemetry.update();
            }

            waitForStart();

            while (opModeIsActive() && !isStopRequested()) {
                switch(action){
                    case GRAB:
                        if(!drive.isBusy()){
                            grab.reset();
                            claw.grab();
                            action = AutoPhase.FORWARD;
                        }
                        break;

                    case FORWARD:
                        if(grab.seconds() > grabTime) {
                            drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                                    .forward(10)
                                    .build()
                            );
                            action = AutoPhase.SLIDE_UP;

                        }
                        break;

                    case SLIDE_UP:
                        if(!drive.isBusy()){
                            slideUp.reset();
                            lift.setJunction(Junction.HIGH);
                            action = AutoPhase.FACE_POLE;
                        }
                        break;

                    case FACE_POLE:
                        if(slideUp.seconds()>slideUpTime) {
                            drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                                    .turn(Math.toRadians(rotAngle))
                                    .build()
                            );
                            action = AutoPhase.MINOR_FORWARD;
                        }
                        break;

                    case MINOR_FORWARD:
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                                .forward(12)
                                .build()

                        );
                        action = AutoPhase.DROP_CONE;
                        break;

                    case DROP_CONE:
                        if(!drive.isBusy()){
                            drop.reset();
                            claw.release();
                            action = AutoPhase.MINOR_BACKWARD;
                        }
                        break;

                    case MINOR_BACKWARD:
                        if(drop.seconds()>dropTime) {
                            drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                                    .forward(12)
                                    .build()
                            );
                            action = AutoPhase.RESET_ROTATION;
                        }
                        break;

                    case RESET_ROTATION:
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                                .turn(Math.toRadians(-1*rotAngle))
                                .build()
                        );
                        action = AutoPhase.SLIDE_DOWN;
                        break;

                    case SLIDE_DOWN:
                        if(!drive.isBusy()){
                            slideDown.reset();
                            lift.setJunction(Junction.NONE);
                            action = AutoPhase.PARK;
                        }
                        break;

                    case PARK:
                        if(slideDown.seconds() > slideDownTime) {

                            switch (pos) {
                                case RIGHT:
                                    drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                                            .turn(Math.toRadians(-2*rotAngle))
                                            .forward(40)
                                            .build()
                                    );
                                    break;

                                case CENTER:
                                    drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                                            .forward(40)
                                            .build()
                                    );
                                    break;

                                case LEFT:
                                    drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                                            .forward(20)
                                            .build()
                                    );
                                    break;
                            }
                            action = AutoPhase.IDLE;
                        }
                    case IDLE:
                        break;

                }

                drive.update();
                lift.periodic();

                Pose2d currentPoseEstimate = drive.getPoseEstimate();
                telemetry.addData("X-pos", currentPoseEstimate.getX());
                telemetry.addData("Y-pos", currentPoseEstimate.getY());
                telemetry.addData("Heading", currentPoseEstimate.getHeading());
                telemetry.addData("Current Action", action);
                telemetry.update();

            }
        }





}
