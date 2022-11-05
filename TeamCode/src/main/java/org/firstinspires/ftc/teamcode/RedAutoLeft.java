package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Left Red Auto")
public class RedAutoLeft extends AutoSuper {

    @Override
    public void runOpMode() {
        startX = -34;
        startY = -60;

        super.runOpMode();
    }

}
