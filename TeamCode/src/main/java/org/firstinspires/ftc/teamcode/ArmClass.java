package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.ServoImpl;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.*;

public final class ArmClass {

  //data section

    public static DcMotor liftMotor;
    public static ServoImpl armServo, clawServo;

    //needs to be tweaked
    /**
     * the number of encoder ticks it takes to fully extend the arm.
     */
    private static final int LIFT_RANGE = 2849; // + .81, double check if this is right

    /**
     * the location that our arm is extended too
     */
    public enum LiftTarget {

        JUNCTION,
        SHORT,
        MEDIUM,
        TALL;

        /**
         * iterator for enum
         */
        public LiftTarget next() {

            if ( this == TALL ){

                //return TALL;
                return JUNCTION;

            }

            return values()[ordinal() + 1];

        }

        public LiftTarget prev() {

            if (this == JUNCTION){

                //return JUNCTION;
                return TALL;

            }

            return values()[ordinal() - 1];

        }

    }

    static LiftTarget curentLiftTarget = LiftTarget.JUNCTION;

  //TeleOp Functions

    /**
     * initialises all the arm hardware
     */
    public static void init() {

        //init lift motor
        liftMotor = hardwareMap.dcMotor.get("arm_motor"); //512 rpm motor
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setTargetPosition(0);
        liftMotor.setMode( DcMotor.RunMode.RUN_TO_POSITION );

        telemetry.addData("Lift motor", "initialized");
        telemetry.update();

        //init servos
        armServo = (ServoImpl) hardwareMap.servo.get("arm_servo");
        clawServo = (ServoImpl) hardwareMap.servo.get("claw_servo");
        telemetry.addData("Arm servos", "initialized");
        telemetry.update();

        telemetry.addData("Arm init", "done");
        telemetry.update();

    }

    /**
     * Run during loop phase to run arm.
     * Dpad controls arm height and bumpers control arm turning and claw.
     */
    public static void loop(Gamepad gamepad) {

        //dpad controls height. up/down for top/bottom and left/right for cycling
        if (gamepad.dpad_up){
            runLift( LiftTarget.TALL );
        }

        if (gamepad.dpad_left) {

            runLift( curentLiftTarget.prev() );

        }

        if (gamepad.dpad_right) {

            runLift( curentLiftTarget.next() );

        }

        if (gamepad.dpad_down) {

            runLift( LiftTarget.JUNCTION );

        }

        if (gamepad.left_bumper) {

            turnArm();

        }

        if (gamepad.right_bumper) {

            runClaw();

        }

        telemetry.addData( "Arm target", curentLiftTarget.name() );
        telemetry.update();

    }

    /**
     * this is to reset arm
     */
    public static void stop() {

        telemetry.addData("Stopping OpMode", "");
        telemetry.update();

        armServo.setPosition(0);
        clawServo.setPosition(0);

        liftMotor.setTargetPosition(0);

        telemetry.addData("OpMode", "done");
        telemetry.update();

    }

  //the function section

    public int getLIFT_RANGE() {
        return LIFT_RANGE;
    }

    /**
     * rotates arm 180 degrees
     */
    public static void turnArm () {

        if( armServo.getPosition() == 0.0 ) {
            armServo.setPosition(1);
        }

        else if( armServo.getPosition() == 1 ) {
            armServo.setPosition(0);
        }

        else {
            armServo.setPosition(0);
        }

    }

    /**
     * rotates arm "degrees" degrees
     * @param degrees
     */
    public static void turnArm (int degrees) {

        armServo.setPosition( Math.min( Math.max( armServo.getPosition() + degrees, 0 ), 1 ) );

    }

    /**
     * toggles claw (open to cosed & vice versa)
     */
    public static void runClaw () {
        if( clawServo.getPosition() == 0 ) {
            clawServo.setPosition(1);
        }

        else{
            clawServo.setPosition(0);
        }
    }

    /**
     * will raise arm to height specified by input
     * @param liftTarget
     */
    public static void runLift (LiftTarget liftTarget) {

        curentLiftTarget = liftTarget;

        switch (liftTarget) { //swap 33.5 with max arm height

            case JUNCTION:
                liftMotor.setTargetPosition(0);
                break;

            case SHORT:
                liftMotor.setTargetPosition( (int)(LIFT_RANGE * 13.5 / 33.5) );
                break;

            case MEDIUM:
                liftMotor.setTargetPosition( (int)(LIFT_RANGE * 23.5 / 33.5) );
                break;

            case TALL:
                liftMotor.setTargetPosition(LIFT_RANGE);
                break;

            default:
                break;
        }

    }

    /**
     * will grab cone from back. bot must be aligned
     */
    public static void grabCone () {

        if (armServo.getPosition() != 0) {
            turnArm();
        }

        runLift(LiftTarget.JUNCTION);

        runClaw();

    }


    /**
     * will raise arm to height specified, rotate arm, and deposit cone
     * @param liftTarget
     */
    public static void dropCone (LiftTarget liftTarget) {

        runLift(liftTarget);

        turnArm();

        runClaw();
    }

    /**
     * will raise arm to height specified, rotate arm degrees specified, and deposit cone
     * @param liftTarget
     * @param degrees
     */
    public static void dropCone (LiftTarget liftTarget, int degrees) {

        runLift(liftTarget);

        turnArm(degrees);

        runClaw();
    }

}