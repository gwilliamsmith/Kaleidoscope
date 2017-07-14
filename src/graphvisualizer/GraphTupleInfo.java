package graphvisualizer;

import SwingElements.Base;
import java.awt.Color;
import java.util.Random;

/**
 * Template information for creating new {@link GraphTuple} line objects
 */
public class GraphTupleInfo {

    public int startHealth;
    public Color color;
    public int mutationPercentage;
    public int reproductionClock = 1;
    public boolean edge;
    public int family;
    public int depthColorIndex;

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

    //TODO: Add in ability to tune this
    /**
     * Generates a {@link GraphTupleInfo} containing random line genes
     * @param ref The {@link Base} for which the random {@link GraphTupleInfo} will be created
     * @return The generated {@link GraphTupleInfo}
     */
    public static GraphTupleInfo generateRandomGTI(Base ref) {
        Random rand = new Random();
        GraphTupleInfo out = new GraphTupleInfo();
        out.startHealth = rand.nextInt();
        if (out.startHealth < 0) {
            out.startHealth *= -1;
        }
        out.color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        out.mutationPercentage = rand.nextInt(10001);
        out.reproductionClock = rand.nextInt(out.startHealth) + 1;
        out.family = ref.getGraph().newFamilyID();
        out.edge = false;
        return out;
    }//end generateRandomGTI

}//end GraphTupleInfo class
