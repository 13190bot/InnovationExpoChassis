package org.firstinspires.ftc.teamcode.autonomous.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class
SleeveDetection extends OpenCvPipeline {
    /*
    YELLOW  = Parking Left
    CYAN    = Parking Middle
    MAGENTA = Parking Right
     */

    public enum ParkingPosition {
        LEFT,
        CENTER,
        RIGHT
    }

    // TODO TOPLEFT anchor point for the bounding box, is this in the correct spot?
    private static final Point SLEEVE_TOPLEFT_ANCHOR_POINT = new Point(640, 260);

    // Width and height for the bounding box
    public static int REGION_WIDTH = 60;
    public static int REGION_HEIGHT = 60;

    // Color definitions

    private final Scalar
            YELLOW  = new Scalar(255, 255, 0),
            CYAN    = new Scalar(0, 255, 255),
            MAGENTA = new Scalar(255, 0, 255);

    private Scalar retVal;

    // Anchor point definitions
    Point sleeve_pointA = new Point(
            SLEEVE_TOPLEFT_ANCHOR_POINT.x,
            SLEEVE_TOPLEFT_ANCHOR_POINT.y);
    Point sleeve_pointB = new Point(
            SLEEVE_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            SLEEVE_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

    // Running variable storing the parking position
    public volatile int position = 1;

    @Override
    public Mat processFrame(Mat input) {
        // Get the submat frame, and then sum all the values
        Mat areaMat = input.submat(new Rect(sleeve_pointA, sleeve_pointB));
        Scalar sumColors = Core.sumElems(areaMat);

        // Get the minimum RGB value from every single channel
        double red = sumColors.val[0];
        double green = sumColors.val[1];
        double blue = sumColors.val[2];
        double minColor = Math.min(red, Math.min(green, blue));

        // Change the bounding box color based on the sleeve color
        if (red == minColor) {
            position = 0; //center
            Imgproc.rectangle(
                    input,
                    sleeve_pointA,
                    sleeve_pointB,
                    CYAN,
                    2
                    );

        } else if (blue == minColor)  {
            position = 1; //left
            Imgproc.rectangle(
                    input,
                    sleeve_pointA,
                    sleeve_pointB,
                    YELLOW,
                    2
                    );
        } else {
            position = 2; //right
            Imgproc.rectangle(
                    input,
                    sleeve_pointA,
                    sleeve_pointB,
                    MAGENTA,
                    2
                    );
        }

        // Release and return input
        areaMat.release();
        return input;
    }

    // returns an enum for parking position
    public int getPosition() {
        return position;
    }
}