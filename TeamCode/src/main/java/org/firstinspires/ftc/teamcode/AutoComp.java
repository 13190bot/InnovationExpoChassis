package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import static android.os.SystemClock.sleep;

public class AutoComp extends OpMode {

    DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;

    @Override
    public void init() {
        motorFrontLeft = hardwareMap.dcMotor.get("front_left_motor");
        motorBackLeft = hardwareMap.dcMotor.get("back_left_motor");
        motorFrontRight = hardwareMap.dcMotor.get("front_right_motor");
        motorBackRight = hardwareMap.dcMotor.get("back_right_motor");

        DcMotor[] motors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};

        for(DcMotor motor : motors) {

            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void loop() {

        DcMotor[] motors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};

        for(DcMotor motor : motors) {

            motor.setPower(.5);

        }

        sleep(1000);

        for(DcMotor motor : motors) {

            motor.setPower(0);

        }

    }
}
