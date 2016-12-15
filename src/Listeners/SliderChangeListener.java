package Listeners;

import SwingElements.Base;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SliderChangeListener implements ChangeListener{

    Base ref;
    
    public SliderChangeListener(Base in){
        ref = in;
    }//end constructor
    
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        int newStepTime = slider.getValue();
        ref.updateStepTime(newStepTime);
    }//end stateChanged

}//end SliderChangeListener class
