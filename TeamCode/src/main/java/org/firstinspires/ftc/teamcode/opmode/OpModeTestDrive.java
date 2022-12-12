package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.command.drive.DefaultRobotCentricTestDrive;
import org.firstinspires.ftc.teamcode.command.drive.SlowModeTest;
import org.firstinspires.ftc.teamcode.opmode.baseOpModes.BaseDriveOpModeTest;


@TeleOp(name = "Drive Test TeleOp")
public class OpModeTestDrive extends BaseDriveOpModeTest {
    // image of gamepad: https://gm0.org/en/latest/_images/logitech-f310.png

    private GamepadEx driverOp1;

    private DefaultRobotCentricTestDrive robotCentricDrive;

    private SlowModeTest slowMode;
    private Button slowtime;


    @Override
    public void initialize() {
        super.initialize();
        /*
        Player1
            Right Stick X -> Strafe
            Left Stick Y -> Forward and Back
            Left Stick X -> rotation

            Left bumper -> toggles between slow mode and normal mode\
         */

        driverOp1 = new GamepadEx(gamepad1);

        robotCentricDrive = new DefaultRobotCentricTestDrive(drive,
                () -> driverOp1.getRightX(),
                () -> driverOp1.getLeftY(),
                () -> driverOp1.getLeftX());

        slowMode = new SlowModeTest(drive,
                () -> driverOp1.getRightX(),
                () -> driverOp1.getLeftY(),
                () -> driverOp1.getLeftX());

        slowtime = (new GamepadButton(driverOp1,
                GamepadKeys.Button.LEFT_BUMPER)).toggleWhenPressed(robotCentricDrive,slowMode);

        register(drive);
        drive.setDefaultCommand(robotCentricDrive);
    }
}
