package Listeners;

import SwingElements.Base;
import SwingElements.FolderSelectFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FolderSelectActionListener implements ActionListener {

    private Base ref;

    public FolderSelectActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        new FolderSelectFileChooser(ref, false).setVisible(true);
    }//enc actionPerformed

}//end FolderSelectActionListener
