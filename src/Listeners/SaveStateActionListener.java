package Listeners;

import graphvisualizer.Base;
import graphvisualizer.GraphNode;
import graphvisualizer.GraphTuple;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class SaveStateActionListener implements ActionListener {

    private Base ref;

    public SaveStateActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent evt) {
            ref.setRun(false);
            saveState();
    }//actionPerformed

    private void saveState() {
        GraphNode[][] refMatrix = ref.graph.getMatrix();
        String connectionOutString = "";
        String nodeListOutString = refMatrix.length + " " + refMatrix[0].length + "\n";
        for (int i = 0; i < refMatrix.length; i++) {
            for (int j = 0; j < refMatrix[i].length; j++) {
                String tempString = "";
                tempString += "Node: " + i + " " + j + "\n";
                for (int k=0;k<refMatrix[i][j].getNumberOfConnections();k++) {
                    GraphTuple gt = refMatrix[i][j].getConnection(k);
                    GraphNode gn = gt.getToLocation();
                    tempString += "Connection: " + gn.getILoc() + " " + gn.getJLoc() + " " + gn.getColor().getRed() + " " + gn.getColor().getGreen() + " " + gn.getColor().getBlue() + "\n";
                }//end for
                connectionOutString += tempString;
            }//end for
        }//end for
        System.out.println(nodeListOutString);
        System.out.println(connectionOutString);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("save2.lsv", "UTF-8");
            writer.print(nodeListOutString);
            writer.print(connectionOutString);
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
        } finally {
            if (writer != null) {
                writer.close();
                System.out.println("Done Saving");
            }//end if
        }//end try-catch-finally
    }//end saveState
}//end TimerActionListner
