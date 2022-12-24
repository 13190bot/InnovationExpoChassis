package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.command.drive.*;
import org.firstinspires.ftc.teamcode.command.lift.*;
import org.firstinspires.ftc.teamcode.command.claw.*;


import org.firstinspires.ftc.teamcode.opmode.baseOpModes.BaseTotalOpMode;

@TeleOp(name = "Main TeleOp")
public class OpModeTotal extends BaseTotalOpMode {
    // image of gamepad: https://gm0.org/en/latest/_images/logitech-f310.png

    private GamepadEx driverOp1;
    private GamepadEx driverOp2;

    private DefaultRobotCentricDrive robotCentricDrive;

    private SlowMode slowMode;

    private DropCone dropCone;

    private GrabCone grabCone;

    private LiftUp liftUp;

    private LiftDown liftDown;

    private LiftStop liftStop;
    private Button slowtime, slideManip, clawManip;

    private Button moveGround, moveLow, moveMedium, moveHigh, moveCancel, isUp, isDown;

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

        Player2
            START -> moveCancel (Cancel all current movement)
            A -> moveGround (Ground junction)
            X -> moveLow (Low junction)
            B -> moveMedium (Medium junction)
            Y -> moveHigh (High junction)
              Y
            X   B
              A

            right_bumper -> alternates between claw open and close
            left_bumper -> alternates between arm forward and back
            dpad_up -> slides go up
            dpad_down -> slides go down
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

        //TODO probably need to tune speedvalue inside of SlowMode to make this work properly
        slowtime = (new GamepadButton(driverOp1,
                GamepadKeys.Button.LEFT_BUMPER)).toggleWhenPressed(robotCentricDrive,slowMode);

        register(drive);
        drive.setDefaultCommand(robotCentricDrive);



        //toggles between open and close
        grabCone = new GrabCone(arm);
        dropCone = new DropCone(arm);
        clawManip = (new GamepadButton(driverOp2, GamepadKeys.Button.RIGHT_BUMPER)).toggleWhenPressed(grabCone, dropCone);

        //toggles between open and close
        grabCone = new GrabCone(arm);
        dropCone = new DropCone(arm);
        clawManip = (new GamepadButton(driverOp2, GamepadKeys.Button.RIGHT_BUMPER)).toggleWhenPressed(grabCone, dropCone);


        liftStop = new LiftStop(arm);
        //manual lift code
        liftUp = new LiftUp(arm);
        isUp = (new GamepadButton(driverOp2, GamepadKeys.Button.DPAD_UP)).whileHeld(liftUp);
        isDown = (new GamepadButton(driverOp2, GamepadKeys.Button.DPAD_DOWN)).whileHeld(liftDown);

        // automatic junction code [quite mid actually(since perkeet wrote it), everything else is w code(Since I wrote it)]
        //TODO REMEMBER TO TUNE VALUES IN ArmSubsystem_OG BEFORE TRYING TO USE and ask if they even want move cancel
        moveCancel = (new GamepadButton(driverOp2, GamepadKeys.Button.START)).whenPressed(
                new SetJunction(arm, ArmSubsystem_OG.Junction.NONE)
        );
        moveGround = (new GamepadButton(driverOp2, GamepadKeys.Button.A)).whenPressed(
                new SetJunction(arm, ArmSubsystem_OG.Junction.GROUND)
        );
        moveLow = (new GamepadButton(driverOp2, GamepadKeys.Button.X)).whenPressed(
                new SetJunction(arm, ArmSubsystem_OG.Junction.LOW)
        );
        moveMedium = (new GamepadButton(driverOp2, GamepadKeys.Button.B)).whenPressed(
                new SetJunction(arm, ArmSubsystem_OG.Junction.MEDIUM)
        );
        moveHigh = (new GamepadButton(driverOp2, GamepadKeys.Button.Y)).whenPressed(
                new SetJunction(arm, ArmSubsystem_OG.Junction.HIGH)        );


        register(drive, arm);
//        arm.setDefaultCommand(liftStop);
        drive.setDefaultCommand(robotCentricDrive);
    }

    @Override
    public void run() {
        super.run();

        arm.manualControlLift(gamepad2.left_stick_y);
    }
}
