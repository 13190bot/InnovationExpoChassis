package org.firstinspires.ftc.teamcode.ftcLib.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;


public class RawArmSubsystem extends SubsystemBase{

    private final MotorEx slideL, slideR;


    //TODO tune height values
    public static int DEFAULT = 0;
    public static int GROUND = 230;
    public static int LOW = 540;
    public static int MEDIUM = 770;
    public static int HIGH = 970;

    public static int SlidePosMax = 1000; //need to define max height

    public static int SlidePosMin = 0; //need to define min height

    private double calc;

    public static Junction currentGoal = Junction.DEFAULT;

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

    public RawArmSubsystem(MotorEx slideL, MotorEx slideR){
        this.slideL = slideL;
        this.slideR = slideR;

        //todo make sure this works right (on start should just stay at the bottom)

    }

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
        slidesGo();
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
      slideL.set(input*manualSlideSpeed);
      slideR.set(input*manualSlideSpeed);
    }


    //helper methods
    public void slidesTargetPos(int targetPos){
        slideR.setTargetPosition(targetPos);
        slideL.setTargetPosition(targetPos);
    }

    public void slidesGo(){
        slideL.set(manualSlideSpeed);
        slideR.set(manualSlideSpeed);

    }


    public int getSlideLEncoder(){
        return slideL.getCurrentPosition();
    }
    public int getSlideREncoder(){
        return slideR.getCurrentPosition();
    }

    public double getSlideLVelocity(){
        return slideL.getVelocity();
    }
    public double getSlideRVelocity(){
        return slideR.getVelocity();
    }


}
