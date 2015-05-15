package Listeners;

import graphvisualizer.Base;
import graphvisualizer.GraphNode;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WhiteOutGridActionListener implements ActionListener {

    Base ref = null;

    public WhiteOutGridActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ref != null) {
            for (GraphNode[] matrix1 : ref.graph.getMatrix()) {
                for (GraphNode gn : matrix1) {
                    gn.setColor(new Color(255, 255, 255));
                    ref.getCanvas().repaint();
                }//end for
            }//end for
        }//end if
    }//end actionPerformed
}//end WhiteOutGridActionListener
