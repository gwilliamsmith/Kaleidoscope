package Listeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@link ActionListener} child used to perform step auto-running, as well as
 * repainting on an interval.
 */
public class TimerActionListener implements ActionListener {

    private final Base ref;                                                           //Base object, used for reference

    /**
     * Constructor.
     *
     * @param in {@link Base} class, used for reference
     */
    public TimerActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent evt) {
        new Thread(() -> ref.getGraph().takeStep()).start();
    }//actionPerformed

}//end TimerActionListner
