package Listeners;

import SwingElements.Base;
import SwingElements.SchedulerForm;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


//This is a MouseListener and not a MenuListener because MenuListeners force the
//focus away from the created window, and can be activated by moving the mouse
//from one menu item to the Scheduler menu item
public class SchedulerMenuActionListener implements MouseListener {

    private Base ref;

    public SchedulerMenuActionListener(Base in) {
        ref = in;
    }//end SchedulerMenuActionListener

    @Override
    public void mouseClicked(MouseEvent e) {
        new SchedulerForm(ref).setVisible(true);
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

}//end SchedulerMenuActionListener class
