package graphvisualizer;

import SwingElements.Base;
import java.awt.Color;
import java.util.Random;

public class GraphTupleInfo {

    public int startHealth;
    public Color color;
    public int mutationPercentage;
    public int reproductionClock = 1;
    public boolean edge;
    public int family;

    public GraphTupleInfo() {
        startHealth = 50;
        color = Color.BLACK;
        mutationPercentage = 0;
        edge = false;
    }//end constructor

    public GraphTupleInfo(Color colorIn) {
        startHealth = 50;
        color = colorIn;
        mutationPercentage = 0;
    }//end constructor

    public GraphTupleInfo(int startHealthIn, Color colorIn, int mutationPercentageIn) {
        startHealth = startHealthIn;
        color = colorIn;
        mutationPercentage = mutationPercentageIn;
    }//end constructor

    public GraphTupleInfo(int startHealthIn, Color colorIn, int mutationPercentageIn, int reproductionClockIn) {
        startHealth = startHealthIn;
        color = colorIn;
        mutationPercentage = mutationPercentageIn;
        reproductionClock = reproductionClockIn;
    }//end constructor

    public GraphTupleInfo(int startHealthIn, Color colorIn, int mutationPercentageIn, int reproductionClockIn, boolean edgeIn) {
        startHealth = startHealthIn;
        color = colorIn;
        mutationPercentage = mutationPercentageIn;
        reproductionClock = reproductionClockIn;
        edge = edgeIn;
    }//end constructor

    public GraphTupleInfo(int startHealthIn, Color colorIn, int mutationPercentageIn, int reproductionClockIn, boolean edgeIn, int familyID) {
        startHealth = startHealthIn;
        color = colorIn;
        mutationPercentage = mutationPercentageIn;
        reproductionClock = reproductionClockIn;
        edge = edgeIn;
        family = familyID;
    }//end constructor
    
    //TODO: Add in ability to tune this
    public static GraphTupleInfo generateRandomGTI(Base ref){
        Random rand = new Random();
        GraphTupleInfo out = new GraphTupleInfo();
        out.startHealth = rand.nextInt();
        if(out.startHealth <0){
            out.startHealth *= -1;
        }
        out.color = new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat());
        out.mutationPercentage = rand.nextInt(10001);
        out.reproductionClock = rand.nextInt(out.startHealth)+1;
        out.family = ref.getGraph().newFamilyID();
        out.edge = false;
        return out;
    }//end generateRandomGTI

}//end GraphTupleInfo class
