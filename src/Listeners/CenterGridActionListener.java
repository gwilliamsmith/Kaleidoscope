package Listeners;

import SwingElements.Base;
import SwingElements.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@link ActionListener} child, used to center the grid in the {@link Base}
 * window when the corresponding menu button is pressed in the {@link Base}
 * class.
 */
public class CenterGridActionListener implements ActionListener {

    private Base ref;                                                           //Base object, used for reference

    /**
     * Constructor.
     *
     * @param in {@link Base} object, used for reference
     */
    public CenterGridActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    /**
     * Method performed on trigger. Calculates the coordinates closest to the
     * center of the window, then modifies the windowX and windowY
     * {@link Canvas} variables to move the grid into the center of the window.
     */
    public void actionPerformed(ActionEvent e) {
        Canvas canvas = ref.getCanvas();
        int halfColumns = ref.getGraph().getMatrix().length / 2;
        int halfRows = ref.getGraph().getMatrix()[0].length / 2;
        canvas.resetCanvasWindow();
        canvas.modifyWindowX(canvas.getWidth() / 2 - ((canvas.getPointSize() + canvas.getSpacing()) * halfColumns));
        canvas.modifyWindowY(canvas.getHeight() / 2 - ((canvas.getPointSize() + canvas.getSpacing()) * halfRows));

    }//end actionPerformed

}//end CenterGridActionListener
