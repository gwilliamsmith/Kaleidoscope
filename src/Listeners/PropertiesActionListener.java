package Listeners;

import SwingElements.Base;
import SwingElements.PropertiesSelectionForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

public class PropertiesActionListener implements ActionListener {

    private Base ref;

    public PropertiesActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new PropertiesSelectionForm(ref));
    }//end actionPerformed

}//end PropertiesActionListener