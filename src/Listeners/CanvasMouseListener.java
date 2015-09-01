package Listeners;

import SwingElements.Canvas;
import SwingElements.Base;
import graphvisualizer.GraphNode;
import graphvisualizer.GraphTupleInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.SwingUtilities;

public class CanvasMouseListener extends MouseAdapter implements MouseWheelListener, MouseMotionListener{

    private Base ref;
    private int startY;
    private int startX;

    public CanvasMouseListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            
            if (ref.getCanvas().getConnect()) {
                for (GraphNode gn : ref.getGraph().getGraphNodes()) {
                    if (gn.mapMovement(ref.getCanvas().getWindowX(),ref.getCanvas().getWindowY()).contains(e.getPoint())) {
                        if (gn != ref.getCanvas().getConnectA() && !gn.isConnected(ref.getCanvas().getConnectA())) {
                            ref.getCanvas().setConnectB(gn);
                        }//end if
                        ref.getCanvas().setConnect(false);
                        break;
                    }//end if
                }//end for
                if (ref.getCanvas().getConnectB() != null && ref.getCanvas().getConnectA() != null) {
                    if (ref.getCanvas().getGtiStorage() != null) {
                        ref.getGraph().connector(ref.getCanvas().getConnectA(), ref.getCanvas().getConnectB(), ref.getCanvas().getGtiStorage());
                        //ref.setGtiStorage(null);
                    }//end if
                    else {
                        if (ref.getCanvas().getTempColor() != null) {
                            GraphTupleInfo gtiOut = new GraphTupleInfo();
                            gtiOut.color = ref.getCanvas().getTempColor();
                            ref.getGraph().connector(ref.getCanvas().getConnectA(), ref.getCanvas().getConnectB(), gtiOut);
                            ref.getCanvas().setTempColor(null);
                        }//end if 
                        else {
                            ref.getGraph().connector(ref.getCanvas().getConnectA(), ref.getCanvas().getConnectB(), new GraphTupleInfo());
                        }//end else
                    }//end else
                }//end if
                ref.getCanvas().setActionString("");
                ref.getCanvas().repaint();
                ref.getCanvas().setConnect(false);
                ref.getCanvas().setConnectA(null);
                ref.getCanvas().setConnectB(null);
            }//end if
            else {
                for (GraphNode gn : ref.getGraph().getGraphNodes()) {
                    if (gn.mapMovement(ref.getCanvas().getWindowX(),ref.getCanvas().getWindowY()).contains(e.getPoint())) {
                        ref.getCanvas().setActionString("Connect node");
                        ref.getCanvas().setConnectA(gn);
                        ref.getCanvas().setConnect(true);
                        break;
                    }//end else
                }//end for
            }//end else
        }//end if
        else if (SwingUtilities.isRightMouseButton(e)) {
            ref.getRightClickMenu().show(ref.getCanvas(), e.getX(), e.getY());
        }//end else if
        ref.repaint();
    }//end MouseClicked
    
    @Override
    public void mousePressed(MouseEvent e){
        startY = e.getYOnScreen();
        startX = e.getXOnScreen();
    }//end mousePressed
    
    @Override
    public void mouseDragged(MouseEvent e){
        ref.getCanvas().modifyWindowX(e.getXOnScreen()-startX);
        ref.getCanvas().modifyWindowY(e.getYOnScreen()-startY);
        startX = e.getXOnScreen();
        startY = e.getYOnScreen();
        //System.out.println(ref.getCanvas().windowX + " " + ref.getCanvas().windowY);
    }//end mouseDragged

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getPreciseWheelRotation() > 0) {
            if (ref.getPointSize() > 2) {
                ref.setPointSize((int) Math.max(ref.getPointSize() - (e.getPreciseWheelRotation() * 2), 2));
            }//end if
            if (ref.getSpacing() > 10) {
                 ref.setSpacing((int) Math.max(ref.getSpacing() - (e.getPreciseWheelRotation() * 2), 10));
            }//end if
        }//end if
        else if (e.getPreciseWheelRotation() < 0) {
            if (ref.getPointSize() < 10) {
                ref.setPointSize((int) Math.max(ref.getPointSize() - (e.getPreciseWheelRotation() * 2), 2));
            }//end if
            if (ref.getSpacing() < 18) {
                ref.setSpacing((int) Math.max(ref.getSpacing() - (e.getPreciseWheelRotation() * 2), 10));
            }//end if
        }//end else if
        ref.getGraph().resizeGrid();
    }//end mouseWheelMoved
}//end CanvasMouseListener
