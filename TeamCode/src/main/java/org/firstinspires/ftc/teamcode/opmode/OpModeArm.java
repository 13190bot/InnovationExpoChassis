package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.command.claw.*;
import org.firstinspires.ftc.teamcode.command.lift.*;

import org.firstinspires.ftc.teamcode.opmode.baseOpModes.BaseArmOpMode;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;

@TeleOp(name = "Arm TeleOp")
public class OpModeArm extends BaseArmOpMode {
    // image of gamepad: https://gm0.org/en/latest/_images/logitech-f310.png

    private GamepadEx driverOp1;

    //claw
    private DropCone dropCone;

    private GrabCone grabCone;

    //slides

    private ManualLift manualLift;

    private MoveToJunction moveToDefault, moveToGround, moveToLow, moveToMedium, moveToHigh;

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
            b -> alternates between arm home and score

            right joystick y-axis --> slides control
         */

        driverOp1 = new GamepadEx(gamepad1);

        //toggles claw between open and close
        grabCone = new GrabCone(arm);
        dropCone = new DropCone(arm);
        clawManip = (new GamepadButton(driverOp1, GamepadKeys.Button.A)).toggleWhenPressed(grabCone, dropCone);

        //slides manual
        manualLift = new ManualLift(arm, () -> driverOp1.getRightY());

        // automatic junction code

        moveToDefault = new MoveToJunction(arm, ArmSubsystem.Junction.DEFAULT);
        moveToGround = new MoveToJunction(arm, ArmSubsystem.Junction.GROUND);
        moveToLow = new MoveToJunction(arm, ArmSubsystem.Junction.LOW);
        moveToMedium = new MoveToJunction(arm, ArmSubsystem.Junction.MEDIUM);
        moveToHigh = new MoveToJunction(arm, ArmSubsystem.Junction.HIGH);



        //TODO REMEMBER TO TUNE VALUES IN ArmSubsystem BEFORE TRYING TO USE
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
        arm.setDefaultCommand(manualLift);
    }
}
