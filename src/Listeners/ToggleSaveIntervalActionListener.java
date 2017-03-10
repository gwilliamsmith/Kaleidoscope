package Listeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ToggleSaveIntervalActionListener implements ActionListener{

    Base ref;
    
    public ToggleSaveIntervalActionListener(Base in){
        ref = in;
    }//end constructor
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ref.getGraph().setPictureSaveToggle(!ref.getGraph().getPictureSaveToggle());
        String text = ref.getGraph().getPictureSaveToggle() ? "Disable saving pictures on interval" : "Enable saving pictures on interval";
        System.out.println(text);
        ref.setSaveIntervalToggleText(text);
    }//end actionPerformed

}//end ToggleSaveIntervalActionListener class
