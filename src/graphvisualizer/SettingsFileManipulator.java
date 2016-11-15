package graphvisualizer;

import SwingElements.Base;
import SwingElements.Canvas;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles all manipulation of the global settings file
 */
public class SettingsFileManipulator extends GenericFileManipulator {

    private File settingsFile;
    private Base ref;

    /**
     * @param fileName the name of the settings file
     * @param in the {@link Base} object the global settings are for
     */
    public SettingsFileManipulator(String fileName, Base in) {
        settingsFile = new File(fileName);
        ref = in;
    }//end constructor

    /**
     * Reads the global settings from the specified file
     */
    public void readSettingsIn() {
        ArrayList<String> lines = super.readFile(settingsFile);
        HashMap<String, String> values = new HashMap<>();
        Canvas canvas = ref.getCanvas();
        Graph graph = ref.getGraph();
        for (String s : lines) {
            String[] tokens = s.split("[:]");
            values.put(tokens[0], tokens[1]);
        }//end for
        for (Map.Entry<String, String> entry : values.entrySet()) {
            switch (entry.getKey()) {
                case "Min Spacing":
                    canvas.setMinSpacing(Integer.parseInt(entry.getValue()));
                    break;
                case "Min Point Size":
                    canvas.setMinPointSize(Integer.parseInt(entry.getValue()));
                    break;
                case "Zoom Level":
                    canvas.setZoomLevel(Integer.parseInt(entry.getValue()));
                    break;
                case "Trim":
                    graph.setTrim(Boolean.parseBoolean(entry.getValue()));
                    break;
                case "Consume":
                    graph.setConsume(Boolean.parseBoolean(entry.getValue()));
                    break;
                case "Growth Type":
                    graph.setGrowthType(Integer.parseInt(entry.getValue()));
                    break;
                case "Mutate Color":
                    graph.setMutateColor(Boolean.parseBoolean(entry.getValue()));
                    break;
                case "Mutate Health":
                    graph.setMutateHealth(Boolean.parseBoolean(entry.getValue()));
                    break;
                case "Seed1":
                    graph.setSeed1(Boolean.parseBoolean(entry.getValue()));
                    break;
                case "Seed2":
                    graph.setSeed2(Boolean.parseBoolean(entry.getValue()));
                    break;
                case "Seed4":
                    graph.setSeed4(Boolean.parseBoolean(entry.getValue()));
                    break;
                case "Seed8":
                    graph.setSeed8(Boolean.parseBoolean(entry.getValue()));
                    break;
                /*
                 case "File Location":
                 ref.setBookDirectory(new File(entry.getValue()));
                 break;
                 */
            }//end switch
        }//end for
        ref.getCanvas().setResized(true);
    }//end readSettingsIn
    
    /**
     * Writes global settings to the settings file
     */
    public void writeSettings() {
        Canvas canvas = ref.getCanvas();
        Graph graph = ref.getGraph();
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(settingsFile.getCanonicalPath(), false)))) {
            out.println("Min Spacing:" + Integer.toString(canvas.getMinSpacing()));
            out.println("Min Point Size:" + Integer.toString(canvas.getMinPointSize()));
            out.println("Zoom Level:" + Integer.toString(canvas.getZoomLevel()));
            out.println("Trim:" + Boolean.toString(Graph.TRIM));
            out.println("Consume:" + Boolean.toString(Graph.CONSUME));
            out.println("Growth Type:" + Integer.toString(graph.getGrowthType()));
            out.println("Mutate Color:" + Boolean.toString(Graph.MUTATE_COLOR));
            out.println("Mutate Health:" + Boolean.toString(Graph.MUTATE_HEALTH));
            out.println("Seed1:" + Boolean.toString(graph.getSeed1()));
            out.println("Seed2:" + Boolean.toString(graph.getSeed2()));
            out.println("Seed4:" + Boolean.toString(graph.getSeed4()));
            out.println("Seed8:" + Boolean.toString(graph.getSeed8()));
            //out.println("File Location:" + ref.getBookDirectory().getAbsolutePath());
            out.flush();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }//end try catch
    }//end writeSettings

}//end SettingsFileManipulator class
