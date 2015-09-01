package Listeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoopActionListener implements ActionListener {

    private Base ref;

    public LoopActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        ref.flipRun();
    }//endActionPerformed
}//end LoopActionListener
