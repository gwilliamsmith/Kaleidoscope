package SwingElements;

import Listeners.*;
import graphvisualizer.Graph;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
/**
 * Initializes both the Graph, and the Canvas.&nbsp;Attaches mouse and key listeners, for user interaction.
 * @author Redpox
 */
public class Base extends JFrame {

    //The grid
    private Graph graph;

    //////////////////////////////
    //    Display Variables     //
    //////////////////////////////
    //Canvas for displaying stuff
    private Canvas canvas;

    //////////////////////////////
    //Right-Click Menu Variables//
    //////////////////////////////
    //Right-Click Menu
    private final JPopupMenu rightClickMenu = new JPopupMenu();

    //Menu buttons
    private final JMenuItem step = new JMenuItem("Step forward");
    private final JMenuItem loop = new JMenuItem("Run/Pause steps");
    private final JMenuItem reset = new JMenuItem("Reset grid");
    private final JMenuItem setColor = new JMenuItem("Set color for next connection");
    private final JMenuItem whiteOutGrid = new JMenuItem("Turn all grid points white");
    private final JMenuItem properties = new JMenuItem("Edit properties");
    private final JMenuItem averageColor = new JMenuItem("Show average connection color");
    private final JMenuItem customLine = new JMenuItem("Set properties for next line");
    private final JMenuItem saveState = new JMenuItem("Save state");
    private final JMenuItem savePicture = new JMenuItem("Save Picture");
    private final JMenuItem seedColoringBook  = new JMenuItem("Set up starting coloring book seed");
            
    //Determines if Graph.takeStep() should be executed inside the TimerActionListener
    private boolean run = false;

    //Displays average color of all lines
    private AverageColorDisplay averageDisplay = new AverageColorDisplay();

    /**
     * Constructor for Base class.&nbsp;Determines grid dimensions, as well as the interval for steps and repaint actions
     * 
     * @param c The number of columns in the grid
     * @param r The number of rows in the grid
     * @param stepTime The time in between TimerActionLister events
     */
    public Base(int c, int r, int stepTime) {
        canvas = new Canvas(this);
        graph = new Graph(r, c, this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addMenuListeners();
        createRightClickMenu();

        this.addKeyListener(new BaseKeyListener(this));
        this.setSize(500, 500);
        this.add(canvas);

        //Repaints on an interval
        new Timer(stepTime, new TimerActionListener(this)).start();
    }//end constructor


    /**
     * Changes the color of the AverageColorDisplay
     */
    public void updateAverageColor() {
        averageDisplay.updateColor(graph.getAverageColor());
    }//end updateAverageColor

    /**
     * Adds the ActionListeners to the right-click menu buttons
     */
    private void addMenuListeners() {
        step.addActionListener(new StepActionListener(this));
        loop.addActionListener(new LoopActionListener(this));
        reset.addActionListener(new ResetActionListener(this));
        setColor.addActionListener(new SetColorActionListener(this));
        whiteOutGrid.addActionListener(new WhiteOutGridActionListener(this));
        properties.addActionListener(new PropertiesActionListener(this));
        averageColor.addActionListener(new AverageColorActionListener(this));
        customLine.addActionListener(new CustomLineActionListener(this));
        saveState.addActionListener(new SaveStateActionListener(this));
        savePicture.addActionListener(new SavePictureActionListener(this));
        seedColoringBook.addActionListener(new SeedColoringBookListener(this));
    }//end addMenuListeners

    /**
     * Adds the right-click menu buttons to the menu
     */
    private void createRightClickMenu() {
        rightClickMenu.add(step);
        rightClickMenu.add(loop);
        rightClickMenu.add(reset);
        rightClickMenu.add(setColor);
        rightClickMenu.add(whiteOutGrid);
        rightClickMenu.add(properties);
        rightClickMenu.add(averageColor);
        rightClickMenu.add(customLine);
        rightClickMenu.add(saveState);
        rightClickMenu.add(savePicture);
        rightClickMenu.add(seedColoringBook);
    }//end createRightClickMenu
    
    /**
     * Inverts the run boolean, used in the TimerActionListener
     */
    public void flipRun(){
        run = !run;
    }//end flipRun

    //////////////////////////////
    //     Setters/Getters      //
    //////////////////////////////
    
    /**
     * Returns the {@link Canvas} object
     * @return The canvas object
     * @see Canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }//end getCanvas

    /**
     * Sets the value for the run variable
     * @param in The new value for run
     */
    public void setRun(boolean in) {
        run = in;
    }//end setRun

    /**
     * Returns the value of the run variable
     * @return The value of run
     */
    public boolean getRun() {
        return run;
    }//end getRun

    /**
     * Shows the {@link AverageColorDisplay} window
     */
    public void showAverageDisplay() {
        averageDisplay.setVisible(true);
    }//end getAverageDisplay
    
    /**
     * Returns the right click menu
     * @return The right click menu
     */
    public JPopupMenu getRightClickMenu() {
        return rightClickMenu;
    }//end getRightClickMenu
    
    /**
     * Returns the Graph object
     * @return The graph object
     */
    public Graph getGraph(){
        return graph;
    }//end getGraph
    
}//end Base class
