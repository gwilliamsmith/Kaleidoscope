package Listeners.PropertiesListeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@link ActionListener} child, used to seed the graph for coloring book page
 * generation when the corresponding menu button is pressed in the {@link Base}
 * class.
 */
public class SeedColoringBookListener implements ActionListener {

    private final Base ref;                                                           //Base object, used for reference

    /**
     * Constructor.
     *
     * @param in {@link Base} object, used as reference
     */
    public SeedColoringBookListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        ref.getGraph().generateSeeds();
        ref.getGraph().setSeeded(true);
    }//end actionPerformed
}//end SeedColoringBookListener
