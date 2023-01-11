package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp (name = "Independent Motor Control")
public class indepententMotorControl extends OpMode {

    DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;

    double motorSpeed = 1.0;

    public void init() {

        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorBackRight = hardwareMap.dcMotor.get("backRight");

        DcMotor[] motors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};

        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }


        telemetry.addData("init", "done");

    }

    public void loop() {

        //nothing here is reversed so all motors should spin clockwise when given input. If they dont, fix wiring.......

        if (gamepad1.dpad_up) motorFrontLeft.setPower(motorSpeed);
        else motorFrontLeft.setPower(0);

        if (gamepad1.dpad_down) motorFrontRight.setPower(motorSpeed);
        else motorFrontRight.setPower(0);

        if (gamepad1.dpad_right) motorBackRight.setPower(motorSpeed);
        else motorBackRight.setPower(0);

        if (gamepad1.dpad_left) motorBackLeft.setPower(motorSpeed);
        else motorBackLeft.setPower(0);

    }

}

