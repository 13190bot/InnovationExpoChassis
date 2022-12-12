package org.firstinspires.ftc.teamcode.command.drive;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystem.DriveTestSubsystem;

import java.util.function.DoubleSupplier;

public class DefaultRobotCentricTestDrive extends CommandBase {
    private final DriveTestSubsystem drive;
    private final DoubleSupplier strafeSpeed, forwardSpeed, turnSpeed;

    public DefaultRobotCentricTestDrive(DriveTestSubsystem drive,
                                        DoubleSupplier strafeSpeed,
                                        DoubleSupplier forwardSpeed,
                                        DoubleSupplier turnSpeed)
    {
        this.drive = drive;
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.turnSpeed = turnSpeed;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        drive.driveRobotCentric(
                strafeSpeed.getAsDouble(),
                forwardSpeed.getAsDouble(),
                turnSpeed.getAsDouble()
        );
    }
}