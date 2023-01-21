package org.firstinspires.ftc.teamcode.command.drive;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystem.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class DriveSlowMode extends CommandBase {
    private final DriveSubsystem drive;
    private final DoubleSupplier strafeSpeed, forwardSpeed, turnSpeedL, turnSpeedR;

    public DriveSlowMode(DriveSubsystem drive, DoubleSupplier strafeSpeed, DoubleSupplier forwardSpeed, DoubleSupplier turnSpeedL, DoubleSupplier turnSpeedR){
        this.drive = drive;
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.turnSpeedL = turnSpeedL;
        this.turnSpeedR = turnSpeedR;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        drive.driveRobotCentricSlowMode(strafeSpeed.getAsDouble(), forwardSpeed.getAsDouble(), turnSpeedL.getAsDouble(), turnSpeedR.getAsDouble());
    }
}
