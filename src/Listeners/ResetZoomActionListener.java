package Listeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ResetZoomActionListener implements ActionListener{

    Base ref;
    
    public ResetZoomActionListener(Base in){
        ref = in;
    }//end constructor
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ref.getCanvas().setZoomLevel(0);
    }//end actionPerformed

}//end ResetZoomActionListener class
