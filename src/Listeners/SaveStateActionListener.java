/*TODO:
 Break up saveState()
 */
package Listeners;

import SwingElements.Base;
import SwingElements.Canvas;
import graphvisualizer.Graph;
import graphvisualizer.GraphNode;
import graphvisualizer.GraphTuple;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JFileChooser;

/**
 * {@link ActionListener} child, used to save the grid state when the
 * corresponding menu button is pressed in the {@link Base} class.
 */
public class SaveStateActionListener implements ActionListener {

    private Base ref;                                                           //Base object, used for reference

    /**
     * Constructor.
     *
     * @param in {@link Base} object, used for reference
     */
    public SaveStateActionListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    /**
     * Method performed on trigger. Pauses step auto-running, records the grid
     * state, then returns the grid to its previous auto-run setting.
     */
    public void actionPerformed(ActionEvent evt) {
        boolean tempRun = ref.isRunning();
        ref.pause();
        saveState();
        if (tempRun) {
            ref.run();
        }//end if
    }//actionPerformed

    /**
     * Records all global grid information (rows, columns, step count, point
     * size/spacing), then records information for all graph nodes and lines.
     */
    private void saveState() {

        GraphNode[][] refMatrix = ref.getGraph().getMatrix();
        double itemsTotal = refMatrix.length * refMatrix[0].length;
        double itemsDone = 0;
        Canvas canvas = ref.getCanvas();
        Graph graph = ref.getGraph();

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
        String globals = "Spacing:" + canvas.getMinSpacing() + "\n";
        globals += "Point size:" + canvas.getMinPointSize() + "\n";
        globals += "Step count:" + graph.getStepCount() + "\n";
        globals += "Step time:" + ref.getStepTime() + "\n";
        globals += "Zoom level:" + canvas.getZoomLevel() + "\n";
        globals += "Cycle base:" + graph.getCycleBase() + "\n";
        globals += "Cycle count:" + graph.getCycleCount() + "\n";
        globals += "Trim:" + Graph.TRIM + "\n";
        globals += "Mutate:" + Graph.MUTATE + "\n";
        globals += "Mutate Color:" + Graph.MUTATE_COLOR + "\n";
        globals += "Mutate Health:" + Graph.MUTATE_HEALTH + "\n";
        globals += "Growth Type:" + graph.getGrowthType() + "\n";

        //Seems magic, but is actually the number of global variables. Not likely to change
        itemsTotal += 12;
        itemsDone += 12;

        //Walk through matrix, record nodes, and connections w/ life totals
        String connectionOutString = "";
        String nodeListOutString = refMatrix.length + " " + refMatrix[0].length + "\n";
        for (int i = 0; i < refMatrix.length; i++) {
            for (int j = 0; j < refMatrix[i].length; j++) {
                String tempString = "";
                for (int k = 0; k < refMatrix[i][j].getNumberOfConnections(); k++) {
                    GraphTuple gt = refMatrix[i][j].getConnection(k);
                    GraphNode gn = gt.getToLocation();
                    GraphNode gn2 = gt.getFromLocation();
                    tempString += gn.getILoc() + " "
                            + gn.getJLoc() + " "
                            + gn2.getILoc() + " "
                            + gn2.getJLoc() + " "
                            + gt.getRed() + " "
                            + gt.getGreen() + " "
                            + gt.getBlue() + " "
                            + gt.getHealth() + " "
                            + gt.getStartHealth() + " "
                            + gt.getMutatePercentage() + " "
                            + gt.getReproductionClock() + " "
                            + gt.getStartReproductionClock() + " "
                            + gt.isEdge(graph) + " "
                            + gt.isCurve() + " "
                            + gt.getCurveDirection() + " "
                            + gt.getCurveSeverity() + "\n";
                }//end for
                connectionOutString += tempString;
                itemsDone++;
            }//end for
        }//end for
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
            writer.print(nodeListOutString);
            writer.print(globals);
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
