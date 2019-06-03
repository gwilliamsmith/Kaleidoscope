package Listeners.RightClickListeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@link ActionListener} child, used to progress the cellular automata forward
 * one step when the corresponding menu button is pressed in the {@link Base}
 * class.
 */
public class StepActionListener implements ActionListener {

    private Base ref;                                                           //Base object, used for reference

    /**
     * Constructor.
     *
     * @param in {@link Base} object, used as reference
     */
    public StepActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        ref.getGraph().takeStep();
    }//end actionPerformed

}//end StepActionListener
