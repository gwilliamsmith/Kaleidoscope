package EventScheduler.Events;

import SwingElements.Base;


public class PauseEvent extends Event{

    public PauseEvent(int stepTargetIn, String eventNameIn, boolean repeatIn, Base in) {
        super(stepTargetIn, eventNameIn, repeatIn, in);
    }//end constructor

    @Override
    public void takeAction() {
        ref.pause();
    }//end takeAction
    
}//end RunEvent class