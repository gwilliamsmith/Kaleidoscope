package Listeners;

import SwingElements.Base;
import SwingElements.CalculateSizeForm;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;


public class CalculateSizeActionListener implements MouseListener{
    
    Base ref;
    
    public CalculateSizeActionListener(Base in){
        ref = in;
    }//end constructor

    @Override
    /**
     * Event handler method, opens the scheduler window when triggered.
     */
    public void mouseClicked(MouseEvent e) {
        CalculateSizeForm form = new CalculateSizeForm(ref);
        SwingUtilities.invokeLater(form);
    }//end mouseClicked

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}//end CalculateSizeActionListener
