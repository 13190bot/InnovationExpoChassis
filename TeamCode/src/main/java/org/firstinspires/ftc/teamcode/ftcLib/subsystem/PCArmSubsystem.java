package org.firstinspires.ftc.teamcode.ftcLib.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;

public class PCArmSubsystem extends SubsystemBase {

    private final Motor slideL, slideR;


    //TODO tune height values
    public static int DEFAULT = 0;
    public static int GROUND = 230;
    public static int LOW = 540;
    public static int MEDIUM = 770;
    public static int HIGH = 970;

    public static int SlidePosMax = 10000; //need to define max height
    public static int SlidePosMin = -100; //need to define min height

    public static PCArmSubsystem.Junction currentGoal = PCArmSubsystem.Junction.DEFAULT;

    private final double clawOpen = 1;//servo position for open

    private final double clawClose = 0;//servo position for close

    private final double manualSlideSpeed = 0.5; //higher is faster

    //TODO tune
    private final double PositionCoefficient = 0.05;
    private final double PositionTolerance = 10;


    public enum Junction{
        DEFAULT,
        GROUND,
        LOW,
        MEDIUM,
        HIGH
    }

    //constructor
    public PCArmSubsystem(Motor slideL, Motor slideR){
        this.slideL = slideL;
        this.slideR = slideR;

        Motor slides[] = {this.slideL, this.slideR};
        for(Motor slide : slides){
            slide.setRunMode(Motor.RunMode.PositionControl);
            slide.setPositionCoefficient(PositionCoefficient);
            slide.setPositionTolerance(PositionTolerance);
        }

    }

    //slide stuff
    public void moveToJunction(PCArmSubsystem.Junction junction){
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

        while(!slideL.atTargetPosition()){
            slidesSetPower(manualSlideSpeed);
        }
        slidesSetPower(0);

    }

    public void manualSlide(double input){
        double calc = slideL.getCurrentPosition() + input * manualSlideSpeed;

        if(calc > SlidePosMax){
            slidesSetPower(0);
        }
        else if(calc < SlidePosMin){
            slidesSetPower(0);
        } else if (input == 0) {
            //might be able to tune this value to reduce lift falling
            slidesSetPower(0);
        } else{
            slidesSetPower(input*manualSlideSpeed);
        }
    }

    //helper methods
    public void slidesTargetPos(int targetPos){
        slideR.setTargetPosition(targetPos);
        slideL.setTargetPosition(targetPos);
    }

    public void slidesSetPower(double power){
        slideL.set(power);
        slideR.set(power);
    }


    public int getSlideLEncoder(){
        return slideL.getCurrentPosition();
    }
    public int getSlideREncoder(){
        return slideR.getCurrentPosition();
    }

}
