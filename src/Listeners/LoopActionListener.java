package Listeners;

import SwingElements.Base;
import SwingElements.FolderSelectFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoopActionListener implements ActionListener {

    private Base ref;

    public LoopActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!ref.getRun() && ref.getBookDirectory() == null) {
            int choice = JOptionPane.showConfirmDialog(ref, "Do you want to save generated pictures?", "No folder selected!", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION) {
                ref.flipRun();
            }//end if
            else{
                new FolderSelectFileChooser(ref,true).setVisible(true);
            }//end else
        }//end if
        else {
            ref.flipRun();
        }//end else
        if (ref.getRun()) {
            ref.getLoop().setText("Pause");
        }//end if
        else {
            ref.getLoop().setText("Run");
        }//end else
    }//endActionPerformed
}//end LoopActionListener
