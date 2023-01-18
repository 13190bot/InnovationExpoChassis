package org.firstinspires.ftc.teamcode.ftcLib.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public class ArmSubsystem extends SubsystemBase{

    private final MotorEx slideL, slideR;
    private final ServoEx claw;

    public static int SlidePosMax = 10000; //need to define max height
    public static int SlidePosMin = -100; //need to define min height

    private final double clawOpen = 1;//servo position for open
    private final double clawClose = 0;//servo position for close

    private final double manualSlideSpeed = 0.7; //higher is faster


    //constructor
    public ArmSubsystem(MotorEx slideL, MotorEx slideR, ServoEx claw){
        this.slideL = slideL;
        this.slideR = slideR;
        this.claw = claw;

        slideL.setRunMode(Motor.RunMode.RawPower);
        slideR.setRunMode(Motor.RunMode.RawPower);
    }

    //claw control
    public void clawOpen(){
        claw.setPosition(clawOpen);
    }

    public void clawClose(){
        claw.setPosition(clawClose);
    }

    //manual slides control
    public void manualSlide(double input){
        double calc = slideR.getCurrentPosition()+input*manualSlideSpeed;
        if(calc > SlidePosMax || calc < SlidePosMin){
            slidesSetPower(0);
        }
        else{
            slidesSetPower(input*manualSlideSpeed);
        }
    }

    //helper method
    public void slidesSetPower(double power){
        slideL.set(power);
        slideR.set(power);
    }

    //accessors
    public int getSlideLEncoder(){
        return slideL.getCurrentPosition();
    }
    public int getSlideREncoder(){
        return slideR.getCurrentPosition();
    }

    public double getSLideLPower(){
        return slideL.get();
    }
    public double getSLideRPower(){
        return slideR.get();
    }

}
