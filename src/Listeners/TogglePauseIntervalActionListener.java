package Listeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TogglePauseIntervalActionListener implements ActionListener {

    Base ref;

    public TogglePauseIntervalActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        ref.getGraph().setPicturePauseToggle(!ref.getGraph().getPicturePauseToggle());
        String text = ref.getGraph().getPicturePauseToggle() ? "Disable pausing after interval picture" : "Enable pausing after interval picture";
        System.out.println(text);
        ref.setPauseIntervalToggleText(text);
    }//end actionPerformed

}//end ToggleSaveIntervalActionListener class

