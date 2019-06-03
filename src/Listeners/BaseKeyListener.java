package Listeners;

import SwingElements.Base;
import SwingElements.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BaseKeyListener implements KeyListener {

    private int debugCount;

    private static final int DEBUG_TOGGLE_COUNT = 5;

    private final Base ref;

    public BaseKeyListener(Base in) {
        ref = in;
        debugCount = Canvas.DEBUG ? DEBUG_TOGGLE_COUNT : 0;
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
            if (++debugCount == DEBUG_TOGGLE_COUNT) {
                ref.showDebugMenu();
                debugCount = 0;
            }//end if
            return;
        }//end if
        if((e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) != 0){
            ref.setShiftDown(true);
        }//end if
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                ref.getGraph().takeStep();
                return;
            case KeyEvent.VK_SPACE:
                ref.triggerLoop();
                return;
            case KeyEvent.VK_S:
                ref.triggerSaveAction();
                return;
            case KeyEvent.VK_Q:
                ref.getCanvas().increaseZoomLevel();
                return;
            case KeyEvent.VK_A:
                ref.getCanvas().decreaseZoomLevel();
        }//end switch
    }//end keyPressed

    @Override
    public void keyReleased(KeyEvent e) {
        if((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) == 0){
            debugCount = 0;
        }//end if
        if((e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) == 0){
            ref.setShiftDown(false);
        }//end if
    }//end keyReleased
}//end BaseKeyListener
