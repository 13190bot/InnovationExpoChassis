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

@TeleOp(name = "ThisIsSoSus")
public class SusOp extends BaseOpMode {
    // image of gamepad: https://gm0.org/en/latest/_images/logitech-f310.png

    private GamepadEx driverOp1;
    private GamepadEx driverOp2;

    private DefaultFieldCentricDrive fieldCentricDrive;
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
            Left Stick -> Drive
            //TODO try and change this so driving forward and back is left joystick while left and right is right joystick

            Left bumper -> slow mode

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
                () -> driverOp1.getRightX(), () -> driverOp1.getLeftY());

        slowMode = new SlowMode(drive, () -> driverOp1.getLeftX(),
                () -> driverOp1.getRightX(), () -> driverOp1.getLeftY());

        //TODO probably need to tune speedvalue inside of SlowMode to make this work properly
        slowtime = (new GamepadButton(driverOp1, GamepadKeys.Button.LEFT_BUMPER))
                .toggleWhenPressed(robotCentricDrive,slowMode);

        //arm, slides, and claw manipulation
        grabCone = new GrabCone(arm);
        dropCone = new DropCone(arm);
        clawManip = (new GamepadButton(driverOp2, GamepadKeys.Button.RIGHT_BUMPER)).toggleWhenPressed(grabCone, dropCone);

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
