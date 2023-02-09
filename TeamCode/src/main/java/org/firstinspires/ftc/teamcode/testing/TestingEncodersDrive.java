package org.firstinspires.ftc.teamcode.testing;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import static android.os.SystemClock.sleep;

@TeleOp(name = "W ENCODER TESTING", group = "Testing")
public class TestingEncodersDrive extends OpMode {

    DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;

    double motorSpeed = 0.5;
    boolean Test_Automatically = false;

    public void init() {

        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorBackRight = hardwareMap.dcMotor.get("backRight");

        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor[] motors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};

        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }


        telemetry.addData("init", "done");


        if (Test_Automatically == true){
            for (DcMotor motor : motors) {
                motor.setPower(motorSpeed);
            }
            sleep(10000);

            for (DcMotor motor : motors) {
                motor.setPower(0);
            }


        }
        telemetry.addData("init", "done");
    }

    public void loop() {

        //nothing here is reversed so all motors should spin clockwise when given input. If they dont, fix wiring.......



        telemetry.addData("FL", motorFrontLeft.getCurrentPosition());
        telemetry.addData("FR", motorFrontRight.getCurrentPosition());
        telemetry.addData("BL", motorBackLeft.getCurrentPosition());
        telemetry.addData("BR", motorBackRight.getCurrentPosition());






        if(gamepad1.a){



            DcMotor[] motors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};

            for (DcMotor motor : motors) {
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }


        }
    }
}
