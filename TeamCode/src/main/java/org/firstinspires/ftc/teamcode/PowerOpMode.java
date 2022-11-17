//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
////TODO: use getposition with liftmotor and telemetry to get liftmotor max height for other opmode
//
//@TeleOp
//public class PowerOpMode extends OpMode {
//
//    double maxSpeed = .7;
//    DcMotor liftMotor;
//
//    @Override
//    public void init () {
//
//
//
//    }
//
//    @Override
//    public void loop () {
//
//        //will use gamepad 2 for actual comp
//        liftMotor.setPower( Math.min(gamepad1.left_stick_y, maxSpeed) );
//
//        if(gamepad1.left_bumper){
//
//            claw.setPosition(1);
//
//        } else if (gamepad1.right_bumper) {
//
//            claw.setPosition(0);
//
//        }
//
//    }
//
//}