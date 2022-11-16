package cf.devmello.ftc;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class Slides {
    //create objects properties
    /*
     * The ENUM for slide direction
     * @param UP
     * @param DOWN
     */
    enum DIRECTION {
        UP,
        DOWN
    }

    //the DCMotor used for the slides
    protected DcMotor slideMotor;
//    DcMotor slideMotor =


    //slides class object
    protected Slides(DcMotor slideMotor) {
        this.slideMotor = slideMotor;
    }

    //the method to set the slideMotor
    public void setSlideMotor(DcMotor slideMotor) {
        this.slideMotor = slideMotor;
    }


    /**
     * get the position of the slide
     *
     * @return the position of the slide in Tick encoders
     */
    protected int getSlidePos() {
        return slideMotor.getCurrentPosition();
    }

    /**
     * moves the slide to a targeted position
     *
     * @param direction the direction to move the slide
     */
    protected void move(DIRECTION direction) {
        if (direction == DIRECTION.UP) {
            slideMotor.setTargetPosition((int) (slideMotor.getCurrentPosition() + 20));
        } else if (direction == DIRECTION.DOWN) {
            slideMotor.setTargetPosition((int) (slideMotor.getCurrentPosition() - 20));
        }
    }

}