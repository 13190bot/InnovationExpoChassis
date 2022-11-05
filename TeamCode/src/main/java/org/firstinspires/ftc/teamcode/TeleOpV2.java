package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * an opMode that tries the MechDrive and ArmClass classes
 */
@TeleOp(name = "Prateek OpMode 2")
public class TeleOpV2 extends OpMode {

    @Override
    public void init() {

        //MechDrive.init();
        //ArmClass.init();

    }

    @Override
    public void loop() {

        //ArmClass.loop(gamepad1);
        //MechDrive.loop();

        //a faster way to stop opmode
        if (gamepad1.guide || gamepad2.guide) {

            terminateOpModeNow();

        }

    }

    @Override
    public void stop() {

        //ArmClass.stop();
        //MechDrive.stop();

    }

}
