package Listeners;

import SwingElements.Base;
import SwingElements.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ToggleDragActionListener implements ActionListener{
    
    private Base ref;
    
    public ToggleDragActionListener(Base in){
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        Canvas canvas = ref.getCanvas();
        canvas.flipDrag();
        if(canvas.canDrag()){
            ref.getToggleDrag().setText("Disable drag to reposition");
        }//end if
        else{
            ref.getToggleDrag().setText("Enable drag to reposition");
        }//end else
    }//end actionPerformed

}//end ToggleDracActionListener
