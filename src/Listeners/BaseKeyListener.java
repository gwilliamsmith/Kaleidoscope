package Listeners;

import graphvisualizer.Base;
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
        if (ref != null) {
            if (e.getKeyChar() == 's') {
                ref.takeStep();
            }//end if
        }//end if
    }//end keyPressed

    @Override
    public void keyReleased(KeyEvent e) {
    }//end keyReleased
}//end BaseKeyListener
