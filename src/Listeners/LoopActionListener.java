package Listeners;

import SwingElements.Base;
import SwingElements.FolderSelectFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * {@link ActionListener} child, used to begin or pause auto-run steps when the
 * corresponding menu button is pressed in the {@link Base} class.
 */
public class LoopActionListener implements ActionListener {

    private Base ref;                                                           //Base object, used for reference

    /**
     * Constructor.
     *
     * @param in {@link Base} object, used for reference
     */
    public LoopActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    /**
     * Method performed on trigger. Flips the auto-run boolean, updates the menu
     * button text, and prompts the user if they want to save pictures of the
     * patterns generated.
     */
    public void actionPerformed(ActionEvent e) {
        if (!ref.getRun() && ref.getBookDirectory() == null) {
            int choice = JOptionPane.showConfirmDialog(ref, "Do you want to save generated pictures?", "No folder selected!", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION) {
                ref.flipRun();
            }//end if
            else {
                new FolderSelectFileChooser(ref, true).setVisible(true);
            }//end else
        }//end if
        else {
            ref.flipRun();
        }//end else
    }//endActionPerformed
}//end LoopActionListener
