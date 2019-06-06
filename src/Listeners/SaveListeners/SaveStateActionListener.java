package Listeners.SaveListeners;

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
        Canvas canvas = ref.getCanvas();
        Graph graph = ref.getGraph();

        //File saving code
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save State");
        int returnVal = fileChooser.showSaveDialog(ref);
        String fileName = "";
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileName = fileChooser.getSelectedFile().getAbsolutePath() + ".lsv";
        }//end if
        else if (returnVal == JFileChooser.CANCEL_OPTION) {
            return;
        }//end saveState

        //Walk through matrix, record nodes, and connections w/ life totals
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
            writer.print(refMatrix.length + " " + refMatrix[0].length + "\n");
            writer.print(writeGlobalString(canvas,graph));
            writer.print(writeConnectonString(graph));
        } catch (FileNotFoundException | UnsupportedEncodingException ignored) {
        } finally {
            if (writer != null) {
                writer.close();
                System.out.println("Done Saving");
            }//end if
        }//end try-catch-finally
    }//end saveState

    private String writeGlobalString(Canvas canvas, Graph graph){
        return "Spacing:" + canvas.getMinSpacing() +
                "\n" +
                "Point size:" +
                canvas.getMinPointSize() +
                "\n" +
                "Step count:" +
                graph.getStepCount() +
                "\n" +
                "Step time:" +
                ref.getStepTime() +
                "\n" +
                "Zoom level:" +
                canvas.getZoomLevel() +
                "\n" +
                "Cycle base:" +
                graph.getCycleBase() +
                "\n" +
                "Cycle count:" +
                graph.getCycleCount() +
                "\n" +
                "Trim:" +
                Graph.TRIM +
                "\n" +
                "Mutate Color:" +
                Graph.MUTATE_COLOR +
                "\n" +
                "Mutate Health:" +
                Graph.MUTATE_HEALTH +
                "\n" +
                "Growth Type:" +
                graph.getMode() +
                "\n";
    }//end writeGlobalString

    private String writeConnectonString(Graph graph){
        GraphNode[][] refMatrix = ref.getGraph().getMatrix();
        StringBuilder connectionOutString = new StringBuilder();
        for (int i = 0; i < refMatrix.length; i++) {
            for (int j = 0; j < refMatrix[i].length; j++) {
                StringBuilder tempString = new StringBuilder();
                for (int k = 0; k < refMatrix[i][j].getNumberOfConnections(); k++) {
                    GraphTuple gt = refMatrix[i][j].getConnection(k);
                    GraphNode gn = gt.getToLocation();
                    GraphNode gn2 = gt.getFromLocation();
                    tempString.append(gn.getILoc()).append(" ").
                            append(gn.getJLoc()).append(" ").
                            append(gn2.getILoc()).append(" ").
                            append(gn2.getJLoc()).append(" ").
                            append(gt.getRed()).append(" ").
                            append(gt.getGreen()).append(" ").
                            append(gt.getBlue()).append(" ").
                            append(gt.getHealth()).append(" ").
                            append(gt.getStartHealth()).append(" ").
                            append(gt.getMutatePercentage()).append(" ").
                            append(gt.getReproductionClock()).append(" ").
                            append(gt.getStartReproductionClock()).append(" ").
                            append(gt.isEdge(graph)).append(" ").
                            append(gt.isCurve()).append(" ").
                            append(gt.getCurveDirection()).append(" ").
                            append(gt.getCurveSeverity()).append("\n");
                }//end for
                connectionOutString.append(tempString);
            }//end for
        }//end for
        return connectionOutString.toString();
    }//end writeConnectionString
}//end TimerActionListner
