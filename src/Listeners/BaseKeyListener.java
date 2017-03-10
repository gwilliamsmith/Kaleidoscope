package Listeners;

import SwingElements.Base;
import SwingElements.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BaseKeyListener implements KeyListener {

    private int debugCount = 0;
    
    private static int DEBUG_TOGGLE_COUNT = 5;
    
    private Base ref;

    public BaseKeyListener(Base in) {
        ref = in;
        debugCount = Canvas.DEBUG ? DEBUG_TOGGLE_COUNT:0;
    }//end constructor

    @Override
    public void keyTyped(KeyEvent e) {
    }//end keyTyped

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
            ref.triggerSaveStateAction();
            return;
        }//end if
        if ((e.getKeyCode() == KeyEvent.VK_F) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
            for (int i = 1; i <= ref.getGraph().getFamilyCount(); i++) {
                ref.getGraph().showFamilyColorGradient(i - 1);
            }//end for
            return;
        }//end if
        if ((e.getKeyCode() == KeyEvent.VK_D) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
            if (Canvas.DEBUG){
                Canvas.DEBUG = (--debugCount != 0);
            }//end if
            else{
                Canvas.DEBUG = (++debugCount == DEBUG_TOGGLE_COUNT);
            }//end else
            System.out.println(Canvas.DEBUG + " " + debugCount);
            return;
        }//end if
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                ref.getGraph().takeStep();
                break;
            case KeyEvent.VK_SPACE:
                ref.triggerLoop();
                break;
        }//end switch
        switch (e.getKeyChar()) {
            case 's':
                ref.triggerSaveAction();
                break;
            case 'q':
                ref.getCanvas().increaseZoomLevel();
                break;
            case 'a':
                ref.getCanvas().decreaseZoomLevel();
                break;
        }//end switch
    }//end keyPressed

    @Override
    public void keyReleased(KeyEvent e) {
    }//end keyReleased
}//end BaseKeyListener
