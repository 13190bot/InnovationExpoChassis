package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;


//TODO: fix format
public final class PlaceholderAutoCameraClass {

    public static char parkPos = 0; //actuall position will be 1, 2, or 3.

    private static SampleMecanumDrive drive_;

    private static TFConeScanner coneScanner;

    public static void init(SampleMecanumDrive drive) {
        drive_ = drive;

        coneScanner.init();

        telemetry.addData("camera init", "done");
        telemetry.update();
    }


    /**
     * will read cone and determine parking spot.
     */
    public static void setParkPos () {

        while (parkPos == 0) {
            //determine portion of sleeve visible and set parkPos to that value (1, 2, or 3)
        }

        //for now i will set parkPos to 1 as a placeholder
        parkPos = 1;

        telemetry.addData("parking spot is", parkPos);
        telemetry.update();

    }

    //TODO: line up cases with actual outputs
    /**
     * will run bot to parking spot or terminal if any problems. only call in loop phase
     * @param startPos
     */
    public static void park (com.acmerobotics.roadrunner.geometry.Pose2d startPos) {

        int modY = (startPos.getY() < 0)? 1 : -1;
        int modX = (startPos.getX() < 0)? 1 : -1;


        switch (parkPos) {

            case 'B':
                TrajectorySequence toL1 = drive_.trajectorySequenceBuilder( startPos )
                        .lineTo( new Vector2d( (-35 * modX) - (25 * modY), startPos.getY() * modY ) )
                        .lineTo( new Vector2d( (-35 * modX) - (25 * modY), -23 * modY) )
                        .build();

                telemetry.addData("To parking spot", 1);
                telemetry.update();

                drive_.followTrajectorySequence(toL1);
                break;

            case 'C':
                TrajectorySequence toL2 = drive_.trajectorySequenceBuilder( startPos )
                        .lineTo( new Vector2d( -34 * modX, startPos.getY() ) )
                        .lineTo( new Vector2d( -34 * modX, -23 * modY ) )
                        .build();

                telemetry.addData("To parking spot", 2);
                telemetry.update();

                drive_.followTrajectorySequence(toL2);
                break;

            case 'D':
                TrajectorySequence toL3 = drive_.trajectorySequenceBuilder( startPos )
                        .lineTo( new Vector2d( (-35 * modX) + (25 * modY), startPos.getY() * modY ) )
                        .lineTo( new Vector2d( (-35 * modX) + (25 * modY), -23 * modY) )
                        .build();

                telemetry.addData("To parking spot", 3);
                telemetry.update();

                drive_.followTrajectorySequence(toL3);
                break;

            case 'A': //no break so these will merge
            default: //if somehow parkPos is an unexpected value, robot will park in terminal
                TrajectorySequence toTerminal2 = drive_.trajectorySequenceBuilder( startPos )
                        .lineTo( new Vector2d(-60, -11 * modY ) )
                        .strafeRight(50 * modY )
                        .build();

                telemetry.addData("Error", "parking in terminal");
                telemetry.addData("parkPos is", parkPos );
                telemetry.update();

                drive_.followTrajectorySequence( toTerminal2 );
                break;

        }

    }


}
