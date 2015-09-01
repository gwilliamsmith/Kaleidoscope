package graphvisualizer;

import SwingElements.GridSelectionFrame;
/**
 * This class starts the program, by creating(or retrieving) the single allowed instance of the GridSelectionFrame
 * @author Will Smith
 */
public class ArenaRunner {

    public static void main(String[] args) {
        GridSelectionFrame.getInstance().setVisible(true);
    }//end main
}//end ArenaRunner
