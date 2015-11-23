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
        if(ref.getRun()){
            ref.getLoop().setText("Pause");
        }//end if
        else{
            ref.getLoop().setText("Run");
        }
    }//endActionPerformed
}//end LoopActionListener