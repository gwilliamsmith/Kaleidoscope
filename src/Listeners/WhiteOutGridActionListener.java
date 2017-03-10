package Listeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@link ActionListener} child, used to turn all grid points white when the
 * corresponding menu button is pressed in the {@link Base} class.
 */
public class WhiteOutGridActionListener implements ActionListener {

    private Base ref;                                                           //Base object, used for reference

    private boolean toggle = false;

    /**
     * Constructor.
     *
     * @param in {@link Base} class, used for reference
     */
    public WhiteOutGridActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    /**
     * Method performed on trigger. Iterates through all graph nodes and sets
     * their color to white.
     */
    public void actionPerformed(ActionEvent e) {
        System.out.println("toggled");
        if (!toggle) {
            ref.getGraph().whiteOutNodeColors();
            ref.getWhiteOutGrid().setText("Restore all grid point colors");
            toggle = true;
        }//end if
        else{
            ref.getGraph().resetNodeColors();
            ref.getWhiteOutGrid().setText("Turn all grid points white");
            toggle = false;
        }//end else
    }//end actionPerformed
    
    public void setToggle(boolean in){
        toggle = in;
    }//end setToggle
}//end WhiteOutGridActionListener
