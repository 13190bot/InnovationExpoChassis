package org.firstinspires.ftc.teamcode.teleOp.command.drive;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.teleOp.subsystem.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class DriveRobotCentric extends CommandBase {
    private final DriveSubsystem drive;
    private final DoubleSupplier strafeSpeed, forwardSpeed, turnSpeedL, turnSpeedR;

    public DriveRobotCentric(DriveSubsystem drive, DoubleSupplier strafeSpeed, DoubleSupplier forwardSpeed,
                             DoubleSupplier turnSpeedL, DoubleSupplier turnSpeedR){
        this.drive = drive;
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.turnSpeedL = turnSpeedL;
        this.turnSpeedR = turnSpeedR;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        drive.driveRobotCentric(strafeSpeed.getAsDouble(), forwardSpeed.getAsDouble(), turnSpeedL.getAsDouble(), turnSpeedR.getAsDouble());
    }
}
