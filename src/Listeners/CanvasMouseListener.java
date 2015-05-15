package Listeners;

import graphvisualizer.Base;
import graphvisualizer.GraphNode;
import graphvisualizer.GraphTupleInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class CanvasMouseListener extends MouseAdapter {

    Base ref = null;

    public CanvasMouseListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (ref.getConnect()) {
                for (GraphNode gn : ref.graph.getGraphNodes()) {
                    if (gn.contains(e.getPoint())) {
                        ref.setConnectB(gn);
                        ref.setConnect(false);
                        break;
                    }//end if
                }//end for
                if (ref.getConnectB() != null && ref.getConnectA() != null) {
                    if (ref.getConnectA() != ref.getConnectB()) {
                        if (!ref.getConnectA().isConnected(ref.getConnectB())) {
                            if (ref.getGtiStorage() != null) {
                                ref.graph.connector(ref.getConnectA(), ref.getConnectB(), ref.getGtiStorage());
                                ref.setGtiStorage(null);
                            }//end if
                            else {
                                if (ref.getTempColor() != null) {
                                    GraphTupleInfo gtiOut = new GraphTupleInfo();
                                    gtiOut.color = ref.getTempColor();
                                    ref.graph.connector(ref.getConnectA(), ref.getConnectB(), gtiOut);
                                    ref.setTempColor(null);
                                }//end if 
                                else {
                                    ref.graph.connector(ref.getConnectA(), ref.getConnectB(), new GraphTupleInfo());
                                }//end else
                            }//end else
                        }//end if
                    }//end if
                }//end if
                ref.setActionString("");
                ref.getCanvas().repaint();
                ref.setConnect(false);
                ref.setConnectA(null);
                ref.setConnectB(null);
            }//end if
            else {
                boolean inside = false;
                GraphNode temp = null;
                for (GraphNode gn : ref.graph.getGraphNodes()) {
                    if (gn.contains(e.getPoint())) {
                        inside = true;
                        temp = gn;
                        break;
                    }//end else
                }//end for
                if (inside) {
                    if (temp != null) {
                        ref.setActionString("Connect node");
                        ref.setConnect(true);
                        ref.setConnectA(temp);
                    }//end if
                }//end if
            }//end else
        }//end if
        else if (SwingUtilities.isRightMouseButton(e)) {
            ref.getRightClickMenu().show(ref.getCanvas(), e.getX(), e.getY());
        }//end else if
        ref.repaint();
    }//end MouseClicked

}//end CanvasMouseListener
