package org.firstinspires.ftc.teamcode.subsystem;

import android.util.Log;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ProfiledPIDController;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;

public class ArmSubsystem extends SubsystemBase{

    private final ServoEx claw;

    private final MotorEx slideL, slideR;


    //TODO tune height values
    public static int DEFAULT = 0;
    public static int GROUND = 0;
    public static int LOW = 0;
    public static int MEDIUM = 0;
    public static int HIGH = 0;


    //PID stuff for motors
    //TODO NEED TO TUNE THESE VALUES

    //follow advice in ctrlaltftc
    public static double slide_P = 0;

    public static double slide_I = 0;

    public static double slide_D = 0;

    //try using ftc dashboard to get these values
    public static double maxVel = 1000;

    public static double maxAccel = 1000;

    //https://www.ctrlaltftc.com/the-pid-controller/tuning-methods-of-a-pid-controller

    //both motors need to be same spec

    private final ProfiledPIDController slide_pidL = new ProfiledPIDController(slide_P, slide_I, slide_D,
            new TrapezoidProfile.Constraints(maxVel, maxAccel));

    private final ProfiledPIDController slide_pidR = new ProfiledPIDController(slide_P, slide_I, slide_D,
            new TrapezoidProfile.Constraints(maxVel, maxAccel));

    private double out_left;

    private double out_right;

    public static double tolerance = 10;

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

    public ArmSubsystem(ServoEx claw,  MotorEx slideL, MotorEx slideR){
        this.claw = claw;
        this.slideL = slideL;
        this.slideR = slideR;

        slide_pidL.setTolerance(tolerance);
        slide_pidR.setTolerance(tolerance);

        //todo make sure this works right (on start should just stay at the bottom)
        slide_pidL.setGoal(0);
        slide_pidR.setGoal(0);


        moveToJunction(Junction.DEFAULT);//might not need this part

    }

    //claw control

    public void clawOpen() {claw.setPosition(clawOpen);}

    public void clawClose() {claw.setPosition(clawClose);}

    //slide pid stuff

    public void loopPID(){
        out_left = slide_pidL.calculate(slideL.getCurrentPosition());
        out_right = slide_pidR.calculate(slideR.getCurrentPosition());
        slideL.set(out_left);
        slideR.set(out_right);
    }

    public void setSlidesPID (int goal){
        slide_pidL.setGoal(goal);
        slide_pidR.setGoal(goal);
    }

    public void moveToJunction(Junction junction){
        currentGoal = junction;
        switch(junction){
            case DEFAULT:
                setSlidesPID(DEFAULT);
                break;
            case GROUND:
                setSlidesPID(GROUND);
                break;
            case LOW:
                setSlidesPID(LOW);
                break;
            case MEDIUM:
                setSlidesPID(MEDIUM);
                break;
            case HIGH:
                setSlidesPID(HIGH);
                break;
        }
        out_left = slide_pidL.calculate(slideL.getCurrentPosition());

        while(!slide_pidL.atGoal()){
            out_left = out_left = slide_pidL.calculate(slideL.getCurrentPosition());
            Log.d("output", "" + out_left);
            Log.d("error", "" + slide_pidL.getPositionError());
            Log.d("encoder", "" + slideL.getCurrentPosition());

            out_left = out_right;

            slideL.set(out_left);
            slideR.set(out_right);
        }


    }

    public void changeSetPoint(double input){
        slide_pidL.setGoal((int)(slideL.getCurrentPosition()+input*manualSlideSpeed));
        slide_pidR.setGoal((int)(slideR.getCurrentPosition()+input*manualSlideSpeed));
        Log.d("setpoint left", "" + slide_pidL.getSetpoint().position);
        Log.d("setpoint right", "" + slide_pidR.getSetpoint().position);
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

    public double getSlideLError(){return slide_pidL.getPositionError();}

    public double getSlideRError(){return slide_pidR.getPositionError();}

    public double getClawPos(){return claw.getPosition();}

}
