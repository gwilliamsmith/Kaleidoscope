 package Listeners;

import SwingElements.Base;
import SwingElements.CustomLineForm;
import graphvisualizer.GraphTupleInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

public class CustomLineActionListener implements ActionListener {

    private Base ref;

    public CustomLineActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        ref.getCanvas().setGtiStorage(new GraphTupleInfo());
        SwingUtilities.invokeLater(new CustomLineForm(ref));
    }//end actionPerformed
}//end CustomLineActionListener