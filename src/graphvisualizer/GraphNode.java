package graphvisualizer;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class GraphNode extends Rectangle {

    public ArrayList<GraphTuple> connections = new ArrayList<>();
    public int id;
    public boolean searchPath = false;
    public boolean searched = false;
    private final int iLoc;
    private final int jLoc;
    int food;
    int foodVar = 10;
    int regenVar = 3;
    private int regenRate;
    private Color color;

    public GraphNode(int xloc, int yloc, int width, int height, int id, int iLoc, int jLoc, boolean food) {
        super(xloc, yloc, width, height);
        if (food) {
            Random rand = new Random();
            this.food = rand.nextInt(foodVar);
            this.regenRate = rand.nextInt(regenVar);
        }//end if
        else {
            this.food = 50;
        }//end else
        this.id = id;
        this.iLoc = iLoc;
        this.jLoc = jLoc;
    }//end constructor

    public void connect(GraphNode node, GraphTupleInfo gti) {
        if (!isConnected(node)) {
            connections.add(new GraphTuple(node, gti));
        }//end if
    }//end connect

    public void regenFood() {
        food += regenRate;
    }//end regenFood

    public void print() {
        System.out.println("This node id: " + id);
        System.out.println("Search Path: " + searchPath);
        System.out.println("Connected ids: ");
        for (GraphTuple gt : connections) {
            System.out.print(gt.location.id + " ");
        }//end for
        System.out.println();
    }//end print

    @Override
    public String toString() {
        return "Node ID: " + Integer.toString(id);
    }//end toString

    public boolean isConnected(GraphNode target) {
        for (GraphTuple gt : connections) {
            if (gt.location == target) {
                return true;
            }//end if
        }//end for
        return false;
    }//end isConnected
    
    //////////////////////////////
    //     Setters/Getters      //
    //////////////////////////////

    public void setColor(Color in) {
        color = in;
    }//end setColor

    public void setColor(int rin, int bin, int gin) {
        color = new Color(rin, gin, bin);
    }//end setColor

    public Color getColor() {
        return color;
    }//end getColor
    
    public int getILoc(){
        return iLoc;
    }//end getILoc
    
    public int getJLoc(){
        return jLoc;
    }//end getJLoc
}//end GraphNode
