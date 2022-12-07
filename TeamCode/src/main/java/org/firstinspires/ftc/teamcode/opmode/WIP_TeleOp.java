package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.command.drive.DefaultFieldCentricDrive;
import org.firstinspires.ftc.teamcode.command.drive.DefaultRobotCentricDrive;

import org.firstinspires.ftc.teamcode.command.lift.SetJunction;
import org.firstinspires.ftc.teamcode.subsystem.ArmSubsystem;

@TeleOp(name = "BasicDrive")
public class WIP_TeleOp extends BaseOpMode {
    // image of gamepad: https://gm0.org/en/latest/_images/logitech-f310.png

    private GamepadEx driverOp1;
    private GamepadEx driverOp2;

    private DefaultFieldCentricDrive fieldCentricDrive;
    private DefaultRobotCentricDrive robotCentricDrive;

    private Button changeCenter, moveGround, moveLow, moveMedium, moveHigh, moveCancel;



    @Override
    public void initialize() {
        super.initialize();

        /*
        Player1
            Left Stick -> Drive
            Left Stick Click -> Change Mode

        Player2
            START -> moveCancel (Cancel all current movement)
            A -> moveGround (Ground junction)
            X -> moveLow (Low junction)
            B -> moveMedium (Medium junction)
            Y -> moveHigh (High junction)
              Y
            X   B
              A

         */





        driverOp1 = new GamepadEx(gamepad1);
        driverOp2 = new GamepadEx(gamepad2);

        fieldCentricDrive = new DefaultFieldCentricDrive(drive, () -> driverOp1.getLeftX(),
                () -> driverOp1.getLeftY(), () -> driverOp1.getRightX(), () -> imu.getHeading());
        robotCentricDrive = new DefaultRobotCentricDrive(drive, () -> driverOp1.getLeftX(),
                () -> driverOp1.getRightX(), () -> driverOp1.getLeftY());

        changeCenter = (new GamepadButton(driverOp1, GamepadKeys.Button.LEFT_STICK_BUTTON))
                .toggleWhenPressed(fieldCentricDrive, robotCentricDrive);




        // /*
        // W code
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
        // */










        register(drive, arm);
        drive.setDefaultCommand(robotCentricDrive);
    }
}
