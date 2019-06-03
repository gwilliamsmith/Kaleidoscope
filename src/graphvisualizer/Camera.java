package graphvisualizer;

import SwingElements.Base;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Captures images of the {@link SwingElements.Canvas} object, in the form of .jpg files
 */
public class Camera {

    private final Base ref;                           //The Base object, used as a reference to get all needed objects
    private boolean pictureTaken;               //True if the camera is unable to take a picture, false if it is able.
    private boolean cameraOn = false;           //False if the camera is inactive, true if it is active (CURRENTLY UNUSED)
    private int pictureCount = 0;               //Counts the number of pictures taken. Used by the graph to re-seed the graph on an interval

    /**
     * Constructor.
     *
     * @param in The {@link Base} object, used for reference
     */
    Camera(Base in) {
        ref = in;
        pictureTaken = false;
    }//end constructor

    /**
     * Captures a picture of the trimmed canvas. The picture is automatically 
     * named.
     */
    void takePicture() {
        try {
            if (ref.getBookDirectory() != null) {           //Creates a picture if a place to put it has been set
                ImageIO.write(ref.getCanvas().produceTrimmedImage(), "png", new File(ref.getBookDirectory().getAbsolutePath() + "\\" + pictureCount++ + ".png"));
            }//end if
        } catch (IOException ex) {              //Try to do something with this?
        }//end try catch block
    }//end takePicture

    /**
     * Captures a picture of the trimmed canvas, with a user-created name.
     * @param name What the picture will be named.
     */
    public void takePicture(String name) {
        try {
            if (name != null) {            //Creates a picture if place to put it has been set
                ImageIO.write(ref.getCanvas().produceTrimmedImage(), "png", new File(name));
            }//end if
        } catch (IOException ex) {         //Should something be done with this?
        }//end try catch block
    }//end takePicture

    /**
     * Returns the value of the pictureTaken variable.
     *
     * @return The value of pictureTaken
     */
    boolean isPictureTaken() {
        return pictureTaken;
    }//end isPictureTaken

    /**
     * Sets the pictureTaken variable, used to determine if the camera has
     * recently taken a picture.
     *
     * @param in The new value for pictureTaken
     */
    void setPictureTaken(boolean in) {
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
