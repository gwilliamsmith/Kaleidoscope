package Listeners;

import graphvisualizer.Base;
import SwingElements.CustomLineForm;
import graphvisualizer.GraphTupleInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomLineActionListener implements ActionListener{
    
    Base ref = null;
    
    public CustomLineActionListener(Base in){
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ref != null) {
            ref.setGtiStorage(new GraphTupleInfo());
            CustomLineForm clf = new CustomLineForm();
            clf.giveStoreLocation(ref.getGtiStorage());
            clf.giveField(ref);
            clf.setVisible(true);
        }//end if
    }//end actionPerformed
}//end CustomLineActionListener
