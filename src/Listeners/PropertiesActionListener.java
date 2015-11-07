package Listeners;

import SwingElements.Base;
import SwingElements.PropertiesSelectionForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertiesActionListener implements ActionListener {

    private Base ref;

    public PropertiesActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        ref.setRun(false);
        new PropertiesSelectionForm(ref).setVisible(true);
    }//end actionPerformed

}//end PropertiesActionListener
