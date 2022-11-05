package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ServoImpl;

//kinda outdated
public final class Funcs  {

    public static ServoImpl armServo, clawServo; // will hve to initialise these in a separate init
    public static DcMotor armMotor;

    final double ARM_HEIGHT = 3.625;

    /**
    *Rotates the arm 180 degrees. It assumes that the limit on the servo would be 0 and 180.
    *It returns 0 if it worked and -1 if nothing happened, likely because the arm was not close to it's 0 or 1 position.
    */
    public static int turnArm() {

        if ( armServo.getPosition() > .9 ) {
            armServo.setPosition(0);

            return 0;
        }

        if ( armServo.getPosition() < .1 ) {
            armServo.setPosition(1);

            return 0;
        }

        return -1;

    }

    /**
     *If the claw is open, it will close it. Closed, it will open it. if the claw is in the middle, nothing will happen.
     *Assumes that the open and close state are 0 and 1 position for servo.
     */
    public static void runClaw() {
        if ( clawServo.getPosition() > .9 ) {
            clawServo.setPosition(0);
        }

        if ( clawServo.getPosition() < .1 ) {
            clawServo.setPosition(1);
        }
    }

    public static int turn (int num, int denom) {
        int radius = 6;
        num *= radius;
        num*= Math.PI;
        return ( inchesToTicks( num/denom) );
    }

    //------------------------------------------------------------------------------------------------------------------
    // a bunch of funcs that need more

    /**
     *takes a tick input and spits out an integer
     */

    public int ticksToInches(int ticks) {
        return (int) (6 * Math.PI * ticks / 537.7); //537.7 is ticks per revolution
    }

    /**
    *takes an integer input and spits out an encoder location (ticks)
    */
    public static int inchesToTicks(int inches) {
        return (int)( (inches * 537.7 ) / ( 6 * Math.PI ) );
    }

    /**
     *raise the arm to max level
     */
    public void runArm() {
        armMotor.setTargetPosition( (int) (537.7 * 5.3) );
    }

    /**
     *raise the arm a fraction of its height equal to scale
    */
    public void runArm(double scale) {

        if (scale > 1 || scale < 0) {
            return;
        } // if the scale would exceed one or be less than 0, do nothing

        armMotor.setTargetPosition( (int) (537.7 * 5.3) );
    }

}
