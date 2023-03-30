package org.firstinspires.ftc.teamcode.teleOp.opmode;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.teleOp.command.drive.DriveRobotCentric;
import org.firstinspires.ftc.teamcode.teleOp.command.drive.DriveSlowMode;
import org.firstinspires.ftc.teamcode.util.ConeStack;
import org.firstinspires.ftc.teamcode.util.Junction;

@TeleOp
public class MainOpMode1PTEST extends BaseOpMode {
    private DriveRobotCentric robotCentricDrive;
    private DriveSlowMode slowMode;

    @Override
    public void initialize() {
        super.initialize();

        // drive
        robotCentricDrive = new DriveRobotCentric(drive,
                gamepadEx1::getRightX,
                gamepadEx1::getLeftY,
                () -> gamepadEx1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                () -> gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));

        slowMode = new DriveSlowMode(drive,
                gamepadEx1::getRightX,
                gamepadEx1::getLeftY,
                () -> gamepadEx1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                () -> gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));
        /*
        gb1(GamepadKeys.Button.LEFT_STICK_BUTTON)
                .whileHeld(slowMode);
         */

        gb1(GamepadKeys.Button.RIGHT_BUMPER)
                .whileHeld(slowMode);


        register(drive);
        drive.setDefaultCommand(robotCentricDrive);
    }
}
