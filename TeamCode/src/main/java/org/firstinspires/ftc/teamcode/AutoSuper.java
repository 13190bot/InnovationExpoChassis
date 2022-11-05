package org.firstinspires.ftc.teamcode;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

//bot always has to be placed facing opposite team terminal on side
public class AutoSuper extends LinearOpMode {

    protected int startX, startY =0;

    int modX = (startX < 0)? 1 : -1;
    int modY = (startY < 0)? 1 : -1;

        @Override
        public void runOpMode() {

            SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
            ArmClass.init();
            PlaceholderAutoCameraClass.init(drive);

          //define trajectories

            Trajectory Traj1 = drive.trajectoryBuilder( new Pose2d(startX, startY, 0) ) //x -34, y -60
                    .strafeLeft(49 * modY)
                    .forward(11 * modX)
                    .addDisplacementMarker(() -> {
                        ArmClass.dropCone(ArmClass.LiftTarget.TALL, 90);
                    })
                    .build();

            Trajectory LoopTraj = drive.trajectoryBuilder(Traj1.end())
                    .strafeTo( new Vector2d(-56 * modX,-12 * modY ) )
                    .addDisplacementMarker( ArmClass::grabCone )

                    .strafeTo( new Vector2d(-23 * modX,-11 * modY ) )
                    .addDisplacementMarker(() -> {
                        ArmClass.dropCone(ArmClass.LiftTarget.TALL, 90);
                    })

                    .build();

            telemetry.addData("init", "done");
            telemetry.update();

            waitForStart();

            if(isStopRequested()) return;

            PlaceholderAutoCameraClass.setParkPos();

            drive.followTrajectory(Traj1);

            int i;
            for (i = 0; i < 5; i++) {

                if (isStopRequested()) return;

                drive.followTrajectory(LoopTraj);

            }

            if (isStopRequested()) return;

            PlaceholderAutoCameraClass.park( LoopTraj.end() );

            telemetry.addData("Auto", "done");
            telemetry.update();

        }

}
