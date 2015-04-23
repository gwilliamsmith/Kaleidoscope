package graphvisualizer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public final class Base extends JFrame {

    public static Graph graph = new Graph();
    private static JPanel canvas;

    //Right click menu variables
    private JPopupMenu rightClickMenu = new JPopupMenu();
    private final JMenuItem connectPoint = new JMenuItem("Connect Point(unweighted)");
    private final JMenuItem step = new JMenuItem("Step forward");
    private final JMenuItem loop = new JMenuItem("Run/Pause steps");
    private final JMenuItem clear = new JMenuItem("Clear grid");
    private final JMenuItem setColor = new JMenuItem("Set color for next connection");
    private final JMenuItem whiteOutGrid = new JMenuItem("Turn all grid points white");
    private final JMenuItem properties = new JMenuItem("Edit properties");
    private final JMenuItem averageColor = new JMenuItem("Show average connection color");
    private final JMenuItem customLine = new JMenuItem("Set properties for next line");
    private Point rightStore = new Point();
    private GraphNode connectA;
    private GraphNode connectB;
    private boolean connect = false;
    private int idCount = 1;
    private String actionString = "";
    private GraphNode[][] matrix;
    private final int spacing = 10;
    private final int pointSize = 2;
    private static final MyQueue<GraphNode> queue = new MyQueue<>();
    private long stepCount = 0;
    public long cycleBase = 0;
    public long cycleCount = 0;
    private boolean trim = true;
    private boolean consume = false;
    private boolean mutate = true;
    private boolean mutateColor = true;
    private boolean mutateHealth = false;
    private int growthType = 0;
    private int foodThreshhold = 1;
    private boolean run = false;
    private Color tempColor = null;
    private final AverageColorDisplay averageDisplay = new AverageColorDisplay();
    private GraphTupleInfo gtiStorage = null;

    public Base(int c, int r, int stepTime) {
        matrix = new GraphNode[r][c];
        canvas = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                graph.paint(g);
                FontMetrics fontSize = g.getFontMetrics();
                g.setColor(Color.black);
                int textWidth = fontSize.stringWidth("Action: " + actionString);
                g.drawString("Action: " + actionString, this.getWidth() - textWidth, 11);
                textWidth = fontSize.stringWidth("Steps taken:" + stepCount);
                g.drawString("Steps taken: " + stepCount, this.getWidth() - textWidth - 5, this.getHeight() - 11);
                if (cycleBase > 0) {
                    textWidth = fontSize.stringWidth("Cycles: " + cycleCount);
                    g.drawString("Cycles: " + cycleCount, this.getWidth() - textWidth - 5, this.getHeight() - 22);
                }//end if
            }//end paint
        };
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        canvas.setBackground(Color.white);
        canvas.setSize(500, 500);

        connectPoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (GraphNode gn : graph.nodes) {
                    if (gn.contains(rightStore)) {
                        connect = true;
                        connectA = gn;
                        break;
                    }//end if
                }//end for
                actionString = "Connect Node (unweighted)";
                canvas.repaint();
            }//end actionPerformed
        });
        step.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildQueue();
                takeStep();
            }//end actionPerformed
        });
        loop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run = !run;
                System.out.println(run);
            }//end actionPerformed
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearGrid();
            }//end actionPerformed
        });
        setColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ColorSelectionFrame(Base.this).setVisible(true);
            }//end actionPerformed
        });
        whiteOutGrid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (GraphNode[] matrix1 : matrix) {
                    for (GraphNode gn : matrix1) {
                        gn.setColor(new Color(255, 255, 255));
                        canvas.repaint();
                    }//end for
                }//end for
            }//end actionPerformed
        });
        properties.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run = false;
                new SettingsSelectionForm(Base.this).setVisible(true);
            }//end actionPerformed
        });
        averageColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                averageDisplay.setVisible(true);
            }//end actionPerformed
        });
        customLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gtiStorage = new GraphTupleInfo();
                CustomLineForm clf = new CustomLineForm();
                clf.giveStoreLocation(gtiStorage);
                clf.giveField(Base.this);
                clf.setVisible(true);
            }//end actionPerformed
        });

        rightClickMenu.add(connectPoint);
        rightClickMenu.add(step);
        rightClickMenu.add(loop);
        rightClickMenu.add(clear);
        rightClickMenu.add(setColor);
        rightClickMenu.add(whiteOutGrid);
        rightClickMenu.add(properties);
        rightClickMenu.add(averageColor);
        rightClickMenu.add(customLine);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (connect) {
                    for (GraphNode gn : graph.nodes) {
                        if (gn.contains(e.getPoint())) {
                            connectB = gn;
                            connect = false;
                            break;
                        }//end if
                    }//end for
                    if (connectB != null && connectA != null) {
                        if (connectA != connectB) {
                            if (!connectA.isConnected(connectB)) {
                                if (gtiStorage != null) {
                                    graph.biconnect(connectA, connectB, gtiStorage);
                                    gtiStorage = null;
                                }//end if
                                else {
                                    if (tempColor != null) {
                                        graph.biconnect(connectA, connectB, tempColor);
                                        tempColor = null;
                                    }//end if 
                                    else {
                                        graph.biconnect(connectA, connectB);
                                    }//end else
                                }//end else
                            }//end if
                        }//end if
                    }//end if
                    actionString = "";
                    canvas.repaint();
                    connect = false;
                    connectA = null;
                    connectB = null;
                }//end if
                else if (!connect) {
                    boolean inside = false;
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        GraphNode temp = null;
                        for (GraphNode gn : graph.nodes) {
                            if (gn.contains(e.getPoint())) {
                                inside = true;
                                temp = gn;
                                gn.print();
                                break;
                            }//end else
                        }//end for
                        if (inside) {
                            if (temp != null) {
                                System.out.println("Connector");
                                actionString = "Connect node (unweighted)";
                                connect = true;
                                connectA = temp;
                            }//end if
                        }//end if
                        canvas.repaint();
                    }//end if
                    else if (SwingUtilities.isRightMouseButton(e)) {
                        rightClickMenu.show(canvas, e.getX(), e.getY());
                    }//end else if
                }//end else
            }//end MouseClicked
        }); //end canvas mouseListener
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 's') {
                    buildQueue();
                    takeStep();
                }//end if
                else if (e.getKeyChar() == 'w') {
                    saveState();
                }//end else if
            }//end keyPressed

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        this.setSize(500, 500);
        this.add(canvas);
        initializeGrid();
        //Repaints on an interval
        ActionListener painter = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (run) {
                    buildQueue();
                    takeStep();
                    canvas.repaint();
                }//end if
            }//ectionPerformed
        };
        new Timer(stepTime, painter).start();
    }//end constructor

    public void initializeGrid() {
        for (int i = 0, ySpace = pointSize / 2; i < matrix.length; i++, ySpace += spacing) {
            for (int j = 0, xSpace = pointSize / 2; j < matrix[i].length; j++, xSpace += spacing) {
                GraphNode temp = new GraphNode(/*canvas.getWidth()+*/xSpace - pointSize / 2,/* canvas.getHeight() +*/ ySpace - pointSize / 2, pointSize, pointSize, idCount, i, j, consume);
                temp.setColor(Color.BLUE);
                idCount++;
                graph.add(temp);
                matrix[i][j] = temp;
            }//end for
        }//end for
        outlineGrid();
    }//end initializeGrid

    public void outlineGrid() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                GraphNode temp = matrix[i][j];
                try {
                    if (j == 0 || j == matrix[i].length - 1 || i == 0 || i == matrix.length - 1) {
                        temp.setColor(Color.BLACK);
                        if (j == 0) {
                            if (i == 0) {
                                graph.biconnect(temp, matrix[i + 1][j], true);
                                graph.biconnect(temp, matrix[i][j + 1], true);
                            }//end if
                            else if (i == matrix.length - 1) {
                                graph.biconnect(temp, matrix[i - 1][j], true);
                                graph.biconnect(temp, matrix[i][j + 1], true);
                            }//end if
                            else {
                                graph.biconnect(temp, matrix[i + 1][j], true);
                                graph.biconnect(temp, matrix[i - 1][j], true);
                            }//end else
                        }//end if
                        else if (j == matrix[i].length - 1) {
                            if (i == 0) {
                                graph.biconnect(temp, matrix[i + 1][j], true);
                                graph.biconnect(temp, matrix[i][j - 1], true);
                            }//end if
                            else if (i == matrix.length - 1) {
                                graph.biconnect(temp, matrix[i - 1][j], true);
                                graph.biconnect(temp, matrix[i][j - 1], true);
                            }//end if
                            else {
                                graph.biconnect(temp, matrix[i + 1][j], true);
                                graph.biconnect(temp, matrix[i - 1][j], true);
                            }//end else
                        }//end else if
                        if (i == 0) {
                            if (j == 0) {
                                graph.biconnect(temp, matrix[i + 1][j], true);
                                graph.biconnect(temp, matrix[i][j + 1], true);
                            }//end if
                            else if (j == matrix[i].length - 1) {
                                graph.biconnect(temp, matrix[i + 1][j], true);
                                graph.biconnect(temp, matrix[i][j - 1], true);
                            }//end if
                            else {
                                graph.biconnect(temp, matrix[i][j + 1], true);
                                graph.biconnect(temp, matrix[i][j - 1], true);
                            }//end else
                        }//end if
                        else if (i == matrix.length - 1) {
                            if (j == 0) {
                                graph.biconnect(temp, matrix[i - 1][j], true);
                                graph.biconnect(temp, matrix[i][j + 1], true);
                            }//end if
                            else if (j == matrix[i].length - 1) {
                                graph.biconnect(temp, matrix[i - 1][j], true);
                                graph.biconnect(temp, matrix[i][j - 1], true);
                            }//end if
                            else {
                                graph.biconnect(temp, matrix[i][j + 1], true);
                                graph.biconnect(temp, matrix[i][j - 1], true);
                            }//end else
                        }//end else if
                    }//end if
                    if (temp.iLoc == matrix.length / 2 || temp.jLoc == matrix[0].length / 2) {
                        temp.setColor(Color.BLACK);
                    }//end if
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Figure out something to do with this problem");
                }//end try catch
            }//end for
        }//end for
    }//end outlineGrid

    public void buildQueue() {
        for (GraphNode[] matrix1 : matrix) {
            for (GraphNode temp : matrix1) {
                if (temp.connections.size() == 1) {
                    queue.enqueue(temp);
                }//end if
            }//end for
        } //end for
    }//end buildQueue

    public void takeStep() {
        //stepCount++;
        //System.out.println(stepCount);
        if (growthType == 0) {
            while (queue.hasFront()) {
                GraphNode temp = queue.dequeue();
                int xCompare = (temp.x - temp.connections.get(0).location.x) / spacing;
                int yCompare = (temp.y - temp.connections.get(0).location.y) / spacing;
                if (!mutate) {
                    regularStep(temp, xCompare, yCompare);
                }//end if
                else {
                    Random rand = new Random();
                    int mutator = rand.nextInt(1000) + 1;
                    if (mutator <= temp.connections.get(0).mutatePercentage) {
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
                System.out.println("Hit");
                GraphNode temp = queue.dequeue();
                System.out.println(temp.iLoc + "," + temp.jLoc);
                System.out.println(temp.id);
                growthStep(temp);
            }//end while
        }//end else if
        if (trim) {
            decay();
        }//end if
        if (consume || growthType == 1) {
            eat();
        }//end if
        stepCount++;
        cycleCount = stepCount / cycleBase;
        canvas.repaint();
        updateAverageColor();
    }//end takeStep

    public void decay() {
        for (GraphNode[] matrix1 : matrix) {
            for (GraphNode temp : matrix1) {
                if (temp.connections.size() > 0) {
                    for (int i = 0; i < temp.connections.size(); i++) {
                        GraphTuple gt = temp.connections.get(i);
                        if (temp.iLoc != matrix.length - 1 && temp.iLoc != 0 && temp.jLoc != 0 && temp.jLoc != matrix[0].length - 1) {
                            gt.health--;
                            if (gt.health <= 0) {
                                GraphNode gn = gt.location;
                                for (int j = 0; j < gn.connections.size(); j++) {
                                    GraphTuple gt2 = gn.connections.get(j);
                                    if (gt2.location == temp) {
                                        gn.connections.remove(gt2);
                                        j--;
                                    }//end if
                                }//end for
                                temp.connections.remove(gt);
                                i--;
                            }//end if
                        }//end if
                    }//end for
                }//end if
            }//end for
        } //end for
    }//end decay

    public void regularStep(GraphNode temp, int xCompare, int yCompare) {
        GraphTuple parent = temp.connections.get(0);
        parent.reproductionClock--;
        if (parent.reproductionClock <= 0) {
            Color newColor = parent.getColor();
            int newHealth = parent.startHealth;
            int mutationPercentage = parent.mutatePercentage;
            if (temp.food > 0 || !consume) {
                if (xCompare == 1 && yCompare == 1) {
                    if (matrix[temp.iLoc][temp.jLoc + 1].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc][temp.jLoc + 1], newColor, newHealth, mutationPercentage);
                    }//end if
                    if (matrix[temp.iLoc + 1][temp.jLoc].food > 0) {
                        graph.biconnect(temp, matrix[temp.iLoc + 1][temp.jLoc], newColor, newHealth, mutationPercentage);
                    }//end if
                }//end if
                else if (xCompare == -1 && yCompare == 1) {
                    if (matrix[temp.iLoc][temp.jLoc - 1].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc][temp.jLoc - 1], newColor, newHealth, mutationPercentage);
                    }//end if
                    if (matrix[temp.iLoc + 1][temp.jLoc].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc + 1][temp.jLoc], newColor, newHealth, mutationPercentage);
                    }//end if
                }//end else if
                else if (xCompare == 1 && yCompare == -1) {
                    if (matrix[temp.iLoc][temp.jLoc + 1].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc][temp.jLoc + 1], newColor, newHealth, mutationPercentage);
                    }//end if
                    if (matrix[temp.iLoc - 1][temp.jLoc].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc - 1][temp.jLoc], newColor, newHealth, mutationPercentage);
                    }//end if
                }//end else if
                else if (xCompare == -1 && yCompare == -1) {
                    if (matrix[temp.iLoc][temp.jLoc - 1].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc][temp.jLoc - 1], newColor, newHealth, mutationPercentage);
                    }//end if
                    if (matrix[temp.iLoc - 1][temp.jLoc].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc - 1][temp.jLoc], newColor, newHealth, mutationPercentage);
                    }//end if
                }//end else if
                else if (xCompare == 0 && yCompare == 1) {
                    if (matrix[temp.iLoc + 1][temp.jLoc - 1].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc + 1][temp.jLoc - 1], newColor, newHealth, mutationPercentage);
                    }
                    if (matrix[temp.iLoc + 1][temp.jLoc + 1].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc + 1][temp.jLoc + 1], newColor, newHealth, mutationPercentage);
                    }//end if
                }//end else if
                else if (xCompare == 1 && yCompare == 0) {
                    if (matrix[temp.iLoc + 1][temp.jLoc + 1].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc + 1][temp.jLoc + 1], newColor, newHealth, mutationPercentage);
                    }//end if
                    if (matrix[temp.iLoc - 1][temp.jLoc + 1].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc - 1][temp.jLoc + 1], newColor, newHealth, mutationPercentage);
                    }//end if
                }//end else if
                else if (xCompare == -1 && yCompare == 0) {
                    if (matrix[temp.iLoc + 1][temp.jLoc - 1].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc + 1][temp.jLoc - 1], newColor, newHealth, mutationPercentage);
                    }//end if
                    if (matrix[temp.iLoc - 1][temp.jLoc - 1].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc - 1][temp.jLoc - 1], newColor, newHealth, mutationPercentage);
                    }//end if
                }//end else if
                else if (xCompare == 0 && yCompare == -1) {
                    if (matrix[temp.iLoc - 1][temp.jLoc - 1].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc - 1][temp.jLoc - 1], newColor, newHealth, mutationPercentage);
                    }//end if
                    if (matrix[temp.iLoc - 1][temp.jLoc + 1].food > 0 || !consume) {
                        graph.biconnect(temp, matrix[temp.iLoc - 1][temp.jLoc + 1], newColor, newHealth, mutationPercentage);
                    }//end if
                }//end else if
            }//end if
            parent.reproductionClock = parent.startReproductionClock;
        }//end if
    }//end regularStep

    public void mutateStep(GraphNode temp, int xCompare, int yCompare) {
        GraphTuple parent = temp.connections.get(0);
        parent.reproductionClock--;
        if (parent.reproductionClock <= 0) {
            Random rand = new Random();
            int rInfluence = rand.nextInt(51);
            int gInfluence = rand.nextInt(51);
            int bInfluence = rand.nextInt(51);
            int healthInfluence;
            int mutationPercentage = parent.mutatePercentage;
            if (parent.startHealth <= 1) {
                healthInfluence = 0;
            }//end if
            else {
                double deviation = .05;
                int variance = (int) (parent.startHealth * deviation);
                if (variance <= 1) {
                    variance = 1;
                }//end if
                System.out.println("Variance: " + variance);
                healthInfluence = rand.nextInt(variance + 1);
            }//end else
            if (rand.nextBoolean()) {
                rInfluence = rInfluence * -1;
            }//end if
            if (rand.nextBoolean()) {
                gInfluence = gInfluence * -1;
            }//end if
            if (rand.nextBoolean()) {
                bInfluence = bInfluence * -1;
            }//end if
            if (rand.nextBoolean()) {
                healthInfluence = healthInfluence * -1;
            }//end if
            int red = parent.getColor().getRed();
            if (rand.nextBoolean() && mutateColor) {
                System.out.println("red");
                red = parent.getColor().getRed() + rInfluence;
            }//end if
            int green = parent.getColor().getGreen();
            if (rand.nextBoolean() && mutateColor) {
                System.out.println("green");
                green = parent.getColor().getGreen() + gInfluence;
            }//end if
            int blue = parent.getColor().getBlue();
            if (rand.nextBoolean() && mutateColor) {
                System.out.println("blue");
                blue = parent.getColor().getBlue() + bInfluence;
            }//end if
            int newHealth = parent.startHealth;
            if (rand.nextBoolean() && mutateHealth) {
                newHealth = parent.startHealth + healthInfluence;
                System.out.println("Old health: " + parent.startHealth + " New health: " + newHealth);
            }//end if
            if (red > 255) {
                red = 255;
            }//end if
            else if (red < 0) {
                red = 0;
            }//end else if
            if (green > 255) {
                green = 255;
            }//end if
            else if (green < 0) {
                green = 0;
            }//end else if
            if (blue > 255) {
                blue = 255;
            }//end if
            else if (blue < 0) {
                blue = 0;
            }//end else if
            if (newHealth < 0) {
                newHealth = 0;
            }//end if
            Color newColor = new Color(red, green, blue);
            if (xCompare == 1 && yCompare == 1) {
                graph.biconnect(temp, matrix[temp.iLoc][temp.jLoc + 1], newColor, newHealth, mutationPercentage);
                graph.biconnect(temp, matrix[temp.iLoc + 1][temp.jLoc], newColor, newHealth, mutationPercentage);
            }//end if
            else if (xCompare == -1 && yCompare == 1) {
                graph.biconnect(temp, matrix[temp.iLoc][temp.jLoc - 1], newColor, newHealth, mutationPercentage);
                graph.biconnect(temp, matrix[temp.iLoc + 1][temp.jLoc], newColor, newHealth, mutationPercentage);
            }//end else if
            else if (xCompare == 1 && yCompare == -1) {
                graph.biconnect(temp, matrix[temp.iLoc][temp.jLoc + 1], newColor, newHealth, mutationPercentage);
                graph.biconnect(temp, matrix[temp.iLoc - 1][temp.jLoc], newColor, newHealth, mutationPercentage);
            }//end else if
            else if (xCompare == -1 && yCompare == -1) {
                graph.biconnect(temp, matrix[temp.iLoc][temp.jLoc - 1], newColor, newHealth, mutationPercentage);
                graph.biconnect(temp, matrix[temp.iLoc - 1][temp.jLoc], newColor, newHealth, mutationPercentage);
            }//end else if
            else if (xCompare == 0 && yCompare == 1) {
                graph.biconnect(temp, matrix[temp.iLoc + 1][temp.jLoc - 1], newColor, newHealth, mutationPercentage);
                graph.biconnect(temp, matrix[temp.iLoc + 1][temp.jLoc + 1], newColor, newHealth, mutationPercentage);
            }//end else if
            else if (xCompare == 1 && yCompare == 0) {
                graph.biconnect(temp, matrix[temp.iLoc + 1][temp.jLoc + 1], newColor, newHealth, mutationPercentage);
                graph.biconnect(temp, matrix[temp.iLoc - 1][temp.jLoc + 1], newColor, newHealth, mutationPercentage);
            }//end else if
            else if (xCompare == -1 && yCompare == 0) {
                graph.biconnect(temp, matrix[temp.iLoc + 1][temp.jLoc - 1], newColor, newHealth, mutationPercentage);
                graph.biconnect(temp, matrix[temp.iLoc - 1][temp.jLoc - 1], newColor, newHealth, mutationPercentage);
            }//end else if
            else if (xCompare == 0 && yCompare == -1) {
                graph.biconnect(temp, matrix[temp.iLoc - 1][temp.jLoc - 1], newColor, newHealth, mutationPercentage);
                graph.biconnect(temp, matrix[temp.iLoc - 1][temp.jLoc + 1], newColor, newHealth, mutationPercentage);
            }//end else if
            parent.reproductionClock = parent.startReproductionClock;
        }//end if
    }//end mutateStep

    public void growthStep(GraphNode temp) {
        GraphNode target = null;
        int greatestFood = 0;
        ArrayList<GraphNode> sameNodes = new ArrayList<>();
        if (temp.jLoc > 0 && temp.iLoc > 0 && temp.jLoc < matrix[0].length - 1 && temp.iLoc < matrix.length - 1) {
            //Top Left Check
            if (matrix[temp.iLoc - 1][temp.jLoc - 1] != temp && matrix[temp.iLoc - 1][temp.jLoc - 1].food > greatestFood && matrix[temp.iLoc - 1][temp.jLoc - 1].food > 0) {
                greatestFood = matrix[temp.iLoc - 1][temp.jLoc - 1].food;
                target = matrix[temp.iLoc - 1][temp.jLoc - 1];
            }//end if
            //Top Middle Check
            if (matrix[temp.iLoc - 1][temp.jLoc] != temp && matrix[temp.iLoc - 1][temp.jLoc].food > greatestFood) {
                greatestFood = matrix[temp.iLoc - 1][temp.jLoc].food;
                target = matrix[temp.iLoc - 1][temp.jLoc];
                sameNodes.clear();
            }//end if
            else if (matrix[temp.iLoc - 1][temp.jLoc] != temp && matrix[temp.iLoc - 1][temp.jLoc].food > greatestFood && matrix[temp.iLoc - 1][temp.jLoc].food > 0) {
                sameNodes.add(matrix[temp.iLoc - 1][temp.jLoc]);
            }//end else if
            //Top Right Check
            if (matrix[temp.iLoc - 1][temp.jLoc + 1] != temp && matrix[temp.iLoc - 1][temp.jLoc + 1].food > greatestFood && matrix[temp.iLoc - 1][temp.jLoc + 1].food > 0) {
                greatestFood = matrix[temp.iLoc - 1][temp.jLoc + 1].food;
                target = matrix[temp.iLoc - 1][temp.jLoc + 1];
                sameNodes.clear();
            }//end if
            else if (matrix[temp.iLoc - 1][temp.jLoc + 1] != temp && matrix[temp.iLoc - 1][temp.jLoc + 1].food > greatestFood && matrix[temp.iLoc - 1][temp.jLoc + 1].food > 0) {
                sameNodes.add(matrix[temp.iLoc - 1][temp.jLoc + 1]);
            }//end else if
            //Middle Left Check
            if (matrix[temp.iLoc][temp.jLoc - 1] != temp && matrix[temp.iLoc][temp.jLoc - 1].food > greatestFood && matrix[temp.iLoc][temp.jLoc - 1].food > 0) {
                greatestFood = matrix[temp.iLoc][temp.jLoc - 1].food;
                target = matrix[temp.iLoc][temp.jLoc - 1];
                sameNodes.clear();
            }//end if
            else if (matrix[temp.iLoc][temp.jLoc - 1] != temp && matrix[temp.iLoc][temp.jLoc - 1].food > greatestFood && matrix[temp.iLoc][temp.jLoc - 1].food > 0) {
                sameNodes.add(matrix[temp.iLoc][temp.jLoc - 1]);
            }//end else if
            //Middle Right Check
            if (matrix[temp.iLoc][temp.jLoc + 1] != temp && matrix[temp.iLoc][temp.jLoc + 1].food > greatestFood && matrix[temp.iLoc][temp.jLoc + 1].food > 0) {
                greatestFood = matrix[temp.iLoc][temp.jLoc + 1].food;
                target = matrix[temp.iLoc][temp.jLoc - 1];
                sameNodes.clear();
            }//end if
            else if (matrix[temp.iLoc][temp.jLoc + 1] != temp && matrix[temp.iLoc][temp.jLoc + 1].food > greatestFood && matrix[temp.iLoc][temp.jLoc + 1].food > 0) {
                sameNodes.add(matrix[temp.iLoc][temp.jLoc + 1]);
            }//end else if
            //Bottom Left Check
            if (matrix[temp.iLoc + 1][temp.jLoc - 1] != temp) {
                if (matrix[temp.iLoc + 1][temp.jLoc - 1].food > greatestFood && matrix[temp.iLoc + 1][temp.jLoc - 1].food > 0) {
                    greatestFood = matrix[temp.iLoc + 1][temp.jLoc - 1].food;
                    target = matrix[temp.iLoc + 1][temp.jLoc - 1];
                    sameNodes.clear();
                }//end if
            }//end if
            else if (matrix[temp.iLoc + 1][temp.jLoc - 1] != temp && matrix[temp.iLoc + 1][temp.jLoc - 1].food > greatestFood && matrix[temp.iLoc + 1][temp.jLoc - 1].food > 0) {
                sameNodes.add(matrix[temp.iLoc + 1][temp.jLoc - 1]);
            }//end else if
            //Bottom Middle Check
            if (matrix[temp.iLoc + 1][temp.jLoc] != temp && matrix[temp.iLoc + 1][temp.jLoc].food > greatestFood && matrix[temp.iLoc + 1][temp.jLoc].food > 0) {
                greatestFood = matrix[temp.iLoc + 1][temp.jLoc].food;
                target = matrix[temp.iLoc + 1][temp.jLoc];
                sameNodes.clear();
            }//end if
            else if (matrix[temp.iLoc + 1][temp.jLoc] != temp && matrix[temp.iLoc + 1][temp.jLoc].food > greatestFood && matrix[temp.iLoc + 1][temp.jLoc].food > 0) {
                sameNodes.add(matrix[temp.iLoc + 1][temp.jLoc]);
            }//end else if
            //Bottom Left Check
            if (matrix[temp.iLoc + 1][temp.jLoc + 1] != temp && matrix[temp.iLoc + 1][temp.jLoc + 1].food > greatestFood && matrix[temp.iLoc + 1][temp.jLoc + 1].food > 0) {
                target = matrix[temp.iLoc + 1][temp.jLoc + 1];
                sameNodes.clear();
            }//end if
            else if (matrix[temp.iLoc + 1][temp.jLoc + 1] != temp && matrix[temp.iLoc + 1][temp.jLoc + 1].food > greatestFood && matrix[temp.iLoc + 1][temp.jLoc - +1].food > 0) {
                sameNodes.add(matrix[temp.iLoc + 1][temp.jLoc + 1]);
            }//end else if
            //Check to see if there are nodes in the list of possible targets
            if (sameNodes.isEmpty()) {
                graph.biconnect(temp, target, temp.connections.get(0).getColor());
            }//end if
            else {
                graph.biconnect(temp, sameNodes.get(0), temp.connections.get(0).getColor());
            }//end else
        }//end if
    }//end growthStep

    public void eat() {
        for (GraphNode[] matrix1 : matrix) {
            for (GraphNode gn : matrix1) {
                for (GraphTuple gt : gn.connections) {
                    gt.location.food--;
                }//end for
                if (gn.food < 0) {
                    gn.food = 0;
                }//end if
                if (gn.food >= foodThreshhold) {
                    gn.regenFood();
                }//end if
            }//end for
        }//end for
    }//end eat

    public void clearGrid() {
        for (GraphNode[] matrix1 : matrix) {
            for (GraphNode gn : matrix1) {
                gn.connections.clear();
            }//end for
        }//end for
        outlineGrid();
        canvas.repaint();
    }//end clearGrid

    public void setTempColor(Color c) {
        tempColor = c;
    }//end setColor

    public Color getTempColor() {
        if (tempColor != null) {
            return new Color(tempColor.getRed(), tempColor.getGreen(), tempColor.getBlue());
        }//end if
        else {
            return null;
        }//end else
    }//end getColor

    public void updateAverageColor() {
        int redVal = 0;
        int greenVal = 0;
        int blueVal = 0;
        int numOfConnections = 0;
        for (GraphNode gn : graph.nodes) {
            for (GraphTuple gt : gn.connections) {
                if (!gt.isEdge()) {
                    redVal += gt.r;
                    greenVal += gt.g;
                    blueVal += gt.b;
                    numOfConnections++;
                }//end if
            }//end for
        }//end for
        if (numOfConnections > 0) {
            redVal = redVal / numOfConnections;
            greenVal = greenVal / numOfConnections;
            blueVal = blueVal / numOfConnections;
            averageDisplay.updateColor(new Color(redVal, greenVal, blueVal));
        }//end if
    }//end updateAverageColor

    public void saveState() {
        String connectionOutString = "";
        String nodeListOutString = matrix.length + " " + matrix[0].length + "\n";
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                String tempString = "";
                tempString += "Node: " + i + " " + j + "\n";
                for (GraphTuple gt : matrix[i][j].connections) {
                    GraphNode gn = gt.location;
                    tempString += "Connection: " + gn.iLoc + " " + gn.jLoc + " " + gn.getColor().getRed() + " " + gn.getColor().getGreen() + " " + gn.getColor().getBlue() + "\n";
                }//end for
                connectionOutString += tempString;
            }//end for
        }//end for
        System.out.println(nodeListOutString);
        System.out.println(connectionOutString);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("save2.lsv", "UTF-8");
            writer.print(nodeListOutString);
            writer.print(connectionOutString);
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
        } finally {
            if (writer != null) {
                writer.close();
                System.out.println("Done Saving");
            }//end if
        }
    }
}//end Base class
