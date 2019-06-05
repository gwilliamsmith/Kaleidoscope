package graphvisualizer;

import java.awt.Color;
import java.util.Random;

/**
 * Contains information describing a connection between two {@link GraphNode}s.
 */
public class GraphTuple {

    private static int DEPTH_COLOR_INTERVAL = 1;                                //The amount of color to change from generation to generation in depth-based coloring mode
    private int depthColorIndex = 0;                                            //The depth-based coloring index. Resets to 0 when (++depthColorIndex / (255 / DEPTH_COLOR_INTERVAL) < 6) is true

    public static int MUTATION_DIVISOR = 20000;                                 //The maximum value for mutation percentage. TODO: make this editable, and find a reason why I made it 20000

    private GraphNode toLocation;                                               //The node this line connects to
    private GraphNode fromLocation;                                             //The node housing the object
    private int startHealth = 50;                                               //The total amount of steps this line lives for
    private int health = startHealth;                                           //This line's remaining health
    private int decayRate = 1;                                                  //The rate at which this line decays. Currently, will always be set to one
    private int mutatePercentage = 0;                                           //Out of MUTATION_DIVISOR
    private Color color = Color.BLACK;                                          //This line's color
    private int startReproductionClock = 1;                                     //The number of turns it takes for this line to attempt reproducing
    private int reproductionClock = startReproductionClock;                     //The number of turns remaining for this line to attempy reproducing
    private boolean edge = false;                                               //Determines if this line is an edge
    private int family;                                                         //The family ID for this line

    private int curveDirecton = 0;                                              //Determines the direction of a curve between two nodes
    private double curveSeverity = 0;                                           //Determines the severity of a curve between two nodes
    private static boolean curved = false;                                      //Tells other objects if this tuple is curved

    public boolean redundant = false;                                           //Boolean that tells the Canvas if this line should be drawn (redundant lines are created by GraphNode.connect and are lines going in the exact opposite direction)

    public GraphTuple() {
    }

    /**
     * @param to One of the {@link GraphNode} locations for the connection
     * @param from The other {@link GraphNode} location for the connection
     * @param gti Information describing the connection. See
     * {@link GraphTupleInfo}
     */
    public GraphTuple(GraphNode to, GraphNode from, GraphTupleInfo gti) {
        toLocation = to;
        fromLocation = from;
        this.color = gti.color;
        startHealth = gti.startHealth;
        health = startHealth;
        mutatePercentage = gti.mutationPercentage;
        startReproductionClock = gti.reproductionClock;
        reproductionClock = startReproductionClock;
        edge = gti.edge;
        family = gti.family;
        depthColorIndex = gti.depthColorIndex;
    }//end constructor

    /**
     * Generates a {@link GraphTupleInfo} object with the base characteristics
     * of this connection
     *
     * @return {@link GraphTupleInfo} containing the base information about this
     * connection
     */
    GraphTupleInfo generateGTI() {
        GraphTupleInfo out = new GraphTupleInfo(this.startHealth, this.color, this.mutatePercentage, this.startReproductionClock);
        out.family = this.family;
        //Resets the depthColorIndex once it's reached pure red again
        out.depthColorIndex = (++depthColorIndex / (255 / DEPTH_COLOR_INTERVAL) < 6) ? depthColorIndex : 0;
        return out;
    }//end generateGTI

    /**
     * Generates a new {@link GraphTupleInfo} object, with traits different from
     * a given {@link GraphTuple}
     *
     * @param parent The {@link GraphTuple} to base the new
     * {@link GraphTupleInfo} on
     * @return A new {@link GraphTupleInfo} with slightly different traits
     */
    GraphTupleInfo generateMutatedGti(GraphTuple parent) {
        Random rand = new Random();
        GraphTupleInfo out = new GraphTupleInfo(generateMutatedHealth(rand),
                generateMutatedColor(rand),
                parent.getMutatePercentage(),
                parent.getStartReproductionClock());
        //Resets the depthColorIndex once it's reached pure red again
        out.depthColorIndex = (++depthColorIndex / (255 / DEPTH_COLOR_INTERVAL) < 6) ? depthColorIndex : 0;
        out.family = parent.getFamily();
        return out;
    }//end generateMutatedGti

    /**
     * Creates a new color, based on the color of the {@link GraphTuple} given.
     * Only generates a new color if MUTATE_COLOR is true
     *
     * @param parent The {@link GraphTuple} to base the new color on
     * @param rand The {@link Random} object to get random numbers from, to
     * influence color RGB values
     * @return The new color
     */
    private Color generateMutatedColor(Random rand) {
        if (Graph.MUTATE_COLOR) {                                               //Only change RGB values if MUTATE_COLOR is true
            int rInfluence = flipInfluenceCheck(rand.nextInt(51), rand);
            int gInfluence = flipInfluenceCheck(rand.nextInt(51), rand);
            int bInfluence = flipInfluenceCheck(rand.nextInt(51), rand);
            int red = influenceInt(getRed(), rInfluence, rand);
            int green = influenceInt(getGreen(), gInfluence, rand);
            int blue = influenceInt(getBlue(), bInfluence, rand);
            red = validateColor(red);                                           //Ensure the new red value is within 0-255
            green = validateColor(green);                                       //Ensure the new green value is within 0-255
            blue = validateColor(blue);                                         //Ensure the new blue value is within 0-255
            return new Color(red, green, blue);
        }//end if
        else {                                                                  //If MUTATE_COLOR isn't true, return the original color
            return color;
        }//end else
    }//end generateMutatedColor

    /**
     * Creates a new starting health value, based on the starting health of the
     * {@link GraphTuple} given. Only generates a new value if MUTATE_HEALTH is
     * true
     *
     * @param parent The {@link GraphTuple} to base the new starting health on
     * @param rand The {@link Random} object to get random numbers from, to
     * influence the starting health value
     * @return The new starting health value
     */
    private int generateMutatedHealth(Random rand) {
        if (Graph.MUTATE_HEALTH && startHealth > 1) {                           //Only modify the starting health value if MUTATE_HEALTH is true, and the partent GraphTuple has enough health
            double deviation = .1;
            int variance = (int) (startHealth * deviation);                     //The maximum distance the starting health can change
            if (variance <= 1) {
                variance = 1;
            }//end if
            int healthInfluence = flipInfluenceCheck(rand.nextInt(variance + 1), rand);
            int newHealth = influenceInt(startHealth, healthInfluence, rand);
            if (newHealth < 0) {                                                //Ensure the new starting health is not negative
                newHealth = 0;
            }//end if
            return newHealth;
        }//end if
        else {
            return startHealth;
        }//end else
    }//end generateMutatedHealth

    /**
     * Ensures that the red, green, or blue value passed in is between 0 and
     * 255. Values higher than 255 are cut off at 255, and values lower than 0
     * are cut off at 0
     *
     * @param in The value to be checked
     * @return The validated value
     */
    private int validateColor(int in) {
        int out = (in > 255) ? 255 : in;
        out = (out < 0) ? 0 : out;
        return out;
    }//end validateColor

    /**
     * Decides if a change to a value involving an integer should be positive or
     * negative
     *
     * @param in The absolute value of the change in question
     * @param rand The {@link Random} object that generates a boolean
     * @return The new value of the influencing integer
     */
    private int flipInfluenceCheck(int in, Random rand) {
        return rand.nextBoolean() ? in *= -1 : in;
    }//end flipColorInfluence

    /**
     * Potentially modifies an integer trait of a {@link GraphTuple} by a given
     * amount
     *
     * @param in The trait to be modified
     * @param influencer The modifier for the trait
     * @param rand A {@link Random} object, to be used to determine if the trait
     * is actually modified
     * @return The new value for the trait in question
     */
    private int influenceInt(int in, int influencer, Random rand) {
        return rand.nextBoolean() ? in += influencer : in;
    }//end influenceInt

    /**
     * Resets the reproduction clock of this connection to its starting value
     */
    void resetReproductionClock() {
        reproductionClock = startReproductionClock;
    }//end resetReproductionClock

    /**
     * Decrements connection health by the decay rate
     */
    void decay() {
        health -= decayRate;
    }//end decay

    /**
     * Creates new values describing the curve of the connection, and sets
     * curved to true.
     */
    void generateCurve(int direction, double severity) {
        curveDirecton = direction;
        curveSeverity = severity;
        curved = true;
    }//end generateCurve

    /**
     * Generates a new direction value for curveDirection.
     */
    static int generateCurveDirection() {
        Random rand = new Random();
        boolean direction = rand.nextBoolean();
        return direction ? 1 : 0;
    }//end generateCurveDirection

    /**
     * Generates a new value for curve severity, ranging from .1 to 1.0.
     */
    static double generateCurveSeverity() {
        return Math.random() + .1;
    }//end generateCurveSeverity

    /**
     * Sets the color of the connection
     *
     * @param in The color to be used
     */
    public void setColor(Color in) {
        color = in;
    }//end setColor

    /**
     * Sets the color of the connection using RGB values
     *
     * @param rin The red value of the color
     * @param bin The blue value of the color
     * @param gin The green value of the color
     */
    public void setColor(int rin, int gin, int bin) {
        color = new Color(rin, gin, bin);
    }//end setColor

    /**
     * Gets the color of the connection
     *
     * @return The color of the connection
     */
    public Color getColor() {
        return color;
    }//end getColor

    /**
     * Gets the red value for the color of the connection
     *
     * @return The red value of the color for the connection
     */
    public int getRed() {
        return color.getRed();
    }//end getRed

    /**
     * Gets the green value for the color of the connection
     *
     * @return The green value of the color for the connection
     */
    public int getGreen() {
        return color.getGreen();
    }//end getRed

    /**
     * Gets the blue value for the color of the connection
     *
     * @return The blue value of the color for the connection
     */
    public int getBlue() {
        return color.getBlue();
    }//end getRed

    /**
     * Determines if the connection is an edge connection
     *
     * @param graph The graph to check against
     * @return True if both nodes are on the same edge of the provided graph, or
     * the connection has been set as an edge by the user
     */
    public boolean isEdge(Graph graph) {
        return ((graph.nodeIsEdge(toLocation) && graph.nodeIsEdge(fromLocation))
                || edge);
    }//end isEdge

    /**
     * Sets the value for edge
     *
     * @param in The new value for edge
     */
    void setEdge(boolean in) {
        edge = in;
    }//end setEdge

    /**
     * Checks to see if the connection has health remaining
     *
     * @return True if connection health > 0
     */
    boolean isAlive() {
        return (health > 0);
    }//return isAlive

    /**
     * Returns the to location for this connection
     *
     * @return The {@link GraphNode} object stored in toLocation
     */
    public GraphNode getToLocation() {
        return toLocation;
    }//end getLocation

    /**
     * Sets the to location for this connection
     *
     * @param in The {@link GraphNode} to connect to
     */
    void setToLocation(GraphNode in) {
        toLocation = in;
    }//end setToLocation

    /**
     * Returns the from location for this connection
     *
     * @return The {@link GraphNode} object stored in fromLocation
     */
    public GraphNode getFromLocation() {
        return fromLocation;
    }//end fromLocation

    /**
     * Sets the from location for this connection
     *
     * @param in The {@link GraphNode} to connect to
     */
    void setFromLocation(GraphNode in) {
        fromLocation = in;
    }//end setFromLocation

    /**
     * Returns the health this connection begins with. startHealth is also a
     * trait in {@link GraphTupleInfo}
     *
     * @return The startHealth value, used to set the health when the connection
     * is created
     */
    public int getStartHealth() {
        return startHealth;
    }//end getStartHealth

    /**
     * Sets the starting health for the connection
     *
     * @param in The value for startHealth
     */
    void setStartHealth(int in) {
        startHealth = in;
    }//end setStartHealth

    /**
     * Gets the remaining health for the connection
     *
     * @return The number of turns of health the connection has remaining
     */
    public int getHealth() {
        return health;
    }//return getHealth

    /**
     * Sets the remaining health for the connection. This value must be less
     * than or equal to {@link GraphTuple#startHealth}.
     *
     * @param in The new value for the remaining health of this connection
     */
    void setHealth(int in) {
        if (in <= startHealth) {
            health = in;
        }//end if
    }//end setHealth

    /**
     * Gets the probability (out of {@link GraphTuple#MUTATION_DIVISIOR) that the connection will mutate on
     * reproduction
     *
     * @return The chance for mutation (out of {@link GraphTuple#MUTATION_DIVISIOR))
     */
    public int getMutatePercentage() {
        return mutatePercentage;
    }//end getMutatePercentage

    /**
     * Sets the probability (out of {@link GraphTuple#MUTATION_DIVISIOR) that the connection will mutate on
     * reproduction
     *
     * @param in The chance for mutation (out of {@link GraphTuple#MUTATION_DIVISIOR))
     */
    void setMutatePercentage(int in) {
        mutatePercentage = in;
    }//end setMutatePercentage

    /**
     * Gets the number of turns it takes for the connection to reproduce
     *
     * @return The amount of turns for the connection to reproduce
     */
    public int getStartReproductionClock() {
        return startReproductionClock;
    }//end getStartReproductionClock

    /**
     * Sets the number of turns it takes for the connection to reproduce
     *
     * @param in The number of turns it takes for the connection to reproduce
     */
    void setStartReproductionClock(int in) {
        startReproductionClock = in;
    }//end setStartReproductionClock

    /**
     * Gets the remaining turn count for connection reproduction
     *
     * @return The number of turns remaining for the connection to create
     * children.
     */
    public int getReproductionClock() {
        return reproductionClock;
    }//end getReproductionClock

    /**
     * Sets the remaining number of turns for connection reproduction. This
     * number must be less than or equal to the
     * {@link GraphTuple#startReproductionClock}.
     *
     * @param in The number of remaining turns for the connection to reproduce.
     * Cannot be higher than the value for startReproductionClock
     */
    void setReproductionClock(int in) {
        if (in <= startReproductionClock) {
            reproductionClock = in;
        }//end if
    }//end setReproductionClock

    /**
     * Gets the familyID for the connection
     *
     * @return The family number for the connection
     */
    public int getFamily() {
        return family;
    }//end getFamily\

    /**
     * Returns the int determining the direction of the curve.
     *
     * @return The integer determining if the curve goes up, down, or if there's
     * no curve
     */
    public int getCurveDirection() {
        return curveDirecton;
    }//end getCurveDirection

    /**
     * Sets the new value for curve direction.
     *
     * @param in The new value for curve direction
     */
    void setCurveDirection(int in) {
        if (in < -1) {
            in = -1;
        }//end if
        else if (in > 1) {
            in = 1;
        }//end else
        curveDirecton = in;
    }//end setCurveDirection

    /**
     * Returns the severity of a curve to be drawn.
     *
     * @return The percentage of the full curve to be reached
     */
    public double getCurveSeverity() {
        return curveSeverity;
    }//end getCurveSeverity

    /**
     * Sets the severity of curves to be drawn.
     *
     * @param in The percentage of the full curve to be reached
     */
    void setCurveSeverity(double in) {
        if (in > 1) {
            in = 1;
        }//end if
        curveSeverity = in;
    }//end setCurveSeverity

    /**
     * Returns if the connection is curved or not.
     *
     * @return The value of curved
     */
    public boolean isCurve() {
        return curved;
    }//end isCurve

    /**
     * Sets curved, the boolean determining if {@link GraphTuple}s should be
     * drawn as curves.
     *
     * @param in The new value for curved
     */
    void setCurve(boolean in) {
        curved = in;
    }//end setCurve

    /**
     * Gets the color for the connection, based on its depthColorIndex, and
     * DEPTH_COLOR_INTERVAL.
     *
     * @return The calculated color of the connection
     */
    Color getDepthColor() {
        int maxSegments = 255 / DEPTH_COLOR_INTERVAL;
        int segmentNumber = depthColorIndex / maxSegments;
        int segmentStep = depthColorIndex % maxSegments;
        int segmentValue = segmentStep * DEPTH_COLOR_INTERVAL;
        int red = 0;
        int green = 0;
        int blue = 0;
        switch (segmentNumber) {
            case 0:                             //Red -> Yellow
                red = 255;
                green = segmentValue;
                break;
            case 1:                             //Yellow -> Green
                red = 255 - segmentValue;
                green = 255;
                break;
            case 2:                             //Green -> Cyan
                green = 255;
                blue = segmentValue;
                break;
            case 3:                             //Cyan -> Blue
                green = 255 - segmentValue;
                blue = 255;
                break;
            case 4:                             //Blue -> Magenta
                red = segmentValue;
                blue = 255;
                break;
            case 5:                             //Magenta -> Red
                red = 255;
                blue = 255 - segmentValue;
                break;
        }//end switch
        return new Color(red, green, blue);
    }//end getDepthColor

    /**
     * Returns the value of the depth-based color interval
     *
     * @return The value of DEPTH_COLOR_INTERVAL, which is always a factor of
     * 255
     */
    public static int getDepthBasedColorInterval() {
        return DEPTH_COLOR_INTERVAL;
    }//end getDepthBasedColorInterval

    /**
     * Sets the value of the depth-based color interval
     *
     * @param in The new value of DEPTH_COLOR_INTERVAL, which must be a factor
     * of 255
     */
    public static void setDepthBasedColorInterval(int in) {
        int[] factors = {1, 3, 5, 15, 17, 51, 85, 255};
        boolean contains = false;
        for (int i = 0; i < factors.length && !contains; i++) {
            if (in == factors[i]) {
                contains = true;
            }//end if
        }//end for
        if (contains) {
            DEPTH_COLOR_INTERVAL = in;
        }//end if
        else {
            System.err.println(in + " is not a factor of 255!");
        }//end else
    }//end setDepthBasedColorInterval
}//end GraphTuple
