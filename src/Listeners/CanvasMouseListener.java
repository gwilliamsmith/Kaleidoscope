package Listeners;

import SwingElements.Base;
import SwingElements.Canvas;
import graphvisualizer.GraphNode;
import graphvisualizer.GraphTupleInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.SwingUtilities;

public class CanvasMouseListener extends MouseAdapter implements MouseWheelListener, MouseMotionListener {

    private Base ref;
    private int startY;
    private int startX;

    public CanvasMouseListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void mouseClicked(MouseEvent e) {
        Canvas canvas = ref.getCanvas();
        if (SwingUtilities.isLeftMouseButton(e)) {

            if (canvas.getConnect()) {
                for (GraphNode gn : ref.getGraph().getGraphNodes()) {
                    if (gn.mapMovement(canvas.getWindowX(), canvas.getWindowY()).contains(e.getPoint())) {
                        if (gn != canvas.getConnectA() && !gn.isConnected(canvas.getConnectA())) {
                            canvas.setConnectB(gn);
                        }//end if
                        canvas.setConnect(false);
                        break;
                    }//end if
                }//end for
                if (canvas.getConnectB() != null && canvas.getConnectA() != null) {
                    if (canvas.getGtiStorage() != null) {
                        ref.getGraph().connector(canvas.getConnectA(), canvas.getConnectB(), canvas.getGtiStorage());
                        //ref.setGtiStorage(null);
                    }//end if
                    else {
                        if (canvas.getTempColor() != null) {
                            GraphTupleInfo gtiOut = new GraphTupleInfo();
                            gtiOut.color = canvas.getTempColor();
                            ref.getGraph().connector(canvas.getConnectA(), canvas.getConnectB(), gtiOut);
                            canvas.setTempColor(null);
                        }//end if 
                        else {
                            ref.getGraph().connector(canvas.getConnectA(), canvas.getConnectB(), new GraphTupleInfo());
                        }//end else
                    }//end else
                }//end if
                canvas.setActionString("");
                canvas.repaint();
                canvas.setConnect(false);
                canvas.setConnectA(null);
                canvas.setConnectB(null);
            }//end if
            else {
                for (GraphNode gn : ref.getGraph().getGraphNodes()) {
                    if (gn.mapMovement(canvas.getWindowX(), canvas.getWindowY()).contains(e.getPoint())) {
                        canvas.setActionString("Connect node");
                        canvas.setConnectA(gn);
                        canvas.setConnect(true);
                        break;
                    }//end else
                }//end for
            }//end else
        }//end if
        else if (SwingUtilities.isRightMouseButton(e)) {
            ref.getRightClickMenu().show(canvas, e.getX(), e.getY());
        }//end else if
        ref.repaint();
    }//end MouseClicked

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && ref.getCanvas().canDrag()) {
            startY = e.getYOnScreen();
            startX = e.getXOnScreen();
        }//end if
    }//end mousePressed

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && ref.getCanvas().canDrag()) {
            Canvas canvas = ref.getCanvas();
            canvas.modifyWindowX(e.getXOnScreen() - startX);
            canvas.modifyWindowY(e.getYOnScreen() - startY);
            startX = e.getXOnScreen();
            startY = e.getYOnScreen();
        }//end if
    }//end mouseDragged

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Canvas canvas = ref.getCanvas();
        if (e.getPreciseWheelRotation() > 0) {
            if (canvas.getPointSize() > canvas.getMinPointSize()) {
                canvas.setPointSize((int) Math.max(canvas.getPointSize() - (e.getPreciseWheelRotation() * 2), canvas.getMinPointSize()));
            }//end if
            if (canvas.getSpacing() > canvas.getMinSpacing()) {
                canvas.setSpacing((int) Math.max(canvas.getSpacing() - (e.getPreciseWheelRotation() * 2), canvas.getMinSpacing()));
            }//end if
        }//end if
        else if (e.getPreciseWheelRotation() < 0) {
            if (canvas.getPointSize() < canvas.getMinPointSize() * 5) {
                canvas.setPointSize((int) Math.max(canvas.getPointSize() - (e.getPreciseWheelRotation() * 2), canvas.getMinPointSize()));
            }//end if
            if (canvas.getSpacing() < canvas.getMinSpacing() + (canvas.getMinPointSize() * 5) - canvas.getMinPointSize()) {
                canvas.setSpacing((int) Math.max(canvas.getSpacing() - (e.getPreciseWheelRotation() * 2), canvas.getMinSpacing()));
            }//end if
        }//end else if
        ref.getGraph().resizeGrid();
    }//end mouseWheelMoved
}//end CanvasMouseListener
