package org.firstinspires.ftc.teamcode.autonomous.manualAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Preload_Auto")
public class oneZeroAuto extends LinearOpMode {
    private Servo arm_servo, claw_servo;
    private DcMotor lb, lf, rb, rf;
    int SHORT_TIMER = 1000;
    int MEDIUM_TIMER = 2000;
    int LONG_TIMER = 3000;
    @Override
    public void runOpMode() throws InterruptedException {
        lb = hardwareMap.dcMotor.get("left_back");
        lf = hardwareMap.dcMotor.get("left_front");
        rb = hardwareMap.dcMotor.get("right_back");
        rf = hardwareMap.dcMotor.get("right_front");
        arm_servo = hardwareMap.servo.get("claw");
        claw_servo = hardwareMap.servo.get("arm");


        // Close claw (if not closed)
        claw_servo.setPosition(0);
        sleep(SHORT_TIMER);
        // Move arm up to preset for high junc
        // Go forward a certain distance (for now, leave it as 5 inches)
        // Open claw on top of pole
        // move arm down
        // Go to conestack
        // Grab another cone by closing claw
        // go to high junc, drop cone, then run parkauto



    }
}
