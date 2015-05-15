package Listeners;

import graphvisualizer.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetActionListener implements ActionListener {

    Base ref = null;

    public ResetActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ref != null) {
            ref.reset();
        }//end if
    }//endActionPerformed
}//end LoopActionListener