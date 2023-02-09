package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.autonomous.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.autonomous.roadrunner.trajectorysequence.TrajectorySequence;

import static android.os.SystemClock.sleep;

@Autonomous(name = "Road Runner Tuner")
public class RRTuner extends OpMode {
    private SampleMecanumDrive drive;
    Pose2d startingPos = new Pose2d(0,0,Math.toRadians(180)); //figure out correct to rad.

    double forwardMod = 1,
            turnMod = 7;
    @Override
    public void init() {
        drive = new SampleMecanumDrive(hardwareMap);
        telemetry = new MultipleTelemetry(telemetry);
        telemetry.addData("init", "done");
    }

    @Override
    public void start() {
        telemetry.addData("hello", "world");

        TrajectorySequence trajectory = drive.trajectorySequenceBuilder(startingPos)
                .forward(18 * forwardMod) //in
                .turn(Math.toRadians(180 * turnMod)) //deg
                .build();


        drive.followTrajectorySequence(trajectory);

        telemetry.addData("done", "");
    }

    @Override
    public void loop () {}
}
