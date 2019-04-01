package Listeners.PropertiesListeners;

import SwingElements.Base;
import SwingElements.PropertiesSelectionForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

/**
 * {@link ActionListener} child, used to open the properties menu when the
 * corresponding menu button is pressed in the {@link Base} class.
 */
public class PropertiesActionListener implements ActionListener {

    private Base ref;                                                           //Base object, used for reference

    /**
     * Constructor.
     *
     * @param in {@link Base} object, used for reference
     */
    public PropertiesActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    /**
     * Method performed on trigger. Opens the properties menu.
     */
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new PropertiesSelectionForm(ref));
    }//end actionPerformed

}//end PropertiesActionListener
