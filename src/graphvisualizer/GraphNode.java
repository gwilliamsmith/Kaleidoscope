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
    int iLoc;
    int jLoc;
    int red = 0;
    int blue = 0;
    int green = 0;
    int food;
    int foodVar = 10;
    int regenVar = 3;
    private int regenRate;
    private Color color;

    public GraphNode(int xloc, int yloc, int width, int height, boolean food) {
        super(xloc, yloc, width, height);
        if (food) {
            Random rand = new Random();
            this.food = rand.nextInt(foodVar);
            this.regenRate = rand.nextInt(regenVar);
        }//end if
        else {
            this.food = 50;
        }//end else
    }//end constructor

    public GraphNode(int xloc, int yloc, int width, int height, int id, boolean food) {
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
    }//end constructor

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

    public GraphNode(GraphNode node) {
        super(node.x, node.y, node.width, node.height);
        this.food = node.food;
        this.regenRate = node.regenRate;
        this.id = node.id;
    }//end constructor

    public void connect(GraphNode node) {
        double distance = Math.sqrt((this.x - node.x) * (this.x - node.x) + (this.y - node.y) * (this.y - node.y));
        if (!isConnected(node)) {
            connections.add(new GraphTuple(node, distance));
        }//end if
    }//end connect

    public void connect(GraphNode node, boolean edge) {
        double distance = Math.sqrt((this.x - node.x) * (this.x - node.x) + (this.y - node.y) * (this.y - node.y));
        if (!isConnected(node)) {
            connections.add(new GraphTuple(node, distance, false, edge));
        }//end if
    }//end connect

    public void connect(GraphNode node, Color color) {
        double distance = Math.sqrt((this.x - node.x) * (this.x - node.x) + (this.y - node.y) * (this.y - node.y));
        if (!isConnected(node)) {
            connections.add(new GraphTuple(node, distance, false, color));
        }//end if
    }//end connect

    public void connect(GraphNode node, Color color, int health) {
        double distance = Math.sqrt((this.x - node.x) * (this.x - node.x) + (this.y - node.y) * (this.y - node.y));
        if (!isConnected(node)) {
            connections.add(new GraphTuple(node, distance, false, color, health));
        }//end if
    }//end connect

    public void connect(GraphNode node, Color color, int health, int mutationPercentage) {
        double distance = Math.sqrt((this.x - node.x) * (this.x - node.x) + (this.y - node.y) * (this.y - node.y));
        if (!isConnected(node)) {
            connections.add(new GraphTuple(node, distance, false, color, health, mutationPercentage));
        }//end if
    }//enc connect

    public void connect(GraphNode node, Color color, int health, boolean edge) {
        double distance = Math.sqrt((this.x - node.x) * (this.x - node.x) + (this.y - node.y) * (this.y - node.y));
        if (!isConnected(node)) {
            connections.add(new GraphTuple(node, distance, false, color, health, edge));
        }//end if
    }//end connect

    public void connect(GraphNode node, GraphTupleInfo gti) {
        System.out.println(gti.color);
        double distance = Math.sqrt((this.x - node.x) * (this.x - node.x) + (this.y - node.y) * (this.y - node.y));
        if (!isConnected(node)) {
            connections.add(new GraphTuple(node, distance, false, gti));
        }//end if
    }//end connect

    public void weightedConnect(GraphNode node, double weight, boolean weighted) {
        if (!isConnected(node)) {
            connections.add(new GraphTuple(node, weight, weighted));
        }//end if
    }//end weightedConnect

    public void refresh() {
        searched = false;
        for (GraphTuple gt : connections) {
            searchPath = false;
        }//end for
    }//end refresh

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

    public void setColor(Color in) {
        color = in;
    }//end setColor

    public void setColor(int rin, int bin, int gin) {
        color = new Color(rin, gin, bin);
    }//end setColor

    public Color getColor() {
        return color;
    }//end getColor
}//end GraphNode
