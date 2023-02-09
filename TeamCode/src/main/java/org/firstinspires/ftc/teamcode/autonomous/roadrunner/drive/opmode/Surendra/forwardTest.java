package org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.opmode.Surendra;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.autonomous.vision.SleeveDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Config
@Autonomous(name = "forward penis")

public class forwardTest extends LinearOpMode {

    private OpenCvCamera camera;

    private SampleMecanumDrive drive;

    public static double distance = 20;

    Pose2d startingPos = new Pose2d(0,0,Math.toRadians(180)); //figure out correct to rad.

    @Override
    public void runOpMode() throws InterruptedException {
        //drive setup
        drive = new SampleMecanumDrive(hardwareMap);


        while (!isStarted()) {
            telemetry.addData("penis", 1);
            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)
                            .forward(distance)

                            .build()
            );


            }
        }
    }



