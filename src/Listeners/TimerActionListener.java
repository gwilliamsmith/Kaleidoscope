package Listeners;

import graphvisualizer.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerActionListener implements ActionListener {

    Base ref = null;

    public TimerActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (ref != null) {
            if (ref.getRun()) {
                ref.takeStep();
                ref.getCanvas().repaint();
            }//end if
        }//end if
    }//actionPerformed

}//end TimerActionListner
