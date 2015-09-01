package Listeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StepActionListener implements ActionListener {

    private Base ref;

    public StepActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        ref.getGraph().takeStep();
    }//end actionPerformed

}//end StepActionListener
