
package graphvisualizer;

import java.awt.Color;

public class GraphTupleInfo {
    public int startHealth;
    public Color color;
    public int mutationPercentage;
    public int reproductionClock = 1;
    public boolean edge;
    
    public GraphTupleInfo(){
        startHealth = 50;
        color = Color.BLACK;
        mutationPercentage = 0;
    }//end constructor
    
    public GraphTupleInfo(Color colorIn){
        startHealth = 50;
        color = colorIn;
        mutationPercentage = 0;
    }//end constructor
    
    public GraphTupleInfo(int startHealthIn, Color colorIn, int mutationPercentageIn){
        startHealth = startHealthIn;
        color = colorIn;
        mutationPercentage = mutationPercentageIn;
    }//end constructor
    
    public GraphTupleInfo(int startHealthIn, Color colorIn, int mutationPercentageIn, int reproductionClockIn){
        startHealth = startHealthIn;
        color = colorIn;
        mutationPercentage = mutationPercentageIn;
        reproductionClock = reproductionClockIn;
    }//end constructor
    
    public GraphTupleInfo(int startHealthIn, Color colorIn, int mutationPercentageIn, int reproductionClockIn, boolean edgeIn){
        startHealth = startHealthIn;
        color = colorIn;
        mutationPercentage = mutationPercentageIn;
        reproductionClock = reproductionClockIn;
        edge = edgeIn;
    }//end constructor

}//end GraphTupleInfo class