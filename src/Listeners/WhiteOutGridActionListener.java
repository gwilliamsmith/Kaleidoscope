package Listeners;

import SwingElements.Base;
import graphvisualizer.GraphNode;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WhiteOutGridActionListener implements ActionListener {

    private Base ref;

    public WhiteOutGridActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        for (GraphNode[] matrix1 : ref.getGraph().getMatrix()) {
            for (GraphNode gn : matrix1) {
                gn.setColor(new Color(255, 255, 255));
                ref.getCanvas().repaint();
            }//end for
        }//end for
    }//end actionPerformed
}//end WhiteOutGridActionListener
