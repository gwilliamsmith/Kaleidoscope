package Listeners.PropertiesListeners;

import SwingElements.Base;
import SwingElements.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@link ActionListener} child, used to enable/disable grid repositioning via
 * mouse dragging when the corresponding menu button is pressed in the
 * {@link Base} class.
 */
public class ToggleDragActionListener implements ActionListener {

    private Base ref;                                                           //Base object, used for reference

    /**
     * Constructor.
     *
     * @param in {@link Base} class, used for reference
     */
    public ToggleDragActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        Canvas canvas = ref.getCanvas();
        canvas.flipDrag();
        if (canvas.canDrag()) {
            ref.getToggleDrag().setText("Disable drag to reposition");
        }//end if
        else {
            ref.getToggleDrag().setText("Enable drag to reposition");
        }//end else
    }//end actionPerformed

}//end ToggleDracActionListener
