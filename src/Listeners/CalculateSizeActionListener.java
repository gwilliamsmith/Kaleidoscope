package Listeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CalculateSizeActionListener implements ActionListener{
    
    Base ref;
    
    public CalculateSizeActionListener(Base in){
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }//end actionPerformed

}//end CalculateSizeActionListener
