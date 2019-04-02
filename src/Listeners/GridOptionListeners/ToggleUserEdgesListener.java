/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners.GridOptionListeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToggleUserEdgesListener implements ActionListener {

    private final Base ref;

    public ToggleUserEdgesListener(Base in){
        ref = in;
    }//end ToggleUserEdgesListener
    
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean out = !ref.getShowUserEdges();
        String newText = out ? "Hide User Placed Edges":"Show User Placed Edges";
        ref.setToggleUserEdgesText(newText);
        ref.setShowUserEdges(out);
    }//end actionPerformed
    
    
}//end ToggleUserEdgesListener class
