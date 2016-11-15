package graphvisualizer;

import java.io.*;
import java.util.ArrayList;

/**
 * Generic text-based file manipulator.
 */
public class GenericFileManipulator {

    /**
     * Gathers all lines of text from a file, given the file location as a
     * string.
     *
     * @param fileName A string containing the path to a file to be read
     * @return A {@link ArrayList} containing all lines of text in the given
     * file, or null if {@link IOException} occurs
     */
    public ArrayList<String> readFile(String fileName) {
        return readFile(new File(fileName));
    }//end readFile

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
     * @param in The file in question
     * @return An {@link ArrayList} containing all lines of text in the given file
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
}//end GenericFileManipulator class
