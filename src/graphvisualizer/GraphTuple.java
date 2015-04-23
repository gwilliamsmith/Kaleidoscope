package graphvisualizer;

import java.awt.Color;

public class GraphTuple {

    public GraphNode location;
    public double weight;
    public boolean searched;
    public boolean weighted = false;
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

    public GraphTuple(GraphNode loc, double dis) {
        location = loc;
        weight = dis;
        searched = false;
        recent = true;
    }//end constructor

    public GraphTuple(GraphNode loc, double weight, boolean weighted) {
        location = loc;
        this.weight = weight;
        this.weighted = weighted;
    }//end constructor

    public GraphTuple(GraphNode loc, double weight, boolean weighted, boolean edge) {
        location = loc;
        this.weight = weight;
        this.weighted = weighted;
        this.edge = edge;
    }//end constructor

    public GraphTuple(GraphNode loc, double weight, boolean weighted, Color color) {
        location = loc;
        this.weight = weight;
        this.weighted = false;
        this.color = color;
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
    }//end constructor

    public GraphTuple(GraphNode loc, double weight, boolean weighted, Color color, int baseHealth) {
        location = loc;
        this.weight = weight;
        this.weighted = false;
        this.color = color;
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
        startHealth = baseHealth;
        health = startHealth;
    }//end constructor

    public GraphTuple(GraphNode loc, double weight, boolean weighted, Color color, int baseHealth, boolean edge) {
        location = loc;
        this.weight = weight;
        this.weighted = false;
        this.color = color;
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
        startHealth = baseHealth;
        health = startHealth;
        this.edge = edge;
    }//end constructor

    public GraphTuple(GraphNode loc, double weight, boolean weighted, Color color, int baseHealth, int mutationPercentage) {
        location = loc;
        this.weight = weight;
        this.weighted = false;
        this.color = color;
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
        startHealth = baseHealth;
        health = startHealth;
        mutatePercentage = mutationPercentage;
    }//end constructor

    public GraphTuple(GraphNode loc, double weight, boolean weighted, GraphTupleInfo gti) {
        location = loc;
        this.weight = weight;
        this.weighted = false;
        this.color = gti.color;
        r = gti.color.getRed();
        g = gti.color.getGreen();
        b = gti.color.getBlue();
        startHealth = gti.startHealth;
        health = startHealth;
        mutatePercentage = gti.mutationPercentage;
    }//end constructor

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
