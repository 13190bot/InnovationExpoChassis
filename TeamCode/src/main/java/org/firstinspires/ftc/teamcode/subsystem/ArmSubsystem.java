package org.firstinspires.ftc.teamcode.subsystem;


import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.DcMotor;

public class ArmSubsystem extends SubsystemBase{

    private final ServoEx claw;

    private final DcMotor liftL, liftR;

    //TODO tune values appropriately (otherwise no work)
    private static int NONE = 0;
    private static int LOW = 0;
    private static int MEDIUM = 0;
    private static int HIGH = 0;
    private static int GROUND = 0;

    //change this to change lift speed
    double speed = 0.5;

    public enum Junction {
        NONE,
        GROUND,
        LOW,
        MEDIUM,
        HIGH
    }


    public ArmSubsystem(ServoEx claw, DcMotor liftL, DcMotor liftR) {
        this.claw = claw;
        this.liftL = liftL;
        this.liftR = liftR;

        liftL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setLiftPosition(0);

//        liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        liftR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


    public void clawOpen() {claw.setPosition(1);}

    public void clawClose() {claw.setPosition(0);}

    public void setLiftPosition(int targetPosition) {
        liftL.setTargetPosition(targetPosition);
        liftR.setTargetPosition(targetPosition);
    }

    public void LiftUp(){
//        liftL.setPower(speed);
//        liftR.setPower(speed);

        liftL.setTargetPosition(liftL.getCurrentPosition() - 20);
        liftR.setTargetPosition(liftR.getCurrentPosition() - 20);

    }
    public void LiftDown(){

//        liftL.setPower(-speed);
//        liftR.setPower(-speed);

        liftL.setTargetPosition(liftL.getCurrentPosition() + 20);
        liftR.setTargetPosition(liftR.getCurrentPosition() + 20);

    }
    public void LiftStop(){

        liftL.setPower(0);
        liftR.setPower(0);
    }

    public void setLiftManual(){
        liftL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public void setLiftAuto(){
        liftR.setTargetPosition(liftR.getCurrentPosition());
        liftL.setTargetPosition(liftL.getCurrentPosition());
        liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public int[] getLiftsPos () {
        return new int[] {
                liftL.getCurrentPosition(),
                liftR.getCurrentPosition()
        };
    }


    public void setJunction(ArmSubsystem arm, Junction junction) {
        switch(junction) {
            case NONE:
                arm.setLiftPosition(0);
                // arm.setLiftPosition(getLiftsPos()[0]);
                break;
            case GROUND:
                arm.setLiftPosition(GROUND);
                break;
            case LOW:
                arm.setLiftPosition(LOW);
                break;
            case MEDIUM:
                arm.setLiftPosition(MEDIUM);
                break;
            case HIGH:
                arm.setLiftPosition(HIGH);
        }
    }

    public void manualControlLift(double power) {
        liftL.setPower(power/2);
        liftR.setPower(power/2);
    }
}
