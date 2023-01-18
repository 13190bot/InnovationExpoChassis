package org.firstinspires.ftc.teamcode.normal;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp (name = "Final Normie")
public class FinalNormal extends OpMode {

    protected MotorEx fL, fR, bL, bR;
    protected MotorEx slideLeft, slideRight;
    protected ServoEx claw;

    // slowmode value
    double mul = 1;
    double turnSpeed = 0.5;
    double slowFactor = 0.5;
    double manualSlideSpeed = 0.7;
    double SlidePosMax = 10000;
    double SlidePosMin = -100;

    /*
        Slowmode
        Lift -> Hold
        Drive -> Toggle

        How toggle works:
        First, what is pressing? It is when a button was previously (on last check) not held down and is now being held down.
        This is all so that it doesn't repeatedly switch on and off.
        Now just implement that!
    */

    public void init () {
        initHardware();
        setUpHardwareDevices();

        telemetry.addData("init", "done");
        telemetry.update();
    }
    public void loop () {
// claw manipulation
        if(gamepad2.right_bumper)claw.setPosition(1);
        if(gamepad2.left_bumper)claw.setPosition(0);


        if(gamepad2.right_stick_y > 0.3 || gamepad2.right_stick_y < -0.3){
            double calc = slideRight.getCurrentPosition()+gamepad2.right_stick_y*manualSlideSpeed;
            if(calc > SlidePosMax || calc < SlidePosMin){
                slidesSetPower(-1*gamepad2.right_stick_y/0.1);
            }
            else{
                slidesSetPower(gamepad2.right_stick_y*manualSlideSpeed);
            }
        }


        //drive
        double Y = -gamepad1.left_stick_y;
        double X = gamepad1.right_stick_x;
        double rx = gamepad1.right_trigger;
        double lx = gamepad1.left_trigger;



        if (gamepad2.left_bumper) {
            mul = slowFactor;
        } else {
            mul = 1;
        }


        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = (Math.max(Math.abs(Y) + Math.abs(X) , 1));

        double frontLeftPower = (Y + X ) / denominator, backLeftPower= (Y - X ) / denominator, frontRightPower = (Y - X ) / denominator, backRightPower = (Y + X ) / denominator;

        if(rx>0.3) {
            frontLeftPower = backLeftPower = turnSpeed;
            frontRightPower = backRightPower = -turnSpeed;
        }
        if(lx>0.3) {
            frontLeftPower = backLeftPower = -turnSpeed;
            frontRightPower = backRightPower = turnSpeed;
        }

        frontLeftPower *= mul;
        backLeftPower *= mul;
        frontRightPower *= mul;
        backRightPower *= mul;

        fL.set(-frontLeftPower);
        bL.set(-backLeftPower);
        fR.set(-frontRightPower);
        bR.set(-backRightPower);

    }

    protected void initHardware() {
        fL = new MotorEx(hardwareMap, "frontLeft");
        fR = new MotorEx(hardwareMap, "frontRight");
        bL = new MotorEx(hardwareMap, "backLeft");
        bR = new MotorEx(hardwareMap, "backRight");

        slideLeft = new MotorEx(hardwareMap, "slideL");
        slideRight = new MotorEx(hardwareMap, "slideR");

        //TODO find min and max
        claw = new SimpleServo(hardwareMap, "claw", 0, 120);
    }

    protected void setUpHardwareDevices() {
        //motor reversal
        slideRight.setInverted(true);
        bR.setInverted(true);

        slideLeft.resetEncoder();
        slideRight.resetEncoder();

        //sets all motors to brake.
        MotorEx[] motors = {slideLeft, slideRight, fL, fR, bL, bR};
        for(MotorEx motor : motors){
            motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        }

    }

    public void slidesSetPower(double power){
        slideLeft.set(power);
        slideRight.set(power);
    }



}
