package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.command.drive.DefaultRobotCentricDrive;
import org.firstinspires.ftc.teamcode.command.drive.SlowMode;


@TeleOp(name = "Drive TeleOp")
public class OpModeDrive extends BaseDriveOpMode {
    // image of gamepad: https://gm0.org/en/latest/_images/logitech-f310.png

    private GamepadEx driverOp1;

    private DefaultRobotCentricDrive robotCentricDrive;

    private SlowMode slowMode;
    private Button slowtime;


    @Override
    public void initialize() {
        super.initialize();
        /*
        Player1
            Left Stick X -> Strafe
            Right Stick Y -> Forward and Back
            Right Stick X -> turning
            //TODO try and change this so turning is triggers

            Left bumper -> toggles between slow mode and normal mode
         */

        driverOp1 = new GamepadEx(gamepad1);

        robotCentricDrive = new DefaultRobotCentricDrive(drive, () -> driverOp1.getLeftX(),
                () -> driverOp1.getRightY(), () -> driverOp1.getRightX());

        slowMode = new SlowMode(drive, () -> driverOp1.getLeftX(),
                () -> driverOp1.getRightY(), () -> driverOp1.getRightX());

        //TODO probably need to tune speedvalue inside of SlowMode to make this work properly
        slowtime = (new GamepadButton(driverOp1, GamepadKeys.Button.LEFT_BUMPER))
                .toggleWhenPressed(robotCentricDrive,slowMode);

        register(drive);
        drive.setDefaultCommand(robotCentricDrive);
    }
}
