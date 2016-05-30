package graphvisualizer;

import SwingElements.Base;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Camera {

    private Base ref;

    private boolean pictureTaken;
    
    private boolean cameraOn = false;

    private int pictureCount = 0;

    public Camera(Base in) {
        ref = in;
        pictureTaken = false;
    }//end constructor

    public void takePicture() {
        try {
            if (ref.getBookDirectory() != null) {
                ImageIO.write(ref.getCanvas().produceTrimmedImage(ref.getCanvas().getGridPicture()), "jpg", new File(ref.getBookDirectory().getAbsolutePath() + "\\" + pictureCount + ".jpg"));
            }//end if
        } catch (IOException ex) {
        } finally {
            pictureCount++;
            if(pictureCount % ref.getPictureInterval() == 0){
                ref.setRun(ref.isIntervalPause());
                if(ref.isRefreshOn()){
                    ref.getGraph().refreshSeed();
                }//end if
            }//end if
        }//end try catch block
    }//end takePicture

    public boolean isPictureTaken() {
        return pictureTaken;
    }//end isPictureTaken

    public void setPictureTaken(boolean in) {
        pictureTaken = in;
    }//end setPictureTaken

    public int getPictureCount() {
        return pictureCount;
    }//end getPictureCount

    public void setPictureCount(int in) {
        pictureCount = in;
    }//end setPictureCount

}//end Camera class
