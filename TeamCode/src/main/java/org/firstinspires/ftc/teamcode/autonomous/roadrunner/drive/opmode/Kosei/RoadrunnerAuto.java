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

                .lineToLinearHeading(new Pose2d(35, -58.333333 + 46.5, Math.toRadians(0)))
                .forward(27)

                //.splineToLinearHeading(new Pose2d(35 + 27, -58.333333 + 46.5, Math.toRadians(0)), Math.toRadians(-180))











                // LOOP



                // grab cone



                // go to high junction
                /*
                .back(27 + 11.5)
                .turn(Math.toRadians(90))
                .forward(5)
                 */
                .lineToLinearHeading(new Pose2d(35 + 27 - (27 + 11.5), -58.333333 + 46.5, Math.toRadians(90)))
                .forward(5)

                // set lift height to high



                // drop cone



                // go back a bit so we don't put claw on junction
                /*
                .back(5)
                */
                .lineToLinearHeading(new Pose2d(35 + 27 - (27 + 11.5), -58.333333 + 46.5, Math.toRadians(0)))

                // set lift height to ground



                // go back to cone stack
                //.turn(Math.toRadians(-90))
                .forward(27 + 11.5)


                .build()
        );
    }
}


