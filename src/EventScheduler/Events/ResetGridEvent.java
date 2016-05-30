package EventScheduler.Events;

import SwingElements.Base;


public class ResetGridEvent extends Event{

    public ResetGridEvent(int stepTargetIn, String eventNameIn, boolean repeatIn, Base in) {
        super(stepTargetIn, eventNameIn, repeatIn, in);
    }//end constructor

    @Override
    public void takeAction() {
        ref.getGraph().reset();
    }//end takeAction
    
}//end RunEvent class
