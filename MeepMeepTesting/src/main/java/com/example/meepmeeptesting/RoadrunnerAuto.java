package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.example.meepmeeptesting.Junction;

import com.example.meepmeeptesting.LiftSubsystem;

import java.util.Vector;

public class RoadrunnerAuto {


    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(900);

        Pose2d startingPos = new Pose2d(35,-58.333333,Math.toRadians(90));
        Vector2d medianPos = new Vector2d(34.5,-11.6);
        Vector2d depositPos = new Vector2d(32.5,-8.5);
        Vector2d leftPos = new Vector2d(11.666666,-11.666666);
        Vector2d centerPos = new Vector2d(35,-11.6666666);
        Vector2d rightPos = new Vector2d(56.5,-11.6666666);

        LiftSubsystem lift = new LiftSubsystem();

        ClawSubsystem claw = new ClawSubsystem();

        double tileSize = 24;
        Vector2d relative = new Vector2d(1, 1);



        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // TODO Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width that match our actual bot
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(startingPos)

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

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}