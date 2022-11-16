package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ServoImpl;



@TeleOp
public class ConnectionTeleOp extends OpMode {


    DcMotor motorFrontLeft, motorBackLeft, motorFrontRight, motorBackRight, slideMotor;

    ServoImpl clamp;

    @Override
    public void init() {

        motorFrontLeft = hardwareMap.dcMotor.get("lf");
        motorBackLeft = hardwareMap.dcMotor.get("lb");
        motorFrontRight = hardwareMap.dcMotor.get("rf");
        motorBackRight = hardwareMap.dcMotor.get("rb");

        slideMotor = hardwareMap.dcMotor.get("slideMotor");

        clamp = (ServoImpl) hardwareMap.servo.get("clamp");

    }

    @Override
    public void loop() {

        telemetry.clear();

        telemetry.addData("lf motor", motorFrontLeft::getCurrentPosition);
        telemetry.addData("lb motor", motorBackLeft::getCurrentPosition);
        telemetry.addData("rf motor", motorFrontRight::getCurrentPosition);
        telemetry.addData("rb motor", motorBackRight::getCurrentPosition);

        telemetry.addData("clamp pos", clamp::getPosition );

        telemetry.update();

    }


}
