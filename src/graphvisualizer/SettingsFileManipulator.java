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

    private final File settingsFile;
    private final Base ref;

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
        if (settingsFile.exists()) {
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
                    case "Mode Type":
                        graph.setMode(Integer.parseInt(entry.getValue()));
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
        }//end if
        else{
            writeSettings();
        }//end else
    }//end readSettingsIn

    /**
     * Writes global settings to the settings file
     */
    public void writeSettings() {
        Canvas canvas = ref.getCanvas();
        Graph graph = ref.getGraph();
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(settingsFile.getCanonicalPath(), false)))) {
            out.println("Min Spacing:" + canvas.getMinSpacing());
            out.println("Min Point Size:" + canvas.getMinPointSize());
            out.println("Zoom Level:" + canvas.getZoomLevel());
            out.println("Trim:" + Graph.TRIM);
            out.println("Consume:" + Graph.CONSUME);
            out.println("Mode Type:" + graph.getMode());
            out.println("Mutate Color:" + Graph.MUTATE_COLOR);
            out.println("Mutate Health:" + Graph.MUTATE_HEALTH);
            out.println("Seed1:" + graph.getSeed1());
            out.println("Seed2:" + graph.getSeed2());
            out.println("Seed4:" + graph.getSeed4());
            out.println("Seed8:" + graph.getSeed8());
            //out.println("File Location:" + ref.getBookDirectory().getAbsolutePath());
            out.flush();
        } catch (IOException ignored) {
        }//end try catch
    }//end writeSettings

}//end SettingsFileManipulator class
