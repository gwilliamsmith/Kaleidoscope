package Listeners;

import graphvisualizer.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetActionListener implements ActionListener {

    private Base ref;

    public ResetActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        ref.reset();
    }//endActionPerformed
}//end LoopActionListener
