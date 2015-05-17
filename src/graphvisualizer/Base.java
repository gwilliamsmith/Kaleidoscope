package graphvisualizer;

import Listeners.*;
import SwingElements.AverageColorDisplay;
import SwingElements.Canvas;
import java.awt.Color;
import java.awt.Point;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Timer;

public class Base extends JFrame {

    //////////////////////////////
    //      Grid Variables      //
    //////////////////////////////
    public Graph graph;

    //////////////////////////////
    //    Display Variables     //
    //////////////////////////////
    //Canvas for displaying stuff
    private Canvas canvas;

    //Says what the next action is
    private String actionString = "";

    //Spacing/Size for grid points
    private final int spacing = 10;
    private final int pointSize = 2;

    //number of steps, steps per cycle, and number of cycles
    private long stepCount = 0;
    private long cycleBase = 0;
    private long cycleCount = 0;

    //////////////////////////////
    //Right-Click Menu Variables//
    //////////////////////////////
    //Actual menu
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

    //Remembers point clicked on 
    private Point rightStore = new Point();

    //Color for next line(black if null)
    private Color tempColor = null;

    //Info for next line
    private GraphTupleInfo gtiStorage = null;

    //////////////////////////////
    //   Connection Variables   //
    //////////////////////////////
    private GraphNode connectA;
    private GraphNode connectB;
    private boolean connect = false;

    //Determines if loop should be run
    private boolean run = false;

    //Displays average color of all lines
    private final AverageColorDisplay averageDisplay = new AverageColorDisplay();

    public Base(int c, int r, int stepTime) {
        graph = new Graph(r, c, this);
        canvas = new Canvas(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addMenuListeners();
        createRightClickMenu();

        this.addKeyListener(new BaseKeyListener(this));
        this.setSize(500, 500);
        this.add(canvas);

        //Repaints on an interval
        new Timer(stepTime, new TimerActionListener(this)).start();
    }//end constructor

    public void takeStep() {
        graph.stepForward();
        stepCount++;
        if (cycleBase > 0) {
            cycleCount = stepCount / cycleBase;
        }//end if
        canvas.repaint();
    }//end takeStep

    public void reset() {
        graph.clearGrid();
        stepCount = 0;
        cycleBase = 0;
        cycleCount = 0;
        canvas.repaint();
    }//end reset

    public void updateAverageColor() {
        averageDisplay.updateColor(graph.getAverageColor());
    }//end updateAverageColor

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
    }//end addMenuListeners

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
    }//end createRightClickMenu
    
    public void flipRun(){
        run = !run;
    }//end flipRun

    //////////////////////////////
    //     Setters/Getters      //
    //////////////////////////////
    public void setTempColor(Color c) {
        tempColor = c;
    }//end setTempColor

    public Color getTempColor() {
        return tempColor;
    }//end getTempColor

    public void setRightStore(Point in) {
        rightStore = in;
    }//end setRightStore

    public Point getRightStore() {
        return rightStore;
    }//end getRightStore

    public void setConnect(boolean in) {
        connect = in;
    }//end setConnect

    public boolean getConnect() {
        return connect;
    }//end getConnect

    public void setConnectA(GraphNode in) {
        connectA = in;
    }//end setConnectA

    public GraphNode getConnectA() {
        return connectA;
    }//end getConnectA

    public void setConnectB(GraphNode in) {
        connectB = in;
    }//end setConnectB

    public GraphNode getConnectB() {
        return connectB;
    }//end getConnectB

    public void setActionString(String in) {
        actionString = in;
    }//end setActionString

    public String getActionString() {
        return actionString;
    }//end getActionString

    public JPanel getCanvas() {
        return canvas;
    }//end getCanvas

    public void setRun(boolean in) {
        run = in;
    }//end setRun

    public boolean getRun() {
        return run;
    }//end getRun

    public void showAverageDisplay() {
        averageDisplay.setVisible(true);
    }//end getAverageDisplay

    public void setGtiStorage(GraphTupleInfo in) {
        gtiStorage = in;
    }//end setGtiStorage

    public GraphTupleInfo getGtiStorage() {
        return gtiStorage;
    }//end getGtiStorage

    public JPopupMenu getRightClickMenu() {
        return rightClickMenu;
    }//end getRightClickMenu

    public long getStepCount() {
        return stepCount;
    }//end getStepCount

    public void setCycleBase(long in) {
        cycleBase = in;
    }//end setCycleBase

    public long getCycleBase() {
        return cycleBase;
    }//end getCycleBase

    public long getCycleCount() {
        return cycleCount;
    }//end getCycleCount

    public int getPointSize() {
        return pointSize;
    }//end getPointSize

    public int getSpacing() {
        return spacing;
    }//end getSpacing
}//end Base class
