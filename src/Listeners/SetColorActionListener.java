package Listeners;

import graphvisualizer.Base;
import SwingElements.ColorSelectionFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SetColorActionListener implements ActionListener {

    Base ref = null;

    public SetColorActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ref != null) {
            new ColorSelectionFrame(ref).setVisible(true);
        }//end if
    }//endActionPerformed
}//end LoopActionListener
