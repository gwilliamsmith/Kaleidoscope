package graphvisualizer;

import java.awt.Color;

public class GraphTuple {

    private final GraphNode toLocation;
    private final GraphNode fromLocation;
    private int startHealth = 50;
    private int health = startHealth;
    private int decayRate = 1;
    private int mutatePercentage = 50;    //Out of 1000
    private Color color = Color.BLACK;
    private int startReproductionClock = 1;
    private int reproductionClock = startReproductionClock;

    public GraphTuple(GraphNode to, GraphNode from, GraphTupleInfo gti) {
        toLocation = to;
        fromLocation = from;
        this.color = gti.color;
        startHealth = gti.startHealth;
        health = startHealth;
        mutatePercentage = gti.mutationPercentage;
        startReproductionClock = gti.reproductionClock;
        reproductionClock = startReproductionClock;
    }//end constructor

    public GraphTupleInfo generateGTI() {
        GraphTupleInfo out = new GraphTupleInfo(this.startHealth, this.color, this.mutatePercentage, this.startReproductionClock);
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

    public int getRed() {
        return color.getRed();
    }//end getRed

    public int getGreen() {
        return color.getGreen();
    }//end getRed

    public int getBlue() {
        return color.getBlue();
    }//end getRed

    public boolean isEdge() {
        return (toLocation.isEdgeNode() && fromLocation.isEdgeNode());
    }//end isEdge
    
    public boolean isAlive(){
        return (health > 0);
    }//return isAlive

    public GraphNode getToLocation() {
        return toLocation;
    }//end getLocation
    
    public GraphNode getFromLocation(){
        return fromLocation;
    }//end fromLocation

    public int getStartHealth() {
        return startHealth;
    }//edn getStartHealth

    public void setStartHealth(int in) {
        startHealth = in;
    }//end setStartHealth
    
    public int getHealth(){
        return health;
    }//return getHealth
    
    public void decay(){
        health = health - decayRate;
    }//end decay
    
    public int getMutatePercentage(){
        return mutatePercentage;
    }//end getMutatePercentage
    
    public int getStartReproductionClock(){
        return startReproductionClock;
    }//end getStartReproductionClock
    
    public int getReproductionCock(){
        return reproductionClock;
    }//end getReproductionClock
    
    public void setReproductionClock(int in){
        reproductionClock = in;
    }//end setReproductionClock
}//end GraphTuple
