package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.SampleMecanumDrive;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(900);

        Pose2d startingPos = new Pose2d(35,-58.333333,Math.toRadians(90));
        Vector2d medianPos = new Vector2d(34.5,-11.6);
        Vector2d depositPos = new Vector2d(32.5,-8.5);
        Vector2d leftPos = new Vector2d(11.666666,-11.666666);
        Vector2d centerPos = new Vector2d(35,-11.6666666);
        Vector2d rightPos = new Vector2d(56.5,-11.6666666);


        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // TODO Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width that match our actual bot
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(startingPos)
                                .lineToConstantHeading(medianPos)
                                .turn(Math.toRadians(45))
                                .lineToConstantHeading(depositPos)
                                .lineToConstantHeading(medianPos)
                                .turn(Math.toRadians(-45))

                                .lineToConstantHeading(leftPos)
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}