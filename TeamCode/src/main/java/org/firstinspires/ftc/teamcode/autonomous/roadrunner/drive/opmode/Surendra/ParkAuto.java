package org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.opmode.Surendra;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.autonomous.vision.SleeveDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "ParkAuto_Surendra")

public class ParkAuto extends LinearOpMode {

    private SleeveDetection sleeveDetection;

    private SleeveDetection.ParkingPosition pos;

    private OpenCvCamera camera;

    private String webcamName = "Webcam 1";

    private SampleMecanumDrive drive;

    Pose2d startingPos = new Pose2d(0,0,Math.toRadians(180)); //figure out correct to rad.

    @Override
    public void runOpMode() throws InterruptedException {
        //drive setup
        drive = new SampleMecanumDrive(hardwareMap);


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
                case LEFT:
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                            .forward(23.333333)
                            .turn(Math.toRadians(90))
                            .forward(23.333333)
                            .build()
                );

                    break;

            }
        }
    }
}


