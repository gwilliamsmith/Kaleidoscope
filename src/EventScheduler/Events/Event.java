package EventScheduler.Events;

import SwingElements.Base;


public abstract class Event {
    int stepTarget;
    String eventName;
    boolean repeat;
    Base ref;
    
    public Event(int stepTargetIn, String eventNameIn, boolean repeatIn, Base in){
        stepTarget = stepTargetIn;
        eventName = eventNameIn;
        repeat = repeatIn;
        ref = in;
    }//end constructor
    
    public abstract void takeAction();
    
    public int getStepTarget(){
        return stepTarget;
    }//end getStepTarget
    
    public void setStepTarget(int in){
        if(in > 0){
            stepTarget = in;
        }//end if
    }//end setStepTarget
    
    public String getEventName(){
        return eventName;
    }//end getEventName
    
    public void setEventName(String in){
        eventName = in;
    }//end setEventName
    
    public boolean getRepeat(){
        return repeat;
    }//end getRepeat
    
    public void setRepeat(Boolean in){
        repeat = in;
    }//end setRepeat
}//end Event abstract class
