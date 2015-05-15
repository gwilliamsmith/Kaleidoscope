package Listeners;

import graphvisualizer.Base;
import SwingElements.SettingsSelectionForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertiesActionListener implements ActionListener {

    Base ref = null;

    public PropertiesActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ref != null) {
            ref.setRun(false);
            new SettingsSelectionForm(ref).setVisible(true);
        }//end if
    }//end actionPerformed

}//end PropertiesActionListener
