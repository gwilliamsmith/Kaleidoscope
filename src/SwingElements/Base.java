package SwingElements;

import Listeners.SaveListeners.*;
import Listeners.PropertiesListeners.*;
import Listeners.GridOptionListeners.*;
import Listeners.RightClickListeners.*;
import EventScheduler.EventScheduler;
import Listeners.*;
import graphvisualizer.Graph;
import graphvisualizer.SettingsFileManipulator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.util.Hashtable;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * Initializes both the {@link Graph} and the {@link Canvas}. Attaches mouse and
 * key listeners, for user interaction.
 */
public class Base extends JFrame {

    public static Graph graph;                                                  //The grid

    private Canvas canvas;                                                      //Canvas for displaying stuff

    private Timer timer;                                                        //Takes steps on an interval
    private final TimerActionListener timerListener;                                  //Action listener for the Timer

    private final Timer painter;                                                      //Repaints the grid every millisecond

    private int stepTime;                                                       //Interval for repainting

    private File bookDirectory;                                                 //Folder location where generated pictures are to be saved

    private final JPopupMenu rightClickMenu = new JPopupMenu();                 //Right-Click Menu

    private final JMenuBar menuBar = new JMenuBar();                            //Menu Bar

    private final JMenu gridOptions = new JMenu("Grid Options");                //Menu Bar Items
    private final JMenu propertiesMenu = new JMenu("Properties");
    private final JMenu save = new JMenu("Save");
    private final JMenu schedulerMenu = new JMenu("Scheduler");
    private final JMenu calculateSizeMenu = new JMenu("Calculate grid size");

    private final JMenuItem step = new JMenuItem("Step forward");                                           //Right-click button
    private final JMenuItem loop = new JMenuItem("Run");                                                    //Right-click button   
    private final JMenuItem averageColor = new JMenuItem("Show average connection color");                  //Right-click button
    private final JMenuItem databaseConnect = new JMenuItem("Connect to local database");                   //Right-click button  

    private final JMenuItem reset = new JMenuItem("Reset grid");                                            //Grid options menu
    private final JMenuItem whiteOutGrid = new JMenuItem("Turn all grid points white");                     //Grid options menu
    private final JMenuItem centerGrid = new JMenuItem("Center the grid on the screen");                    //Grid options menu
    private final JMenuItem resetZoom = new JMenuItem("Reset zoom level");                                  //Grid options menu
    private final JMenuItem toggleUserEdges = new JMenuItem("Hide user-placed edges");

    private final JMenuItem propertiesItem = new JMenuItem("Edit properties");                              //Properties menu
    private final JMenuItem customLine = new JMenuItem("Set properties for next line");                     //Properties menu
    private final JMenuItem seedColoringBook = new JMenuItem("Set up starting coloring book seed");         //Properties menu
    private final JMenuItem toggleDrag = new JMenuItem("Disable drag to reposition");                       //Properties menu

    private final JMenuItem saveState = new JMenuItem("Save state");                                        //Save menu
    private final JMenuItem loadState = new JMenuItem("Load state");                                        //Save menu
    private final JMenuItem savePicture = new JMenuItem("Save picture");                                    //Save menu
    private final JMenuItem folderSelect = new JMenuItem("Choose folder to save book images in");           //Save menu
    private final JMenuItem toggleSaveInterval = new JMenuItem("Enable saving pictures on interval");      //Save menu
    private final JMenuItem togglePauseInterval = new JMenuItem("Enable pausing after interval picture");   //Save menu

    private final DebugMenuForm debugMenu;                                      // The debug menu form

    private JSlider stepTimeSlider;                                             //Slider that can control step timing

    private final SettingsFileManipulator settingsManager;                      //Settings manager, reads the persistent settings in from the file

    private AverageColorDisplay averageDisplay = new AverageColorDisplay();     //Displays average color of all lines

    private static Connection conn = null;                                      //MySQL connection variable

    public EventScheduler scheduler = new EventScheduler();                     //Event scheduler

    public boolean curveSwitcher = false;                                       //Handles determining if the curve toggle boolean should be switched between steps.
    
    private boolean showUserEdges = true;                                       //Determines if user-placed edges should be drawn
    
    private boolean shiftDown = false;                                        //Is the shift key pressed?

    /**
     * Constructor for Base class. Determines grid dimensions, as well as the
     * interval for steps and repaint actions
     *
     * @param c The number of columns in the grid
     * @param r The number of rows in the grid
     * @param st The time in between TimerActionLister events
     */
    public Base(int c, int r, int st) {
        BoxLayout layout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
        setLayout(layout);
        canvas = new Canvas(this);
        graph = new Graph(r, c, this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(new BaseKeyListener(this));

        settingsManager = new SettingsFileManipulator("./kaleidoscopesettings.ksf", this);
        settingsManager.readSettingsIn();

        graph.initializeGrid();

        addMenuListeners();
        createRightClickMenu();
        createGridOptionsMenu();
        createPropertiesMenu();
        createSaveMenu();
        createMenuBar();

        setJMenuBar(menuBar);
        menuBar.addKeyListener(new BaseKeyListener((this)));

        setSize(500, 500);
        setTitle("Kaleidoscope v 0.5");
        add(canvas);

        debugMenu = new DebugMenuForm(this);

        stepTime = st;
        initializeStepTimer();
        //Takes steps on an interval
        timerListener = new TimerActionListener(this);
        timer = new Timer(stepTime, timerListener);

        painter = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.repaint();
            }
        });
        painter.start();
        //Repaints on an interval
    }//end constructor

    /**
     * Sets up the {@link JSlider} that can control the interval between steps.
     */
    private void initializeStepTimer() {
        stepTimeSlider = new JSlider(1, 1000, stepTime);
        stepTimeSlider.addChangeListener(new SliderChangeListener((this)));
        stepTimeSlider.setMajorTickSpacing(100);
        stepTimeSlider.setMinorTickSpacing(1);
        Hashtable<Integer, JLabel> sliderLabels = new Hashtable<>();            //This is obsolete, but it only exists to give the slider labels 
        sliderLabels.put(20, new JLabel("Faster"));
        sliderLabels.put(980, new JLabel("Slower"));
        stepTimeSlider.setLabelTable(sliderLabels);
        stepTimeSlider.setPaintLabels(true);
        stepTimeSlider.addKeyListener(new BaseKeyListener((this)));
        stepTimeSlider.setFocusable(false);
        add(stepTimeSlider);
    }//end initializeStepTimer

    /**
     * Re-sizes the grid dimensions, as well as the step interval, and the
     * number of pictures in a cycle for coloring book picture generation.
     *
     * @param c The number of columns
     * @param r The number of rows
     * @param st The amount of time in ms between steps when auto-running steps
     */
    public void resizeGrid(int c, int r, int st) {
        timer.stop();
        int tempPictureCount = graph.getCamera().getPictureCount();
        graph = new Graph(r, c, this);
        graph.initializeGrid();
        graph.getCamera().setPictureCount(tempPictureCount);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        if ("Restore all grid point colors".equals(whiteOutGrid.getText())) {
            whiteOutGrid.getActionListeners()[0].actionPerformed(null);
        }//end if
        stepTime = st;
        timer = new Timer(stepTime, new TimerActionListener(this));
    }//end resizeGrid

    /**
     * Changes the color of the AverageColorDisplay
     */
    public void updateAverageColor() {
        if (averageDisplay.isVisible()) {
            averageDisplay.updateColor(graph.getAverageColor());
        }//end if
    }//end updateAverageColor

    /**
     * Updates the time between steps.
     *
     * @param st the new stepTime
     */
    public void updateStepTime(int st) {
        stepTime = st;
        boolean resume = timer.isRunning();
        timer.stop();
        timer = new Timer(stepTime, new TimerActionListener(this));
        int sliderVal = (stepTime <= 0 ? 0 : st);
        stepTimeSlider.setValue(sliderVal);
        if (resume) {
            timer.start();
        }//end if
    }//end updateStepTime

    /**
     * Adds the ActionListeners to the right-click menu buttons
     */
    private void addMenuListeners() {
        step.addActionListener(new StepActionListener(this));
        loop.addActionListener(new LoopActionListener(this));
        reset.addActionListener(new ResetActionListener(this));
        whiteOutGrid.addActionListener(new WhiteOutGridActionListener(this));
        propertiesItem.addActionListener(new PropertiesActionListener(this));
        averageColor.addActionListener(new AverageColorActionListener(this));
        customLine.addActionListener(new CustomLineActionListener(this));
        saveState.addActionListener(new SaveStateActionListener(this));
        loadState.addActionListener(new LoadStateActionListener(this));
        savePicture.addActionListener(new SavePictureActionListener(this));
        seedColoringBook.addActionListener(new SeedColoringBookListener(this));
        centerGrid.addActionListener(new CenterGridActionListener(this));
        toggleDrag.addActionListener(new ToggleDragActionListener(this));
        folderSelect.addActionListener(new FolderSelectActionListener(this));
        databaseConnect.addActionListener(new DatabaseConnectListener(this));
        schedulerMenu.addMouseListener(new SchedulerMenuActionListener(this));
        toggleSaveInterval.addActionListener(new ToggleSaveIntervalActionListener((this)));
        togglePauseInterval.addActionListener(new TogglePauseIntervalActionListener(this));
        resetZoom.addActionListener(new ResetZoomActionListener(this));
        calculateSizeMenu.addMouseListener(new CalculateSizeActionListener(this));
        toggleUserEdges.addActionListener(new ToggleUserEdgesListener(this));
    }//end addMenuListeners

    /**
     * Adds the right-click menu buttons to the menu
     */
    private void createRightClickMenu() {
        rightClickMenu.add(step);
        rightClickMenu.add(loop);
        rightClickMenu.add(averageColor);
        rightClickMenu.add(databaseConnect);
    }//end createRightClickMenu

    /**
     * Adds the grid options menu buttons to the menu
     */
    private void createGridOptionsMenu() {
        gridOptions.add(reset);
        gridOptions.add(whiteOutGrid);
        gridOptions.add(toggleUserEdges);
        gridOptions.add(centerGrid);
        gridOptions.add(resetZoom);
    }//end createGridOptionsMenu

    /**
     * Adds the properties menu buttons to the menu
     */
    private void createPropertiesMenu() {
        propertiesMenu.add(propertiesItem);
        propertiesMenu.add(customLine);
        propertiesMenu.add(seedColoringBook);
        propertiesMenu.add(toggleDrag);
    }//end createPropertiesMenu

    /**
     * Adds the save menu buttons to the menu
     */
    private void createSaveMenu() {
        save.add(saveState);
        save.add(loadState);
        save.add(savePicture);
        save.add(folderSelect);
        save.add(toggleSaveInterval);
        save.add(togglePauseInterval);
    }//end createSaveMenu

    /**
     * Adds the menu bar items to the menu bar
     */
    private void createMenuBar() {
        menuBar.add(gridOptions);
        menuBar.add(propertiesMenu);
        menuBar.add(save);
        menuBar.add(schedulerMenu);
        menuBar.add(calculateSizeMenu);
    }//end createMenuBar

    /**
     * Used to trigger the save picture action listener when the 's' key is
     * pressed.
     */
    public void triggerSaveAction() {
        savePicture.getActionListeners()[0].actionPerformed(null);
    }//end triggerSaveAction

    /**
     * Used to trigger the save state action listener when control-s is pressed.
     */
    public void triggerSaveStateAction() {
        saveState.getActionListeners()[0].actionPerformed(null);
    }//end triggerSaveStateAction

    /**
     * Used to trigger the loop action listener when the spacebar is pressed.
     */
    public void triggerLoop() {
        loop.getActionListeners()[0].actionPerformed(null);
    }//end triggerLoop

    /**
     * Used to trigger the white out grid action listener. Currently unused.
     */
    public void triggerWhiteOutGrid() {
        whiteOutGrid.getActionListeners()[0].actionPerformed(null);
    }//end triggerWhiteOutGrid

    /**
     * Inverts the run boolean, used in the loop menu button
     */
    public void flipRun() {
        if (timer.isRunning()) {
            pause();
        }//end if
        else {
            run();
        }//end else
    }//end flipRun

    /**
     * Sets the run variable to true, and updates the text of the loop menu
     * item.
     */
    public void run() {
        if (!timer.isRunning()) {
            timer.start();
            loop.setText("Pause");
        }//end if
    }//end run

    /**
     * Sets the run variable to false, and updates the text of the loop menu
     * item.
     */
    public void pause() {
        timer.stop();
        loop.setText("Run");
    }//end pause;

    /**
     * Makes the debug menu visible, and pulls it to the front of the screen.
     */
    public void showDebugMenu() {
        debugMenu.setVisible(true);
        debugMenu.toFront();
        debugMenu.requestFocus();
    }//end showDebugMenu

    /**
     * Hides the debug menu.
     */
    public void hideDebugMenu() {
        debugMenu.setVisible(false);
    }//end hideDebugMenu

    /**
     * Checks to see if the connection variable has been created.
     *
     * @return True if the connection has been set, false if not
     */
    public static boolean checkConnection() {
        return conn == null;
    }//end checkConnection

    /**
     * Sets the text for the save on interval menu button.
     *
     * @param in The new text for the button
     */
    public void setSaveIntervalToggleText(String in) {
        toggleSaveInterval.setText(in);
    }//end setSaveIntervalToggleText

    /**
     * Sets the text for the pause on interval menu button.
     *
     * @param in The new text for the button
     */
    public void setPauseIntervalToggleText(String in) {
        togglePauseInterval.setText(in);
    }//end setSaveIntervalToggleText
    
    public void setToggleUserEdgesText(String in){
        toggleUserEdges.setText(in);
    }//end setToggleUserEdgesText

    /**
     * Returns the {@link Canvas} object
     *
     * @return The canvas object
     * @see Canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }//end getCanvas

    /**
     * Returns true if steps are being run
     *
     * @return True if timer is started
     */
    public boolean isRunning() {
        return timer.isRunning();
    }//end isRunning

    /**
     * Shows the {@link AverageColorDisplay} window
     */
    public void showAverageDisplay() {
        averageDisplay = new AverageColorDisplay();
        SwingUtilities.invokeLater(averageDisplay);
    }//end getAverageDisplay

    /**
     * Returns the right click menu
     *
     * @return The right click menu
     */
    public JPopupMenu getRightClickMenu() {
        return rightClickMenu;
    }//end getRightClickMenu

    /**
     * Returns the Graph object
     *
     * @return The graph object
     */
    public Graph getGraph() {
        return graph;
    }//end getGraph

    /**
     * Returns the {@link JMenuItem} used to control mouse dragging.
     *
     * @return The {@link JMenuItem} controlling mouse dragging
     */
    public JMenuItem getToggleDrag() {
        return toggleDrag;
    }//end getToggleDrag

    /**
     * Returns the {@link JMenuItem} used to toggle step auto-running
     *
     * @return The {@link JMenuItem} controlling auto-running
     */
    public JMenuItem getLoop() {
        return loop;
    }//end getLoop

    /**
     * Returns the directory where coloring book pictures are saved.
     *
     * @return The file path to the directory
     */
    public File getBookDirectory() {
        return bookDirectory;
    }//end getBookDirectory

    /**
     * Sets the directory where coloring book pages are saved.
     *
     * @param in The location
     */
    public void setBookDirectory(File in) {
        bookDirectory = in;
    }//end setBookDirectory

    /**
     * Gets the amount of time in ms between steps when auto-running
     *
     * @return The number of ms between steps when auto-running
     */
    public int getStepTime() {
        return stepTime;
    }//end getStepTime

    /**
     * Sets the amount of time in ms between steps when auto-running
     *
     * @param in The number of ms to wait between steps when auto-running
     */
    public void setStepTime(int in) {
        updateStepTime(in);
    }//end setStepTime

    /**
     * Gets the object responsible for reading/editing the settings file
     *
     * @return The {@link SettingsFileManipulator} object created at
     * initialization
     */
    public SettingsFileManipulator getSettingsManager() {
        return settingsManager;
    }//end getSettingsManager

    /**
     * Returns the {@link TimerActionListener} used to auto-run steps.
     *
     * @return The {@link TimerActionListener} used to auto-run steps.
     */
    public TimerActionListener getTimerListener() {
        return timerListener;
    } //end getTimerListener

    /**
     * Returns the {@link Connection} used to communicate with the MySQL
     * database.
     *
     * @return The {@link Connection} object used to communicate with the MySQL
     * database
     */
    public Connection getConn() {
        return conn;
    }//end getConn

    /**
     * Sets a new {@link Connection} used to communicate with the MySQL
     * database.
     *
     * @param in A new {@link Connection} used to communicate with the MySQL
     * database
     */
    public void setConn(Connection in) {
        conn = in;
    }//end setConn

    /**
     * Gets the {@link JMenuItem} for toggling grid node colors.
     *
     * @return whiteOutGrid, the {@link JMenuItem} for toggling grid node colors
     */
    public JMenuItem getWhiteOutGrid() {
        return whiteOutGrid;
    }//end getWhiteOutGrid

    /**
     * Sets the value for the step time slider
     *
     * @param location The new value for stepTime
     */
    public void setStepTimeSliderLocation(int location) {
        stepTimeSlider.setValue(location);
    }//end setSliderLocation
    
    public boolean getShowUserEdges(){
        return showUserEdges;
    }//end getShowUserEdges
    
    public void setShowUserEdges(boolean in){
        showUserEdges = in;
    }//end setShowUserEdges
    
    public boolean isShiftDown(){
        return shiftDown;
    }//end isShiftDown
    
    public void setShiftDown(boolean in){
        shiftDown = in;
    }//end setShiftDown

}//end Base class
