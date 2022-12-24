package org.firstinspires.ftc.teamcode.subsystem;

import android.util.Log;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ProfiledPIDController;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;

public class ArmPIDSubsystem extends SubsystemBase{

    private final ServoEx claw, arm1, arm2;

    private final MotorEx slideL, slideR;


    //TODO tune height values
    public static int NONE = 0;
    public static int GROUND = 0;
    public static int LOW = 0;
    public static int MEDIUM = 0;
    public static int HIGH = 0;


    //PID stuff for motors
    //TODO NEED TO TUNE THESE VALUES
    public static double slide_P = 0.003;

    public static double slide_I = 0.05;

    public static double slide_D = 0.0003;

    public static double maxVel = 1000;

    public static double maxAccel = 1000;

    //https://www.ctrlaltftc.com/the-pid-controller/tuning-methods-of-a-pid-controller

    private final ProfiledPIDController slide_pidL = new ProfiledPIDController(slide_P, slide_I, slide_D,
            new TrapezoidProfile.Constraints(maxVel, maxAccel));

    private final ProfiledPIDController slide_pidR = new ProfiledPIDController(slide_P, slide_I, slide_D,
            new TrapezoidProfile.Constraints(maxVel, maxAccel));

    private double out_left;

    private double out_right;

    public static double tolerance = 10;

    public static Junction currentGoal = Junction.NONE;

    //TODO TUNE THESE VALUES
    private final double arm1_home = 0;

    private final double arm2_home = 0;

    private final double arm1_score = 0;

    private final double arm2_score = 0;

    private final double clawOpen = 1;

    private final double clawClose = 0;

    private final double manualSlideSpeed = 0;

    public enum Junction{
        NONE,
        GROUND,
        LOW,
        MEDIUM,
        HIGH
    }

    //constructor

    public ArmPIDSubsystem(ServoEx claw, ServoEx arm1, ServoEx arm2, MotorEx slideL, MotorEx slideR){
        this.claw = claw;
        this.arm1 = arm1;
        this.arm2 = arm2;
        this.slideL = slideL;
        this.slideR = slideR;

        slide_pidL.setTolerance(tolerance);
        slide_pidR.setTolerance(tolerance);

        slide_pidL.setGoal(0);
        slide_pidR.setGoal(0);

    }

    //claw control

    public void clawOpen() {claw.setPosition(clawOpen);}

    public void clawClose() {claw.setPosition(clawClose);}


    //arm control

    public void armHome() {
        arm1.setPosition(arm1_home);
        arm2.setPosition(arm2_home);
    }

    //TODO tune values after servo placed
    public void armScore() {
        arm1.setPosition(arm1_score);
        arm2.setPosition(arm2_score);
    }

    //would this even work? would both servos have the same motion thingy???
    public void armPos(double pos){
        arm1.setPosition(pos);
        arm2.setPosition(pos);
    }


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
            case NONE:
                setSlidesPID(NONE);
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

            slideL.set(out_left);
            slideR.set(out_left);
        }


    }

    public void changeSetPoint(double input){
        slide_pidL.setGoal((int)(slideL.getCurrentPosition()+input*manualSlideSpeed));
        slide_pidL.setGoal((int)(slideL.getCurrentPosition()+input*manualSlideSpeed));
    }





}
