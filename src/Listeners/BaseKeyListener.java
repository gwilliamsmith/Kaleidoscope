package Listeners;

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
    }//end keyPressed

    @Override
    public void keyReleased(KeyEvent e) {
    }//end keyReleased
}//end BaseKeyListener