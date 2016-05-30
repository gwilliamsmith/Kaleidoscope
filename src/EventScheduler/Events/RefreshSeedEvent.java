package EventScheduler.Events;

import SwingElements.Base;


public class RefreshSeedEvent extends Event{

    public RefreshSeedEvent(int stepTargetIn, String eventNameIn, boolean repeatIn, Base in) {
        super(stepTargetIn, eventNameIn, repeatIn, in);
    }//end constructor

    @Override
    public void takeAction() {
        ref.getGraph().refreshSeed();
    }//end takeAction
    
}//end RunEvent class
