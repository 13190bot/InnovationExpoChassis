package org.firstinspires.ftc.teamcode.ftcLib.teleop;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.ftcLib.command.claw.*;
import org.firstinspires.ftc.teamcode.ftcLib.command.lift.*;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.RawArmSubsystem;
import org.firstinspires.ftc.teamcode.ftcLib.teleop.baseOpModes.BaseRawArmOpMode;

@TeleOp(name = "Raw Arm TeleOp")
public class OpModeArmRaw extends BaseRawArmOpMode {
    // image of gamepad: https://gm0.org/en/latest/_images/logitech-f310.png

    private GamepadEx driverOp1;

    //claw
    private DropCone dropCone;

    private GrabCone grabCone;


    //slides
    private RawLift rawLift;

    private RawJunc moveToDefault, moveToGround, moveToLow, moveToMedium, moveToHigh;


    //buttons
    private Button clawManip, slideMovement;

    private Button moveDefault, moveGround, moveLow, moveMedium, moveHigh;


    @Override
    public void initialize() {
        super.initialize();
        /*
        Player1
            right_bumper -> resets slides to ground
            dpad_down -> moveGround (Ground junction)
            dpad_left -> moveLow (Low junction)
            dpad_right -> moveMedium (Medium junction)
            dpad_up -> moveHigh (High junction)

            a -> alternates between claw open and close

            right joystick y-axis --> slides control
         */

        driverOp1 = new GamepadEx(gamepad1);

        //toggles claw between open and close
        grabCone = new GrabCone(oldarm);
        dropCone = new DropCone(oldarm);
        clawManip = (new GamepadButton(driverOp1, GamepadKeys.Button.A)).toggleWhenPressed(grabCone, dropCone);

        //slides manual
        rawLift = new RawLift(arm, () -> driverOp1.getRightY());
        telemetry.addData("Right Stick Y:", driverOp1.getRightY());

        // automatic junction code
        moveToDefault = new RawJunc(arm, RawArmSubsystem.Junction.DEFAULT);
        moveToGround = new RawJunc(arm, RawArmSubsystem.Junction.GROUND);
        moveToLow = new RawJunc(arm, RawArmSubsystem.Junction.LOW);
        moveToMedium = new RawJunc(arm, RawArmSubsystem.Junction.MEDIUM);
        moveToHigh = new RawJunc(arm, RawArmSubsystem.Junction.HIGH);

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

        register(arm, oldarm);
        arm.setDefaultCommand(rawLift);
    }
}
