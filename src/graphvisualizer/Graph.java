package graphvisualizer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Graph {

    private final ArrayList<GraphNode> nodes = new ArrayList<>();
    private final GraphNode[][] matrix;
    private int idCount = 1;
    private final MyQueue<GraphNode> queue = new MyQueue<>();
    private Base ref = null;

    //////////////////////////////
    //    Runtime Variables     //
    //////////////////////////////
    private boolean trim = true;
    private boolean consume = false;
    private boolean mutate = true;
    private boolean mutateColor = true;
    private boolean mutateHealth = false;
    private int growthType = 0;
    private int foodThreshhold = 1;

    public Graph(int r, int c, Base in) {
        matrix = new GraphNode[r][c];
        ref = in;
        initializeGrid();
    }//end constructor

    private void add(GraphNode gn, int i, int j) {
        nodes.add(gn);
        matrix[i][j] = gn;
    }//end add

    private void biconnect(GraphNode n1, GraphNode n2, GraphTupleInfo gti) {
        if (nodes.contains(n1) && nodes.contains(n2)) {
            if (!n1.isConnected(n2) && !n2.isConnected(n1)) {
                n1.connect(n2, gti);
                n2.connect(n1, gti);
            }//end if
        }//end if
        else {
            System.out.println("One or more nodes is not in the node list!");
        }//end else
    }//end biconnect

    //Connect one node with any number of other nodes
    public void connector(GraphNode start, GraphNode[] targets, GraphTupleInfo gti) {
        for (GraphNode gn : targets) {
            biconnect(start, gn, gti);
        }//end for
    }//end connectTo

    //Connect one node to another node
    public void connector(GraphNode start, GraphNode target, GraphTupleInfo gti) {
        biconnect(start, target, gti);
    }//end connectTo

    private void initializeGrid() {
        int pointSize = ref.getPointSize();
        int spacing = ref.getSpacing();
        for (int i = 0, ySpace = pointSize / 2; i < matrix.length; i++, ySpace += spacing) {
            for (int j = 0, xSpace = pointSize / 2; j < matrix[i].length; j++, xSpace += spacing) {
                GraphNode temp = new GraphNode(xSpace - pointSize / 2, ySpace - pointSize / 2, pointSize, pointSize, newID(), i, j, consume);
                temp.setColor(Color.BLUE);
                add(temp, i, j);
            }//end for
        }//end for
        outlineGrid();
    }//end initializeGrid

    private void outlineGrid() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                GraphNode temp = matrix[i][j];
                try {
                    if (j == 0 || j == matrix[i].length - 1 || i == 0 || i == matrix.length - 1) {
                        temp.setColor(Color.BLACK);
                        GraphTupleInfo gti = new GraphTupleInfo(50, Color.BLACK, 0, 1, true);
                        if (j == 0) {
                            if (i == 0) {
                                connector(temp, new GraphNode[]{matrix[i + 1][j], matrix[i][j + 1]}, gti);
                            }//end if
                            else if (i == matrix.length - 1) {
                                connector(temp, new GraphNode[]{matrix[i - 1][j], matrix[i][j + 1]}, gti);
                            }//end if
                            else {
                                connector(temp, new GraphNode[]{matrix[i + 1][j], matrix[i - 1][j]}, gti);
                            }//end else
                        }//end if
                        else if (j == matrix[i].length - 1) {
                            if (i == 0) {
                                connector(temp, new GraphNode[]{matrix[i + 1][j], matrix[i][j - 1]}, gti);
                            }//end if
                            else if (i == matrix.length - 1) {
                                connector(temp, new GraphNode[]{matrix[i - 1][j], matrix[i][j - 1]}, gti);
                            }//end if
                            else {
                                connector(temp, new GraphNode[]{matrix[i + 1][j], matrix[i - 1][j]}, gti);
                            }//end else
                        }//end else if
                        if (i == 0) {
                            if (j == 0) {
                                connector(temp, new GraphNode[]{matrix[i + 1][j], matrix[i][j + 1]}, gti);
                            }//end if
                            else if (j == matrix[i].length - 1) {
                                connector(temp, new GraphNode[]{matrix[i + 1][j], matrix[i][j - 1]}, gti);
                            }//end if
                            else {
                                connector(temp, new GraphNode[]{matrix[i][j + 1], matrix[i][j - 1]}, gti);
                            }//end else
                        }//end if
                        else if (i == matrix.length - 1) {
                            if (j == 0) {
                                connector(temp, new GraphNode[]{matrix[i - 1][j], matrix[i][j + 1]}, gti);
                            }//end if
                            else if (j == matrix[i].length - 1) {
                                connector(temp, new GraphNode[]{matrix[i - 1][j], matrix[i][j - 1]}, gti);
                            }//end if
                            else {
                                connector(temp, new GraphNode[]{matrix[i][j + 1], matrix[i][j - 1]}, gti);
                            }//end else
                        }//end else if
                    }//end if
                    if (temp.getILoc() == matrix.length / 2 || temp.getJLoc() == matrix[0].length / 2) {
                        temp.setColor(Color.BLACK);
                    }//end if
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Figure out something to do with this problem");
                }//end try catch
            }//end for
        }//end for
    }//end outlineGrid

    private void buildQueue() {
        for (GraphNode[] matrix1 : matrix) {
            for (GraphNode temp : matrix1) {
                if (temp.connections.size() == 1) {
                    queue.enqueue(temp);
                }//end if
            }//end for
        } //end for
    }//end buildQueue

    private void decay() {
        for (GraphNode[] matrix1 : matrix) {
            for (GraphNode temp : matrix1) {
                if (temp.connections.size() > 0) {
                    for (int i = 0; i < temp.connections.size(); i++) {
                        GraphTuple gt = temp.connections.get(i);
                        if (temp.getILoc() != matrix.length - 1 && temp.getILoc() != 0 && temp.getJLoc() != 0 && temp.getJLoc() != matrix[0].length - 1) {
                            gt.health--;
                            if (gt.health <= 0) {
                                GraphNode gn = gt.location;
                                for (int j = 0; j < gn.connections.size(); j++) {
                                    GraphTuple gt2 = gn.connections.get(j);
                                    if (gt2.location == temp) {
                                        gn.connections.remove(gt2);
                                        j--;
                                    }//end if
                                }//end for
                                temp.connections.remove(gt);
                                i--;
                            }//end if
                        }//end if
                    }//end for
                }//end if
            }//end for
        } //end for
    }//end decay

    private void regularStep(GraphNode temp, int xCompare, int yCompare) {
        GraphTuple parent = temp.connections.get(0);
        parent.reproductionClock--;
        if (parent.reproductionClock <= 0) {
            GraphTupleInfo gti = new GraphTupleInfo(parent.startHealth, parent.getColor(), parent.mutatePercentage);
            normalRules(temp,gti,xCompare,yCompare);   
            parent.reproductionClock = parent.startReproductionClock;
        }//end if
    }//end regularStep

    private void mutateStep(GraphNode temp, int xCompare, int yCompare) {
        GraphTuple parent = temp.connections.get(0);
        parent.reproductionClock--;
        if (parent.reproductionClock <= 0) {
            Random rand = new Random();
            int rInfluence = rand.nextInt(51);
            int gInfluence = rand.nextInt(51);
            int bInfluence = rand.nextInt(51);
            int healthInfluence;
            int mutationPercentage = parent.mutatePercentage;
            if (parent.startHealth <= 1) {
                healthInfluence = 0;
            }//end if
            else {
                double deviation = .05;
                int variance = (int) (parent.startHealth * deviation);
                if (variance <= 1) {
                    variance = 1;
                }//end if
                healthInfluence = rand.nextInt(variance + 1);
            }//end else
            if (rand.nextBoolean()) {
                rInfluence = rInfluence * -1;
            }//end if
            if (rand.nextBoolean()) {
                gInfluence = gInfluence * -1;
            }//end if
            if (rand.nextBoolean()) {
                bInfluence = bInfluence * -1;
            }//end if
            if (rand.nextBoolean()) {
                healthInfluence = healthInfluence * -1;
            }//end if
            int red = parent.getColor().getRed();
            if (rand.nextBoolean() && mutateColor) {
                red = parent.getColor().getRed() + rInfluence;
            }//end if
            int green = parent.getColor().getGreen();
            if (rand.nextBoolean() && mutateColor) {
                green = parent.getColor().getGreen() + gInfluence;
            }//end if
            int blue = parent.getColor().getBlue();
            if (rand.nextBoolean() && mutateColor) {
                blue = parent.getColor().getBlue() + bInfluence;
            }//end if
            int newHealth = parent.startHealth;
            if (rand.nextBoolean() && mutateHealth) {
                newHealth = parent.startHealth + healthInfluence;
            }//end if
            if (red > 255) {
                red = 255;
            }//end if
            else if (red < 0) {
                red = 0;
            }//end else if
            if (green > 255) {
                green = 255;
            }//end if
            else if (green < 0) {
                green = 0;
            }//end else if
            if (blue > 255) {
                blue = 255;
            }//end if
            else if (blue < 0) {
                blue = 0;
            }//end else if
            if (newHealth < 0) {
                newHealth = 0;
            }//end if
            GraphTupleInfo gti = new GraphTupleInfo(newHealth, new Color(red, green, blue), mutationPercentage);
            normalRules(temp,gti,xCompare,yCompare);            
            parent.reproductionClock = parent.startReproductionClock;
        }//end if
    }//end mutateStep

    private void growthStep(GraphNode temp) {
        GraphNode target = null;
        int greatestFood = 0;
        ArrayList<GraphNode> sameNodes = new ArrayList<>();
        int iLoc = temp.getILoc();
        int jLoc = temp.getJLoc();
        if (jLoc > 0 && iLoc > 0 && jLoc < matrix[0].length - 1 && iLoc < matrix.length - 1) {
            //Top Left Check
            if (matrix[iLoc - 1][jLoc - 1] != temp && matrix[iLoc - 1][jLoc - 1].food > greatestFood && matrix[iLoc - 1][jLoc - 1].food > 0) {
                greatestFood = matrix[iLoc - 1][jLoc - 1].food;
                target = matrix[iLoc - 1][jLoc - 1];
            }//end if
            //Top Middle Check
            if (matrix[iLoc - 1][jLoc] != temp && matrix[iLoc - 1][jLoc].food > greatestFood) {
                greatestFood = matrix[iLoc - 1][jLoc].food;
                target = matrix[iLoc - 1][jLoc];
                sameNodes.clear();
            }//end if
            else if (matrix[iLoc - 1][jLoc] != temp && matrix[iLoc - 1][jLoc].food > greatestFood && matrix[iLoc - 1][jLoc].food > 0) {
                sameNodes.add(matrix[iLoc - 1][jLoc]);
            }//end else if
            //Top Right Check
            if (matrix[iLoc - 1][jLoc + 1] != temp && matrix[iLoc - 1][jLoc + 1].food > greatestFood && matrix[iLoc - 1][jLoc + 1].food > 0) {
                greatestFood = matrix[iLoc - 1][jLoc + 1].food;
                target = matrix[iLoc - 1][jLoc + 1];
                sameNodes.clear();
            }//end if
            else if (matrix[iLoc - 1][jLoc + 1] != temp && matrix[iLoc - 1][jLoc + 1].food > greatestFood && matrix[iLoc - 1][jLoc + 1].food > 0) {
                sameNodes.add(matrix[iLoc - 1][jLoc + 1]);
            }//end else if
            //Middle Left Check
            if (matrix[iLoc][jLoc - 1] != temp && matrix[iLoc][jLoc - 1].food > greatestFood && matrix[iLoc][jLoc - 1].food > 0) {
                greatestFood = matrix[iLoc][jLoc - 1].food;
                target = matrix[iLoc][jLoc - 1];
                sameNodes.clear();
            }//end if
            else if (matrix[iLoc][jLoc - 1] != temp && matrix[iLoc][jLoc - 1].food > greatestFood && matrix[iLoc][jLoc - 1].food > 0) {
                sameNodes.add(matrix[iLoc][jLoc - 1]);
            }//end else if
            //Middle Right Check
            if (matrix[iLoc][jLoc + 1] != temp && matrix[iLoc][jLoc + 1].food > greatestFood && matrix[iLoc][jLoc + 1].food > 0) {
                greatestFood = matrix[iLoc][jLoc + 1].food;
                target = matrix[iLoc][jLoc - 1];
                sameNodes.clear();
            }//end if
            else if (matrix[iLoc][jLoc + 1] != temp && matrix[iLoc][jLoc + 1].food > greatestFood && matrix[iLoc][jLoc + 1].food > 0) {
                sameNodes.add(matrix[iLoc][jLoc + 1]);
            }//end else if
            //Bottom Left Check
            if (matrix[iLoc + 1][jLoc - 1] != temp) {
                if (matrix[iLoc + 1][jLoc - 1].food > greatestFood && matrix[iLoc + 1][jLoc - 1].food > 0) {
                    greatestFood = matrix[iLoc + 1][jLoc - 1].food;
                    target = matrix[iLoc + 1][jLoc - 1];
                    sameNodes.clear();
                }//end if
            }//end if
            else if (matrix[iLoc + 1][jLoc - 1] != temp && matrix[iLoc + 1][jLoc - 1].food > greatestFood && matrix[iLoc + 1][jLoc - 1].food > 0) {
                sameNodes.add(matrix[iLoc + 1][jLoc - 1]);
            }//end else if
            //Bottom Middle Check
            if (matrix[iLoc + 1][jLoc] != temp && matrix[iLoc + 1][jLoc].food > greatestFood && matrix[iLoc + 1][jLoc].food > 0) {
                greatestFood = matrix[iLoc + 1][jLoc].food;
                target = matrix[iLoc + 1][jLoc];
                sameNodes.clear();
            }//end if
            else if (matrix[iLoc + 1][jLoc] != temp && matrix[iLoc + 1][jLoc].food > greatestFood && matrix[iLoc + 1][jLoc].food > 0) {
                sameNodes.add(matrix[iLoc + 1][jLoc]);
            }//end else if
            //Bottom Left Check
            if (matrix[iLoc + 1][jLoc + 1] != temp && matrix[iLoc + 1][jLoc + 1].food > greatestFood && matrix[iLoc + 1][jLoc + 1].food > 0) {
                target = matrix[iLoc + 1][jLoc + 1];
                sameNodes.clear();
            }//end if
            else if (matrix[iLoc + 1][jLoc + 1] != temp && matrix[iLoc + 1][jLoc + 1].food > greatestFood && matrix[iLoc + 1][jLoc - +1].food > 0) {
                sameNodes.add(matrix[iLoc + 1][jLoc + 1]);
            }//end else if
            //Check to see if there are nodes in the list of possible targets
            if (sameNodes.isEmpty()) {
                connector(temp, target, new GraphTupleInfo(temp.connections.get(0).getColor()));
            }//end if
            else {
                connector(temp, sameNodes.get(0), new GraphTupleInfo(temp.connections.get(0).getColor()));
            }//end else
        }//end if
    }//end growthStep
    
    private void normalRules(GraphNode start, GraphTupleInfo gti, int xCompare, int yCompare){
        int iLoc = start.getILoc();
        int jLoc = start.getJLoc();
        if (xCompare == 1 && yCompare == 1) {
                connector(start, new GraphNode[]{matrix[iLoc][jLoc + 1], matrix[iLoc + 1][jLoc]}, gti);
            }//end if
            else if (xCompare == -1 && yCompare == 1) {
                connector(start, new GraphNode[]{matrix[iLoc][jLoc - 1], matrix[iLoc + 1][jLoc]}, gti);
            }//end else if
            else if (xCompare == 1 && yCompare == -1) {
                connector(start, new GraphNode[]{matrix[iLoc][jLoc + 1], matrix[iLoc - 1][jLoc]}, gti);
            }//end else if
            else if (xCompare == -1 && yCompare == -1) {
                connector(start, new GraphNode[]{matrix[iLoc][jLoc - 1], matrix[iLoc - 1][jLoc]}, gti);
            }//end else if
            else if (xCompare == 0 && yCompare == 1) {
                connector(start, new GraphNode[]{matrix[iLoc + 1][jLoc - 1], matrix[iLoc + 1][jLoc + 1]}, gti);
            }//end else if
            else if (xCompare == 1 && yCompare == 0) {
                connector(start, new GraphNode[]{matrix[iLoc + 1][jLoc + 1], matrix[iLoc - 1][jLoc + 1]}, gti);
            }//end else if
            else if (xCompare == -1 && yCompare == 0) {
                connector(start, new GraphNode[]{matrix[iLoc + 1][jLoc - 1], matrix[iLoc - 1][jLoc - 1]}, gti);
            }//end else if
            else if (xCompare == 0 && yCompare == -1) {
                connector(start, new GraphNode[]{matrix[iLoc - 1][jLoc - 1], matrix[iLoc - 1][jLoc + 1]}, gti);
            }//end else if
    }

    private void eat() {
        for (GraphNode[] matrix1 : matrix) {
            for (GraphNode gn : matrix1) {
                for (GraphTuple gt : gn.connections) {
                    gt.location.food--;
                }//end for
                if (gn.food < 0) {
                    gn.food = 0;
                }//end if
                if (gn.food >= foodThreshhold) {
                    gn.regenFood();
                }//end if
            }//end for
        }//end for
    }//end eat

    public void clearGrid() {
        for (GraphNode[] matrix1 : matrix) {
            for (GraphNode gn : matrix1) {
                gn.connections.clear();
                gn.setColor(Color.BLUE);
            }//end for
        }//end for
        outlineGrid();
    }//end clearGrid

    public Color getAverageColor() {
        int redVal = 0;
        int greenVal = 0;
        int blueVal = 0;
        int numOfConnections = 0;
        for (GraphNode gn : nodes) {
            for (GraphTuple gt : gn.connections) {
                if (!gt.isEdge()) {
                    redVal += gt.r;
                    greenVal += gt.g;
                    blueVal += gt.b;
                    numOfConnections++;
                }//end if
            }//end for
        }//end for
        if (numOfConnections > 0) {
            redVal = redVal / numOfConnections;
            greenVal = greenVal / numOfConnections;
            blueVal = blueVal / numOfConnections;
            return new Color(redVal, greenVal, blueVal);
        }//end if
        return new Color(Color.OPAQUE);
    }//end getAverageColor

    public void stepForward() {
        int spacing = ref.getSpacing();
        buildQueue();
        if (growthType == 0) {
            while (queue.hasFront()) {
                GraphNode temp = queue.dequeue();
                int xCompare = (temp.x - temp.connections.get(0).location.x) / spacing;
                int yCompare = (temp.y - temp.connections.get(0).location.y) / spacing;
                if (!mutate) {
                    regularStep(temp, xCompare, yCompare);
                }//end if
                else {
                    Random rand = new Random();
                    int mutator = rand.nextInt(1000) + 1;
                    if (mutator <= temp.connections.get(0).mutatePercentage) {
                        mutateStep(temp, xCompare, yCompare);
                    }//end if
                    else {
                        regularStep(temp, xCompare, yCompare);
                    }//end else
                }//end else
            }//end while
        }//end if
        else if (growthType == 1) {
            while (queue.hasFront()) {
                GraphNode temp = queue.dequeue();
                growthStep(temp);
            }//end while
        }//end else if
        if (trim) {
            decay();
        }//end if
        if (consume || growthType == 1) {
            eat();
        }//end if
    }//end stepForward

    public void paint(Graphics g) {
        for (GraphNode r : this.nodes) {
            g.setColor(r.getColor());
            if (r.food <= 0) {
                g.setColor(Color.WHITE);
            }//end if
            g.fillRect(r.x, r.y, r.height, r.width);
            for (GraphTuple gt : r.connections) {
                g.setColor(gt.getColor());
                if (r.isConnected(gt.location) && gt.location.isConnected(r)) {
                    g.drawLine(r.x + r.width / 2, r.y + r.height / 2, gt.location.x + gt.location.width / 2, gt.location.y + gt.location.height / 2);
                }//end if
            }//end for
        }//end for
    }//end paint

    public GraphNode[][] getMatrix() {
        return matrix;
    }//end getMatrix

    public ArrayList<GraphNode> getGraphNodes() {
        return nodes;
    }//end getGraphNodes

    private int newID() {
        int out = idCount;
        idCount++;
        return out;
    }//end newID
}//end Graph
