package org.firstinspires.ftc.teamcode.ftcLib.teleop;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftcLib.command.drive.*;
import org.firstinspires.ftc.teamcode.ftcLib.command.lift.*;
import org.firstinspires.ftc.teamcode.ftcLib.command.claw.*;


import org.firstinspires.ftc.teamcode.ftcLib.teleop.baseOpModes.BaseTotalOpMode;
import org.firstinspires.ftc.teamcode.ftcLib.subsystem.ArmSubsystem;

@TeleOp(name = "Main TeleOp")
public class OpModeTotal extends BaseTotalOpMode {
    // image of gamepad: https://gm0.org/en/latest/_images/logitech-f310.png

    private GamepadEx driverOp1;
    private GamepadEx driverOp2;

    private DefaultRobotCentricDrive robotCentricDrive;
    private SlowMode slowMode;

    private DropCone dropCone;
    private GrabCone grabCone;

    //slides
    private ManualLift manualLift;

    private Button driveManip, clawManip, slideMovement;


    @Override
    public void initialize() {
        super.initialize();

        /*
        Player1
            Left Stick X -> Strafe
            Right Stick Y -> Forward and Back
            Left Trigger = Turn left
            Right Trigger = Turn Right

            Left bumper -> toggles between slow mode and normal mode

         */

        driverOp1 = new GamepadEx(gamepad1);
        driverOp2 = new GamepadEx(gamepad2);

        //drive
        robotCentricDrive = new DefaultRobotCentricDrive(drive,
                () -> driverOp1.getRightX(),
                () -> driverOp1.getLeftY(),
                () -> driverOp1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                () -> driverOp1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));

        slowMode = new SlowMode(drive,
                () -> driverOp1.getRightX(),
                () -> driverOp1.getLeftY(),
                () -> driverOp1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                () -> driverOp1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));

        driveManip = (new GamepadButton(driverOp1,
                GamepadKeys.Button.LEFT_BUMPER)).toggleWhenPressed(robotCentricDrive,slowMode);

       /*
        Player2

            a -> alternates between claw open and close

            right joystick y-axis --> slides control
         */

        driverOp1 = new GamepadEx(gamepad1);

        //toggles claw between open and close
        grabCone = new GrabCone(arm);
        dropCone = new DropCone(arm);
        clawManip = (new GamepadButton(driverOp2, GamepadKeys.Button.A)).toggleWhenPressed(grabCone, dropCone);

        //slides manual
        manualLift = new ManualLift(arm, () -> driverOp2.getRightY());

        register(drive, arm);
        drive.setDefaultCommand(robotCentricDrive);
        arm.setDefaultCommand(manualLift);
    }
}
