package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

@TeleOp (name = "Wheels Test", group = "Testing")
public class WheelsTest extends OpMode {
    boolean safeMode = false;
    DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;

    public void init () {
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorBackRight = hardwareMap.dcMotor.get("backRight");


        DcMotor[] motors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};

        for(DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);




        telemetry.addData("init", "done");

    }
    public void loop () {



        if (gamepad1.y) {
            if (safeMode) {
                telemetry.addData("Error cannot run Safe mode on (disable by L and R bumper) ", safeMode);
                telemetry.update();
            } else {
                motorFrontLeft.setPower(1);
                motorBackLeft.setPower(1);
                motorFrontRight.setPower(1);
                motorBackRight.setPower(1);
            }


        } else {
            motorFrontLeft.setPower(0);
            motorBackLeft.setPower(0);
            motorFrontRight.setPower(0);
            motorBackRight.setPower(0);
        }
    if (gamepad1.left_bumper){  //For fixing the robot and disable wheel movement
        if (gamepad1.right_bumper){
            if (safeMode == false){
                safeMode = true;
                telemetry.addData("Safe Mode on :", safeMode);
                telemetry.update();
            } else {
                safeMode = false;
                telemetry.addData("Safe mode off", safeMode);
                telemetry.update();
            }

        }
    }

    }


}