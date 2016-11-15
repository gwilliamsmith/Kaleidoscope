package Listeners;

import SwingElements.Base;
import SwingElements.CustomLineForm;
import graphvisualizer.GraphTupleInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

/**
 * {@link ActionListener} child, used to open the custom line settings window
 * when the corresponding menu button is pressed in the {@link Base} class.
 */
public class CustomLineActionListener implements ActionListener {

    private Base ref;                                                           //Base object, used for reference

    /**
     * Constructor.
     *
     * @param in {@link Base} object, used for reference
     */
    public CustomLineActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    /**
     * Method performed on trigger. Creates a new {@link CustomLineForm} to
     * input details of a new custom line.
     */
    public void actionPerformed(ActionEvent e) {
        ref.getCanvas().setGtiStorage(new GraphTupleInfo());
        SwingUtilities.invokeLater(new CustomLineForm(ref));
    }//end actionPerformed
}//end CustomLineActionListener
