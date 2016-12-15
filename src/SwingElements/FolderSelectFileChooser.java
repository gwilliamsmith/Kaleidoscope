package SwingElements;

import java.io.File;
import javax.swing.JFileChooser;

/**
 * File chooser, used to choose the location to save pictures taken.
 */
public class FolderSelectFileChooser extends JFileChooser {

    /**
     * Constructor.
     *
     * @param in {@link Base} object, used to access other objects as needed
     * @param run Determines if the {@link Base} should begin auto-running steps
     * after the file has been chosen (or the action is canceled)
     */
    public FolderSelectFileChooser(Base in, boolean run) {
        super();
        setCurrentDirectory(new File("C:\\"));
        setDialogTitle("Choose location to save coloring book pictures");
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        setAcceptAllFileFilterUsed(false);
        this.setDialogTitle("Save Location");
        if (showOpenDialog(in) == JFileChooser.APPROVE_OPTION) {
            in.setBookDirectory(getSelectedFile());
        }//end if
        if (run) {
            in.run();
        }//end if
        //in.getSettingsManager().writeSettings();
    }//end constructor

}//end FolderSelectFileChooser
