package graphvisualizer;

import SwingElements.Base;
import SwingElements.Canvas;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingsFileManipulator extends GenericFileManipulator {

    private File settingsFile;
    private Base ref;

    public SettingsFileManipulator(String fileName, Base in) {
        settingsFile = new File(fileName);
        ref = in;
    }//end constructor

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
            }//end switch
        }//end for
        ref.getCanvas().setResized(true);
    }//end readSettingsIn

    public void writeSettings() {
        Canvas canvas = ref.getCanvas();
        Graph graph = ref.getGraph();
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(settingsFile.getCanonicalPath(), false)))) {
            out.println("Min Spacing:" + Integer.toString(canvas.getMinSpacing()));
            out.println("Min Point Size:" + Integer.toString(canvas.getMinPointSize()));
            out.println("Zoom Level:" + Integer.toString(canvas.getZoomLevel()));
            out.println("Trim:" + Boolean.toString(graph.getTrim()));
            out.println("Consume:" + Boolean.toString(graph.getConnect()));
            out.println("Growth Type:" + Integer.toString(graph.getGrowthType()));
            out.println("Mutate Color:" + Boolean.toString(graph.getMutateColor()));
            out.println("Mutate Health:" + Boolean.toString(graph.getMutateHealth()));
            out.println("Seed1:" + Boolean.toString(graph.getSeed1()));
            out.println("Seed2:" + Boolean.toString(graph.getSeed2()));
            out.println("Seed4:" + Boolean.toString(graph.getSeed4()));
            out.println("Seed8:" + Boolean.toString(graph.getSeed8()));
            out.flush();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }//end try catch
    }//end writeSettings

}//end SettingsFileManipulator class
