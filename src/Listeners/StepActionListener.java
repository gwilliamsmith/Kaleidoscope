package Listeners;

import graphvisualizer.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StepActionListener implements ActionListener {

    Base ref = null;

    public StepActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ref != null) {
            ref.takeStep();
        }//end if
    }//end actionPerformed

}//end StepActionListener
