package org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.opmode.Kosei;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.SampleMecanumDrive;

@Autonomous(name = "RR-Auto")

public class RoadrunnerAuto extends LinearOpMode {

    Pose2d startingPos = new Pose2d(0,0,Math.toRadians(180)); //figure out correct to rad.

    private SampleMecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        //drive setup
        drive = new SampleMecanumDrive(hardwareMap);


        waitForStart();

        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startingPos)

                .build()
        );
    }
}


