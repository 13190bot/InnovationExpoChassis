package org.firstinspires.ftc.teamcode.teleOp.opmode;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.teleOp.command.claw.Grab;
import org.firstinspires.ftc.teamcode.teleOp.command.claw.Release;
import org.firstinspires.ftc.teamcode.teleOp.command.drive.DriveRobotCentric;
import org.firstinspires.ftc.teamcode.teleOp.command.drive.DriveSlowMode;
import org.firstinspires.ftc.teamcode.teleOp.command.lift.SetJunction;
import org.firstinspires.ftc.teamcode.util.Junction;

@TeleOp
public class MainOpMode extends BaseOpMode {
    private DriveRobotCentric robotCentricDrive;
    private DriveSlowMode slowMode;

    @Override
    public void initialize() {
        super.initialize();

        // drive use left and right triggers for turning
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



        gb1(GamepadKeys.Button.LEFT_BUMPER)
                .whileHeld(slowMode);


        gb2(GamepadKeys.Button.LEFT_BUMPER)
                .toggleWhenPressed(new Grab(claw).andThen(new SetJunction(lift, Junction.GROUND)),
                        new Release(claw).andThen(new SetJunction(lift, Junction.NONE)));


        gb2(GamepadKeys.Button.A)
                .whenPressed(new SetJunction(lift, Junction.GROUND));
        gb2(GamepadKeys.Button.X)
                .whenPressed(new SetJunction(lift, Junction.LOW));
        gb2(GamepadKeys.Button.B)
                .whenPressed(new SetJunction(lift, Junction.MEDIUM));
        gb2(GamepadKeys.Button.Y)
                .whenPressed(new SetJunction(lift, Junction.HIGH));
        gb2(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new SetJunction(lift, Junction.NONE));



        register(drive, lift, claw);
        drive.setDefaultCommand(robotCentricDrive);
    }
}
