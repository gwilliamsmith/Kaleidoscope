package EventScheduler.Events;

import SwingElements.Base;


public class SavePictureEvent extends Event{
    
    private String pictureLocation = "";
    private int pictureCount = 0;
    
    public SavePictureEvent(int stepTargetIn, String eventNameIn, boolean repeatIn, Base in, String location) {
        super(stepTargetIn, eventNameIn, repeatIn, in);
        pictureLocation = location;
    }//end constructor

    @Override
    public void takeAction() {
        String fileName = pictureLocation + "\\" + pictureCount + ".jpg";
        pictureCount++;
        ref.getGraph().getCamera().takePicture(fileName);
    }//end takeAction
    
    public void setPictureLocation(String in){
        pictureLocation = in;
    }//end setPictureLocation
    
    public String getPictureLocation(){
        return pictureLocation;
    }//end getPictureLocation

}//end SavePictureEvent class