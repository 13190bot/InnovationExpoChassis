package org.firstinspires.ftc.teamcode.normal;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ServoImpl;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.ArmSubsystem;

@TeleOp (name = "Normie TeleOp v2")
public class NormalTeleOp2 extends OpMode {

    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackLeft;
    DcMotor motorBackRight;
    static DcMotor slideL;
    static DcMotor slideR;
    ServoImpl claw;

    // slowmode value
    double mul = 1;

    double regularSpeed = 0.5; //Lift values
    double slowSpeed = 0.1;
    boolean slowTime = false;
    boolean slowSlide = false; //Slowmode Lift
    double turnSpeed = 0.5;

    /*
        Slowmode
        Lift -> Hold
        Drive -> Toggle

        How toggle works:
        First, what is pressing? It is when a button was previously (on last check) not held down and is now being held down.
        This is all so that it doesn't repeatedly switch on and off.
        Now just implement that!
    */
    boolean lastSlowmodeButton = false;
    boolean lastSlowmodeLiftButton = false;

    int groundJunct = 0; // Change these values
    int smallJunct = 0;
    int mediumJunct = 0;
    int highJunct = 0;


    public void init () {
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorBackRight = hardwareMap.dcMotor.get("backRight");


        DcMotor[] motors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};

        for(DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        //TODO correct motors reversed?
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);



        slideL = hardwareMap.dcMotor.get("slideL");
        slideR = hardwareMap.dcMotor.get("slideR");

        DcMotor[] slides = {slideL, slideR};
        for(DcMotor slide : slides) {
            slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }


        slideR.setPower(0); //fixed bug Prateek caused
        slideL.setPower(0);

        claw = hardwareMap.get(ServoImpl.class, "claw");


        telemetry.addData("init", "done");
        telemetry.update();
    }
    public void loop () {
/*
Player 2 :

Right bumper : open claw
Left bumper : close claw
Back : Slow mode toggle
Dpad down : -10 down
Dpad up : +10 up
X : medium junction
A : small junction
Y : high junction
b : ground junction


 */


        //claw manipulation
        if(gamepad2.right_bumper)claw.setPosition(1);
        if(gamepad2.left_bumper)claw.setPosition(0);


        if (gamepad2.back) {
            if (!lastSlowmodeLiftButton) {
                // okay, last check the button was up. now it is PRESSED
                slowSlide = !slowSlide; // toggle
            }
            lastSlowmodeLiftButton = true;
        } else {
            lastSlowmodeLiftButton = false;
        }

        //Presets
        if (gamepad2.x) { //Medium junction
            slideTarget(mediumJunct);
            telemetry.addData("going to medium junction", mediumJunct);
            telemetry.update();
        } else if (gamepad2.a) { //Small junction
            slideTarget(smallJunct);
            telemetry.addData("going to small junction", smallJunct);
            telemetry.update();
        } else if (gamepad2.y) { // High Junction
            slideTarget(highJunct);
            telemetry.addData("going to high junction", highJunct);
            telemetry.update();
        } else if (gamepad2.b) { // Ground Junction
            slideTarget(groundJunct);
            telemetry.addData("going to ground junction", groundJunct);
            telemetry.update();
        } else if (gamepad2.dpad_down) {
            if (!slowSlide) {
                moveSlide(-regularSpeed); //Regular
            } else {
                moveSlide(-slowSpeed); //Slow
            }
            telemetry.addData("Dpad down. Slow Slide :", slowSlide);
            telemetry.update();
        } else if (gamepad2.dpad_up) {
            if (!slowSlide) {
                moveSlide(+regularSpeed); //Regular
            } else {
                moveSlide(+slowSpeed); //Slow
            }
            telemetry.addData("Dpad up. Slow Slide :", slowSlide);
            telemetry.update();

        }
        else if (!slideL.isBusy() & !slideR.isBusy() ) {
            moveSlide(0); //Keeps at same pos
        }

        //run drive
        double Y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double X = gamepad1.right_stick_x; // Counteract imperfect strafing
        double rx = gamepad1.right_trigger;
        double lx = gamepad1.left_trigger;


        if (gamepad1.left_bumper) {
            if (!lastSlowmodeButton) {
                // okay, last check the button was up. now it is PRESSED
                slowTime = !slowTime; // toggle
                telemetry.addData("Slow time ", slowTime);
                telemetry.update();
            }
            lastSlowmodeButton = true;
        } else {
            lastSlowmodeButton = false;
        }


        if (slowTime) {
            mul = 0.5;
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

        motorFrontLeft.setPower(-frontLeftPower);
        motorBackLeft.setPower(-backLeftPower);
        motorFrontRight.setPower(-frontRightPower);
        motorBackRight.setPower(-backRightPower);

    }

    private static void moveSlide(double speed) {
        slideL.setPower(-speed); //reversed
        slideR.setPower(speed);
    }

    private static void slideTarget(int pos) {
        slideL.setTargetPosition(pos);
        slideR.setTargetPosition(pos);
    }

}
