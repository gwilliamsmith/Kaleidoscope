package Listeners.RightClickListeners;

import SwingElements.Base;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@link ActionListener} child, used to begin or pause auto-run steps when the
 * corresponding menu button is pressed in the {@link Base} class.
 */
public class LoopActionListener implements ActionListener {

    private Base ref;                                                           //Base object, used for reference

    /**
     * Constructor.
     *
     * @param in {@link Base} object, used for reference
     */
    public LoopActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        ref.flipRun();
    }//endActionPerformed
}//end LoopActionListener
