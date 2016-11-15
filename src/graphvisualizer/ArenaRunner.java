package graphvisualizer;

import SwingElements.GridSelectionFrame;

/**
 * This class starts the program, by creating(or retrieving) the single allowed instance of the GridSelectionFrame
 */
public class ArenaRunner {

    /**
     * Main method for the project
     * @param args 
     */
    public static void main(String[] args) {
        new GridSelectionFrame(null).setVisible(true);
    }//end main
}//end ArenaRunner