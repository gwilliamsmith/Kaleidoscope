package Listeners;

import graphvisualizer.Base;
import graphvisualizer.GraphNode;
import graphvisualizer.GraphTupleInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class CanvasMouseListener extends MouseAdapter {

    private Base ref;

    public CanvasMouseListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            System.out.println("1");
            if (ref.getConnect()) {
                System.out.println("2");
                for (GraphNode gn : ref.graph.getGraphNodes()) {
                    if (gn.contains(e.getPoint())) {
                        System.out.println("3");
                        if (gn != ref.getConnectA() && !gn.isConnected(ref.getConnectA())) {
                            System.out.println("4");
                            ref.setConnectB(gn);
                        }//end if
                        ref.setActionString("");
                        ref.setConnect(false);
                        break;
                    }//end if
                }//end for
                if (ref.getConnectB() != null && ref.getConnectA() != null) {
                    System.out.println("5");
                    if (ref.getGtiStorage() != null) {
                        System.out.println("6");
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
                ref.setActionString("");
                ref.getCanvas().repaint();
                ref.setConnect(false);
                ref.setConnectA(null);
                ref.setConnectB(null);
            }//end if
            else {
                for (GraphNode gn : ref.graph.getGraphNodes()) {
                    if (gn.contains(e.getPoint())) {
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
}//end CanvasMouseListener
