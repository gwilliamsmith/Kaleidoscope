package Listeners;

import graphvisualizer.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AverageColorActionListener implements ActionListener {

    Base ref = null;

    public AverageColorActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ref != null) {
                ref.getAverageDisplay().setVisible(true);
        }//end if
    }//end actionPerformed

}//end PropertiesActionListener