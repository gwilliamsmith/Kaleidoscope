package graphvisualizer;

import SwingElements.Base;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Captures images of the {@link Canvas} object, in the form of .jpg files
 */
public class Camera {

    private Base ref;                           //The Base object, used as a reference to get all needed objects
    private boolean pictureTaken;               //True if the camera is unable to take a picture, false if it is able.
    private boolean cameraOn = false;           //False if the camera is inactive, true if it is active (CURRENTLY UNUSED)
    private int pictureCount = 0;               //Counts the number of pictures taken. Used by the graph to re-seed the graph on an interval

    /**
     * Constructor.
     *
     * @param in The {@link Base} object, used for reference
     */
    public Camera(Base in) {
        ref = in;
        pictureTaken = false;
    }//end constructor

    /**
     * Captures a picture of the trimmed canvas.
     */
    public void takePicture() {
        try {
            if (ref.getBookDirectory() != null) {
                ImageIO.write(ref.getCanvas().produceTrimmedImage(), "jpg", new File(ref.getBookDirectory().getAbsolutePath() + "\\" + pictureCount + ".jpg"));
            }//end if
        } catch (IOException ex) {
        }//end try catch block
    }//end takePicture

    public void takePicture(String name) {
        try {
            if (name != null) {
                ImageIO.write(ref.getCanvas().produceTrimmedImage(), "jpg", new File(name));
            }//end if
        } catch (IOException ex) {
        }//end try catch block
    }//end takePicture

    /**
     * Returns the value of the pictureTaken variable.
     *
     * @return The value of pictureTaken
     */
    public boolean isPictureTaken() {
        return pictureTaken;
    }//end isPictureTaken

    /**
     * Sets the pictureTaken variable, used to determine if the camera has
     * recently taken a picture.
     *
     * @param in The new value for pictureTaken
     */
    public void setPictureTaken(boolean in) {
        pictureTaken = in;
    }//end setPictureTaken

    /**
     * Returns the number of pictures taken by the camera.
     *
     * @return The number of pictures taken by the camera.
     */
    public int getPictureCount() {
        return pictureCount;
    }//end getPictureCount

    //Maybe get rid of this, replace with increment/reset functions
    /**
     * Sets the number of pictures taken by the camera.
     *
     * @param in The new number of pictures taken by the camera
     */
    public void setPictureCount(int in) {
        pictureCount = in;
    }//end setPictureCount

}//end Camera class
