package Listeners.SaveListeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * {@link ActionListener} child, used to save a picture of the graph when the
 * corresponding menu button is pressed in the {@link Base} class.
 */
public class SavePictureActionListener implements ActionListener {

    private Base ref;                                                           //Base object, used for reference

    /**
     * Constructor.
     *
     * @param in {@link Base} object, used for reference
     */
    public SavePictureActionListener(Base in) {
        ref = in;
    }//end constructor 

    @Override
    /**
     * Method performed on trigger. Opens a dialog for the user to name the
     * picture to be created, as well as select a location for the file to be
     * saved. Once both have been done, saves the picture.
     */
    public void actionPerformed(ActionEvent e) {
        boolean tempRun = ref.isRunning();
        ref.pause();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Picture");
        FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.addChoosableFileFilter(imageFilter);
        int returnVal = fileChooser.showSaveDialog(ref);
        if (returnVal == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().getName() != null) {
            try {
                ImageIO.write(ref.getCanvas().produceTrimmedImage(), "png", new File(fileChooser.getSelectedFile().getAbsolutePath() + ".png"));
            } catch (IOException ex) {
                System.err.println("Error Saving file: " + fileChooser.getSelectedFile().getAbsolutePath() + ".png");
            }//end try catch block
        }//end if
        if (tempRun) {
            ref.run();
        }//end if
    }//end actionPerformed

}//end SavePictureActionListener
