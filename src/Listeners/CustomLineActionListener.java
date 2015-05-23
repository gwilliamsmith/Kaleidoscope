package Listeners;

import graphvisualizer.Base;
import SwingElements.CustomLineForm;
import graphvisualizer.GraphTupleInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomLineActionListener implements ActionListener {

    private Base ref;

    public CustomLineActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        ref.setGtiStorage(new GraphTupleInfo());
        CustomLineForm clf = new CustomLineForm(ref);
        clf.setVisible(true);
    }//end actionPerformed
}//end CustomLineActionListener
