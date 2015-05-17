package graphvisualizer;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class GraphNode extends Rectangle {

    private final ArrayList<GraphTuple> connections = new ArrayList<>();
    private final int id;
    private final int iLoc;
    private final int jLoc;
    private final int iMax;
    private final int jMax;
    private int food;
    private final int foodVar = 10;
    private final int regenVar = 3;
    private final int regenThreshold = 1;
    private int regenRate;
    private Color color;

    public GraphNode(int xloc, int yloc, int width, int height, int id, int iLoc, int jLoc, int iMax, int jMax, boolean food) {
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
        this.iMax = iMax;
        this.jMax = jMax;
    }//end constructor

    public void connect(GraphNode to, GraphTupleInfo gti) {
        if (!isConnected(to)) {
            connections.add(new GraphTuple(to, this, gti));
        }//end if
    }//end connect

    public void regenFood() {
        food += regenRate;
    }//end regenFood

    public void print() {
        System.out.println("This node id: " + id);
        System.out.println("Connected ids: ");
        for (GraphTuple gt : connections) {
            System.out.print(gt.getToLocation().id + " ");
        }//end for
        System.out.println();
    }//end print

    @Override
    public String toString() {
        return "Node ID: " + id;
    }//end toString

    public boolean isConnected(GraphNode target) {
        for (GraphTuple gt : connections) {
            if (gt.getToLocation() == target) {
                return true;
            }//end if
        }//end for
        return false;
    }//end isConnected

    public GraphTuple getParentLine() {
        return connections.get(0);
    }//end getParentLine

    public GraphTuple getConnection(int i) {
        return connections.get(i);
    }//end getConnection

    public void severConnection(GraphNode gn) {
        if (isConnected(gn)) {
            for (int i = 0; i < connections.size(); i++) {
                if (connections.get(i).getToLocation() == gn) {
                    connections.remove(i);
                    break;
                }//end if
            }//end for
            gn.severConnection(this);
        }//end if
    }//removeConnection

    public void clearConnections() {
        connections.clear();
    }//end clearConnections

    public int getNumberOfConnections() {
        return connections.size();
    }//end getNumberOfConnections

    public int compareX(int spacing) {
        return  (x - getParentLine().getToLocation().x) / spacing;
    }//end compareX
    
    public int compareY(int spacing) {
        return (y - getParentLine().getToLocation().y) / spacing;
    }//end compareX

    public boolean isEdgeNode() {
        return (getILoc() == iMax || getILoc() == 0 || getJLoc() == 0 || getJLoc() == jMax);
    }//end isEdgeNode

    public void consume() {
        for (GraphTuple gt : connections) {
            if (!gt.isEdge()) {
                food--;
            }//end if
            if (food < 0) {
                food = 0;
            }//end if
            if (food >= regenThreshold) {
                regenFood();
            }//end if
        }//end for
    }

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

    public int getFood() {
        return food;
    }//end getFood

    public int getILoc() {
        return iLoc;
    }//end getILoc

    public int getJLoc() {
        return jLoc;
    }//end getJLoc
}//end GraphNode
