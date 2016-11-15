package Listeners;

import SwingElements.Base;
import graphvisualizer.GraphNode;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@link ActionListener} child, used to turn all grid points white when the
 * corresponding menu button is pressed in the {@link Base} class.
 */
public class WhiteOutGridActionListener implements ActionListener {

    private Base ref;                                                           //Base object, used for reference

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
        for (GraphNode[] matrix1 : ref.getGraph().getMatrix()) {
            for (GraphNode gn : matrix1) {
                gn.setColor(new Color(255, 255, 255));
                ref.getCanvas().repaint();
            }//end for
        }//end for
    }//end actionPerformed
}//end WhiteOutGridActionListener
