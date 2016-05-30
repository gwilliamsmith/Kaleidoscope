package EventScheduler.Events;

import SwingElements.Base;

public class RunEvent extends Event{

    public RunEvent(int stepTargetIn, String eventNameIn, boolean repeatIn, Base in) {
        super(stepTargetIn, eventNameIn, repeatIn, in);
    }//end constructor

    @Override
    public void takeAction() {
        ref.run();
    }//end takeAction
    
}//end RunEvent class
