package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.DoubleSupplier;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.*;

/**
 * this turns most of Pranav's mechanum drive into a class that can be implemented in any opMode
 */
public class MechDrive {

    private DcMotor lf, rf, lb, rb;

    public MechDrive(DcMotor lf, DcMotor rf, DcMotor lb, DcMotor rb){
        this.lf = lf;
        this.rf = rf;
        this.lb = lb;
        this.rb = rb;
    }

    /**
     * call this in init phase
     */
    public void init() {
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //left side might need to be swapped
        lf.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.REVERSE);

        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    /**
     * call this in loop phase
     */
    public void drive(double px, double py, double pa) {

        double p1 = px + py - pa;
        double p2 = -px + py - pa;
        double p3 = -px + py + pa;
        double p4 = px + py + pa;

        double max = Math.max(1.0, Math.abs(p2));
        max = Math.max(max, Math.abs(p1));
        max = Math.max(max, Math.abs(p3));
        max = Math.max(max, Math.abs(p4));

        p2 /= max;
        p1 /= max;
        p3 /= max;
        p4 /= max;

        lf.setPower(p1);
        lb.setPower(p2);
        rf.setPower(p3);
        rb.setPower(p4);
    }

    /**
     * this is to reset motors. call in stop section
     */
    public void stop() {
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void brake(){
        lf.setPower(0);
        lb.setPower(0);
        rf.setPower(0);
        rb.setPower(0);
    }

}
