package Listeners;

import Statistics.FamilyStatisticsTuple;
import SwingElements.Base;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BaseKeyListener implements KeyListener {

    private Base ref;

    public BaseKeyListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void keyTyped(KeyEvent e) {
    }//end keyTyped

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 's') {
            ref.getGraph().takeStep();
        }//end if
        else if(e.getKeyChar() == 'q'){
            ref.getCanvas().increaseZoomLevel();
        }//end else if
        else if (e.getKeyChar() == 'a'){
            ref.getCanvas().decreaseZoomLevel();
        }//end else if
        else if(e.getKeyChar() == 'f'){
            for(int i=1;i<=ref.getGraph().getFamilyCount();i++){
                System.out.println(new FamilyStatisticsTuple(ref.getGraph().pullFamily(i),i,ref.getGraph()).outputPureStatistics());
                ref.getGraph().showFamilyColorGradient(i-1);
            }//end for
        }//end else if
    }//end keyPressed

    @Override
    public void keyReleased(KeyEvent e) {
    }//end keyReleased
}//end BaseKeyListener