package org.firstinspires.ftc.teamcode.ftcLib.teleop;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.ftcLib.command.lift.PCJunc;
import org.firstinspires.ftc.teamcode.ftcLib.command.lift.PCLift;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.PCArmSubsystem;
import org.firstinspires.ftc.teamcode.ftcLib.teleop.baseOpModes.BasePCArmOpMode;

@TeleOp(name = "PC Arm TeleOp")
public class OpModeArmPC extends BasePCArmOpMode {
    // image of gamepad: https://gm0.org/en/latest/_images/logitech-f310.png

    private GamepadEx driverOp1;

    //slides
    private PCLift PCLift;

    private PCJunc moveToDefault, moveToGround, moveToLow, moveToMedium, moveToHigh;

    //buttons
    private Button slideMovement;

    private Button moveDefault, moveGround, moveLow, moveMedium, moveHigh;


    @Override
    public void initialize() {
        super.initialize();
        /*
        Player1
            right_bumper -> resets slides to ground
            dpad_down -> moveGround (Ground junction)hbj
            dpad_left -> moveLow (Low junction)
            dpad_right -> moveMedium (Medium junction)
            dpad_up -> moveHigh (High junction)

            a -> alternates between claw open and close

            right joystick y-axis --> slides control
         */

        driverOp1 = new GamepadEx(gamepad1);

        //slides manual
        PCLift = new PCLift(arm, () -> driverOp1.getRightY());
        telemetry.addData("Right Stick Y:", driverOp1.getRightY());
        telemetry.update();

        // automatic junction code
        moveToDefault = new PCJunc(arm, PCArmSubsystem.Junction.DEFAULT);
        moveToGround = new PCJunc(arm, PCArmSubsystem.Junction.GROUND);
        moveToLow = new PCJunc(arm, PCArmSubsystem.Junction.LOW);
        moveToMedium = new PCJunc(arm, PCArmSubsystem.Junction.MEDIUM);
        moveToHigh = new PCJunc(arm, PCArmSubsystem.Junction.HIGH);

        moveDefault = (new GamepadButton(driverOp1, GamepadKeys.Button.RIGHT_BUMPER))
                .whenPressed(moveToDefault);

        moveGround = (new GamepadButton(driverOp1, GamepadKeys.Button.DPAD_DOWN))
                .whenPressed(moveToGround);

        moveLow = (new GamepadButton(driverOp1, GamepadKeys.Button.DPAD_LEFT))
                .whenPressed(moveToLow);

        moveMedium= (new GamepadButton(driverOp1, GamepadKeys.Button.DPAD_RIGHT))
                .whenPressed(moveToMedium);

        moveHigh = (new GamepadButton(driverOp1, GamepadKeys.Button.DPAD_UP))
                .whenPressed(moveToHigh);

        register(arm);
        arm.setDefaultCommand(PCLift);
    }
}
