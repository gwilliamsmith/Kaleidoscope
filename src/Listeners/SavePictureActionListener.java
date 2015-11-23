package Listeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class SavePictureActionListener implements ActionListener {
    
    private Base ref;
    
    public SavePictureActionListener(Base in){
        ref = in;
    }//end constructor 
    
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean tempRun = ref.getRun();
        ref.setRun(false);
        JFileChooser fileChooser = new JFileChooser();
        FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.addChoosableFileFilter(imageFilter);
        int returnVal = fileChooser.showSaveDialog(ref);
        if(returnVal == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().getName() != null){
            try {
                ImageIO.write(ref.getCanvas().producePicture(), "jpg", new File(fileChooser.getSelectedFile().getAbsolutePath()+".jpg"));
            } catch (IOException ex) {
                System.err.println("Error Saving file: " + fileChooser.getSelectedFile().getAbsolutePath() + ".jpg");
            }//end try catch block
        }//end if
        ref.setRun(tempRun);
    }//end actionPerformed

}//end SavePictureActionListener