package graphvisualizer;

import SwingElements.Base;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Camera {

    private Base ref;

    private boolean pictureTaken;
    
    private int pictureCount = 1;

    public Camera(Base in) {
        ref = in;
        pictureTaken = false;
    }//end constructor

    public void takePicture() {
        try {
            if (ref.getBookDirectory() != null) {
                ImageIO.write(ref.getCanvas().produceTrimmedImage(ref.getCanvas().getGridPicture()), "jpg", new File(ref.getBookDirectory().getAbsolutePath() + "\\" + pictureCount + ".jpg"));
                pictureCount++;
            }//end if
        } catch (IOException ex) {
        }//end try catch block
    }//end takePicture

    public boolean isPictureTaken() {
        return pictureTaken;
    }//end isPictureTaken

    public void setPictureTaken(boolean in) {
        pictureTaken = in;
    }//end setPictureTaken

}//end Camera class