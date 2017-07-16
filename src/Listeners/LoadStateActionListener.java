package Listeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadStateActionListener implements ActionListener {

    Base ref;

    public LoadStateActionListener(Base in) {
        ref = in;
    }//end LoadStateActionListener

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("LSV Files", "lsv"));
        int returnVal = fileChooser.showOpenDialog(ref);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            loadStateFile(fileChooser.getSelectedFile());
        }//end if
    }//end actionPerformed

    private void loadStateFile(File selectedFile) {
        ArrayList<String> lines = readFile(selectedFile);
        int linesIndex = 1;
        loadGridSize(lines);
        linesIndex = loadGlobalSettings(lines, linesIndex);
        loadConnections(lines,linesIndex);
    }//end loadStateFile

    private int loadGlobalSettings(ArrayList<String> lines, int linesIndex) {
        ArrayList<String> globals = new ArrayList<>();
        for (int i = linesIndex; i <= 12; i++, linesIndex++) {
            globals.add(lines.get(i));
        }//end for
        HashMap<String, String> values = new HashMap<>();
        for (int i=0;i<globals.size();i++) {
            String s = globals.get(i);
            String[] tokens = s.split("[:]");
            values.put(tokens[0], tokens[1]);
        }//end for
        for (Map.Entry<String, String> entry : values.entrySet()) {
            switch (entry.getKey()) {
                case "Spacing":
                    ref.getCanvas().setMinSpacing(Integer.parseInt(entry.getValue()));
                    break;
                case "Point size":
                    ref.getCanvas().setMinPointSize(Integer.parseInt(entry.getValue()));
                    break;
                case "Step count":
                    ref.getGraph().setStepCount(Long.parseLong(entry.getValue()));
                    break;
                case "Step time":
                    ref.setStepTime(Integer.parseInt(entry.getValue()));
                    if(ref.getStepTime() < 1){
                        ref.setStepTimeSliderLocation(0);
                    }//end if
                    else if(ref.getStepTime() > 1000){
                        ref.setStepTimeSliderLocation(1000);
                    }//end else if
                    else{
                        ref.setStepTimeSliderLocation(ref.getStepTime());
                    }//end else
                    break;
                case "Zoom level":
                    ref.getCanvas().setZoomLevel(Integer.parseInt(entry.getValue()));
                    break;
                case "Cycle base":
                    ref.getGraph().setCycleBase(Integer.parseInt(entry.getValue()));
                    break;
                case "Cycle count":
                    ref.getGraph().setCycleCount(Long.parseLong(entry.getValue()));
                    break;
                case "Trim":
                    ref.getGraph().setTrim(Boolean.parseBoolean(entry.getValue()));
                    break;
                case "Mutate Color":
                    ref.getGraph().setMutateColor(Boolean.parseBoolean(entry.getValue()));
                    break;
                case "Mutate Health":
                    ref.getGraph().setMutateHealth(Boolean.parseBoolean(entry.getValue()));
                    break;
                case "Growth Type":
                    ref.getGraph().setMode(Integer.parseInt(entry.getValue()));
                    break;
            }//end switch
        }//end for
        ref.getCanvas().setResized(true);
        return linesIndex;
    }//end loadGlobalSettings

    private void loadGridSize(ArrayList<String> lines) {
        String gridSize = lines.get(0);
        Scanner scan = new Scanner(gridSize);
        int columns = scan.nextInt();
        int rows = scan.nextInt();
        ref.resizeGrid(columns, rows, ref.getStepTime());
    }//end loadGridSize

    private int loadConnections(ArrayList<String> lines, int linesIndex){
        System.out.println(linesIndex);
        for(int i=linesIndex;i<lines.size()-1;i++){
            Scanner scan = new Scanner(lines.get(i));
            ref.getGraph().createConnection(scan.nextInt(),
                    scan.nextInt(),
                    scan.nextInt(),
                    scan.nextInt(),
                    scan.nextInt(),
                    scan.nextInt(),
                    scan.nextInt(),
                    scan.nextInt(),
                    scan.nextInt(),
                    scan.nextInt(),
                    scan.nextInt(),
                    scan.nextInt(),
                    scan.nextBoolean(),
                    scan.nextBoolean(),
                    scan.nextInt(),
                    scan.nextDouble());
        }//end for
        return linesIndex;
    }//end loadConnections
    /**
     * Gathers all lines of text from a given file.
     *
     * @param file The file to be read
     * @return A {@link ArrayList} containing all lines of text in the given
     * file, or null if {@link IOException} occurs
     */
    public ArrayList<String> readFile(File file) {
        try (FileReader reader = new FileReader(file); BufferedReader br = new BufferedReader(reader)) {
            return gatherLines(br);
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }//end try catch
        return null;
    }//end readFile

    /**
     * Looks through the given file line by line, collecting all lines of text.
     *
     * @param in The file in question
     * @return An {@link ArrayList} containing all lines of text in the given
     * file
     * @throws IOException
     */
    private ArrayList<String> gatherLines(BufferedReader in) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = in.readLine()) != null) {
            lines.add(line);
        }//end while
        return lines;
    }//end gatherLines

}//end LoadStateActionListener class
