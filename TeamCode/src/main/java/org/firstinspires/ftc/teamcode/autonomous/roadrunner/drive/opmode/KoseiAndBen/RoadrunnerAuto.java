package org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.opmode.KoseiAndBen;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.SampleMecanumDrive;
import com.qualcomm.robotcore.hardware.ServoImpl;
import org.firstinspires.ftc.teamcode.teleOp.subsystem.ClawSubsystem;
import org.firstinspires.ftc.teamcode.teleOp.subsystem.LiftSubsystem;
import org.firstinspires.ftc.teamcode.util.Junction;

@Autonomous(name = "RR-Auto")

public class RoadrunnerAuto extends LinearOpMode {

    Pose2d startingPos = new Pose2d(0,0,Math.toRadians(180)); //figure out correct to rad.
    //ServoImpl claw;
    private SampleMecanumDrive drive;
    protected LiftSubsystem lift;
    protected ClawSubsystem claw;
    protected SimpleServo clawServo;

    protected MotorEx liftR, liftL;

    @Override
    public void runOpMode() throws InterruptedException {
        //drive setup
        drive = new SampleMecanumDrive(hardwareMap);


        liftL= new MotorEx(hardwareMap, "slideL");
        liftR = new MotorEx(hardwareMap, "slideR");

        clawServo = new SimpleServo(hardwareMap, "claw", 0, 120);


        lift = new LiftSubsystem(liftL, liftR, () -> 1);
        claw = new ClawSubsystem(clawServo);

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
                        .addDisplacementMarker(() -> {
                            claw.grab();
                        })


                // go to high junction
                /*
                .back(27 + 11.5)
                .turn(Math.toRadians(90))
                .forward(5)
                 */
                .lineToLinearHeading(new Pose2d(35 + 27 - (27 + 11.5), -58.333333 + 46.5, Math.toRadians(90)))
//                        .forward(5)

                        .addDisplacementMarker(() -> {
                            // set lift height to high junction
                            lift.setJunction(Junction.HIGH);
                        })

                .forward(5)

                // drop cone
                        .addDisplacementMarker(() -> {
                            claw.release();
                        })


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
                .forward(27 + 11.5)


                .build()
        );
    }
}


