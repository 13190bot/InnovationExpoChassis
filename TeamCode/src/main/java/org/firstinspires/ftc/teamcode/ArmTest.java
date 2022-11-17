package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ServoImpl;

import static android.os.SystemClock.sleep;


@TeleOp
public class ArmTest extends OpMode {

    DcMotor slideMotor;

    ServoImpl clamp;

    @Override
    public void init() {

        slideMotor = hardwareMap.dcMotor.get("slideMotor");
        clamp = (ServoImpl) hardwareMap.servo.get("clamp");
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        clamp.setPosition(0);

    }

    @Override
    public void loop() {

        telemetry.addData("1", "" );
        telemetry.update();
        slideMotor.setPower(.3);
        sleep(3000);
        telemetry.addData("2", "" );
        telemetry.update();
        clamp.setPosition(1);
        telemetry.addData("3", "" );
        telemetry.update();
        slideMotor.setPower(-.3);
        sleep(3000);
        telemetry.addData("4", "" );
        telemetry.update();
        clamp.setPosition(0);
        telemetry.addData("5", "" );
        telemetry.update();

    }

}
