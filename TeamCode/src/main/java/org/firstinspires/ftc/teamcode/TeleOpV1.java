package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ServoImpl;

import static org.firstinspires.ftc.teamcode.Funcs.armMotor;
import static org.firstinspires.ftc.teamcode.Funcs.armServo;
import static org.firstinspires.ftc.teamcode.Funcs.clawServo;

//is a one-gamepad TeleOp
@TeleOp(group = "outdated")
public class TeleOpV1 extends OpMode {

    DcMotor lf, lb, rf, rb;

    //drive code. called in loop
    private void drive() {

        double px = gamepad1.left_stick_x;
        double py = -gamepad1.left_stick_y;
        double pa = gamepad1.right_stick_y;

        if (Math.abs(pa) < 0.05) pa = 0;

        double p1 = px + py + pa;
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

    //motors/servos are assigned(initialized), encoders are reset, and run-modes are set.
    @Override
    public void init() {

        //initialising drive motors
        lf = hardwareMap.dcMotor.get("left_front");
        lb = hardwareMap.dcMotor.get("left_back");
        rf = hardwareMap.dcMotor.get("right_front");
        rb = hardwareMap.dcMotor.get("right_back");
        telemetry.addData("drive", "initialized");
        telemetry.update();

        //initialising arm motor
        armMotor = hardwareMap.dcMotor.get("arm_motor");
        telemetry.addData("arm motor", "initialized");
        telemetry.update();

        //resetting motor encoders
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //setting motors to using encoders and taking power
        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("motors", "initialized");
        telemetry.update();


        //initialising servos
        armServo = (ServoImpl) hardwareMap.servo.get("arm_servo");
        clawServo = (ServoImpl) hardwareMap.servo.get("claw_servo");

        telemetry.addData("servos", "initialized");
        telemetry.update();


        //declaring init done
        telemetry.addData("init", "finished");
        telemetry.update();

    }

    //called constantly once drivers hit play button
    @Override
    public void loop() {

        //can press the left trigger to slow down the speed of the arm raising or lowering. the more you press, the
        //slower it goes
        double armSlow = 1 - (.5 * gamepad1.left_trigger);

        //drive code
        drive();

        //armMotor has no limits, drivers have to be careful
        // use dpad up to extend arm up
        if (gamepad1.dpad_up) {
            armMotor.setPower(.5 * armSlow);
        }

        //use dpad down to retract arm down
        else if (gamepad1.dpad_down) {
            armMotor.setPower(-.5 * armSlow);
        }

        //if no dpad signal, armMotor stops
        else {
            armMotor.setPower(0);
        }

        //left bumper will trigger arm to rotate 180 degrees
        if (gamepad1.left_bumper) {
            Funcs.turnArm();
        }

        //right bumper will cause the claw to open/close
        if (gamepad1.right_bumper) {
            Funcs.runClaw();
        }

    }

    //runs when OpMode is stopped
    @Override
    public void stop() {

        //tells drivers not to worry (they might be confused cause servos will be running and such)
        telemetry.addData("OpMode", "stopping");
        telemetry.update();

        //resets servos
        Funcs.armServo.setPosition(0);
        Funcs.clawServo.setPosition(0);

        ////gives runtime and states end of OpMode
        telemetry.addData("OpMode", "stopped");
        telemetry.addData("runtime", this::getRuntime );
        telemetry.update();
    }
}
