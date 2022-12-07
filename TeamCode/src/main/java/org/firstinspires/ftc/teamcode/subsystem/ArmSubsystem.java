package org.firstinspires.ftc.teamcode.subsystem;


import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class ArmSubsystem extends SubsystemBase{

    private final ServoEx arm1, arm2, claw;

    private final DcMotor liftL, liftR;

    //TODO tune values appropriately
    private static int LOW = 0;
    private static int MEDIUM = 0;
    private static int HIGH = 0;
    private static int GROUND = 0;

    double speed = 0.5;

    public enum Junction {
        NONE,
        GROUND,
        LOW,
        MEDIUM,
        HIGH
    }


    public ArmSubsystem(ServoEx claw, ServoEx arm1, ServoEx arm2, DcMotor liftL, DcMotor liftR) {
        this.claw = claw;
        this.arm1 = arm1;
        this.arm2 = arm2;
        this.liftL = liftL;
        this.liftR = liftR;

        liftL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setLiftPosition(0);

        liftL.setDirection(DcMotorSimple.Direction.REVERSE);

        liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftL.setPower(speed);
        liftR.setPower(speed);
    }


    public void clawOpen() {claw.setPosition(1);}

    public void clawClose() {claw.setPosition(0);}

    //TODO tune values depending on servo placement
    public void armHome() {
        arm1.setPosition(0);
        arm2.setPosition(0);
    }

    public void armScore() {
        arm1.setPosition(1);
        arm2.setPosition(1);
    }

    public void armPos(double pos){
        arm1.setPosition(pos);
        arm2.setPosition(pos);
    }

    public void setLiftPosition(int targetPosition) {
        liftL.setTargetPosition(targetPosition);
        liftR.setTargetPosition(targetPosition);
    }

    /**
        true = goes up, false = go down
    */
    public void setLiftManual(boolean isGoingUp){
        liftL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if(isGoingUp){
            liftL.setPower(speed);
            liftR.setPower(speed);
        }
        else{
            liftL.setPower(-speed);
            liftR.setPower(-speed);
        }

    }

    public void setLiftAuto(){
        liftR.setTargetPosition(liftR.getCurrentPosition());
        liftL.setTargetPosition(liftL.getCurrentPosition());
        liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void grabConePrep() {
        setLiftPosition(0);
        armHome();
        //TODO ask dylan if he wants to to automatically grab and close for him
        clawOpen();
    }

    public void scoreCone(int junc) {
        setLiftPosition(junc);
        armScore();
        //TODO read top one
        clawOpen();

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
                // todo

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


}
