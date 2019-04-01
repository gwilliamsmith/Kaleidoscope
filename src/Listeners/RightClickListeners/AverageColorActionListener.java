package Listeners.RightClickListeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AverageColorActionListener implements ActionListener {

    private Base ref;

    /**
     * Constructor.
     *
     * @param in {@link Base} object, used for reference.
     */
    public AverageColorActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ref != null) {
            ref.showAverageDisplay();
        }//end if
    }//end actionPerformed

}//end PropertiesActionListener
