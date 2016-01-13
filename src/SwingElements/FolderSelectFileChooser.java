package SwingElements;

import java.io.File;
import javax.swing.JFileChooser;


public class FolderSelectFileChooser extends JFileChooser{
    
    public FolderSelectFileChooser(Base in, boolean run){
        super();
        setCurrentDirectory(new File("C:\\"));
        setDialogTitle("Choose location to save coloring book pictures");
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        setAcceptAllFileFilterUsed(false);
        if (showOpenDialog(in) == JFileChooser.APPROVE_OPTION) {
            in.setBookDirectory(getSelectedFile());
        }//end if
        if(run){
            in.run();
        }//end if
        //in.getSettingsManager().writeSettings();
    }//end constructor
    
}//end FolderSelectFileChooser
