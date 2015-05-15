package graphvisualizer;

import java.awt.Color;

public class GraphTuple {

    public GraphNode location;
    public boolean recent;
    public int startHealth = 50;
    public int health = startHealth;
    public int mutatePercentage = 50;    //Out of 1000
    public int r = 0;
    public int g = 0;
    public int b = 0;
    private Color color = Color.BLACK;
    private boolean edge = false;
    public int startReproductionClock = 1;
    public int reproductionClock = startReproductionClock;

    public GraphTuple(GraphNode loc,GraphTupleInfo gti) {
        location = loc;
        this.color = gti.color;
        r = gti.color.getRed();
        g = gti.color.getGreen();
        b = gti.color.getBlue();
        startHealth = gti.startHealth;
        health = startHealth;
        mutatePercentage = gti.mutationPercentage;
        startReproductionClock = gti.reproductionClock;
        reproductionClock = startReproductionClock;
        edge = gti.edge;
    }//end constructor
    
    public GraphTupleInfo generateGTI(){
        GraphTupleInfo out = new GraphTupleInfo(this.startHealth,this.color,this.mutatePercentage,this.startReproductionClock);
        return out;
    }//end generateGTI

    public void setColor(Color in) {
        color = in;
    }//end setColor

    public void setColor(int rin, int bin, int gin) {
        color = new Color(rin, bin, gin);
    }//end setColor

    public Color getColor() {
        return color;
    }//end getColor

    public void setEdge() {
        edge = true;
    }//end setEdge

    public boolean isEdge() {
        return edge;
    }//end isEdge
}//end GraphTuple
