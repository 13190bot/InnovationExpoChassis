package org.firstinspires.ftc.teamcode.ftcLib.subsystem;

import android.util.Log;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ProfiledPIDController;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;

public class RawArmSubsystem extends SubsystemBase{

    private final ServoEx claw;

    private final MotorEx slideL, slideR;


    //TODO tune height values
    public static int DEFAULT = 0;
    public static int GROUND = 0;
    public static int LOW = 0;
    public static int MEDIUM = 0;
    public static int HIGH = 0;

    public static int SlidePosMax = 0; //need to define max height

    public static int SlidePosMin = 0; //need to define min height

    private double calc;

    private double out_left;

    private double out_right;

    public static Junction currentGoal = Junction.DEFAULT;

    //TODO TUNE THESE VALUES

    private final double clawOpen = 1;//servo position for open

    private final double clawClose = 0;//servo position for close

    private final double manualSlideSpeed = 0.5; //higher is faster



    public enum Junction{
        DEFAULT,
        GROUND,
        LOW,
        MEDIUM,
        HIGH
    }

    //constructor

    public RawArmSubsystem(ServoEx claw,  MotorEx slideL, MotorEx slideR){
        this.claw = claw;
        this.slideL = slideL;
        this.slideR = slideR;

        slideL.set(manualSlideSpeed);
        slideR.set(manualSlideSpeed);

        //todo make sure this works right (on start should just stay at the bottom)
        moveToJunction(Junction.DEFAULT);//might not need this part

    }

    //claw control

    public void clawOpen() {claw.setPosition(clawOpen);}

    public void clawClose() {claw.setPosition(clawClose);}

    //slide stuff
    public void moveToJunction(Junction junction){
        currentGoal = junction;
        switch(junction){
            case DEFAULT:
                slidesTargetPos(DEFAULT);
                break;
            case GROUND:
                slidesTargetPos(GROUND);
                break;
            case LOW:
                slidesTargetPos(LOW);
                break;
            case MEDIUM:
                slidesTargetPos(MEDIUM);
                break;
            case HIGH:
                slidesTargetPos(HIGH);
                break;
        }


    }

    public void manualSlide(double input){
        calc = slideL.getCurrentPosition()+input*manualSlideSpeed;
        if(calc > SlidePosMax){
            slidesTargetPos(SlidePosMax);
        }
        else if(calc < SlidePosMin){
            slidesTargetPos(SlidePosMin);
        }
        else{
            slidesTargetPos((int) calc);
        }
    }

    public void slidesTargetPos(int targetPos){
        slideR.setTargetPosition(targetPos);
        slideL.setTargetPosition(targetPos);
    }


    public int getSlideLEncoder(){
        return slideL.getCurrentPosition();
    }
    public int getSlideREncoder(){
        return slideR.getCurrentPosition();
    }

    public double getSlideLPower(){
        return out_left;
    }
    public double getSlideRPower(){
        return out_right;
    }


    public double getClawPos(){return claw.getPosition();}

}
