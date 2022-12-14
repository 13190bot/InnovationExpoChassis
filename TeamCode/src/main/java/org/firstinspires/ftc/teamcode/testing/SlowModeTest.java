package org.firstinspires.ftc.teamcode.testing;

import com.arcrobotics.ftclib.command.CommandBase;

import java.util.function.DoubleSupplier;

public class SlowModeTest extends CommandBase {
    private final DoubleSupplier strafeSpeed, forwardSpeed, turnSpeed;
    private final DriveTestSubsystem drive;
    public SlowModeTest(DriveTestSubsystem drive,
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
        drive.driveRobotCentricSlowMode(
                strafeSpeed.getAsDouble(),
                forwardSpeed.getAsDouble(),
                turnSpeed.getAsDouble()
        );
    }
}
