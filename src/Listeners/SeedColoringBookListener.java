package Listeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeedColoringBookListener implements ActionListener{
    
    private Base ref;
    
    public SeedColoringBookListener(Base in){
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        ref.getGraph().generateSeeds();
        ref.getGraph().setSeeded(true);
    }//end actionPerformed
}//end SeedColoringBookListener