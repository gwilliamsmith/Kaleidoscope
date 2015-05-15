package Listeners;

import graphvisualizer.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoopActionListener implements ActionListener {

    Base ref = null;

    public LoopActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ref != null) {
            ref.setRun(!ref.getRun());
        }//end if
    }//endActionPerformed
}//end LoopActionListener
