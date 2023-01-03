package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.SampleMecanumDrive;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // TODO Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width that match our actual bot
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(0, -58.333333, Math.toRadians(90)))
                                //grab cone
                                //.forward(23.333333*2)
                                //.splineTo(new Vector2d(34.5,-11.6), Math.toRadians(135))
                                .lineToConstantHeading(new Vector2d(34.5,-11.6))
                                //slide up to high junction
                                .turn(Math.toRadians(45))
                                .forward(3)
                                //drop cone
                                .forward(-3)
                                .turn(Math.toRadians(-45))
                                //slides down
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}