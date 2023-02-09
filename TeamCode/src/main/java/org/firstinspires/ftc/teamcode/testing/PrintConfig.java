package org.firstinspires.ftc.teamcode.testing;

import android.annotation.SuppressLint;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@TeleOp
public class PrintConfig extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        String[] devices = new String[100];
        File filePath = new File("/sdcard/FIRST/mechbot.xml"); // NEEDS SD CARD
        int i = 0;
        String c;
        Scanner file = null;
        try {
            file = new Scanner(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("SD CARD NOT FOUND /sdcard/FIRST/mechbot.xml");
        }
//        while(file.hasNext()) {
             c = file.next();
             if (c == "next="){
//             }

             telemetry.addData(c, "");

//             do {
//                 devices[i] += file.next();
//             } while(devices[i].charAt(devices[i].length()) != '\"');
//             i++;
//
//             System.out.println(devices);

                 waitForStart();
                 while (true);
        }
    }
}
