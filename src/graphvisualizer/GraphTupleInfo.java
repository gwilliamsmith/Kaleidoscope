package graphvisualizer;

import SwingElements.Base;
import java.awt.Color;
import java.util.Random;

/**
 * Template information for creating new {@link GraphTuple} line objects
 */
public class GraphTupleInfo {

    public int startHealth;                                                     //The number of steps it will take this line to die
    public Color color;                                                         //The color of the line
    public int mutationPercentage;                                              //The chance(out of GraphTuple.MUTATION_DIVSIOR, that this line will mutate on reproduction
    public int reproductionClock = 1;                                           //The number of turns it will take this line to reproduce
    public boolean edge;                                                        //Determines if this line is an edge
    public int family;                                                          //The family ID for this line
    int depthColorIndex;                                                 //The generation of this line (circular int that resets to 0 when (++depthColorIndex / (255 / GraphTuple.DEPTH_COLOR_INTERVAL) < 6) is true

    public GraphTupleInfo() {
        startHealth = 50;
        color = Color.BLACK;
        mutationPercentage = 0;
        edge = false;
    }//end constructor

    /**
     * @param colorIn The color of the {@link GraphTuple} line to be created
     */
    public GraphTupleInfo(Color colorIn) {
        startHealth = 50;
        color = colorIn;
        mutationPercentage = 0;
    }//end constructor
    
    /**
     * @param startHealthIn The starting health in turns of the
     * {@link GraphTuple} line to be created
     * @param colorIn The starting color of the {@link GraphTuple} line to be
     * created
     * @param mutationPercentageIn The mutation chance (out of 1000) of the
     * {@link GraphTuple} line to be created
     */
    public GraphTupleInfo(int startHealthIn, Color colorIn, int mutationPercentageIn) {
        startHealth = startHealthIn;
        color = colorIn;
        mutationPercentage = mutationPercentageIn;
    }//end constructor

    /**
     * @param startHealthIn The starting health in turns{@link GraphTuple} of the {@link GraphTuple} line to be created
     * @param colorIn The starting color of the {@link GraphTuple} line to be created
     * @param mutationPercentageIn The mutation chance (out of 1000) of the {@link GraphTuple} line to be created
     * @param reproductionClockIn The number of turns it takes the {@link GraphTuple} line to be created to create children
     */
    public GraphTupleInfo(int startHealthIn, Color colorIn, int mutationPercentageIn, int reproductionClockIn) {
        startHealth = startHealthIn;
        color = colorIn;
        mutationPercentage = mutationPercentageIn;
        reproductionClock = reproductionClockIn;
    }//end constructor

    /**
     * @param startHealthIn The starting health in turns{@link GraphTuple} of the {@link GraphTuple} line to be created
     * @param colorIn The starting color of the {@link GraphTuple} line to be created
     * @param mutationPercentageIn The mutation chance (out of 1000) of the {@link GraphTuple} line to be created
     * @param reproductionClockIn The number of turns it takes the {@link GraphTuple} line to be created to create children
     * @param edgeIn If the {@link GraphTuple} line to be created is an edge line
     */
    public GraphTupleInfo(int startHealthIn, Color colorIn, int mutationPercentageIn, int reproductionClockIn, boolean edgeIn) {
        startHealth = startHealthIn;
        color = colorIn;
        mutationPercentage = mutationPercentageIn;
        reproductionClock = reproductionClockIn;
        edge = edgeIn;
    }//end constructor
    
    /**
     * @param startHealthIn The starting health in turns{@link GraphTuple} of the {@link GraphTuple} line to be created
     * @param colorIn The starting color of the {@link GraphTuple} line to be created
     * @param mutationPercentageIn The mutation chance (out of 1000) of the {@link GraphTuple} line to be created
     * @param reproductionClockIn The number of turns it takes the {@link GraphTuple} line to be created to create children
     * @param edgeIn If the {@link GraphTuple} line to be created is an edge line
     * @param familyID the familyID of the {@link GraphTuple} line to be created
     */
    public GraphTupleInfo(int startHealthIn, Color colorIn, int mutationPercentageIn, int reproductionClockIn, boolean edgeIn, int familyID) {
        startHealth = startHealthIn;
        color = colorIn;
        mutationPercentage = mutationPercentageIn;
        reproductionClock = reproductionClockIn;
        edge = edgeIn;
        family = familyID;
    }//end constructor
    
    /**
     * @param startHealthIn The starting health in turns{@link GraphTuple} of the {@link GraphTuple} line to be created
     * @param colorIn The starting color of the {@link GraphTuple} line to be created
     * @param mutationPercentageIn The mutation chance (out of 1000) of the {@link GraphTuple} line to be created
     * @param reproductionClockIn The number of turns it takes the {@link GraphTuple} line to be created to create children
     * @param edgeIn If the {@link GraphTuple} line to be created is an edge line
     * @param familyID the familyID of the {@link GraphTuple} line to be created
     */
    public GraphTupleInfo(int startHealthIn, Color colorIn, int mutationPercentageIn, int reproductionClockIn, boolean edgeIn, int familyID, int depthColorIndexIn) {
        startHealth = startHealthIn;
        color = colorIn;
        mutationPercentage = mutationPercentageIn;
        reproductionClock = reproductionClockIn;
        edge = edgeIn;
        family = familyID;
        depthColorIndex = depthColorIndexIn;
    }//end constructor

    /**
     * Generates a {@link GraphTupleInfo} containing random line genes
     * @param ref The {@link Base} for which the random {@link GraphTupleInfo} will be created
     * @return The generated {@link GraphTupleInfo}
     */
    public static GraphTupleInfo generateRandomGTI(Base ref) {
        Random rand = new Random();
        GraphTupleInfo out = new GraphTupleInfo();
        out.startHealth = rand.nextInt();
        if (out.startHealth < 0) {                                              //Makes sure startHealth is > 0
            out.startHealth *= -1;
        }
        out.color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        out.mutationPercentage = rand.nextInt(GraphTuple.MUTATION_DIVISOR);                           
        out.reproductionClock = rand.nextInt(out.startHealth) + 1;
        out.family = ref.getGraph().newFamilyID();
        out.edge = false;
        return out;
    }//end generateRandomGTI

}//end GraphTupleInfo class
