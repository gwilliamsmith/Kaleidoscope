package Listeners;

import graphvisualizer.Base;
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
            
            if (ref.getConnect()) {
                for (GraphNode gn : ref.getGraph().getGraphNodes()) {
                    if (gn.mapMovement(ref.getCanvas().getWindowX(),ref.getCanvas().getWindowY()).contains(e.getPoint())) {
                        if (gn != ref.getConnectA() && !gn.isConnected(ref.getConnectA())) {
                            ref.setConnectB(gn);
                        }//end if
                        ref.setConnect(false);
                        break;
                    }//end if
                }//end for
                if (ref.getConnectB() != null && ref.getConnectA() != null) {
                    if (ref.getGtiStorage() != null) {
                        ref.getGraph().connector(ref.getConnectA(), ref.getConnectB(), ref.getGtiStorage());
                        ref.setGtiStorage(null);
                    }//end if
                    else {
                        if (ref.getTempColor() != null) {
                            GraphTupleInfo gtiOut = new GraphTupleInfo();
                            gtiOut.color = ref.getTempColor();
                            ref.getGraph().connector(ref.getConnectA(), ref.getConnectB(), gtiOut);
                            ref.setTempColor(null);
                        }//end if 
                        else {
                            ref.getGraph().connector(ref.getConnectA(), ref.getConnectB(), new GraphTupleInfo());
                        }//end else
                    }//end else
                }//end if
                ref.setActionString("");
                ref.getCanvas().repaint();
                ref.setConnect(false);
                ref.setConnectA(null);
                ref.setConnectB(null);
            }//end if
            else {
                for (GraphNode gn : ref.getGraph().getGraphNodes()) {
                    if (gn.mapMovement(ref.getCanvas().getWindowX(),ref.getCanvas().getWindowY()).contains(e.getPoint())) {
                        ref.setActionString("Connect node");
                        ref.setConnectA(gn);
                        ref.setConnect(true);
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
