package graphvisualizer;

import SwingElements.Base;
import SwingElements.FamilyAverageColorGradient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.SwingUtilities;

/**
 * The Graph object. Powers the cellular automata, stores nodes, and contains
 * methods relating to graph manipulation
 */
public class Graph {

    public static boolean TRIM = true;                                          //Determines if connection health decreases over time
    public static boolean CONSUME = false;                                      //Determines if GraphNode food is consumed by connections
    public static boolean MUTATE_COLOR = true;                                  //Determines if, when mutation is possible, connections can MUTATE colors
    public static boolean MUTATE_HEALTH = true;                                 //Determines if, when mutation is possible, connections can MUTATE starting health
    public static GrowthMode MODE = GrowthMode.REGULAR;                         //Enum determining how stepForward() behaves

    private ArrayList<FamilyAverageColorGradient> familyAverageColorGradients;  //FamilyAverageColorGradient objects, one for each family
    //TODO: Maybe get rid of this?
    private final ArrayList<GraphNode> nodes = new ArrayList<>();               //The list of nodes contained in the graph
    private final GraphNode[][] matrix;                                         //The matrix of nodes
    private int idCount = 1;                                                    //The next node ID to be assigned
    private final MyQueue<GraphNode> queue = new MyQueue<>();                   //The queue used to perform growth under the cellular automata rules
    private Base ref;                                                           //The Base object, contining information about the runtime of the program
    private Camera camera;                                                      //The Camera object, used to take pictures of the grid when enabled

    private GraphNode connectA;                                                 //The first node selected by the user when creating a connection
    private GraphNode connectB;                                                 //The second node selected by the user when creating a connection

    //Line Place event variables
    private GraphNode lineEventNode1;                                           //The first node being considered for a line creation event
    private GraphNode lineEventNode2;                                           //The second node being considered for a line creation event

    private int familyCount = 0;                                                //Number of line families

    private boolean seeded = false;                                             //True if grid has been seeded, false if not (and by default)

    private long stepCount = 0;                                                 //Number of steps taken
    private long cycleBase = 0;                                                 //Number of steps in a cycle
    private long cycleCount = 0;                                                //Number of cycles that have occured

    private long cycleSteps = 0;                                                //Not really sure what this does, but it's related to picture taking somehow
    private boolean noNewGrowth = false;                                        //True if there are no additions to the growth queue after buildQueue has been run, false otherwise, and by default
    private int midCount = 0;                                                   //Used to ensure that the camera does not take a picture of a false stop, and actually captues graph state between cycles

    private boolean seed1 = true;                                               //Determines if seeding the graph can result in one line on a line of symmetry
    private boolean seed2 = true;                                               //Determines if seeding the graph can result in one pair of lines on a line of symmetry
    private boolean seed4 = true;                                               //Determines if seeding the graph can result in two pairs of lines, on two lines of symmetry
    private boolean seed8 = true;                                               //Determines if seeding the graph can result in four pairs of line, on all lines of symmetry

    private GraphNode lastHovered;                                              //The node the mouse is hovering over. For displaying its coordinates on the canvas. (Maybe move to CanvasMouseListener?)

    private String testName = "test16";                                         //Name of the test when gathering statistics

    private boolean pictureSaveToggle = true;                                   //Toggle determining if the the Camera should save a picture of the generated pattern when there is a big enough lull in line reproduction

    private boolean picturePauseToggle = false;                                 //Toggle determining if the graph should pause after each picture is taken

    /**
     * @param r Number of rows in the matrix
     * @param c Number of columns in the matric
     * @param in The {@link Base} object to refer to for finding runtime
     * variables
     */
    public Graph(int r, int c, Base in) {
        matrix = new GraphNode[c][r];
        ref = in;
        camera = new Camera(ref);
        familyAverageColorGradients = new ArrayList<>();
        //initializeGrid();
    }//end constructor

    /**
     * Advances the cellular automata forward one step
     */
    public void takeStep() {
        stepForward();
        stepCount++;
        if (cycleBase > 0) {
            cycleCount = stepCount / cycleBase;
        }//end if
        ref.scheduler.checkSchedule(stepCount);
    }//end takeStep

    /**
     * Moves the {@link Graph} forward one step, each connection on it
     * attempting to reproduce. Uses a {@link MyQueue} object to take in to
     * account all lines that will reproduce, ensuring all of them have the
     * opportunity to do so. Also reduces the remaining health of all
     * connections, and the food on each node (if enabled).
     */
    public void stepForward() {
        buildQueue();                                                           //Builds the queue of nodes, each of which houses a connection to reproduce
        while (queue.hasFront()) {
            GraphNode temp = queue.dequeue();
            switch (MODE) {
                case REGULAR:
                    regularStep(temp);
                    break;
                case MUTATION:
                    mutation(temp);
                    break;
                case DEPTH_BASED:
                    depthStep(temp);
                    break;
                case GROWTH:
                    growthStep(temp);
                    break;
            }//end switch
        }//end while
        if (TRIM) {
            decayConnections();                                                 //Reduces the health of each non-edge connection
        }//end if
        if (CONSUME || MODE == GrowthMode.GROWTH) {
            eat();                                                              //Connections eat the food on relevant nodes
        }//end if
        queue.empty();
    }//end stepForward

    /**
     * Resets the grid. Removes all connections from all nodes and resets
     * {@link GraphNode} colors to their original colors
     */
    public void reset() {
        clearGrid();
        stepCount = 0;
        cycleBase = 0;
        cycleCount = 0;
        familyCount = 0;
        seeded = false;
        familyAverageColorGradients.clear();
    }//end reset

    /**
     * Calls {@link Graph#reset()}, then adds seeds to the graph
     */
    public void refreshSeed() {
        reset();
        whiteOutNodeColors();
        generateSeeds();
    }//end refreshSeed

    /**
     * Adds a node to both the list of nodes, and the matrix
     *
     * @param gn The node to be added
     * @param i The I, or y-axis, location in the matrix
     * @param j The J, or x-axis, location in the matrix
     */
    private void addNode(GraphNode gn, int i, int j) {
        nodes.add(gn);
        matrix[i][j] = gn;
    }//end add

    /**
     * Connect method. Checks to ensure both nodes are in the Graph.
     *
     * @param n1 The first node involved in the connection
     * @param n2 The second node involved in the connection
     * @param gti The {@link GraphTupleInfo} describing the connection to be
     * made
     * @param direction The direction of a curved connection
     * @param severity The severity of a curved line
     * @return True if biconnect is successful, false if not
     */
    public boolean biconnect(GraphNode n1, GraphNode n2, GraphTupleInfo gti, int direction, double severity) {
        //Only return true if both n1.connect() and n2.connect() are successful
        return n1.connect(n2, gti, direction, severity, false)
                && n2.connect(n1, gti, direction, severity, true);
    }//end biconnect

    //TODO: Clean this up
    /**
     * Connects two nodes using their descriptions from a state save file.
     *
     * @param n1y The y index of the first node
     * @param n1x The x index of the first node
     * @param n2y The y index of the second node
     * @param n2x The x index of the second node
     * @param red The red RGB value for the connection to be made
     * @param green The green RGB value for the connection to be made
     * @param blue The blue RGB value for the connection to be made
     * @param health The remaining health of the connection
     * @param startHealth The starting health of the connection
     * @param mutatePercentage The mutationPercentage value for the connection
     * @param reproductionClock The number of turns remaining before the
     * connection attempts to reproduce
     * @param startReproductionClock The number of turns the connection must
     * wait before attempting to reproduce
     * @param edge Toggle determining if the connection is an edge
     * @param curved Toggle determining if the connection is curved
     * @param curveDirection Toggle determining the direction of the
     * connection's curve
     * @param curveSeverity The severity of the connection's curve
     */
    public void createConnection(int n1y, int n1x, int n2y, int n2x, int red, int green, int blue, int health, int startHealth, int mutatePercentage, int reproductionClock, int startReproductionClock, boolean edge, boolean curved, int curveDirection, double curveSeverity) {
        GraphNode n1 = matrix[n1y][n1x];
        GraphNode n2 = matrix[n2y][n2x];
        GraphTuple gt1 = new GraphTuple();
        GraphTuple gt2 = new GraphTuple();
        if (!n1.isConnected(n2) && !n2.isConnected(n1)) {
            gt1.setToLocation(n2);
            gt1.setFromLocation(n1);
            gt1.setStartHealth(startHealth);
            gt1.setHealth(health);
            gt1.setMutatePercentage(mutatePercentage);
            gt1.setReproductionClock(reproductionClock);
            gt1.setStartReproductionClock(startReproductionClock);
            gt1.setEdge(edge);
            gt1.setCurve(curved);
            gt1.setCurveDirection(curveDirection);
            gt1.setCurveSeverity(curveSeverity);
            gt1.redundant = false;
            gt1.setColor(new Color(red, green, blue));
            gt2.setToLocation(n1);
            gt2.setFromLocation(n2);
            gt2.setStartHealth(startHealth);
            gt2.setHealth(health);
            gt2.setMutatePercentage(mutatePercentage);
            gt2.setReproductionClock(reproductionClock);
            gt2.setStartReproductionClock(startReproductionClock);
            gt2.setEdge(edge);
            gt2.setCurve(curved);
            gt2.setCurveDirection(curveDirection);
            gt2.setCurveSeverity(curveSeverity);
            gt2.redundant = true;
            gt2.setColor(new Color(red, green, blue));
            n1.addConnection(gt1);
            n2.addConnection(gt2);
        }//end if
    }//end creteConnections

    /**
     * Disconnects two nodes/
     *
     * @param n1 The first node to be disconnected
     * @param n2 The second node to be disconnected
     */
    public void disconnect(GraphNode n1, GraphNode n2) {
        if (n1.isConnected(n2) && n2.isConnected(n1)) {
            n1.severConnection(n2);
            n2.severConnection(n1);
        }//end if
    }//end disconnect

    /**
     * Connects one node with any number of other nodes.
     *
     * @param start The node to be connected to
     * @param targets The list of nodes to connect start to
     * @param gti The {@link GraphTupleInfo} object describing the connection
     * @return An array of booleans. True indicates that the connection was
     * successful, false indicates an unsuccessful connection
     */
    public boolean[] connector(GraphNode start, GraphNode[] targets, GraphTupleInfo gti) {
        boolean[] out = new boolean[targets.length];
        int outIndex = 0;
        int direction = GraphTuple.generateCurveDirection();
        double severity = GraphTuple.generateCurveSeverity();
        for (GraphNode gn : targets) {
            if (gn != start && start.isAdjacentTo(gn)) {
                out[outIndex++] = biconnect(start, gn, gti, direction, severity);
            }//end if
            else {
                out[outIndex++] = false;
            }//end else
        }//end for
        return out;
    }//end connector

    /**
     * Connect one node to another.
     *
     * @param start The first node to be connected
     * @param target The second node to be connected
     * @param gti The {@link GraphTupleInfo} object describing the connection
     * @return True if the connection is successful, false if the connection is
     * unsuccessful
     */
    public boolean connector(GraphNode start, GraphNode target, GraphTupleInfo gti) {
        if (target != start && start.isAdjacentTo(target)) {
            return biconnect(start, target, gti, GraphTuple.generateCurveDirection(), GraphTuple.generateCurveSeverity());
        }//end if
        return false;
    }//end connectTo

    /**
     * Create a new family of lines, starting with a connection between two
     * given nodes.
     *
     * @param node1 The first node to be connected
     * @param node2 The second node to be connected
     * @param gti The {@link GraphTupleInfo} object describing the connection
     */
    public void createNewFamily(GraphNode node1, GraphNode node2, GraphTupleInfo gti) {
        gti.family = newFamilyID();
        familyAverageColorGradients.add(new FamilyAverageColorGradient(gti.family));      //Adds a new FamilyAverageColorGradient to the list, so that this family's changes in average color can be tracked
        if (MODE == GrowthMode.DEPTH_BASED) {
            gti.color = new Color(255, 0, 0);
        }//end if
        connector(node1, node2, gti);
    }//end createNewFamily

    /**
     * Performs all actions required to set up the grid upon initialization of
     * the {@link Graph} itself. This method creates the required number of
     * {@link GraphNode} objects, and places them on the {@link Canvas} at the
     * appropriate coordinates
     */
    public void initializeGrid() {
        int pointSize = ref.getCanvas().getPointSize();
        int spacing = ref.getCanvas().getSpacing();
        for (int i = 0, ySpace = -(pointSize / 2); i < matrix.length; i++, ySpace += (spacing + pointSize)) {
            for (int j = 0, xSpace = -(pointSize / 2); j < matrix[i].length; j++, xSpace += (spacing + pointSize)) {
                int modX = new Random().nextBoolean() ? 1 : -1;
                int modY = new Random().nextBoolean() ? 1 : -1;
                int displacementX = new Random().nextInt(26) * modX;
                int displacementY = new Random().nextInt(26) * modY;
                System.out.println((displacementX+xSpace) + " " + (displacementY+ySpace));
                GraphNode temp = new GraphNode(xSpace+displacementX, ySpace+displacementY, pointSize, pointSize, newID(), i, j, CONSUME);
                addNode(temp, i, j);
                resetNodeColor(temp);
            }//end for
        }//end for
        outlineGrid();
    }//end initializeGrid

    //TODO: Probably should clean this up, too...
    /**
     * Creates edge connections for the nodes at the edges of the matrix.
     */
    private void outlineGrid() {
        GraphTupleInfo gti = new GraphTupleInfo(50, Color.BLACK, 0, 1);
        for (int modi = 0; modi < matrix.length; modi++) {                      //Looks through the left and right edges of a rectangular grid
            if (matrix[0].length > 0) {                                         //Ensures that the node matrix has at least one column
                GraphNode temp1 = matrix[modi][0];                              // The node from the left edge of the rectangle
                int i1 = temp1.getILoc();
                int j1 = temp1.getJLoc();
                GraphNode temp2 = matrix[matrix.length - 1 - modi][matrix[0].length - 1];     //The node from the right edge of the rectangle
                int i2 = temp2.getILoc();
                int j2 = temp2.getJLoc();
                if (modi == 0) {                                                //Case for first node on the left edge, and last node on the right edge
                    connector(temp1, new GraphNode[]{matrix[i1 + 1][j1], matrix[i1][j1 + 1]}, gti);
                    connector(temp2, new GraphNode[]{matrix[i2 - 1][j2], matrix[i2][j2 - 1]}, gti);
                }//end if
                else if (modi == matrix.length - 1) {                             //Case for last node on the left edge, and first node on the left edge
                    connector(temp1, new GraphNode[]{matrix[i1 - 1][j1], matrix[i1][j1 + 1]}, gti);
                    connector(temp2, new GraphNode[]{matrix[i2 + 1][j2], matrix[i2][j2 - 1]}, gti);
                }//end else if
                else {                                                          //All middle nodes on the left and right edges
                    connector(temp1, new GraphNode[]{matrix[i1 + 1][j1], matrix[i1 - 1][j1]}, gti);
                    connector(temp2, new GraphNode[]{matrix[i2 + 1][j2], matrix[i2 - 1][j2]}, gti);
                }//end else
            }//end if
        }//end for
        for (int modj = 0; modj < matrix[0].length; modj++) {                   //Looks through top and bottom edges of a rectangular grid
            if (matrix.length > 0) {                                            //Ensures that there is at least one row in the node matrix
                GraphNode temp1 = matrix[0][modj];                              //The node from the top edge of the rectangle
                int i1 = temp1.getILoc();
                int j1 = temp1.getJLoc();
                GraphNode temp2 = matrix[matrix.length - 1][matrix[0].length - 1 - modj];       //The node from the bottom edge of the rectangle
                int i2 = temp2.getILoc();
                int j2 = temp2.getJLoc();
                if (modj == 0) {                                                //Case for the first node of the top edge, and the last node of the bottom edge
                    connector(temp1, new GraphNode[]{matrix[i1][j1 + 1], matrix[i1 + 1][j1]}, gti);
                    connector(temp2, new GraphNode[]{matrix[i2][j2 - 1], matrix[i2 - 1][j2]}, gti);
                }//end if
                else if (modj == matrix[0].length - 1) {                          //Case for the lase node of the top edge, and the first node of the bottom edge
                    connector(temp1, new GraphNode[]{matrix[i1][j1 - 1], matrix[i1 + 1][j1]}, gti);
                    connector(temp2, new GraphNode[]{matrix[i2][j2 + 1], matrix[i2 - 1][j2]}, gti);
                }//end else if
                else {                                                          //All middle nodes on the top and bottom edges
                    connector(temp1, new GraphNode[]{matrix[i1][j1 + 1], matrix[i1][j1 - 1]}, gti);
                    connector(temp2, new GraphNode[]{matrix[i2][j2 + 1], matrix[i2][j2 - 1]}, gti);
                }//end else
            }//end if
        }//end for
    }//end outlineGrid

    //TODO: 
    //    Move picture taking to Event
    /**
     * Constructs the growth queue for line growth. Built beforehand so all
     * lines that should grow on a given step are given the opportunity
     */
    private void buildQueue() {
        for (int i = 0; i < matrix.length; i++) {
            GraphNode[] matrix1 = matrix[i];
            for (int j = 0; j < matrix1.length; j++) {
                GraphNode temp = matrix1[j];
                if (temp.getNumberOfConnections() == 1) {                       //A node with only one connection indicates that connection is eligible for growth
                    queue.enqueue(temp);
                }//end if
            }//end for
        }//end for
        noNewGrowth = queue.isEmpty();                                          //True if no nodes are found to be valid targets for growth
        if (pictureSaveToggle) {
            checkPicture();
        }//end if
    }//end buildQueue

    //TODO: Replace this with Event
    /**
     * Checks to see if the {@link Camera} should save a picture of the pattern.
     */
    private void checkPicture() {
        if (noNewGrowth && !camera.isPictureTaken()) {                          //Only take a picture if there has been no growth, and the camera is ready
            if (cycleSteps <= 0) {
                cycleSteps = stepCount;
            }//end if
            camera.setPictureTaken(true);
            camera.takePicture();
            midCount = 0;
            if (picturePauseToggle) {
                ref.pause();
            }//end if

        }//end if
        else if (!noNewGrowth && camera.isPictureTaken()) {
            if (midCount >= cycleSteps) {
                camera.setPictureTaken(false);
            }//end if
        }//end else if
        if (camera.isPictureTaken()) {
            midCount++;
        }//end if
    }//end checkPicture

    /**
     * Turns all nodes white.
     */
    public void whiteOutNodeColors() {
        for (int i = 0; i < matrix.length; i++) {
            GraphNode[] matrix1 = matrix[i];
            for (int j = 0; j < matrix1.length; j++) {
                GraphNode gn = matrix1[j];
                gn.setColor(GraphNodeColors.WHITE);
            }//end for
        }//end for
    }//end whiteOutNodeColors

    /**
     * Resets the colors of all nodes
     */
    public void resetNodeColors() {
        for (int i = 0; i < matrix.length; i++) {
            GraphNode[] matrix1 = matrix[i];
            for (int j = 0; j < matrix1.length; j++) {
                GraphNode gn = matrix1[j];
                resetNodeColor(gn);
            }//end 
        }//end for
    }//end resetNodeColors

    /**
     * Reduces the health of all non-edge connections
     */
    private void decayConnections() {
        for (int i = 0; i < matrix.length; i++) {
            GraphNode[] matrix1 = matrix[i];
            for (int j = 0; j < matrix1.length; j++) {
                GraphNode temp = matrix1[j];
                for (int k = 0; k < temp.getNumberOfConnections(); k++) {
                    GraphTuple gt = temp.getConnection(k);
                    if (!gt.isEdge(this)) {
                        gt.decay();
                        if (!gt.isAlive()) {                                    //Remove the connection if it has no health left
                            disconnect(gt.getFromLocation(), gt.getToLocation());
                            k--;                                                //Decrement the index so that the loop still works properly
                        }//end if
                    }//end if
                }//end for
            }//end for
        } //end for
    }//end decay

    /**
     * Middle step used when mutation is enabled. Determines if a connection
     * should or should not MUTATE when reproducing
     *
     * @param temp The node housing the connection to grow
     */
    private void mutation(GraphNode temp) {
        if (new Random().nextInt(GraphTuple.MUTATION_DIVISOR) + 1 <= temp.getParentLine().getMutatePercentage()) {
            mutateStep(temp);
        }//end if
        else {
            regularStep(temp);
        }//end else
    }//end mutation

    /**
     * Sets up the growth-eligible node for connection reproduction
     *
     * @param temp The node housing the connection to grow
     */
    private void regularStep(GraphNode temp) {                                  //Maybe come up with a new name for this?
        GraphTuple parent = temp.getParentLine();
        parent.setReproductionClock(parent.getReproductionClock() - 1);
        if (parent.getReproductionClock() <= 0 && !parent.isEdge(this)) {       //Connections should only reproduce if their reproduction clock is 0, and it's not an edge connection
            GraphTupleInfo gti = parent.generateGTI();
            normalRules(temp, gti);                                             //Now that the setup has been done, actually have the connection reproduce
            parent.resetReproductionClock();
        }//end if
    }//end regularStep

    /**
     * Sets up the growth-eligible node for connection reproduction, generating
     * a mutated {@link GraphTupleInfo} object if required
     *
     * @param temp The node housing the connection to grow
     */
    private void mutateStep(GraphNode temp) {
        GraphTuple parent = temp.getParentLine();
        parent.setReproductionClock(parent.getReproductionClock() - 1);
        if (parent.getReproductionClock() <= 0) {
            GraphTupleInfo gti;
            if (MODE == GrowthMode.MUTATION) {
                gti = parent.generateMutatedGti(parent);
            }//end if
            else {
                gti = parent.generateGTI();
            }//end else
            normalRules(temp, gti);                                             //normalRules is still called, because the rules for growth don't actually change with mutation
            parent.resetReproductionClock();
        }//end if
    }//end mutateStep

    /**
     * Sets up the growth-eligible node for connection reproduction, generating
     * new {@link GraphTupleInfo} objects for the next generation of lines.
     *
     * @param temp The node housing the connection to grow
     */
    private void depthStep(GraphNode temp) {
        GraphTuple parent = temp.getParentLine();
        parent.setReproductionClock(parent.getReproductionClock() - 1);
        if (parent.getReproductionClock() <= 0 && !parent.isEdge(this)) {       //Connections should only reproduce if their reproduction clock is 0, and it's not an edge connection
            Color newColor = parent.getDepthColor();
            GraphTupleInfo gti = parent.generateGTI();
            gti.color = newColor;
            normalRules(temp, gti);                                             //Now that the setup has been done, actually have the connection reproduce using normalRules(line growth is unchanged)
            parent.resetReproductionClock();
        }//end if
    }//end depthStep

    //TODO: Simplify/clean this method
    /**
     * Connection reproduces towards the adjacent node with the greatest amount
     * of food left. Currently un-optimized, and rarely used.
     *
     * @param temp The node housing the connection in question
     */
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

    /**
     * Create new connections, as children of a parent.
     *
     * @param start The node housing the connection to reproduce
     * @param gti The {@link GraphTupleInfo} object describing the traits of the
     * new connection
     */
    private void normalRules(GraphNode start, GraphTupleInfo gti) {
        int xCompare = start.compareX();
        int yCompare = start.compareY();
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

    /**
     * Connections to each node consume food on each one.
     */
    private void eat() {
        for (int i = 0; i < matrix.length; i++) {
            GraphNode[] matrix1 = matrix[i];
            for (int j = 0; j < matrix1.length; j++) {
                GraphNode gn = matrix1[j];
                if (gn.getNumberOfConnections() > 0) {
                    gn.consume(this);
                }//end if
            }//end for
        }//end for
    }//end eat

    /**
     * Removes all connections from all nodes in the {@link Graph}.
     */
    public void clearGrid() {
        for (int i = 0; i < matrix.length; i++) {
            GraphNode[] matrix1 = matrix[i];
            for (int j = 0; j < matrix1.length; j++) {
                GraphNode gn = matrix1[j];
                gn.clearConnections();
                resetNodeColor(gn);
            }//end for
        }//end for
        outlineGrid();              //Re-outline the grid, since clearGrid() also removes the edges
    }//end clearGrid

    /**
     * Highlights a (@link GraphNode}, turning it a given color
     *
     * @param in The node to be highlighted
     * @param highlightColor The new color for the given node
     */
    public void highlightNode(GraphNode in, GraphNodeColors highlightColor) {
        in.setColor(highlightColor);
    }//end highlightNode

    /**
     * Same as
     * {@link Graph#highlightNode(graphvisualizer.GraphNode, java.awt.Color)},
     * but also changes the color of nodes adjacent to the given node.
     *
     * @param in The node to be highlighted
     * @param selectionColor The color for the given node
     * @param adjacentColor The color for nodes adjacent to the given node
     */
    public void highlightNodeAdjacents(GraphNode in, GraphNodeColors selectionColor, GraphNodeColors adjacentColor) {
        in.setColor(selectionColor);
        int iLoc = in.getILoc();
        int jLoc = in.getJLoc();
        if (iLoc > 0) {
            matrix[iLoc - 1][jLoc].setColor(adjacentColor);
            if (jLoc > 0) {
                matrix[iLoc - 1][jLoc - 1].setColor(adjacentColor);
            }//end if
            if (jLoc < matrix[iLoc].length - 1) {
                matrix[iLoc - 1][jLoc + 1].setColor(adjacentColor);
            }//end if
        }//end if
        if (iLoc < matrix.length - 1) {
            matrix[iLoc + 1][jLoc].setColor(adjacentColor);
            if (jLoc > 0) {
                matrix[iLoc + 1][jLoc - 1].setColor(adjacentColor);
            }//end if
            if (jLoc < matrix[iLoc].length - 1) {
                matrix[iLoc + 1][jLoc + 1].setColor(adjacentColor);
            }//end if
        }//end if
        if (jLoc > 0) {
            matrix[iLoc][jLoc - 1].setColor(adjacentColor);
        }//end if
        if (jLoc < matrix[iLoc].length - 1) {
            matrix[iLoc][jLoc + 1].setColor(adjacentColor);
        }//end if
    }//end highlightNodeAdjacents

    /**
     * Sets (or re-sets) the color of a given {@link GraphNode} and its adjacent
     * {@link GraphNode}s.
     *
     * @param in The node to be re-colored
     */
    public void resetNodeAdjacents(GraphNode in) {
        resetNodeColor(in);
        int iLoc = in.getILoc();
        int jLoc = in.getJLoc();
        if (iLoc > 0) {
            resetNodeColor(matrix[iLoc - 1][jLoc]);
            if (jLoc > 0) {
                resetNodeColor(matrix[iLoc - 1][jLoc - 1]);
            }//end if
            if (jLoc < matrix[iLoc].length - 1) {
                resetNodeColor(matrix[iLoc - 1][jLoc + 1]);
            }//end if
        }//end if
        if (iLoc < matrix.length - 1) {
            resetNodeColor(matrix[iLoc + 1][jLoc]);
            if (jLoc > 0) {
                resetNodeColor(matrix[iLoc + 1][jLoc - 1]);
            }//end if
            if (jLoc < matrix[iLoc].length - 1) {
                resetNodeColor(matrix[iLoc + 1][jLoc + 1]);
            }//end if
        }//end if
        if (jLoc > 0) {
            resetNodeColor(matrix[iLoc][jLoc - 1]);
        }//end if
        if (jLoc < matrix[iLoc].length - 1) {
            resetNodeColor(matrix[iLoc][jLoc + 1]);
        }//end if
    }//end resetNodeAdjacents

    /**
     * Sets (or re-sets) the color of a given {@link GraphNode}.
     *
     * @param in The node to be re-colored
     */
    public void resetNodeColor(GraphNode in) {
        if (nodeIsMiddle(in)) {
            in.setColor(GraphNodeColors.DEFAULT_MIDDLE_COLOR);
        }//end if
        else if (nodeIsEdge(in)) {
            in.setColor(GraphNodeColors.DEFAULT_EDGE_COLOR);
        }//end else if
        else {
            in.setColor(GraphNodeColors.DEFAULT_COLOR);
        }//end else
    }//end resetNodeColor

    /**
     * Finds all {@link GraphNode} adjacent to a given {@link GraphNode}.
     *
     * @param in The node to find adjacent nodes for
     * @return An {@link ArrayList} of nodes adjacent to the given
     * {@link GraphNode}
     */
    public ArrayList<GraphNode> findAdjacentNodes(GraphNode in) {
        ArrayList<GraphNode> out = new ArrayList<>();
        int iLoc = in.getILoc();
        int jLoc = in.getJLoc();
        if (iLoc > 0) {
            out.add(matrix[iLoc - 1][jLoc]);
            if (jLoc > 0) {
                out.add(matrix[iLoc - 1][jLoc - 1]);
            }//end if
            if (jLoc < matrix[iLoc].length - 1) {
                out.add(matrix[iLoc - 1][jLoc + 1]);
            }//end if
        }//end if
        if (iLoc < matrix.length - 1) {
            out.add(matrix[iLoc + 1][jLoc]);
            if (jLoc > 0) {
                out.add(matrix[iLoc + 1][jLoc - 1]);
            }//end if
            if (jLoc < matrix[iLoc].length - 1) {
                out.add(matrix[iLoc + 1][jLoc + 1]);
            }//end if
        }//end if
        if (jLoc > 0) {
            out.add(matrix[iLoc][jLoc - 1]);
        }//end if
        if (jLoc < matrix[iLoc].length - 1) {
            out.add(matrix[iLoc][jLoc + 1]);
        }//end if
        return out;
    }//end findAdjacentNodes

    /**
     * Determines if a given {@link GraphNode} is in the middle row or column of
     * the {@link Graph}.
     *
     * @param in The {@link GraphNode} to check
     * @return True if the given {@link GraphNode} is in the middle row or
     * column of the {@link Graph}, false if not
     */
    public boolean nodeIsMiddle(GraphNode in) {
        return (in.getILoc() == matrix.length / 2 || in.getJLoc() == matrix[0].length / 2);
    }//end nodeIsMidde

    /**
     * Determines if a given {@link GraphNode} is at the edge of the
     * {@link Graph}.
     *
     * @param in The {@link GraphNode} to check
     * @return True if the given {@link GraphNode} is at the edge of the
     * {@link Graph}, false if not
     */
    public boolean nodeIsEdge(GraphNode in) {
        return (in.getILoc() == matrix.length - 1 || in.getILoc() == 0 || in.getJLoc() == 0 || in.getJLoc() == matrix[0].length - 1);
    }//end nodeIsEdge

    /**
     * Gets an ID for a {@link GraphNode}, and increments the count of IDs held
     * by the {@link Graph}.
     *
     * @return The ID for the a {@link GraphNode}
     */
    private int newID() {
        int out = idCount;
        idCount++;
        return out;
    }//end newID

    /**
     * Gets a family ID for a new family of {@link GraphTuple} connections.
     *
     * @return The family ID for the new {@link GraphTuple}
     */
    public int newFamilyID() {
        familyCount++;
        return familyCount;
    }//end new FamilyID

    /**
     * Creates seeds for coloring book page generation. Pairs of seeds are the
     * same amount of distance away from their respective edges. This only works
     * on square grids.
     */
    public void generateSeeds() {
        Random rand = new Random();
        int seeds = rand.nextInt(9);
        boolean evenSwitch = ((matrix.length % 2) == 0) && ((matrix[0].length % 2) == 0);               //Checks to see if the matrix has an even # of rows and columns
        boolean seedCheck = (seeds == 1 && !seeded && seed1) || (seeds == 2 && seed2) || (seeds == 4 && seed4) || ((seeds == 8 && seed8) && !evenSwitch);
        while (!seedCheck) {                                                    //Ensures that the number of seeds is any of the following values: (1,2,4,8). Some values may not be used if the user has disabled them.
            seeds = rand.nextInt(9);
            seedCheck = (seeds == 1 && !seeded && seed1) || (seeds == 2 && seed2) || (seeds == 4 && seed4) || ((seeds == 8 && seed8) && !evenSwitch);
        }//end while
        switch (seeds) {                                                        //Switches between numbers of seed cases                                                       //
            case 1:                                                             //One seed line
                if (!evenSwitch) {
                    oddOneSeed();
                }//end if
                else {
                    evenOneSeed();
                }//end else
                break;
            case 2:                                                             //One pair of seed lines, placed on the same line of symmetry
                if (!evenSwitch) {
                    oddTwoSeeds();
                }//end if
                else {
                    evenTwoSeeds();
                }//end else 
                break;
            case 4:                                                             //Four seed lines, on similar lines of symmetry (Top-Bottom + Left-Right, and TopLeft-BottomRight + TopRight-BottomLeft)
                if (!evenSwitch) {
                    oddFourSeeds();
                }//end if
                else {
                    evenFourSeeds();
                }//end else 
                break;
            case 8:                                                             //Seed lines on all eight lines of symmetry
                oddEightSeeds();
                break;
        }//end switch
    }//end generateSeeds

    /**
     * Seeds the graph placing one line on a grid with an odd number of rows and
     * columns.
     */
    private void oddOneSeed() {
        Random rand = new Random();
        int steps = rand.nextInt(matrix.length / 2) + 1;                //Determines the number of steps in from the edge to place the seed line(s)
        switch (rand.nextInt(9)) {                                  //Determines which line of symmetry the seed line will be placed on
            case 0:
                findSeedNodes(new int[][]{{1, 0}}, steps);              //Top
                break;
            case 1:
                findSeedNodes(new int[][]{{-1, 0}}, steps);             //Bottom
                break;
            case 2:
                findSeedNodes(new int[][]{{0, -1}}, steps);             //Left
                break;
            case 3:
                findSeedNodes(new int[][]{{0, 1}}, steps);              //Right
                break;
            case 4:
                findSeedNodes(new int[][]{{1, -1}}, steps);             //Top-Left
                break;
            case 5:
                findSeedNodes(new int[][]{{1, 1}}, steps);              //Top-Right
                break;
            case 6:
                findSeedNodes(new int[][]{{-1, -1}}, steps);            //Bottom-Left
                break;
            case 7:
                findSeedNodes(new int[][]{{-1, 1}}, steps);             //Bottom-Right
                break;
        }//end switch
    }//end oddOneSeed

    /**
     * Seeds the graph placing two lines on a grid with an odd number of rows
     * and columns.
     */
    private void oddTwoSeeds() {
        Random rand = new Random();
        int steps = rand.nextInt(matrix.length / 2) + 1;
        switch (rand.nextInt(4)) {                                      //Determines which of the four lines of symmetry the seed lines are placed on
            case 0:
                findSeedNodes(new int[][]{{1, 0}, {-1, 0}}, steps);       //Top, Bottom
                break;
            case 1:
                findSeedNodes(new int[][]{{0, -1}, {0, 1}}, steps);       //Left, Right
                break;
            case 2:
                findSeedNodes(new int[][]{{1, -1}, {-1, 1}}, steps);      //Top-Left, Bottom-Right
                break;
            case 3:
                findSeedNodes(new int[][]{{1, 1}, {-1, -1}}, steps);      //Top-Right, Bottom-Left
                break;
            default:
                break;
        }//end switch
    }//end oddTwoSeeds

    /**
     * Seeds the graph placing four lines on a grid with an odd number of rows
     * and columns.
     */
    private void oddFourSeeds() {
        Random rand = new Random();
        int steps = rand.nextInt(matrix.length / 2) + 1;
        switch (rand.nextInt(2)) {                                      //Determines which pair of lines of symmetry is used
            case 0:
                findSeedNodes(new int[][]{{1, 0}, {-1, 0}}, steps);       //Top, Bottom
                steps = rand.nextInt(matrix[0].length / 2) + 1;
                findSeedNodes(new int[][]{{0, -1}, {0, 1}}, steps);       //Left, Right
                break;
            case 1:
                findSeedNodes(new int[][]{{1, -1}, {-1, 1}}, steps);      //Top-Left, Bottom-Right
                steps = rand.nextInt(Math.min(matrix[0].length, matrix.length) / 2) + 1;
                findSeedNodes(new int[][]{{1, 1}, {-1, -1}}, steps);      //Top-Right, Bottom-Left
                break;
        }//end switch
    }//end oddFourSeeds

    /**
     * Seeds the graph placing eight lines on a grid with an odd number of rows
     * and columns.
     */
    private void oddEightSeeds() {
        Random rand = new Random();
        int steps = rand.nextInt(matrix.length / 2) + 1;
        findSeedNodes(new int[][]{{1, 0}, {-1, 0}, {0, -1}, {0, 1}}, steps); //Top, Bottom, Left, Right
        steps = rand.nextInt(Math.min(matrix[0].length, matrix.length) / 2) + 1;
        findSeedNodes(new int[][]{{1, -1}, {-1, 1}, {1, 1}, {-1, -1}}, steps);      //Top-Left, Bottom-Right, Top-Right, Bottom-Left
    }///end oddEightSeeds

    /**
     * Seeds the graph placing one line on a grid with an even number of rows
     * and columns.
     */
    private void evenOneSeed() {
        Random rand = new Random();
        int steps = rand.nextInt(matrix.length / 2) + 1;                //Determines the number of steps in from the edge to place the seed line(s)
        switch (rand.nextInt(3)) {                                  //Determines which line of symmetry the seed line will be placed on
            case 0:
                findSeedNodes(new int[][]{{1, -1}}, steps);             //Top-Left
                break;
            case 1:
                findSeedNodes(new int[][]{{1, 1}}, steps);              //Top-Right
                break;
            case 2:
                findSeedNodes(new int[][]{{-1, -1}}, steps);            //Bottom-Left
                break;
            case 3:
                findSeedNodes(new int[][]{{-1, 1}}, steps);             //Bottom-Right
                break;
        }//end switch
    }//end evenOneSeed

    /**
     * Seeds the graph placing two lines on a grid with an even number of rows
     * and columns.
     */
    private void evenTwoSeeds() {
        Random rand = new Random();
        int steps = rand.nextInt(matrix.length / 2) + 1;
        switch (rand.nextInt(1)) {                                      //Determines which of the four lines of symmetry the seed lines are placed on
            case 0:
                findSeedNodes(new int[][]{{1, -1}, {-1, 1}}, steps);      //Top-Left, Bottom-Right
                break;
            case 1:
                findSeedNodes(new int[][]{{1, 1}, {-1, -1}}, steps);      //Top-Right, Bottom-Left
                break;
            default:
                break;
        }//end switch
    }//end evenTwoSeeds

    /**
     * Seeds the graph placing four lines on a grid with an even number of rows
     * and columns.
     */
    private void evenFourSeeds() {
        Random rand = new Random();
        int steps = rand.nextInt(matrix.length / 2) + 1;
        findSeedNodes(new int[][]{{1, -1}, {-1, 1}}, steps);      //Top-Left, Bottom-Right
        steps = rand.nextInt(Math.min(matrix[0].length, matrix.length) / 2) + 1;
        findSeedNodes(new int[][]{{1, 1}, {-1, -1}}, steps);      //Top-Right, Bottom-Left
    }//evenFourSeeds

    /**
     * Finds nodes on lines of symmetry for coloring book seeding.
     *
     * @param mods An array of pairs of mods. mods[][0] is the y modifier,
     * mods[][1] is the x modifier.
     * @param steps The number of nodes in from the edge
     */
    private void findSeedNodes(int[][] mods, int steps) {
        for (int[] i : mods) {
            int n1y = matrix.length / 2;
            int n1x = matrix[0].length / 2;
            int n2y = matrix.length / 2;
            int n2x = matrix[0].length / 2;
            if (i[0] == 1) {
                n1y = steps;
                n2y = n1y - 1;
            }//end if
            else if (i[0] == -1) {                                                  //I have no idea why swapping n1 and n2 works here
                n2y = matrix.length - steps;
                n1y = matrix.length - steps - 1;
            }//end else if
            if (i[1] == 1) {                                                        //I have no idea why swapping n1 and n2 works here
                n2x = matrix[0].length - steps;
                n1x = matrix[0].length - steps - 1;
            }//end if
            else if (i[1] == -1) {
                n1x = steps;
                n2x = n1x - 1;
            }//end if
            biconnect(getNode(n1x, n1y), getNode(n2x, n2y), new GraphTupleInfo(matrix.length + matrix[0].length, Color.black, 0, 0), GraphTuple.generateCurveDirection(), GraphTuple.generateCurveSeverity());
        }//end if
    }//end findSeedNodes

    /**
     * Gathers all {@link GraphTuple} objects belonging to a given family of
     * lines.
     *
     * @param familyID The ID of the family to pull
     * @return An {@link ArrayList} containing all {@link GraphTuple} objects
     * with the given ID
     */
    public ArrayList<GraphTuple> pullFamily(int familyID) {
        ArrayList<GraphTuple> family = new ArrayList<>();
        for (GraphNode gn : nodes) {
            for (int i = 0; i < gn.getNumberOfConnections(); i++) {
                GraphTuple gt = gn.getConnection(i);
                if (gt.getFamily() == familyID) {
                    family.add(gt);
                }//end if
            }//end for
        }//end for
        return family;
    }//end pullFamily

    //This may not belong here
    /**
     * Adds a new color to the gradient of colors for a given family of lines.
     *
     * @param familyID The ID of the family in question
     * @param colorIn The color to be added to the family color gradient
     */
    public void updateFamilyColorGradient(int familyID, Color colorIn) {
        familyAverageColorGradients.get(familyID).addNewColor(colorIn);
        familyAverageColorGradients.get(familyID).refresh();
    }//end updateFamilyColorGradient

    //This may not belong here
    /**
     * Displays the color gradient for a given family of lines.
     *
     * @param familyID The ID of the family in question
     */
    public void showFamilyColorGradient(int familyID) {
        SwingUtilities.invokeLater(familyAverageColorGradients.get(familyID));
    }//end showFamilyColorGradient

    /**
     * Gets the average color of all {@link GraphTuple} objects.
     *
     * @return The averaged color from all {@link GraphTuple} objects
     */
    public Color getAverageColor() {
        int redVal = 0;
        int greenVal = 0;
        int blueVal = 0;
        int numOfConnections = 0;
        for (GraphNode gn : nodes) {
            for (int i = 0; i < gn.getNumberOfConnections(); i++) {
                GraphTuple gt = gn.getConnection(i);
                if (!gt.isEdge(this)) {
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

    //Note: this won't be accurate if the matrix isn't rectangular
    /**
     * Gets the width of a rectangular {@link GraphNode} matrix.
     *
     * @return The width of the {@link GraphNode} matrix
     */
    public int getGraphWidth() {
        return matrix[0].length;
    }//end getGraphWidth

    //Note: this won't be accurate if the matrix isn't rectangular
    /**
     * Gets the height of a rectangular {@link GraphNode} matrix.
     *
     * @return The height of the {@link GraphNode} matrix
     */
    public int getGraphHeight() {
        return matrix.length;
    }//end getGraphHeight

    /**
     * Gets the matrix of {@link GraphNode}s.
     *
     * @return The matrix of {@link GraphNode}s
     */
    public GraphNode[][] getMatrix() {
        return matrix;
    }//end getMatrix

    /**
     * Gets an {@link ArrayList} of all {@link GraphNode}s present in the graph
     *
     * @return The {@link ArrayList} containing all {@link GraphNode}s in the
     * graph
     */
    public ArrayList<GraphNode> getGraphNodes() {
        return nodes;
    }//end getGraphNodes

    /**
     * Gets a node at specific xy coordinates in the matrix.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The node at the coordinates (x,y)
     */
    public GraphNode getNode(int x, int y) {
        return matrix[y][x];
    }//end getNode

    /**
     * Sets the trim static variable.
     *
     * @param in The new value for trim
     */
    public void setTrim(boolean in) {
        TRIM = in;
    }//end setTrim

    /**
     * Sets the consume static variable
     *
     * @param in The new value for consume
     */
    public void setConsume(boolean in) {
        CONSUME = in;
    }//end setConsume

    /**
     * Sets the mutate_color static variable
     *
     * @param in The new value for mutate_color
     */
    public void setMutateColor(boolean in) {
        MUTATE_COLOR = in;
    }//end setMutateColor

    /**
     * Sets the mutate health static variable
     *
     * @param in The new value for mutate_health
     */
    public void setMutateHealth(boolean in) {
        MUTATE_HEALTH = in;
    }//end setMutateHealth

    /**
     * Gets the current number of steps that have been taken
     *
     * @return The number of steps taken
     */
    public long getStepCount() {
        return stepCount;
    }//end getStepCount

    public void setStepCount(long in) {
        stepCount = in;
    }//end setStepCount

    /**
     * Sets the number of steps in a reproduction cycle. Only useful if there is
     * no mutation enabled
     *
     * @param in The number of steps in the cycle
     */
    public void setCycleBase(long in) {
        cycleBase = in;
    }//end setCycleBase

    /**
     * Gets the number of steps in each reproduction cycle. Only useful if
     * mutation is not enabled
     *
     * @return The number of step in the reproduction cycle
     */
    public long getCycleBase() {
        return cycleBase;
    }//end getCycleBase

    /**
     * Gets the number of cycles that have elapsed.
     *
     * @return The number of cycles run
     */
    public long getCycleCount() {
        return cycleCount;
    }//end getCycleCount

    public void setCycleCount(long in) {
        cycleCount = in;
    }//end setCycleCount

    /**
     * Gets if the last step produced any new lines.
     *
     * @return True if there has been no new growth, False if not
     */
    public boolean getNoNewGrowth() {
        return noNewGrowth;
    }//end getCycleStarted

    /**
     * Gets if {@link Graph#generateSeeds()} has been run
     *
     * @return True if {@link Graph#generateSeeds()} has been run, False if not
     */
    public boolean isSeeded() {
        return seeded;
    }//end isSeeded

    /**
     * Sets the value of the seeded boolean.
     *
     * @param in The new value of seeded
     */
    public void setSeeded(boolean in) {
        seeded = in;
    }//end setSeeded

    /**
     * Gets the setting determining if single-line seeds are possible.
     *
     * @return The value of seed1
     */
    public boolean getSeed1() {
        return seed1;
    }//end getSeed1

    /**
     * Sets the variable determining if single-line seeds are possible.
     *
     * @param in The new value of seed1
     */
    public void setSeed1(boolean in) {
        seed1 = in;
    }//end setSeed1

    /**
     * Gets the setting determining if single-pair seeds are possible.
     *
     * @return The value of seed2
     */
    public boolean getSeed2() {
        return seed2;
    }//end getSeed2

    /**
     * Sets the variable determining if single-pair seeds are possible.
     *
     * @param in The new value of seed2
     */
    public void setSeed2(boolean in) {
        seed2 = in;
    }//end setSeed2

    /**
     * Gets the setting determining if two-pair seeds are possible.
     *
     * @return The value of seed4
     */
    public boolean getSeed4() {
        return seed4;
    }//end getSeed4

    /**
     * Sets the variable determining if two-pair seeds are possible.
     *
     * @param in The new value of seed4
     */
    public void setSeed4(boolean in) {
        seed4 = in;
    }//end setSeed4

    /**
     * Gets the setting determining if four-pair seeds are possible.
     *
     * @return The value of seed8
     */
    public boolean getSeed8() {
        return seed8;
    }//end getSeed1

    /**
     * Sets the variable determining if four-pair seeds are possible.
     *
     * @param in The new value of seed2
     */
    public void setSeed8(boolean in) {
        seed8 = in;
    }//end setSeed1

    /**
     * Sets the first node when connecting two nodes.
     *
     * @param in The first user-selected node for connection
     */
    public void setConnectA(GraphNode in) {
        connectA = in;
    }//end setConnectA

    /**
     * Returns the first selected node when connecting two nodes.
     *
     * @return The first user-selected node for connection
     */
    public GraphNode getConnectA() {
        return connectA;
    }//end getConnectA

    /**
     * Sets the second node when connecting two nodes.
     *
     * @param in The second user-selected node for connection
     */
    public void setConnectB(GraphNode in) {
        connectB = in;
    }//end setConnectB

    /**
     * Returns the second selected node when connecting two nodes.
     *
     * @return The second user-selected node for connection
     */
    public GraphNode getConnectB() {
        return connectB;
    }//end getConnectB

    /**
     * Returns the camera object responsible for taking pictures.
     *
     * @return The camera object
     */
    public Camera getCamera() {
        return camera;
    }//end getCamera

    /**
     * Returns the current number of active line families.
     *
     * @return The current number of active line families
     */
    public int getFamilyCount() {
        return familyCount;
    }//end getFamilyCount

    /**
     * Returns the last node hovered over by the user.
     *
     * @return The last node hovered over by the user
     */
    public GraphNode getLastHovered() {
        return lastHovered;
    }//end getLastHovered

    /**
     * Records the last node hovered over by the user.
     *
     * @param in The last node hovered over by the user
     */
    public void setLastHovered(GraphNode in) {
        lastHovered = in;
    }//end setLastHovered

    /**
     * Returns the name of the next test to be run, which will also be stored in
     * the local database.
     *
     * @return The name of the next test
     */
    public String getTestName() {
        return testName;
    }//end getTestName

    /**
     * Sets the test name of the next test to be run, also to be stored in the
     * local database.
     *
     * @param in The name of the next test
     */
    public void setTestName(String in) {
        testName = in;
    }//end setTestName

    /**
     * Gets the first node considered for a line creation event.
     *
     * @return The first node considered for a line creation event
     */
    public GraphNode getLineEventNode1() {
        return lineEventNode1;
    }//end getLineEventNode1

    /**
     * Sets the first node considered for a line creation event.
     *
     * @param in The first node considered for a line creation event
     */
    public void setLineEventNode1(GraphNode in) {
        lineEventNode1 = in;
    }//end setLineEventNode1

    /**
     * Gets the second node considered for a line creation event.
     *
     * @return The second node considered for a line creation event
     */
    public GraphNode getLineEventNode2() {
        return lineEventNode2;
    }//end getLineEventNode1

    /**
     * Sets the second node considered for a line creation event.
     *
     * @param in The second node considered for a line creation event
     */
    public void setLineEventNode2(GraphNode in) {
        lineEventNode2 = in;
    }//end setLineEventNode1

    /**
     * Gets the value of the toggle determining if pictures should be saved on
     * an interval.
     *
     * @return The value of pictureSaveToggle
     */
    public boolean getPictureSaveToggle() {
        return pictureSaveToggle;
    }//end getPictureSaveToggle

    /**
     * Sets the value for the toggle determining if pictures should be saved on
     * an interval.
     *
     * @param in The new value of pictureSaveToggle
     */
    public void setPictureSaveToggle(boolean in) {
        pictureSaveToggle = in;
    }//end getPictureSaveToggle

    /**
     * Gets the value of the toggle determining if the graph should continue
     * taking steps after taking a picture on an interval.
     *
     * @return The value of picturePauseToggle
     */
    public boolean getPicturePauseToggle() {
        return picturePauseToggle;
    }//end getPictureSaveToggle

    /**
     * Sets the value for the toggle determining if the graph should continue
     * taking steps after taking a picture on an interval.
     *
     * @param in The new value of picturePauseToggle
     */
    public void setPicturePauseToggle(boolean in) {
        picturePauseToggle = in;
    }//end getPictureSaveToggle

    /**
     * Sets the growth mode of the graph. Translates an int to
     * {@link GrowthMode}, and prints an error if the int is an invalid value.
     *
     * @param in The new growth mode for the graph
     */
    public void setMode(int in) {
        switch (in) {
            case 0:
                MODE = GrowthMode.REGULAR;
                break;
            case 1:
                MODE = GrowthMode.MUTATION;
                break;
            case 2:
                MODE = GrowthMode.DEPTH_BASED;
                break;
            case 3:
                MODE = GrowthMode.GROWTH;
                break;
            default:
                System.err.println("Invalid mode!");
        }//end switch
    }//end setMode

    /**
     * Returns an int corresponding to the {@link GrowthMode} for the graph.
     *
     * @return An integer corresponding to the {@link GrowthMode} the graph is
     * currently running
     */
    public int getMode() {
        int out = 0;
        switch (MODE) {
            case REGULAR:
                out = 0;
                break;
            case MUTATION:
                out = 1;
                break;
            case DEPTH_BASED:
                out = 2;
                break;
            case GROWTH:
                out = 3;
                break;
        }//end switch
        return out;
    }//end getMode
}//end Graph
