package Listeners;

import SwingElements.Base;
import SwingElements.ColorSelectionFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetColorActionListener implements ActionListener {

    private Base ref;

    public SetColorActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        new ColorSelectionFrame(ref).setVisible(true);
    }//endActionPerformed
}//end LoopActionListener
