package Listeners;

import graphvisualizer.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerActionListener implements ActionListener {

    private Base ref;

    public TimerActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (ref.getRun()) {
            ref.takeStep();
        }//end if
        ref.getCanvas().repaint();
    }//actionPerformed

}//end TimerActionListner
