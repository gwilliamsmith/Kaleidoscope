package SwingElements;

import Listeners.BaseKeyListener;
import Listeners.CanvasMouseListener;
import SwingElements.Canvas;
import graphvisualizer.Graph;
import graphvisualizer.GraphNode;
import graphvisualizer.GraphTuple;
import graphvisualizer.GraphTupleInfo;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import javax.swing.JPanel;

/**
 * Object responsible for all drawing on the screen, as well as generating
 * pictures for the {@link Camera} to record.
 */
public class Canvas extends JPanel {

    private Base ref;                                                   //Base object, used for access to other objects as needed
    public int windowX = 0;                                             //X axis modifier for any screen objects that are not statically placed
    public int windowY = 0;                                             //Y axis modifier for any screen objects that are not statically placed

    //Spacing and Size for grid points
    private int minSpacing = 10;                                        //Minimum amount of spacing allowed between two nodes (in pixels)
    private int minPointSize = 2;                                       //Minimum size (length of width/height) of a graph node (in pixels)
    private int spacing = 10;                                           //Current amount of spacing between two nodes (in pixels)
    private int pointSize = 2;                                          //Current size (length of width/height) of a graph node (in pixels)
    private int zoomLevel = 0;                                          //Level of zoom
    private boolean resized = false;

    private boolean drag = true;                                        //Determines if the drag to reposition is enabled/disabled

    private Color tempColor = null;                                     //Color for next line(black if null)

    private BufferedImage gridPicture = null;                           //Picture of just the grid contents, not the entire window

    private GraphTupleInfo gtiStorage = new GraphTupleInfo();           //Info for next line

    public static boolean curveEnabled = false;                          //Toggles drawing of lines or curves between nodes

    private int curveMaxSeverity = spacing / 4;

    private static final int MAX_ZOOM_LEVEL = 7;

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
    public void paintGraph(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        resizeGrid();
        g2.setStroke(new BasicStroke(zoomLevel));
        drawGrid(g2);
    }//end paint

    /**
     * Generates a picture of the window.
     *
     * @return A {@link BufferedImage} of the window
     */
    private BufferedImage produceGridPicture() {
        BufferedImage out = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2 = out.createGraphics();
        resizeGrid();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.setStroke(new BasicStroke(pointSize / 2));
        drawGrid(g2);
        return out;
    }//end produceGridPicture

    /**
     * Draws the grid using the {@link Graphics2D} object provided.
     *
     * @param g2 The {@link Graphics2D} object to draw the grid.
     */
    private void drawGrid(Graphics2D g2) {
        drawNodes(g2);
        drawConnections(g2);
        /*      This draws the testing bounding rectangle 
         g2.setColor(Color.CYAN);
         Rectangle boundingRectangle = ref.getGraph().getBoundingRectangle();
         g2.drawRect(boundingRectangle.x + windowX,
         boundingRectangle.y + windowY,
         boundingRectangle.width,
         boundingRectangle.height);
         */
    }//end drawGrid

    private void drawNodes(Graphics2D g2) {
        for (GraphNode gn : ref.getGraph().getGraphNodes()) {
            g2.setColor(gn.getColor());
            /* Food isn't used right now, so this is commented out so as to reduce # of method calls
             if (gn.getFood() <= 0) {
             g2.setColor(Color.WHITE);
             }//end if
             */
            g2.fillRect(gn.x + windowX, gn.y + windowY, gn.height, gn.width);
        }//end for
    }//end drawNodes

    private void drawConnections(Graphics2D g2) {
        for (GraphNode gn : ref.getGraph().getGraphNodes()) {
            for (int i = 0; i < gn.getNumberOfConnections(); i++) {
                GraphTuple gt = gn.getConnection(i);
                if (gt.isEdge(ref.getGraph()) || !curveEnabled) {
                    if (!gt.redundant) {
                        drawLine(g2, gt);
                    }//end if
                }//end if
                else {
                    if (!gt.redundant) {
                        drawCurve(g2, gt);
                    }//end if
                }//end else
            }//end for
        }//end for
    }//end drawConnections

    private void drawLine(Graphics2D g2, GraphTuple gt) {
        g2.setColor(gt.getColor());
        GraphNode n1 = gt.getFromLocation();
        GraphNode n2 = gt.getToLocation();
        g2.drawLine(n1.x + windowX + n1.width / 2,
                n1.y + windowY + n1.height / 2,
                n2.x + windowX + n2.width / 2,
                n2.y + windowY + n2.height / 2);
    }//end drawLine

    private void drawCurve(Graphics2D g2, GraphTuple gt) {
        g2.setColor(gt.getColor());
        GraphNode n1 = gt.getFromLocation();
        GraphNode n2 = gt.getToLocation();
        int xdif = n1.getJLoc() - n2.getJLoc();
        int ydif = n1.getILoc() - n2.getILoc();
        QuadCurve2D curve = new QuadCurve2D.Double();
        if (xdif == 0 && ydif == 1) {
            curve = new QuadCurve2D.Double(n1.x + windowX + n1.width / 2,
                    n1.y + windowY + n1.height / 2,
                    n1.x + windowX + gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity,
                    n1.y + windowY - curveMaxSeverity,
                    n2.x + windowX + n2.width / 2,
                    n2.y + windowY + n2.height / 2);
        }//end if
        else if (xdif == 0 && ydif == -1) {
            curve = new QuadCurve2D.Double(n1.x + windowX + n1.width / 2,
                    n1.y + windowY + n1.height / 2,
                    n1.x + windowX + gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity,
                    n1.y + windowY + curveMaxSeverity,
                    n2.x + windowX + n2.width / 2,
                    n2.y + windowY + n2.height / 2);
        }//end else if
        else if (xdif == 1 && ydif == 0) {
            curve = new QuadCurve2D.Double(n1.x + windowX + n1.width / 2,
                    n1.y + windowY + n1.height / 2,
                    n1.x + windowX - curveMaxSeverity,
                    n1.y + windowY + gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity,
                    n2.x + windowX + n2.width / 2,
                    n2.y + windowY + n2.height / 2);
        }//end else if
        else if (xdif == -1 && ydif == 0) {
            curve = new QuadCurve2D.Double(n1.x + windowX + n1.width / 2,
                    n1.y + windowY + n1.height / 2,
                    n1.x + windowX + curveMaxSeverity,
                    n1.y + windowY + gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity,
                    n2.x + windowX + n2.width / 2,
                    n2.y + windowY + n2.height / 2);
        }//end else if
        else if (xdif == 1 && ydif == -1) {
            curve = new QuadCurve2D.Double(n1.x + windowX + n1.width / 2,
                    n1.y + windowY + n1.height / 2,
                    n1.x + windowX /*- spacing / 2*/ + gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity,
                    n1.y + windowY /*- spacing / 2*/ - gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity,
                    n2.x + windowX + n2.width / 2,
                    n2.y + windowY + n2.height / 2);
        }//end else if
        else if (xdif == -1 && ydif == 1) {
            curve = new QuadCurve2D.Double(n1.x + windowX + n1.width / 2,
                    n1.y + windowY + n1.height / 2,
                    n1.x + windowX + /*spacing / 2 +*/ gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity,
                    n1.y + windowY + /*spacing / 2 +*/ gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity,
                    n2.x + windowX + n2.width / 2,
                    n2.y + windowY + n2.height / 2);
        }//end else if
        else if (xdif == 1 && ydif == 1) {
            curve = new QuadCurve2D.Double(n1.x + windowX + n1.width / 2,
                    n1.y + windowY + n1.height / 2,
                    n1.x + windowX /*- spacing / 2*/ - gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity,
                    n1.y + windowY + /*spacing / 2 +*/ gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity,
                    n2.x + windowX + n2.width / 2,
                    n2.y + windowY + n2.height / 2);
        }//end else if
        else if (xdif == -1 && ydif == -1) {
            curve = new QuadCurve2D.Double(n1.x + windowX + n1.width / 2,
                    n1.y + windowY + n1.height / 2,
                    n1.x + windowX + /*spacing / 2 +*/ gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity,
                    n1.y + windowY /*- spacing / 2*/ - gt.getCurveDirection() * gt.getCurveSeverity() * curveMaxSeverity,
                    n2.x + windowX + n2.width / 2,
                    n2.y + windowY + n2.height / 2);
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
        paintGraph(g);
        gridPicture = produceGridPicture();
        drawString("Steps taken: " + ref.getGraph().getStepCount(), 0, 4, g);
        if (ref.getGraph().getCycleBase() > 0) {
            drawString("Cycles: " + ref.getGraph().getCycleCount(), 1, 4, g);
        }//end if
        GraphNode lastHovered = ref.getGraph().getLastHovered();
        if (lastHovered != null) {
            drawString("(" + lastHovered.getJLoc() + "," + lastHovered.getILoc() + ")", 0, 3, g);
        }//end if
        /*Center lines for debugging
         g.setColor(Color.RED);
         g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
         g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
         */
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
    public BufferedImage produceTrimmedImage(BufferedImage in) {
        BufferedImage out = new BufferedImage(trimImageWidth(in) + 2, trimImageHeight(in) + 2, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = out.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, out.getWidth(), out.getHeight());
        g.drawImage(in, 0, 0, null);
        return out;
    }//endGetTrimmedImage

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }//end deepCopy

    /**
     * Changes grid nodes to match new point size and spacing settings.
     */
    public void resizeGrid() {
        if (resized) {
            adjustZoom();
            Graph graph = ref.getGraph();
            for (int i = 0, ySpace = 0; i < graph.getMatrix().length; i++, ySpace += (spacing + pointSize)) {
                for (int j = 0, xSpace = 0; j < graph.getMatrix()[i].length; j++, xSpace += (spacing + pointSize)) {
                    GraphNode temp = graph.getMatrix()[i][j];
                    temp.x = xSpace - pointSize / 2;
                    temp.y = ySpace - pointSize / 2;
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
     Implement logic for other corners
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
     * Finds the width of a given image, with excess whitespace trimmed.
     *
     * @param img The image to find the trimmed width for
     * @return Width, in pixels, of the trimmed image
     */
    private int trimImageWidth(BufferedImage img) {
        int height = img.getHeight();
        int width = img.getWidth();
        int trimmedWidth = 0;

        for (int i = 0; i < height; i++) {
            for (int j = width - 1; j >= 0; j--) {
                if (img.getRGB(j, i) != Color.WHITE.getRGB()
                        && j > trimmedWidth) {
                    trimmedWidth = j;
                    break;
                }//end if
            }//end for
        }//end for
        return trimmedWidth;
    }//end getTrimmedWidth

    /**
     * Finds the height of a given image, with excess whitespace trimmed.
     *
     * @param img The image to find the trimmed height for
     * @return Height, in pixels, of the trimmed image
     */
    private int trimImageHeight(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int trimmedHeight = 0;

        for (int i = 0; i < width; i++) {
            for (int j = height - 1; j >= 0; j--) {
                if (img.getRGB(i, j) != Color.WHITE.getRGB()
                        && j > trimmedHeight) {
                    trimmedHeight = j;
                    break;
                }//end if
            }//end for
        }//end for
        return trimmedHeight;
    }//end getTrimmedHeight

    /**
     * Increments the zoom level, if it is not already at the maximum value.
     */
    public void increaseZoomLevel() {
        if (zoomLevel < MAX_ZOOM_LEVEL) {
            zoomLevel++;
            resized = true;
        }//end if
        ref.getSettingsManager().writeSettings();
    }//end increaseZoomLevel

    /**
     * Decrements the zoom level, if it is not already at 0.
     */
    public void decreaseZoomLevel() {
        if (zoomLevel > 0) {
            zoomLevel--;
            resized = true;
        }//end if
        ref.getSettingsManager().writeSettings();
    }//end decreaseZoomLevel

    /**
     * Sets the zoom level at a specific value, within a maximum value and 0.
     *
     * @param in The new value for zoom level
     */
    public void setZoomLevel(int in) {
        if (in < 0) {
            zoomLevel = 0;
        }//end if
        else if (in > MAX_ZOOM_LEVEL) {
            zoomLevel = MAX_ZOOM_LEVEL;
        }//end else if
        else {
            zoomLevel = in;
        }//end else
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
        pointSize = minPointSize + (zoomLevel * 2);
        spacing = minSpacing + (zoomLevel * 4);
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
     * Modifies windowX
     *
     * @param mod
     */
    public void modifyWindowX(int mod) {
        windowX = windowX + mod;
    }//end modifyWindowX

    public void resetWindowX() {
        windowX = 0;
    }//end resetWindowX

    public int getWindowY() {
        return windowY;
    }//end getWindowY

    public void modifyWindowY(int mod) {
        windowY = windowY + mod;
    }//end modifyWindowX

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

    public void setGtiStorage(GraphTupleInfo in) {
        gtiStorage = in;
    }//end setGtiStorage

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
}//end Canvas
