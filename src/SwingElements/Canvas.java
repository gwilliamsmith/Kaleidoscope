package SwingElements;

import Listeners.BaseKeyListener;
import Listeners.CanvasMouseListener;
import graphvisualizer.Graph;
import graphvisualizer.GraphNode;
import graphvisualizer.GraphTuple;
import graphvisualizer.GraphTupleInfo;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Object responsible for all drawing on the screen, as well as generating
 * pictures for the {@link Camera} to record.
 */
public class Canvas extends JPanel {

    private final Base ref;                                                   //Base object, used for access to other objects as needed
    public int windowX = 0;                                             //X axis modifier for any screen objects that are not statically placed
    public int windowY = 0;                                             //Y axis modifier for any screen objects that are not statically placed

    //Spacing and Size for grid points
    private int minSpacing = 10;                                        //Minimum amount of spacing allowed between two nodes (in pixels)
    private int minPointSize = 2;                                       //Minimum size (length of width/height) of a graph node (in pixels)
    private int spacing = 10;                                           //Current amount of spacing between two nodes (in pixels)
    private int pointSize = 2;                                          //Current size (length of width/height) of a graph node (in pixels)
    private int zoomLevel = 0;                                          //Level of zoom
    private int prevZoomLevel = zoomLevel;                              //Previous value of zoomLevel, used to move the mouse when zooming
    private boolean resized = false;

    private boolean drag = true;                                        //Determines if the drag to reposition is enabled/disabled

    private Color tempColor = null;                                     //Color for next line(black if null)

    private BufferedImage gridPicture = null;                           //Picture of just the grid contents, not the entire window

    private GraphTupleInfo gtiStorage = new GraphTupleInfo();           //Info for next line (should probably be moved to the Graph class)

    public static boolean curveEnabled = false;                         //Toggles drawing of lines or curves between nodes

    private int curveMaxSeverity = spacing / 4;                         //The maximum curve for curved lines

    private static final int MAX_ZOOM_LEVEL = 7;                        //The maximum amount of zoom the canvas can have

    public static boolean DEBUG = false;                                //Master toggle for debug features

    public static boolean DEBUG_GRID_LINES = false;                     //Debug toggle for grey lines drawn every five pixels along x and y axis
    public static boolean DEBUG_CENTER_LINES = false;                   //Debug toggle for red lines drawn down center of window on x and y axis
    public static boolean DEBUG_BOUNDING_RECTANGLE = false;             //Debug toggle for showing the bounding rectangle in cyan
    public static boolean DEBUG_MOUSE_COORDINATES = false;              //Debug toggle for showing the mouse coordinates in top-left corner

    public static int BOUNDING_RECTANGLE_WIDTH = 2550;                  //Width of bounding rectangle
    public static int BOUNDING_RECTANGLE_HEIGHT = 2550;                 //Height of bounding rectangle

    private int mouseX = 0;                                             //The mouse's x location
    private int mouseY = 0;                                             //The mouse's y location

    private BufferedImage nodeImage = null;
    private BufferedImage connnectionsImage = null;

    ;

    /**
     * Constructor.
     *
     * @param in {@link Base} object, used to access other objects as needed
     */
    public Canvas(Base in) {
        ref = in;
        setBackground(Color.white);
        setSize(ref.getWidth(), ref.getHeight());
        CanvasMouseListener mouseListener = new CanvasMouseListener(ref);
        addMouseListener(mouseListener);
        addMouseWheelListener(mouseListener);
        addMouseMotionListener(mouseListener);
        addKeyListener(new BaseKeyListener(ref));
    }//end constructor

    @Override
    /**
     * Overridden paint method from {@link JPanel).
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(producePicture(), 0, 0, null);
        ref.updateAverageColor();
    }//end paint

    /**
     * Resizes the grid (if needed) to match current settings, then draws all
     * grid nodes and lines.
     */
    public void paintGraph(Graphics g, boolean windowMod) {
        Graphics2D g2 = (Graphics2D) g;
        resizeGrid();
        drawGrid(g2, windowMod);
    }//end paint

    /**
     * Generates a picture of the window.
     *
     * @return A {@link BufferedImage} of the window
     */
    private BufferedImage produceGridPicture() {
        drawNodes();
        drawConnections();
        BufferedImage out = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2 = out.createGraphics();
        resizeGrid();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawGrid(g2, true);
        return out;
    }//end produceGridPicture

    /**
     * Draws the grid using the {@link Graphics2D} object provided.
     *
     * @param g2 The {@link Graphics2D} object to draw the grid.
     */
    private void drawGrid(Graphics2D g2, boolean windowMod) {
        if (DEBUG) {
            drawDebug(g2);
        }//end if
        GraphNode firstNode = ref.getGraph().getGraphNodes().get(0);
        g2.drawImage(nodeImage, ((int) firstNode.getX() + windowX * (windowMod ? 1 : 0)), ((int) firstNode.getY() + windowY * (windowMod ? 1 : 0)), null);
        g2.drawImage(connnectionsImage, ((int) firstNode.getX() + windowX * (windowMod ? 1 : 0)), ((int) firstNode.getY() + windowY * (windowMod ? 1 : 0)), null);
    }//end drawGrid

    /**
     * Draws enabled debug options.
     *
     * @param g2 The {@link Graphics 2D} object to do the drawing
     */
    private void drawDebug(Graphics2D g2) {
        drawDebugGridLines(g2);
        drawDebugCenterLines(g2);
        drawDebugBoundingRectangle(g2);
        drawDebugMouseCoordinates(g2);
    }//end drawDebug

    /**
     * Draws light-grey lines every five pixels, creating a grid.
     *
     * @param g2 The {@link Graphics 2D} object to do the drawing
     */
    private void drawDebugGridLines(Graphics2D g2) {
        if (DEBUG_GRID_LINES) {
            for (int i = 0; i < getWidth(); i += 5) {
                g2.setColor(Color.lightGray);
                g2.drawLine(i, 0, i, getHeight());
            }//end for
            for (int i = 0; i < getHeight(); i += 5) {
                g2.setColor(Color.lightGray);
                g2.drawLine(0, i, getWidth(), i);
            }//end for
        }//end if
    }//end drawDebugGridLines

    /**
     * Draws red lines across the center of the window.
     *
     * @param g2 The {@link Graphics 2D} object to do the drawing
     */
    private void drawDebugCenterLines(Graphics2D g2) {
        if (DEBUG_CENTER_LINES) {
            g2.setColor(Color.RED);
            g2.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
            g2.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        }//end if
    }//end drawDebugCenterLines

    /**
     * Draws the bounding rectangle, according to it's given size.
     *
     * @param g2 The {@link Graphics 2D} object to do the drawing
     */
    private void drawDebugBoundingRectangle(Graphics2D g2) {
        if (DEBUG_BOUNDING_RECTANGLE) {
            g2.setColor(Color.CYAN);
            Rectangle boundingRectangle = new Rectangle(0, 0, BOUNDING_RECTANGLE_WIDTH, BOUNDING_RECTANGLE_HEIGHT);
            g2.drawRect(boundingRectangle.x + windowX,
                    boundingRectangle.y + windowY,
                    boundingRectangle.width,
                    boundingRectangle.height);
        }//end if
    }//end drawDebugBoundingRectangle

    /**
     * Draws the mouse's x and y coordinates on the screen in the lower left
     * corner.
     *
     * @param g2 The {@link Graphics 2D} object to do the drawing
     */
    private void drawDebugMouseCoordinates(Graphics2D g2) {
        if (DEBUG_MOUSE_COORDINATES) {
            g2.setColor(Color.black);
            drawString("(" + mouseX + "," + mouseY + ")", 0, 1, g2);
        }//end if
    }//end drawDebugMouseCoordinates

    private void drawNodes() {
        int columns = ref.getGraph().getMatrix()[0].length;
        int rows = ref.getGraph().getMatrix().length;
        nodeImage = new BufferedImage((((columns) * pointSize) + ((columns - 1) * spacing) + pointSize / 2),
                (((rows) * pointSize) + ((rows - 1) * spacing)), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g3 = nodeImage.createGraphics();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < ref.getGraph().getGraphNodes().size(); i++) {
                    GraphNode gn = ref.getGraph().getGraphNodes().get(i);
                    if (!gn.getColor().equals(Color.WHITE)) {
                        g3.setColor(gn.getColor());
                        /* Food isn't used right now, so this is commented out so as to reduce # of method calls
                         if (gn.getFood() <= 0) {
                         g2.setColor(Color.WHITE);
                         }//end if
                         */
                        g3.fillRect(gn.x + pointSize / 2, gn.y + pointSize / 2, gn.height, gn.width);
                    }//end if
                }//end for
            }
        });
    }//end drawNodes

    private void drawConnections() {
        int columns = ref.getGraph().getMatrix()[0].length;
        int rows = ref.getGraph().getMatrix().length;
        connnectionsImage = new BufferedImage((((columns) * pointSize) + ((columns - 1) * spacing)),
                (((rows) * pointSize) + ((rows - 1) * spacing)), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g3 = connnectionsImage.createGraphics();
        g3.setStroke(new BasicStroke(Math.max(zoomLevel, 1)));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < ref.getGraph().getGraphNodes().size(); i++) {
                    GraphNode gn = ref.getGraph().getGraphNodes().get(i);
                    for (int j = 0; j < gn.getNumberOfConnections(); j++) {
                        GraphTuple gt = gn.getConnection(j);
                        if (gt.isEdge(ref.getGraph()) || !curveEnabled) {
                            if (!gt.redundant) {
                                drawLine(g3, gt, false);
                            }//end if
                        }//end if
                        else {
                            if (!gt.redundant) {
                                drawCurve(g3, gt, false);
                            }//end if
                        }//end else
                    }//end for
                }//end for
            }
        });
    }//end drawConnections

    /**
     * Draws an individual {@link GraphTuple} line.
     *
     * @param g2 The {@link Graphics 2D} object to do the drawing
     * @param gt The {@link GraphTuple} object to draw
     * @param windowMod Boolean determining if the drawing should be offset by
     * the user repositioning the grid - this will be removed soon
     */
    private void drawLine(Graphics2D g2, GraphTuple gt, boolean windowMod) {
        g2.setColor(gt.getColor());
        GraphNode n1 = gt.getFromLocation();
        GraphNode n2 = gt.getToLocation();
        g2.drawLine(n1.x /*+ windowX * windowMultiplier*/ + n1.width / 2 + pointSize / 2,
                n1.y /*+ windowY * windowMultiplier*/ + n1.height / 2 + pointSize / 2,
                n2.x /*+ windowX * windowMultiplier*/ + n2.width / 2 + pointSize / 2,
                n2.y /*+ windowY * windowMultiplier*/ + n2.height / 2 + pointSize / 2);
    }//end drawLine

    //TODO: Clean this up
    /**
     * Draws an individual {@link GraphTuple} curve.
     *
     * @param g2 The {@link Graphics 2D} object to do the drawing
     * @param gt The {@link GraphTuple} object to draw
     * @param windowMod Boolean determining if the drawing should be offset by
     * the user repositioning the grid - this will be removed soon
     */
    private void drawCurve(Graphics2D g2, GraphTuple gt, boolean windowMod) {
        int windowMultiplier = windowMod ? 1 : 0;
        g2.setColor(gt.getColor());
        GraphNode n1 = gt.getFromLocation();
        GraphNode n2 = gt.getToLocation();
        int xdif = n1.getJLoc() - n2.getJLoc();
        int ydif = n1.getILoc() - n2.getILoc();
        QuadCurve2D curve = new QuadCurve2D.Double();
        if (xdif == 0 && ydif == 1) {
            curve = new QuadCurve2D.Double(n1.x + windowX * windowMultiplier + n1.width / 2 + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ + n1.height / 2 + pointSize / 2,
                    n1.x /*+ windowX * windowMultiplier*/ + gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ - curveMaxSeverity + pointSize / 2,
                    n2.x /*+ windowX * windowMultiplier*/ + n2.width / 2 + pointSize / 2,
                    n2.y /*+ windowY * windowMultiplier*/ + n2.height / 2 + pointSize / 2);
        }//end if
        else if (xdif == 0 && ydif == -1) {
            curve = new QuadCurve2D.Double(n1.x /*+ windowX * windowMultiplier*/ + n1.width / 2 + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ + n1.height / 2 + pointSize / 2,
                    n1.x /*+ windowX * windowMultiplier*/ + gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ + curveMaxSeverity + pointSize / 2,
                    n2.x /*+ windowX * windowMultiplier*/ + n2.width / 2 + pointSize / 2,
                    n2.y /*+ windowY * windowMultiplier*/ + n2.height / 2 + pointSize / 2);
        }//end else if
        else if (xdif == 1 && ydif == 0) {
            curve = new QuadCurve2D.Double(n1.x /*+ windowX * windowMultiplier*/ + n1.width / 2 + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ + n1.height / 2 + pointSize / 2,
                    n1.x /*+ windowX * windowMultiplier*/ - curveMaxSeverity + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ + gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity + pointSize / 2,
                    n2.x /*+ windowX * windowMultiplier*/ + n2.width / 2 + pointSize / 2,
                    n2.y /*+ windowY * windowMultiplier*/ + n2.height / 2 + pointSize / 2);
        }//end else if
        else if (xdif == -1 && ydif == 0) {
            curve = new QuadCurve2D.Double(n1.x /*+ windowX * windowMultiplier*/ + n1.width / 2 + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ + n1.height / 2 + pointSize / 2,
                    n1.x /*+ windowX * windowMultiplier*/ + curveMaxSeverity + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ + gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity + pointSize / 2,
                    n2.x /*+ windowX * windowMultiplier*/ + n2.width / 2 + pointSize / 2,
                    n2.y /*+ windowY * windowMultiplier*/ + n2.height / 2 + pointSize / 2);
        }//end else if
        else if (xdif == 1 && ydif == -1) {
            curve = new QuadCurve2D.Double(n1.x /*+ windowX * windowMultiplier*/ + n1.width / 2 + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ + n1.height / 2 + pointSize / 2,
                    n1.x /*+ windowX * windowMultiplier*/ /*- spacing / 2*/ + gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ /*- spacing / 2*/ - gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity + pointSize / 2,
                    n2.x /*+ windowX * windowMultiplier*/ + n2.width / 2 + pointSize / 2,
                    n2.y /*+ windowY * windowMultiplier*/ + n2.height / 2 + pointSize / 2);
        }//end else if
        else if (xdif == -1 && ydif == 1) {
            curve = new QuadCurve2D.Double(n1.x /*+ windowX * windowMultiplier*/ + n1.width / 2 + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ + n1.height / 2 + pointSize / 2,
                    n1.x /*+ windowX * windowMultiplier*/ + /*spacing / 2 +*/ gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ + /*spacing / 2 +*/ gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity + pointSize / 2,
                    n2.x /*+ windowX * windowMultiplier*/ + n2.width / 2 + pointSize / 2,
                    n2.y /*+ windowY * windowMultiplier*/ + n2.height / 2 + pointSize / 2);
        }//end else if
        else if (xdif == 1 && ydif == 1) {
            curve = new QuadCurve2D.Double(n1.x /*+ windowX * windowMultiplier*/ + n1.width / 2 + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ + n1.height / 2 + pointSize / 2,
                    n1.x /*+ windowX * windowMultiplier*/ /*- spacing / 2*/ - gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ + /*spacing / 2 +*/ gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity + pointSize / 2,
                    n2.x /*+ windowX * windowMultiplier*/ + n2.width / 2 + pointSize / 2,
                    n2.y /*+ windowY * windowMultiplier*/ + n2.height / 2 + pointSize / 2);
        }//end else if
        else if (xdif == -1 && ydif == -1) {
            curve = new QuadCurve2D.Double(n1.x /*+ windowX * windowMultiplier*/ + n1.width / 2 + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ + n1.height / 2 + pointSize / 2,
                    n1.x /*+ windowX * windowMultiplier*/ + /*spacing / 2 +*/ gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity + pointSize / 2,
                    n1.y /*+ windowY * windowMultiplier*/ /*- spacing / 2*/ - gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity + pointSize / 2,
                    n2.x /*+ windowX * windowMultiplier*/ + n2.width / 2 + pointSize / 2,
                    n2.y /*+ windowY * windowMultiplier*/ + n2.height / 2 + pointSize / 2);
        }//end else if
        g2.draw(curve);
    }//end drawCurve

    /**
     * Creates the picture to be drawn on the {@link Canvas} object.
     *
     * @return A {@link BufferedImage} with the gird, and any descriptive
     * strings needed
     */
    public BufferedImage producePicture() {
        BufferedImage picture = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = picture.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        paintGraph(g, true);
        gridPicture = produceGridPicture();
        drawString("Steps taken: " + ref.getGraph().getStepCount(), 0, 4, g);
        if (ref.getGraph().getCycleBase() > 0) {
            drawString("Cycles: " + ref.getGraph().getCycleCount(), 1, 4, g);
        }//end if
        GraphNode lastHovered = ref.getGraph().getLastHovered();
        if (lastHovered != null) {
            drawString("(" + lastHovered.getJLoc() + "," + lastHovered.getILoc() + ")", 0, 3, g);
        }//end if
        g.dispose();
        return picture;
    }//end producePicture

    /**
     * Produces a {@link BufferedImage} that is only as large as needed,
     * trimming excess whitespace from a given {@link BufferedImage}.
     *
     * @param in The {@link BufferedImage} to be trimmed
     * @return The trimmed {@link BufferedImage}
     */
    public BufferedImage produceTrimmedImage() {
        int columns = ref.getGraph().getMatrix()[0].length;
        int rows = ref.getGraph().getMatrix().length;
        BufferedImage out = new BufferedImage((((columns - 1) * pointSize) + ((columns - 1) * spacing)),
                (((rows - 1) * pointSize) + ((rows - 1) * spacing)), BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = out.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, out.getWidth(), out.getHeight());
        drawGrid((Graphics2D) g, false);
        return out;
    }//endGetTrimmedImage

    /**
     * Changes grid nodes to match new point size and spacing settings.
     */
    public void resizeGrid() {
        if (resized) {
            adjustZoom();
            Graph graph = ref.getGraph();
            for (int i = 0, xSpace = -(pointSize / 2); i < graph.getMatrix().length; i++, xSpace += (spacing + pointSize)) {
                for (int j = 0, ySpace = -(pointSize / 2); j < graph.getMatrix()[i].length; j++, ySpace += (spacing + pointSize)) {
                    GraphNode temp = graph.getMatrix()[i][j];
                    temp.x = xSpace;
                    temp.y = ySpace;
                    temp.width = pointSize;
                    temp.height = pointSize;
                }//end for
            }//end for
            resized = false;
        }//end if
    }//end resizeGrid

    /*
     TODO:
     Clean this up:
     Use static variables for corner (Maybe an enum?)
     lineOffset can be done better
     Implement logic for other corner
     */
    /**
     * Draws a given string in a corner, using an offset.
     *
     * @param in The string to be drawn
     * @param lineOffset The offset for the line to be drawn
     * @param corner The corner to draw the string in (1-Top left, 2-top right,
     * 3-bottom left, 4-bottom right)
     * @param g The {@link Graphics} object drawing the screen
     */
    private void drawString(String in, int lineOffset, int corner, Graphics g) {
        g.setColor(Color.black);
        FontMetrics fontSize = g.getFontMetrics();
        int textWidth = fontSize.stringWidth(in);
        if (corner == 1) {
            g.drawString(in, 5, (fontSize.getHeight() * (lineOffset + 1)));
        }//end if
        else if (corner == 2) {

        }//end else if
        else if (corner == 3) {
            g.drawString(in, 5, this.getHeight() - (fontSize.getHeight() * (lineOffset)) - 5);
        }//end else if
        else if (corner == 4) {
            g.drawString(in, this.getWidth() - textWidth - 5, this.getHeight() - (fontSize.getHeight() * (lineOffset)) - 5);
        }//end else if
    }//end drawString

    /**
     * Increments the zoom level, if it is not already at the maximum value.
     */
    public void increaseZoomLevel() {
        prevZoomLevel = zoomLevel;
        zoomLevel = zoomLevel < MAX_ZOOM_LEVEL ? zoomLevel + 1 : zoomLevel;
        resized = prevZoomLevel != zoomLevel;
        ref.getSettingsManager().writeSettings();
    }//end increaseZoomLevel

    /**
     * Decrements the zoom level, if the grid is not already zoomed all the way
     * out.
     */
    public void decreaseZoomLevel() {
        prevZoomLevel = zoomLevel;
        zoomLevel = (pointSize == 1 && spacing == 1) ? zoomLevel : zoomLevel - 1;
        resized = prevZoomLevel != zoomLevel;
        ref.getSettingsManager().writeSettings();
    }//end decreaseZoomLevel

    /**
     * Sets the zoom level at a specific value, within a maximum value and 0.
     *
     * @param in The new value for zoom level
     */
    public void setZoomLevel(int in) {
        prevZoomLevel = zoomLevel;
        zoomLevel = (Math.max(minPointSize + (in * 2), 1) == 1 && Math.max(minSpacing + (zoomLevel * 4), 1) == 1) ? zoomLevel : in;
        zoomLevel = zoomLevel > MAX_ZOOM_LEVEL ? MAX_ZOOM_LEVEL : zoomLevel;
        resized = prevZoomLevel != zoomLevel;
    }//end setZoomLevel

    /**
     * Returns the current zoom level.
     *
     * @return The zoom level
     */
    public int getZoomLevel() {
        return zoomLevel;
    }//end getZoomLevel

    /**
     * Sets the new point size and spacing, using the zoom level.
     */
    public void adjustZoom() {
        pointSize = Math.max(minPointSize + (zoomLevel * 2), 1);
        spacing = Math.max(minSpacing + (zoomLevel * 4), 1);
    }//end adjustZoom

    /**
     * Returns the current X axis modifier for object drawing.
     *
     * @return The value of windowX
     */
    public int getWindowX() {
        return windowX;
    }//end getWindowX

    /**
     * Modifies windowX.
     *
     * @param mod The amount to change windowX by
     */
    public void modifyWindowX(int mod) {
        windowX = windowX + mod;
    }//end modifyWindowX

    /**
     * Resets windowX, so the grid is in its initial x-axis location.
     */
    public void resetWindowX() {
        windowX = 0;
    }//end resetWindowX

    /**
     * Returns the current Y axis modifier for object drawing.
     *
     * @return The value of windowY
     */
    public int getWindowY() {
        return windowY;
    }//end getWindowY

    /**
     * Modifies windowY.
     *
     * @param mod The amount to change windowY by
     */
    public void modifyWindowY(int mod) {
        windowY = windowY + mod;
    }//end modifyWindowX

    /**
     * Resets windowY, so the grid is in its initial y-axis location.
     */
    public void resetWindowY() {
        windowY = 0;
    }//end resetWindowY

    /**
     * Resets the window coordinate modifiers to 0.
     */
    public void resetCanvasWindow() {
        resetWindowX();
        resetWindowY();
    }//end resetCanvasWindow

    /**
     * Sets the tempColor variable
     *
     * @param c The new color
     */
    public void setTempColor(Color c) {
        tempColor = c;
    }//end setTempColor

    /**
     * Returns tempColor
     *
     * @return The value of tempColor
     */
    public Color getTempColor() {
        return tempColor;
    }//end getTempColor

    /**
     * Sets gtiStorage, the {@link GraphTupleInfo} object describing the next
     * user-placed line.
     *
     * @param in The {@link GraphTupleInfo} object describing the next
     * user-placed line
     */
    public void setGtiStorage(GraphTupleInfo in) {
        gtiStorage = in;
    }//end setGtiStorage

    /**
     * Gets gtiStorage, the {@link GraphTupleInfo} object describing the next
     * user-placed line.
     *
     * @return The {@link GraphTupleInfo} object describing the next user-placed
     * line
     */
    public GraphTupleInfo getGtiStorage() {
        return gtiStorage;
    }//end getGtiStorage

    /**
     * Sets the display size of the graph nodes
     *
     * @param in The new value of pointSize
     */
    public void setPointSize(int in) {
        pointSize = in;
    }//end setPointSize

    /**
     * Returns the display size of the graph nodes
     *
     * @return The value of pointSize
     */
    public int getPointSize() {
        return pointSize;
    }//end getPointSize

    /**
     * Sets the amount of space between graph nodes
     *
     * @param in The new value of spacing
     */
    public void setSpacing(int in) {
        spacing = in;
    }//end setSpacing

    /**
     * Returns the amount of space between graph nodes
     *
     * @return The value of spacing
     */
    public int getSpacing() {
        return spacing;
    }//end getSpacing

    /**
     * Returns the minimum amount of spacing between two nodes.
     *
     * @return The value of minSpacing
     */
    public int getMinSpacing() {
        return minSpacing;
    }//end getMinSpacing

    /**
     * Sets the minimum amount of spacing allowed between two nodes.
     *
     * @param in The new value for minSpacing
     */
    public void setMinSpacing(int in) {
        minSpacing = in;
        spacing = minSpacing;
    }//end setMinSpacing

    /**
     * Returns the minimum allowed size for grid nodes.
     *
     * @return The value of minPointSize
     */
    public int getMinPointSize() {
        return minPointSize;
    }//end getMinPointSize

    /**
     * Sets the minimum allowed size for grid nodes.
     *
     * @param in The new value for minPointSize
     */
    public void setMinPointSize(int in) {
        minPointSize = in;
        pointSize = minPointSize;
    }//end setMinPointSize

    /**
     * Determines if the grid can be moved via mouse dragging.
     *
     * @return The value of drag
     */
    public boolean canDrag() {
        return drag;
    }//end canDrag

    /**
     * Flips the boolean value of drag.
     */
    public void flipDrag() {
        drag = !drag;
    }//end flipDrag

    /**
     * Returns the {@link BufferedImage} of the window.
     *
     * @return the {@link BufferedImage} of the window
     */
    public BufferedImage getGridPicture() {
        if (gridPicture == null) {
            gridPicture = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        }//end if
        return gridPicture;
    }//end getGridPicture

    /**
     * Sets the {@link BufferedImage} of the grid.
     *
     * @param in The new {@link BufferedImage} of the grid
     */
    public void setGridPicture(BufferedImage in) {
        gridPicture = in;
    }//end setGridPicture

    /**
     * Sets the boolean determining if the grid nodes and spacing need to be
     * resized.
     *
     * @param in The new value for the resized variable
     */
    public void setResized(boolean in) {
        resized = in;
    }//end setResized

    /**
     * Returns if curved connections is enabled.
     *
     * @return The value of curveEnabled
     */
    public boolean isCurveEnabled() {
        return curveEnabled;
    }//end isCurveEnabled

    /**
     * Enables curve drawing.
     */
    public void toggleCurveOn() {
        curveEnabled = true;
    }//end toggleCurveOn

    /**
     * Disables curve drawing.
     */
    public void toggleCurveOff() {
        curveEnabled = false;
    }//end toggleCurveOff

    /**
     * Inverts the curveToggle boolean.
     */
    public void toggleCurve() {
        curveEnabled = !curveEnabled;
    }//end toggleCurve

    /**
     * Sets the integer holding the mouse's x location for display in the debug
     * menu.
     *
     * @param in The mouse's x location on screen
     */
    public void setMouseX(int in) {
        mouseX = in;
    }//end setMouseX

    /**
     * Sets the integer holding the mouse's y location for display in the debug
     * menu.
     *
     * @param in The mouse's y location on screen
     */
    public void setMouseY(int in) {
        mouseY = in;
    }//end setMouseY
}//end Canvas
