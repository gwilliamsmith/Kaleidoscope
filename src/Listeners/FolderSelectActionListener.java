package Listeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

public class FolderSelectActionListener implements ActionListener {

    private Base ref;

    public FolderSelectActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("C:\\"));
        chooser.setDialogTitle("Choose location to save coloring book pictures");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(ref) == JFileChooser.APPROVE_OPTION) {
            ref.setBookDirectory(chooser.getSelectedFile());
            System.out.println(ref.getBookDirectory());
        }//end if
    }//enc actionPerformed

}//end FolderSelectActionListener
