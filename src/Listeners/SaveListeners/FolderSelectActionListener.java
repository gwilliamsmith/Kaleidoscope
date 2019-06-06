package Listeners.SaveListeners;

import SwingElements.Base;
import SwingElements.FolderSelectFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@link ActionListener} child, used to open the folder selection window when
 * the corresponding menu button is pressed in the {@link Base} class.
 */
public class FolderSelectActionListener implements ActionListener {

    private Base ref;                                                           //Base object, used for reference

    /**
     * Constructor.
     *
     * @param in {@link Base} object, used for reference
     */
    public FolderSelectActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        new FolderSelectFileChooser(ref, false).setVisible(true);
    }//enc actionPerformed

}//end FolderSelectActionListener
