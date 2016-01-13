package Listeners;

import SwingElements.Base;
import SwingElements.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CenterGridActionListener implements ActionListener{
    
    private Base ref;
    
    public CenterGridActionListener(Base in){
        ref = in;
    }//end constructor
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Canvas canvas = ref.getCanvas();
        int halfColumns = ref.getGraph().getMatrix().length/2; 
        int halfRows = ref.getGraph().getMatrix()[0].length/2;
        canvas.resetCanvasWindow();
        canvas.modifyWindowX(canvas.getWidth()/2 - (canvas.getPointSize()/2 + canvas.getSpacing() * halfColumns));
        canvas.modifyWindowY(canvas.getHeight()/2- (canvas.getPointSize()/2 + canvas.getSpacing() * halfRows));
        
    }//end actionPerformed

}//end CenterGridActionListener
