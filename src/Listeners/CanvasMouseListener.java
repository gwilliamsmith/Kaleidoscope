package Listeners;

import SwingElements.Base;
import SwingElements.Canvas;
import graphvisualizer.Graph;
import graphvisualizer.GraphNode;
import graphvisualizer.GraphNodeColors;
import graphvisualizer.GraphTupleInfo;
import java.awt.event.*;
import javax.swing.SwingUtilities;

public class CanvasMouseListener extends MouseAdapter implements MouseWheelListener, MouseMotionListener {

    private Base ref;
    private int startY;
    private int startX;
    private GraphNode lastHovered;
    private boolean connect = false;

    public CanvasMouseListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void mouseClicked(MouseEvent e) {
        Canvas canvas = ref.getCanvas();
        Graph graph = ref.getGraph();
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (connect) {
                for (int i=0;i<graph.getGraphNodes().size();i++) {
                    GraphNode gn = graph.getGraphNodes().get(i);
                    if (gn.mapMovement(canvas.getWindowX(), canvas.getWindowY()).contains(e.getPoint())) {
                        if (gn != graph.getConnectA() && !gn.isConnected(graph.getConnectA())) {
                            graph.setConnectB(gn);
                        }//end if
                        connect = false;
                        break;
                    }//end if
                }//end for
                if (graph.getConnectB() != null && graph.getConnectA() != null) {
                    if (canvas.getGtiStorage() != null) {
                        graph.createNewFamily(graph.getConnectA(), graph.getConnectB(), canvas.getGtiStorage());
                        //ref.setGtiStorage(null);
                    }//end if
                    else {
                        if (canvas.getTempColor() != null) {
                            GraphTupleInfo gtiOut = new GraphTupleInfo();
                            gtiOut.color = canvas.getTempColor();
                            graph.createNewFamily(graph.getConnectA(), graph.getConnectB(), gtiOut);
                            canvas.setTempColor(null);
                        }//end if 
                        else {
                            graph.createNewFamily(graph.getConnectA(), graph.getConnectB(), new GraphTupleInfo());
                        }//end else
                    }//end else
                }//end if
                graph.resetNodeAdjacents(graph.getConnectA());
                connect  = false;
                graph.setConnectA(null);
                graph.setConnectB(null);
            }//end if
            else {
                for (int i=0;i<graph.getGraphNodes().size();i++) {
                    GraphNode gn = graph.getGraphNodes().get(i);
                    if (gn.mapMovement(canvas.getWindowX(), canvas.getWindowY()).contains(e.getPoint())) {
                        graph.setConnectA(gn);
                        connect = true;
                        graph.highlightNodeAdjacents(gn,GraphNodeColors.SELECTED_COLOR,GraphNodeColors.SELECTED_ADJACENT_COLOR);
                        break;
                    }//end else
                }//end for
            }//end else
        }//end if
        else if (SwingUtilities.isRightMouseButton(e)) {
            ref.getRightClickMenu().show(canvas, e.getX(), e.getY());
        }//end else if
        canvas.repaint();
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
    public void mouseMoved(MouseEvent e) {
        Canvas canvas = ref.getCanvas();
        Graph graph = ref.getGraph();
        GraphNode selected = null;
        canvas.setMouseX(e.getX());
        canvas.setMouseY(e.getY());
        for (int i=0;i<graph.getGraphNodes().size();i++) {
            GraphNode gn = graph.getGraphNodes().get(i);
            if (gn.mapMovement(canvas.getWindowX(), canvas.getWindowY()).contains(e.getPoint()) && gn != lastHovered) {
                selected = gn;
                lastHovered = gn;
            }//end if
            else if(gn.mapMovement(canvas.getWindowX(), canvas.getWindowY()).contains(e.getPoint()) && gn == lastHovered){
                selected = gn;
            }//end else if
        }//end for
        graph.setLastHovered(selected);
    }//end mouseMoved

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Canvas canvas = ref.getCanvas();
        if (e.getPreciseWheelRotation() > 0) {
            canvas.decreaseZoomLevel();
        }//end if
        else if (e.getPreciseWheelRotation() < 0) {
            canvas.increaseZoomLevel();
        }//end else if
        canvas.setResized(true);
    }//end mouseWheelMoved
}//end CanvasMouseListener
