package org.firstinspires.ftc.teamcode.command.drive;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystem.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class SlowMode extends CommandBase {
    private final DoubleSupplier strafeSpeed, forwardSpeed, turnSpeedL, turnSpeedR;
    private final DriveSubsystem drive;
    public SlowMode(DriveSubsystem drive,
                    DoubleSupplier strafeSpeed, DoubleSupplier forwardSpeed,
                    DoubleSupplier turnSpeedL, DoubleSupplier turnspeedR){
        this.drive = drive;
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.turnSpeedL = turnSpeedL;
        this.turnSpeedR = turnspeedR;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        drive.driveRobotCentricSlowMode(
                strafeSpeed.getAsDouble(),
                forwardSpeed.getAsDouble(),
                turnSpeedL.getAsDouble(),
                turnSpeedR.getAsDouble());
    }
}
