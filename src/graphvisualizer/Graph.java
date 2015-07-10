package graphvisualizer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    private boolean mutateHealth = true;
    private int growthType = 0;

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
            if (gn != start && start.isAdjacentTo(gn)) {
                biconnect(start, gn, gti);
            }//end if
        }//end for
    }//end connectTo

    //Connect one node to another node
    public boolean connector(GraphNode start, GraphNode target, GraphTupleInfo gti) {
        if (target != start && start.isAdjacentTo(target)) {
            biconnect(start, target, gti);
            return true;
        }//end if
        return false;
    }//end connectTo

    private void initializeGrid() {
        int pointSize = ref.getPointSize();
        int spacing = ref.getSpacing();
        for (int i = 0, ySpace = pointSize / 2; i < matrix.length; i++, ySpace += spacing) {
            for (int j = 0, xSpace = pointSize / 2; j < matrix[i].length; j++, xSpace += spacing) {
                GraphNode temp = new GraphNode(xSpace - pointSize / 2, ySpace - pointSize / 2, pointSize, pointSize, newID(), i, j, matrix.length - 1, matrix[0].length - 1, consume);
                temp.setColor(Color.BLUE);
                add(temp, i, j);
            }//end for
        }//end for
        outlineGrid();
    }//end initializeGrid

    public void resizeGrid() {
        int pointSize = ref.getPointSize();
        int spacing = ref.getSpacing();
        for (int i = 0, ySpace = pointSize / 2; i < matrix.length; i++, ySpace += spacing) {
            for (int j = 0, xSpace = pointSize / 2; j < matrix[i].length; j++, xSpace += spacing) {
                GraphNode temp = matrix[i][j];
                temp.x = xSpace - pointSize / 2;
                temp.y = ySpace - pointSize / 2;
                temp.width = pointSize;
                temp.height = pointSize;
            }//end for
        }//end for
    }//end resizeGrid

    private void outlineGrid() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                GraphNode temp = matrix[i][j];
                try {
                    if (j == 0 || j == matrix[i].length - 1 || i == 0 || i == matrix.length - 1) {
                        temp.setColor(Color.BLACK);
                        GraphTupleInfo gti = new GraphTupleInfo(50, Color.BLACK, 0, 1);
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
                if (temp.getNumberOfConnections() == 1) {
                    queue.enqueue(temp);
                }//end if
            }//end for
        } //end for
    }//end buildQueue

    private void decayNodes() {
        for (GraphNode[] matrix1 : matrix) {
            for (GraphNode temp : matrix1) {
                for (int i = 0; i < temp.getNumberOfConnections(); i++) {
                    GraphTuple gt = temp.getConnection(i);
                    if (!temp.isEdgeNode()) {
                        gt.decay();
                        if (!gt.isAlive()) {
                            temp.severConnection(gt.getToLocation());
                            i--;
                        }//end if
                    }//end if
                }//end for
            }//end for
        } //end for
    }//end decay

    private void regularStep(GraphNode temp, int xCompare, int yCompare) {
        GraphTuple parent = temp.getParentLine();
        int reproductionClock = parent.getReproductionCock();
        parent.setReproductionClock(reproductionClock--);
        if (reproductionClock <= 0) {
            GraphTupleInfo gti = new GraphTupleInfo(parent.getStartHealth(), parent.getColor(), parent.getMutatePercentage());
            normalRules(temp, gti, xCompare, yCompare);
            parent.setReproductionClock(parent.getStartReproductionClock());
        }//end if
    }//end regularStep

    private void mutateStep(GraphNode temp, int xCompare, int yCompare) {
        GraphTuple parent = temp.getParentLine();
        int reproductionClock = parent.getReproductionCock();
        parent.setReproductionClock(reproductionClock--);
        if (reproductionClock <= 0) {
            GraphTupleInfo gti;
            if (mutate) {
                gti = generateMutatedGti(parent);
            }//end if
            else {
                gti = new GraphTupleInfo(parent.getStartHealth(), parent.getColor(), parent.getMutatePercentage());
            }//end else
            normalRules(temp, gti, xCompare, yCompare);
            parent.setReproductionClock(parent.getStartReproductionClock());
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
            if (matrix[iLoc - 1][jLoc - 1] != temp && matrix[iLoc - 1][jLoc - 1].getFood() > greatestFood && matrix[iLoc - 1][jLoc - 1].getFood() > 0) {
                greatestFood = matrix[iLoc - 1][jLoc - 1].getFood();
                target = matrix[iLoc - 1][jLoc - 1];
            }//end if
            //Top Middle Check
            if (matrix[iLoc - 1][jLoc] != temp && matrix[iLoc - 1][jLoc].getFood() > greatestFood) {
                greatestFood = matrix[iLoc - 1][jLoc].getFood();
                target = matrix[iLoc - 1][jLoc];
                sameNodes.clear();
            }//end if
            else if (matrix[iLoc - 1][jLoc] != temp && matrix[iLoc - 1][jLoc].getFood() > greatestFood && matrix[iLoc - 1][jLoc].getFood() > 0) {
                sameNodes.add(matrix[iLoc - 1][jLoc]);
            }//end else if
            //Top Right Check
            if (matrix[iLoc - 1][jLoc + 1] != temp && matrix[iLoc - 1][jLoc + 1].getFood() > greatestFood && matrix[iLoc - 1][jLoc + 1].getFood() > 0) {
                greatestFood = matrix[iLoc - 1][jLoc + 1].getFood();
                target = matrix[iLoc - 1][jLoc + 1];
                sameNodes.clear();
            }//end if
            else if (matrix[iLoc - 1][jLoc + 1] != temp && matrix[iLoc - 1][jLoc + 1].getFood() > greatestFood && matrix[iLoc - 1][jLoc + 1].getFood() > 0) {
                sameNodes.add(matrix[iLoc - 1][jLoc + 1]);
            }//end else if
            //Middle Left Check
            if (matrix[iLoc][jLoc - 1] != temp && matrix[iLoc][jLoc - 1].getFood() > greatestFood && matrix[iLoc][jLoc - 1].getFood() > 0) {
                greatestFood = matrix[iLoc][jLoc - 1].getFood();
                target = matrix[iLoc][jLoc - 1];
                sameNodes.clear();
            }//end if
            else if (matrix[iLoc][jLoc - 1] != temp && matrix[iLoc][jLoc - 1].getFood() > greatestFood && matrix[iLoc][jLoc - 1].getFood() > 0) {
                sameNodes.add(matrix[iLoc][jLoc - 1]);
            }//end else if
            //Middle Right Check
            if (matrix[iLoc][jLoc + 1] != temp && matrix[iLoc][jLoc + 1].getFood() > greatestFood && matrix[iLoc][jLoc + 1].getFood() > 0) {
                greatestFood = matrix[iLoc][jLoc + 1].getFood();
                target = matrix[iLoc][jLoc - 1];
                sameNodes.clear();
            }//end if
            else if (matrix[iLoc][jLoc + 1] != temp && matrix[iLoc][jLoc + 1].getFood() > greatestFood && matrix[iLoc][jLoc + 1].getFood() > 0) {
                sameNodes.add(matrix[iLoc][jLoc + 1]);
            }//end else if
            //Bottom Left Check
            if (matrix[iLoc + 1][jLoc - 1] != temp) {
                if (matrix[iLoc + 1][jLoc - 1].getFood() > greatestFood && matrix[iLoc + 1][jLoc - 1].getFood() > 0) {
                    greatestFood = matrix[iLoc + 1][jLoc - 1].getFood();
                    target = matrix[iLoc + 1][jLoc - 1];
                    sameNodes.clear();
                }//end if
            }//end if
            else if (matrix[iLoc + 1][jLoc - 1] != temp && matrix[iLoc + 1][jLoc - 1].getFood() > greatestFood && matrix[iLoc + 1][jLoc - 1].getFood() > 0) {
                sameNodes.add(matrix[iLoc + 1][jLoc - 1]);
            }//end else if
            //Bottom Middle Check
            if (matrix[iLoc + 1][jLoc] != temp && matrix[iLoc + 1][jLoc].getFood() > greatestFood && matrix[iLoc + 1][jLoc].getFood() > 0) {
                greatestFood = matrix[iLoc + 1][jLoc].getFood();
                target = matrix[iLoc + 1][jLoc];
                sameNodes.clear();
            }//end if
            else if (matrix[iLoc + 1][jLoc] != temp && matrix[iLoc + 1][jLoc].getFood() > greatestFood && matrix[iLoc + 1][jLoc].getFood() > 0) {
                sameNodes.add(matrix[iLoc + 1][jLoc]);
            }//end else if
            //Bottom Left Check
            if (matrix[iLoc + 1][jLoc + 1] != temp && matrix[iLoc + 1][jLoc + 1].getFood() > greatestFood && matrix[iLoc + 1][jLoc + 1].getFood() > 0) {
                target = matrix[iLoc + 1][jLoc + 1];
                sameNodes.clear();
            }//end if
            else if (matrix[iLoc + 1][jLoc + 1] != temp && matrix[iLoc + 1][jLoc + 1].getFood() > greatestFood && matrix[iLoc + 1][jLoc - +1].getFood() > 0) {
                sameNodes.add(matrix[iLoc + 1][jLoc + 1]);
            }//end else if
            //Check to see if there are nodes in the list of possible targets
            if (sameNodes.isEmpty()) {
                connector(temp, target, temp.getParentLine().generateGTI());
            }//end if
            else {
                connector(temp, sameNodes.get(0), temp.getParentLine().generateGTI());
            }//end else
        }//end if
    }//end growthStep

    private void normalRules(GraphNode start, GraphTupleInfo gti, int xCompare, int yCompare) {
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
    }//end normalRules

    private GraphTupleInfo generateMutatedGti(GraphTuple parent) {
        Random rand = new Random();
        return new GraphTupleInfo(generateMutatedHealth(parent, rand), generateMutatedColor(parent, rand), parent.getMutatePercentage());
    }//end generateMutatedGti

    private Color generateMutatedColor(GraphTuple parent, Random rand) {
        if (mutateColor) {
            int rInfluence = flipInfluenceCheck(rand.nextInt(51), rand);
            int gInfluence = flipInfluenceCheck(rand.nextInt(51), rand);
            int bInfluence = flipInfluenceCheck(rand.nextInt(51), rand);
            int red = influenceInt(parent.getColor().getRed(), rInfluence, rand);
            int green = influenceInt(parent.getColor().getGreen(), gInfluence, rand);
            int blue = influenceInt(parent.getColor().getBlue(), bInfluence, rand);
            red = validateColor(red);
            green = validateColor(green);
            blue = validateColor(blue);
            return new Color(red, green, blue);
        }//end if
        else {
            return parent.getColor();
        }//end else
    }//end generateMutatedColor

    private int generateMutatedHealth(GraphTuple parent, Random rand) {
        if (mutateHealth && parent.getStartHealth() > 1) {
            int healthInfluence;
            double deviation = .05;
            int variance = (int) (parent.getStartHealth() * deviation);
            if (variance <= 1) {
                variance = 1;
            }//end if
            healthInfluence = flipInfluenceCheck(rand.nextInt(variance + 1), rand);
            int newHealth = influenceInt(parent.getStartHealth(), healthInfluence, rand);
            if (newHealth < 0) {
                newHealth = 0;
            }//end if
            return newHealth;
        }//end if
        else {
            return parent.getStartHealth();
        }//end else
    }//end generateMutatedHealth

    private int validateColor(int in) {
        if (in > 255) {
            return 255;
        }//end if
        else if (in < 0) {
            return 0;
        }//end else if
        else {
            return in;
        }//end else
    }//end validateColor

    private int flipInfluenceCheck(int in, Random rand) {
        if (rand.nextBoolean()) {
            in *= -1;
        }//end if
        return in;
    }//end flipColorInfluence

    private int influenceInt(int in, int influencer, Random rand) {
        if (rand.nextBoolean()) {
            in += influencer;
        }//end if
        return in;
    }//end influenceInt

    private void eat() {
        for (GraphNode[] matrix1 : matrix) {
            for (GraphNode gn : matrix1) {
                gn.consume();
            }//end for
        }//end for
    }//end eat

    public void clearGrid() {
        for (GraphNode[] matrix1 : matrix) {
            for (GraphNode gn : matrix1) {
                gn.clearConnections();
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
            for (int i = 0; i < gn.getNumberOfConnections(); i++) {
                GraphTuple gt = gn.getConnection(i);
                if (!gt.isEdge()) {
                    redVal += gt.getRed();
                    greenVal += gt.getGreen();
                    blueVal += gt.getBlue();
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
                int xCompare = temp.compareX(spacing);
                int yCompare = temp.compareY(spacing);
                if (!mutate) {
                    regularStep(temp, xCompare, yCompare);
                }//end if
                else {
                    Random rand = new Random();
                    int mutator = rand.nextInt(1000) + 1;
                    if (mutator <= temp.getParentLine().getMutatePercentage()) {
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
            decayNodes();
        }//end if
        if (consume || growthType == 1) {
            eat();
        }//end if
    }//end stepForward

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(ref.getPointSize()/2));
        for (GraphNode gn : this.nodes) {
            g2.setColor(gn.getColor());
            if (gn.getFood() <= 0) {
                g2.setColor(Color.WHITE);
            }//end if
            g2.fillRect(gn.x + ref.getCanvas().getWindowX(), gn.y + ref.getCanvas().getWindowY(), gn.height, gn.width);
            for (int i = 0; i < gn.getNumberOfConnections(); i++) {
                GraphTuple gt = gn.getConnection(i);
                g2.setColor(gt.getColor());
                GraphNode location = gt.getToLocation();
                if (gn.isConnected(location) && location.isConnected(gn)) {
                    //This line is a mess, fix it at some point
                    g2.drawLine(gn.x + gn.width / 2 + ref.getCanvas().getWindowX(), gn.y + gn.height / 2 + ref.getCanvas().getWindowY(), location.x + location.width / 2 + ref.getCanvas().getWindowX(), location.y + location.height / 2 + ref.getCanvas().getWindowY());
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

    public boolean getTrim() {
        return trim;
    }//end getTrim

    public void setTrim(boolean in) {
        trim = in;
    }//end setTrim

    public boolean getConsume() {
        return consume;
    }//end getConsume

    public void setConsume(boolean in) {
        consume = in;
    }//end setConsume

    public boolean getMutate() {
        return mutate;
    }//end getMutate()

    public void setMutate(boolean in) {
        mutate = in;
    }//end setMutate

    public boolean getMutateColor() {
        return mutateColor;
    }//end getMutateColor

    public void setMutateColor(boolean in) {
        mutateColor = in;
    }//end setMutateColor

    public boolean getMutateHealth() {
        return mutateHealth;
    }//end getMutateHealth

    public void setMutateHealth(boolean in) {
        mutateHealth = in;
    }//end setMutateHealth

    public int getGrowthType() {
        return growthType;
    }//end getGrowthType

    public void setGrowthType(int in) {
        growthType = in;
    }//end setGrowthType

}//end Graph
