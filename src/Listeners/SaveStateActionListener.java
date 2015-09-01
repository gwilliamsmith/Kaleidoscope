/*TODO:
    Break up saveState()
*/

package Listeners;

import SwingElements.Base;
import graphvisualizer.GraphNode;
import graphvisualizer.GraphTuple;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JFileChooser;

public class SaveStateActionListener implements ActionListener {

    private Base ref;

    public SaveStateActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent evt) {
        boolean tempRun = ref.getRun();
        ref.setRun(false);
        saveState();
        ref.setRun(tempRun);
    }//actionPerformed

    private void saveState() {

        GraphNode[][] refMatrix = ref.getGraph().getMatrix();
        double itemsTotal = refMatrix.length * refMatrix[0].length;
        double itemsDone = 0;
        
        //File saving code
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showSaveDialog(ref);
        String fileName = "";
        if (returnVal == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().getName() != null) {
            fileName = fileChooser.getSelectedFile().getAbsolutePath() + ".lsv";
        }//end if
        else if (returnVal == JFileChooser.CANCEL_OPTION) {
            return;
        }//end saveState
        
        //Record global variable states
        String globals = "Spacing: " + ref.getSpacing() + "\n";
        globals += "Point size: " + ref.getPointSize() + "\n";
        globals += "Step count: " + ref.getGraph().getStepCount() + "\n";
        globals += "Cycle base: " + ref.getGraph().getCycleBase() + "\n";
        globals += "Cycle count: " + ref.getGraph().getCycleCount() + "\n";
        globals += "Trim: " + ref.getGraph().getTrim() + "\n";
        globals += "Mutate: " + ref.getGraph().getMutate() + "\n";
        globals += "Mutate Color: " + ref.getGraph().getMutateColor() + "\n";
        globals += "Mutate Health: " + ref.getGraph().getMutateHealth() + "\n";
        globals += "Growth Type: " + ref.getGraph().getGrowthType()+ "\n";
        
        //Seems magic, but is actually the number of global variables. Not likely to change
        itemsTotal += 10;
        itemsDone += 10;
        
        System.out.println("Percent done: " + (itemsDone / itemsTotal) * 100 + "%");

        //Walk through matrix, record nodes, and connections w/ life totals
        String connectionOutString = "";
        String nodeListOutString = refMatrix.length + " " + refMatrix[0].length + "\n";
        for (int i = 0; i < refMatrix.length; i++) {
            for (int j = 0; j < refMatrix[i].length; j++) {
                String tempString = "";
                tempString += "Node: " + i + " " + j + "\n";
                for (int k = 0; k < refMatrix[i][j].getNumberOfConnections(); k++) {
                    GraphTuple gt = refMatrix[i][j].getConnection(k);
                    GraphNode gn = gt.getToLocation();
                    tempString += "Connection: " + gn.getILoc() + " " + gn.getJLoc() + " " + gt.getColor().getRed() + " " + gt.getColor().getGreen() + " " + gt.getColor().getBlue() + "\n";
                }//end for
                connectionOutString += tempString;
                itemsDone++;
                System.out.println("Percent done: " + (itemsDone / itemsTotal) * 100 + "%");
            }//end for
        }//end for
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
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
