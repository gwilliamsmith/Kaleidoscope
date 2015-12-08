package Listeners;

import SwingElements.Base;
import SwingElements.Canvas;
import graphvisualizer.Graph;
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
        Graph graph = ref.getGraph();
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (graph.getConnect()) {
                for (GraphNode gn : graph.getGraphNodes()) {
                    if (gn.mapMovement(canvas.getWindowX(), canvas.getWindowY()).contains(e.getPoint())) {
                        if (gn != graph.getConnectA() && !gn.isConnected(graph.getConnectA())) {
                            graph.setConnectB(gn);
                        }//end if
                        graph.setConnect(false);
                        break;
                    }//end if
                }//end for
                if (graph.getConnectB() != null && graph.getConnectA() != null) {
                    if (canvas.getGtiStorage() != null) {
                        graph.connector(graph.getConnectA(), graph.getConnectB(), canvas.getGtiStorage());
                        //ref.setGtiStorage(null);
                    }//end if
                    else {
                        if (canvas.getTempColor() != null) {
                            GraphTupleInfo gtiOut = new GraphTupleInfo();
                            gtiOut.color = canvas.getTempColor();
                            graph.connector(graph.getConnectA(), graph.getConnectB(), gtiOut);
                            canvas.setTempColor(null);
                        }//end if 
                        else {
                            graph.connector(graph.getConnectA(), graph.getConnectB(), new GraphTupleInfo());
                        }//end else
                    }//end else
                }//end if
                graph.resetSelectionHighlight(graph.getConnectA());
                canvas.repaint();
                graph.setConnect(false);
                graph.setConnectA(null);
                graph.setConnectB(null);
            }//end if
            else {
                for (GraphNode gn : graph.getGraphNodes()) {
                    if (gn.mapMovement(canvas.getWindowX(), canvas.getWindowY()).contains(e.getPoint())) {
                        graph.setConnectA(gn);
                        graph.setConnect(true);
                        graph.highlightNodeSelection(gn);
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
            canvas.decreaseZoomLevel();
        }//end if
        else if (e.getPreciseWheelRotation() < 0) {
            canvas.increaseZoomLevel();
        }//end else if
        canvas.resizeGrid();
    }//end mouseWheelMoved
}//end CanvasMouseListener
