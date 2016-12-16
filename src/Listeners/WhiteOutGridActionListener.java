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
        if (!toggle) {
            for (GraphNode[] matrix1 : ref.getGraph().getMatrix()) {
                for (GraphNode gn : matrix1) {
                    gn.setColor(new Color(255, 255, 255));
                }//end for
            }//end for
            ref.getWhiteOutGrid().setText("Restore all grid point colors");
            toggle = true;
        }//end if
        else{
            for(GraphNode[] matrix1 : ref.getGraph().getMatrix()){
                for(GraphNode gn : matrix1){
                    ref.getGraph().resetNodeColor(gn);
                }//end 
            }//end for
            ref.getWhiteOutGrid().setText("Turn all grid points white");
            toggle = false;
        }//end else
    }//end actionPerformed
}//end WhiteOutGridActionListener
