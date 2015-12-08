package SwingElements;

import Listeners.*;
import graphvisualizer.Graph;
import graphvisualizer.SettingsFileManipulator;
import java.io.File;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
/**
 * Initializes both the Graph and the Canvas.&nbsp;Attaches mouse and key listeners, for user interaction.
 * @author Redpox
 */
public class Base extends JFrame {

    //The grid
    private Graph graph;

    //Canvas for displaying stuff
    private Canvas canvas;
    
    //Repaints the grid on an interval
    private Timer timer;
    
    //Interval for repainting
    private int stepTime;
    
    //Folder location where generated pictures are to be saved
    private File bookDirectory;

    //////////////////////////////
    //Right-Click Menu Variables//
    //////////////////////////////
    //Right-Click Menu
    private final JPopupMenu rightClickMenu = new JPopupMenu();

    //Menu buttons
    private final JMenuItem step = new JMenuItem("Step forward");
    private final JMenuItem loop = new JMenuItem("Run");
    private final JMenuItem reset = new JMenuItem("Reset grid");
    private final JMenuItem whiteOutGrid = new JMenuItem("Turn all grid points white");
    private final JMenuItem properties = new JMenuItem("Edit properties");
    private final JMenuItem averageColor = new JMenuItem("Show average connection color");
    private final JMenuItem customLine = new JMenuItem("Set properties for next line");
    private final JMenuItem saveState = new JMenuItem("Save state");
    private final JMenuItem savePicture = new JMenuItem("Save Picture");
    private final JMenuItem seedColoringBook  = new JMenuItem("Set up starting coloring book seed");
    private final JMenuItem centerGrid = new JMenuItem("Center the grid on the screen");
    private final JMenuItem toggleDrag = new JMenuItem("Disable drag to reposition");
    private final JMenuItem folderSelect = new JMenuItem("Choose folder to save book images in");
            
    //Determines if Graph.takeStep() should be executed inside the TimerActionListener
    private boolean run = false;
    
    private SettingsFileManipulator settingsManager;

    //Displays average color of all lines
    private AverageColorDisplay averageDisplay = new AverageColorDisplay();

    /**
     * Constructor for Base class.&nbsp;Determines grid dimensions, as well as the interval for steps and repaint actions
     * 
     * @param c The number of columns in the grid
     * @param r The number of rows in the grid
     * @param st The time in between TimerActionLister events
     */
    public Base(int c, int r, int st) {
        canvas = new Canvas(this);
        graph = new Graph(r, c, this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        settingsManager = new SettingsFileManipulator("./res/settings.txt",this);
        settingsManager.readSettingsIn();

        addMenuListeners();
        createRightClickMenu();

        this.addKeyListener(new BaseKeyListener(this));
        this.setSize(500, 500);
        this.add(canvas);
        
        stepTime = st;
        //Repaints on an interval
        timer = new Timer(stepTime, new TimerActionListener(this));
        timer.start();
    }//end constructor
    
    public void resizeGrid(int c, int r, int st){
        timer.stop();
        //canvas = new Canvas(this);
        graph = new Graph(r, c, this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(canvas);

        stepTime = st;
        //Repaints on an interval
        timer = new Timer(stepTime, new TimerActionListener(this));
        timer.start();
    }//end resizeGrid

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
        whiteOutGrid.addActionListener(new WhiteOutGridActionListener(this));
        properties.addActionListener(new PropertiesActionListener(this));
        averageColor.addActionListener(new AverageColorActionListener(this));
        customLine.addActionListener(new CustomLineActionListener(this));
        saveState.addActionListener(new SaveStateActionListener(this));
        savePicture.addActionListener(new SavePictureActionListener(this));
        seedColoringBook.addActionListener(new SeedColoringBookListener(this));
        centerGrid.addActionListener(new CenterGridActionListener(this));
        toggleDrag.addActionListener(new ToggleDragActionListener(this));
        folderSelect.addActionListener(new FolderSelectActionListener(this));
    }//end addMenuListeners

    /**
     * Adds the right-click menu buttons to the menu
     */
    private void createRightClickMenu() {
        rightClickMenu.add(step);
        rightClickMenu.add(loop);
        rightClickMenu.add(reset);
        rightClickMenu.add(whiteOutGrid);
        rightClickMenu.add(properties);
        rightClickMenu.add(averageColor);
        rightClickMenu.add(customLine);
        rightClickMenu.add(saveState);
        rightClickMenu.add(savePicture);
        rightClickMenu.add(seedColoringBook);
        rightClickMenu.add(centerGrid);
        rightClickMenu.add(toggleDrag);
        rightClickMenu.add(folderSelect);
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
    
    public JMenuItem getToggleDrag(){
        return toggleDrag;
    }//end getToggleDrag
    
    public JMenuItem getLoop(){
        return loop;
    }//end getLoop
    
    public File getBookDirectory(){
        return bookDirectory;
    }//end getBookDirectory
    
    public void setBookDirectory(File in){
        bookDirectory = in;
    }//end setBookDirectory
    
    public int getStepTime(){
        return stepTime;
    }//end getStepTime
    
    public void setStepTime(int in){
        stepTime = in;
    }//end setStepTime
    
    public SettingsFileManipulator getSettingsManager(){
        return settingsManager;
    }//end getSettingsManager
    
}//end Base class