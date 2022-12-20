package org.firstinspires.ftc.teamcode.command.drive;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystem.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class DefaultRobotCentricDrive extends CommandBase {
    private final DriveSubsystem drive;
    private final DoubleSupplier strafeSpeed, forwardSpeed, turnSpeedL, turnSpeedR;

    public DefaultRobotCentricDrive(DriveSubsystem drive,
                                    DoubleSupplier strafeSpeed,
                                    DoubleSupplier forwardSpeed,
                                    DoubleSupplier turnSpeedL,
                                    DoubleSupplier turnspeedR) {
        this.drive = drive;
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.turnSpeedL = turnSpeedL;
        this.turnSpeedR = turnspeedR;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        drive.driveRobotCentric(
                strafeSpeed.getAsDouble(),
                forwardSpeed.getAsDouble(),
                turnSpeedL.getAsDouble(),
                turnSpeedR.getAsDouble());
    }
}