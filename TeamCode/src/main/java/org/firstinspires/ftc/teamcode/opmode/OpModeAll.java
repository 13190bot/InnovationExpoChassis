package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.command.drive.*;
import org.firstinspires.ftc.teamcode.command.lift.*;
import org.firstinspires.ftc.teamcode.command.arm.*;
import org.firstinspires.ftc.teamcode.command.claw.*;


import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;

@TeleOp(name = "Main TeleOp")
public class OpModeAll extends BaseTotalOpMode {
    // image of gamepad: https://gm0.org/en/latest/_images/logitech-f310.png

    private GamepadEx driverOp1;
    private GamepadEx driverOp2;

    private DefaultRobotCentricDrive robotCentricDrive;

    private SlowMode slowMode;

    private GoHome goHome;

    private GoScore goScore;

    private DropCone dropCone;

    private GrabCone grabCone;

    private Button slowtime, armManip, slideManip, clawManip;

    private Button moveGround, moveLow, moveMedium, moveHigh, moveCancel, isUp, isDown;



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

        robotCentricDrive = new DefaultRobotCentricDrive(drive, () -> driverOp1.getLeftX(),
                () -> driverOp1.getRightY(), () -> driverOp1.getRightX());

        slowMode = new SlowMode(drive, () -> driverOp1.getLeftX(),
                () -> driverOp1.getRightY(), () -> driverOp1.getRightX());

        //TODO probably need to tune speedvalue inside of SlowMode to make this work properly
        slowtime = (new GamepadButton(driverOp1, GamepadKeys.Button.LEFT_BUMPER))
                .toggleWhenPressed(robotCentricDrive,slowMode);

        //arm, slides, and claw manipulation

        //flips between open and close;
        grabCone = new GrabCone(arm);
        dropCone = new DropCone(arm);
        clawManip = (new GamepadButton(driverOp2, GamepadKeys.Button.RIGHT_BUMPER)).toggleWhenPressed(grabCone, dropCone);

        //flips between arm out and arm in
        goHome = new GoHome(arm);
        goScore = new GoScore(arm);
        armManip = (new GamepadButton(driverOp2, GamepadKeys.Button.LEFT_BUMPER)).toggleWhenPressed(goHome, goScore);

        //manual lift code;
        isUp = (new GamepadButton(driverOp2, GamepadKeys.Button.DPAD_UP)).whenPressed( new SetLiftUp(arm));
        isDown = (new GamepadButton(driverOp2, GamepadKeys.Button.DPAD_DOWN)).whenPressed( new SetLiftDown(arm));


        // automatic junction code [quite mid actually(since perkeet wrote it), everything else is w code(Since I wrote it)]
        //TODO REMEMBER TO TUNE VALUES IN ArmSubsystem BEFORE TRYING TO USE
        moveCancel = (new GamepadButton(driverOp2, GamepadKeys.Button.START)).whenPressed(
                new SetJunction(arm, ArmSubsystem.Junction.NONE)
        );
        moveGround = (new GamepadButton(driverOp2, GamepadKeys.Button.A)).whenPressed(
                new SetJunction(arm, ArmSubsystem.Junction.GROUND)
        );
        moveLow = (new GamepadButton(driverOp2, GamepadKeys.Button.X)).whenPressed(
                new SetJunction(arm, ArmSubsystem.Junction.LOW)
        );
        moveMedium = (new GamepadButton(driverOp2, GamepadKeys.Button.B)).whenPressed(
                new SetJunction(arm, ArmSubsystem.Junction.MEDIUM)
        );
        moveHigh = (new GamepadButton(driverOp2, GamepadKeys.Button.Y)).whenPressed(
                new SetJunction(arm, ArmSubsystem.Junction.HIGH)
        );



        register(drive, arm);
        drive.setDefaultCommand(robotCentricDrive);
    }
}
