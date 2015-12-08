package graphvisualizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericFileManipulator {

    public ArrayList<String> readFile(String fileName) {
        ArrayList<String> lines = new ArrayList<>();
        File file = new File(fileName);
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }//end while
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }//end try catch
        return lines;
    }//end readFile

    public ArrayList<String> readFile(File file) {
        ArrayList<String> lines = new ArrayList<>();
        try (FileReader reader = new FileReader(file); BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }//end while
        } catch (FileNotFoundException ex) {
        } catch (IOException ex){ 
        }//end try catch
        return lines;
    }//end readFile
}//end GenericFileManipulator class
