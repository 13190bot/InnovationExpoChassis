package cf.devmello.ftc;

import com.qualcomm.robotcore.hardware.*;

public class ArmClassV2 {
    ArmClassV2(HardwareMap hardwareMap, double pullyRadius, double ticksPerRevolution, Gamepad gamepad, double startSpeed) {

        this.gamepad = gamepad;
        currentLiftTarget = LiftTarget.JUNCTION;
        MAX_LIFT_HEIGHT = (int)( 2 * 39 * Math.PI * pullyRadius * ticksPerRevolution ); //the 39 is how many inches we will use

        liftMotor = hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setTargetPosition(0);
        liftMotor.setPower(startSpeed);
        liftMotor.setMode( DcMotor.RunMode.RUN_TO_POSITION );

        armServo = (ServoImpl) hardwareMap.servo.get("arm_servo");
        clawServo = (ServoImpl) hardwareMap.servo.get("claw_servo");

    }

    //OpMode Functions (constructor acts as init phase) ----------------------------------------------------------------

    //TODO: make a proper loop with proper functionality
    /**
     * crude loop functionality to test individual motions. must eventually be replaced
     */
    public void loopArm () {

        //dpad to change lift height
        if (gamepad.dpad_up) {
            runLift(LiftTarget.TALL);
        } else if (gamepad.dpad_down) {
            runLift(LiftTarget.JUNCTION);
        } else if (gamepad.dpad_left) {
            runLift( currentLiftTarget.next() );
        } else if (gamepad.dpad_right) {
            runLift( currentLiftTarget.prev() );
        }

        if (gamepad.left_bumper) {
            runClaw();
        }

        if (gamepad.right_bumper) {
            turnArm();
        } else if (gamepad.right_trigger > .5) {
            turnArm( ( gamepad.right_trigger - .5 ) * 2 * Math.PI );
        }

    }

    public void stopArm () {
        armServo.setPosition(0);
        clawServo.setPosition(0);
        liftMotor.setTargetPosition(0);
    }

    //fields -----------------------------------------------------------------------------------------------------------

    private Gamepad gamepad;
    private DcMotor liftMotor;
    private Servo armServo, clawServo;
    private final int MAX_LIFT_HEIGHT;
    private LiftTarget currentLiftTarget;

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

    //methods ----------------------------------------------------------------------------------------------------------

    public void runClaw () {
        clawServo.setPosition(  ( (clawServo.getPosition() == 1) ? 0 : 1 )  );
    }

    /**
     * rotates arm radian radians
     * @param radians cant exceed pi (180 deg)
     */
    public void turnArm (double radians) {
        armServo.setPosition( radians/(2 * Math.PI) );
    }

    public void turnArm () {
        armServo.setPosition(1);
    }

    public void runLift (LiftTarget liftTarget) {

        switch (liftTarget) {

            case JUNCTION:
                currentLiftTarget = liftTarget;
                liftMotor.setTargetPosition(0); //39 from the max height used by he code
                break;

            case SHORT:
                currentLiftTarget = liftTarget;
                liftMotor.setTargetPosition(MAX_LIFT_HEIGHT * (17 / 39));
                break;

            case MEDIUM:
                currentLiftTarget = liftTarget;
                liftMotor.setTargetPosition(MAX_LIFT_HEIGHT * (26 / 39));
                break;

            case TALL:
                currentLiftTarget = liftTarget;
                liftMotor.setTargetPosition(MAX_LIFT_HEIGHT);
                break;

            default:
                break;
        }
    }

    public LiftTarget getCurrentLiftTarget() {
        return currentLiftTarget;
    }

    public double getCurrentHeightInTicks () {
        return liftMotor.getCurrentPosition();
    }

    /**
     * returns the percent of the range
     */
    public double getArmRotation () {
        return armServo.getPosition();
    }

    /**
     * zero position is closed servo (can reverse in this function)
     */
    public boolean isClawClosed () {
        return ( (clawServo.getPosition() == 0) ? true : false );
    }

    public double getLiftSpeed () {
        return liftMotor.getPower();
    }

    public void setLiftSpeed (double speed) {
        liftMotor.setPower(speed);
    }


}