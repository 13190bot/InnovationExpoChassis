//package org.firstinspires.ftc.teamcode.FTCLib_Programs.autonomous;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.teamcode.FTCLib_Programs.vision.SleeveDetection;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvWebcam;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import org.openftc.easyopencv.OpenCvPipeline;
//
//
//@Autonomous
//class openCV_auto extends OpMode {
//    OpenCvWebcam webcam;
//    DcMotor lf, lb, rf, rb;
//    SleeveDetection pipeline;
//    SleeveDetection.ParkingPosition snapshotAnalysis = SleeveDetection.ParkingPosition.LEFT;// default
//
//
//
//    @Override
//    public void init() {
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "cam"), cameraMonitorViewId);
//
//        webcam.setPipeline(pipeline);
//        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()) {
//
//            public void onOpened() {
//                webcam.startStreaming(320, 240);
//            }
//
//
//        }
//
//
//
//
//
//
//    }
//        @Override
//        public void loop() {
//
//        }
//        public void OnError(int errorCode) {
//
//        telemetry.addData("Error", "There was an error in accessing the camera");
//    }
//
//
//
//}
