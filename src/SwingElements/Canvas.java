package SwingElements;

import Listeners.CanvasMouseListener;
import graphvisualizer.GraphNode;
import graphvisualizer.GraphTuple;
import graphvisualizer.GraphTupleInfo;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Canvas extends JPanel {

    private Base ref;
    public int windowX = 0;
    public int windowY = 0;

    //Spacing and Size for grid points
    private int minSpacing = 10;
    private int minPointSize = 2;
    private int spacing = 10;
    private int pointSize = 2;
    private int zoomLevel = 1;

    //Determines if the drag to reposition is enabled/disabled
    private boolean drag = true;

    //Color for next line(black if null)
    private Color tempColor = null;

    private BufferedImage gridPicture = null;

    //Info for next line
    private GraphTupleInfo gtiStorage = new GraphTupleInfo();

    public Canvas(Base in) {
        ref = in;
        this.setBackground(Color.white);
        this.setSize(in.getWidth(), in.getHeight());
        CanvasMouseListener mouseListener = new CanvasMouseListener(ref);
        this.addMouseListener(mouseListener);
        this.addMouseWheelListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
    }//end constructor

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(producePicture(), 0, 0, null);
        ref.updateAverageColor();
    }//end paint

    public void paintGraph(Graphics g) {
        Canvas canvas = ref.getCanvas();
        int canvasWindowX = canvas.getWindowX();
        int canvasWindowY = canvas.getWindowY();
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(canvas.getPointSize() / 2));
        for (GraphNode gn : ref.getGraph().getGraphNodes()) {
            g2.setColor(gn.getColor());
            if (gn.getFood() <= 0) {
                g2.setColor(Color.WHITE);
            }//end if
            g2.fillRect(gn.x + canvasWindowX, gn.y + canvasWindowY, gn.height, gn.width);
            for (int i = 0; i < gn.getNumberOfConnections(); i++) {
                GraphTuple gt = gn.getConnection(i);
                g2.setColor(gt.getColor());
                GraphNode location = gt.getToLocation();
                if (gn.isConnected(location) && location.isConnected(gn)) {
                    //This line is a mess, fix it at some point
                    g2.drawLine(gn.x + gn.width / 2 + canvasWindowX, 
                            gn.y + gn.height / 2 + canvasWindowY, 
                            location.x + location.width / 2 + canvasWindowX, 
                            location.y + location.height / 2 + canvasWindowY);
                }//end if
            }//end for
        }//end for
        paintGridPicture();
    }//end paint

    public void paintGridPicture() {
        BufferedImage image = ref.getCanvas().getGridPicture();
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        for (GraphNode gn : ref.getGraph().getGraphNodes()) {
            g2.setColor(gn.getColor());
            if (gn.getFood() <= 0) {
                g2.setColor(Color.white);
            }//end if
            g2.fillRect(gn.x, gn.y, gn.height, gn.width);
            for (int i = 0; i < gn.getNumberOfConnections(); i++) {
                GraphTuple gt = gn.getConnection(i);
                g2.setColor(gt.getColor());
                GraphNode location = gt.getToLocation();
                if (gn.isConnected(location) && location.isConnected(gn)) {
                    //This line is a mess, fix it at some point
                    g2.drawLine(gn.x + gn.width / 2, gn.y + gn.height / 2, location.x + location.width / 2, location.y + location.height / 2);
                }//end if
            }//end for
        }//end for
    }//end paintGridPicture

    public BufferedImage producePicture() {
        BufferedImage picture = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = picture.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        paintGraph(g);
        drawString("Steps taken: " + ref.getGraph().getStepCount(), 0, 4, g);
        if (ref.getGraph().getCycleBase() > 0) {
            drawString("Cycles: " + ref.getGraph().getCycleCount(), 1, 4, g);
        }//end if
        g.dispose();
        return picture;
    }//end producePicture

    public BufferedImage produceTrimmedImage(BufferedImage in) {
        BufferedImage out = new BufferedImage(trimImageWidth(in) + 2, trimImageHeight(in) + 2, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = out.createGraphics();
        g.drawImage(in, 0, 0, null);
        return out;
    }//endGetTrimmedImage

    /*
     TODO:
     Clean this up:
     Use static variables for corner
     lineOffset can be done better
     Implement logic for other corners
     */
    private void drawString(String in, int lineOffset, int corner, Graphics g) {
        g.setColor(Color.black);
        FontMetrics fontSize = g.getFontMetrics();
        int textWidth = fontSize.stringWidth(in);
        if(corner == 1){
            
        }//end if
        else if(corner == 2){
            
        }//end else if
        else if(corner == 3){
            
        }//end else if
        else if(corner == 4){
                g.drawString(in, this.getWidth() - textWidth - 5, this.getHeight() - (fontSize.getHeight() * (lineOffset)) - 5);   
        }//end else if
    }//end drawString

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

    public int getWindowX() {
        return windowX;
    }//end getWindowX

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

    public int getMinSpacing() {
        return minSpacing;
    }//end getMinSpacing

    public void setMinSpacing(int in) {
        minSpacing = in;
    }//end setMinSpacing

    public int getMinPointSize() {
        return minPointSize;
    }//end getMinPointSize

    public void setMinPointSize(int in) {
        minPointSize = in;
    }//end setMinPointSize

    public boolean canDrag() {
        return drag;
    }//end canDrag

    public void flipDrag() {
        drag = !drag;
    }//end flipDrag

    public BufferedImage getGridPicture() {
        if (gridPicture == null) {
            gridPicture = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        }//end if
        return gridPicture;
    }//end getGridPicture

    public void setGridPicture(BufferedImage in) {
        gridPicture = in;
    }//end setGridPicture

}//end Canvas
