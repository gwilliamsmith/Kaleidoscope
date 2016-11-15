package SwingElements;

import EventScheduler.Events.PlaceLineEvent;
import graphvisualizer.GraphNode;
import graphvisualizer.GraphTupleInfo;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Form used to input the details for a line creation event.
 */
public class LineEventDetailsInputForm extends javax.swing.JFrame implements Runnable {

    private SchedulerForm parent;                                   //The SchedulerForm that spawned this form
    private Base sim;                                               //Base object for reference
    public int stepCount;                                           //The step count at which to execute the generated event
    public String eventName;                                        //The name of the event
    public boolean repeat;                                          //Repeat event or single event
    public GraphTupleInfo gti = new GraphTupleInfo();               //GraphTupleInfo object describing the line to be created
    public GraphNode selectedNode1 = null;                          //The first node used for line creation
    public GraphNode selectedNode2 = null;                          //The second node used for line creation
    private ArrayList<GraphNode> adjacentNodes;                     //ArrayList of nodes adjacent to the first selected node
    private PlaceLineEvent temp = null;                             //The event to be created/edited ***Rename this***

    /**
     * Constructor.
     *
     * @param in The {@link SchedulerForm} that spawned the form
     * @param simIn The {@link Base} object used to access other objects as
     * needed
     */
    public LineEventDetailsInputForm(SchedulerForm in, Base simIn) {
        parent = in;
        sim = simIn;
        initComponents();
        setupNode1Spinners();
        selectedNode1 = sim.getGraph().getNode(0, 0);
        sim.getGraph().highlightNodeAdjacents(selectedNode1, GraphNode.LINE_EVENT_NODE1_COLOR, GraphNode.LINE_EVENT_NODE1_ADJACENT_COLOR);
        updateNode2ComboBox();
    }//end constructor

    /**
     * Constructor.
     *
     * @param in The {@link SchedulerForm} that spawned the form
     * @param simIn The {@link Base} object used to access other objects as
     * needed
     * @param e The {@link PlaceLineEvent} to be created/edited
     */
    public LineEventDetailsInputForm(SchedulerForm in, Base simIn, PlaceLineEvent e) {
        parent = in;
        sim = simIn;
        initComponents();

        stepCount = e.getStepTarget();
        eventName = e.getEventName();
        repeat = e.getRepeat();
        gti = e.getGti();
        temp = e;

        TriggerStepTextField.setText(Integer.toString(stepCount));
        EventNameTextField.setText(eventName);
        if (repeat) {
            RepeatEventComboBox.setSelectedIndex(0);
        }//end if
        else {
            RepeatEventComboBox.setSelectedIndex(1);
        }//end else

        setupNode1Spinners();

        selectedNode1 = e.getNode1();
        Node1XSpinner.setValue(selectedNode1.getJLoc());
        Node1YSpinner.setValue(selectedNode1.getILoc());
        updateNode2ComboBox();
        selectedNode2 = e.getNode2();
        Node2SelectionComboBox.setSelectedItem(selectedNode2.printCoordinates());

        sim.getGraph().highlightNodeAdjacents(selectedNode1, GraphNode.LINE_EVENT_NODE1_COLOR, GraphNode.LINE_EVENT_NODE1_ADJACENT_COLOR);
        sim.getGraph().highlightNode(selectedNode2, GraphNode.LINE_EVENT_NODE2_COLOR);
    }//end constructor

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        EventNameLabel = new javax.swing.JLabel();
        EventNameTextField = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        Node1Label = new javax.swing.JLabel();
        Node1XSpinner = new javax.swing.JSpinner();
        Node1YSpinner = new javax.swing.JSpinner();
        jPanel6 = new javax.swing.JPanel();
        RepeatEventLabel = new javax.swing.JLabel();
        RepeatEventComboBox = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        TriggerStepLabel = new javax.swing.JLabel();
        TriggerStepTextField = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        Node2Label = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Node2SelectionComboBox = new javax.swing.JComboBox();
        jPanel10 = new javax.swing.JPanel();
        ChangeLinePropertiesButton = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        CreateEventButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(null);

        jPanel2.setMaximumSize(new java.awt.Dimension(210, 100));
        jPanel2.setMinimumSize(new java.awt.Dimension(210, 100));
        jPanel2.setPreferredSize(new java.awt.Dimension(210, 100));

        jPanel3.setMaximumSize(new java.awt.Dimension(210, 25));
        jPanel3.setMinimumSize(new java.awt.Dimension(210, 25));
        jPanel3.setPreferredSize(new java.awt.Dimension(210, 25));

        EventNameLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        EventNameLabel.setText(" Event Name: ");
        EventNameLabel.setMaximumSize(new java.awt.Dimension(100, 25));
        EventNameLabel.setMinimumSize(new java.awt.Dimension(100, 25));
        EventNameLabel.setPreferredSize(new java.awt.Dimension(100, 25));

        EventNameTextField.setMaximumSize(new java.awt.Dimension(105, 25));
        EventNameTextField.setMinimumSize(new java.awt.Dimension(105, 25));
        EventNameTextField.setPreferredSize(new java.awt.Dimension(105, 25));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(EventNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(EventNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EventNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(EventNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setMaximumSize(new java.awt.Dimension(210, 50));
        jPanel4.setMinimumSize(new java.awt.Dimension(210, 50));
        jPanel4.setPreferredSize(new java.awt.Dimension(210, 50));

        Node1Label.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        Node1Label.setText(" Node 1 Coordinates: ");
        Node1Label.setMaximumSize(new java.awt.Dimension(105, 50));
        Node1Label.setMinimumSize(new java.awt.Dimension(105, 50));
        Node1Label.setPreferredSize(new java.awt.Dimension(105, 25));

        Node1XSpinner.setMaximumSize(new java.awt.Dimension(105, 25));
        Node1XSpinner.setMinimumSize(new java.awt.Dimension(105, 25));
        Node1XSpinner.setPreferredSize(new java.awt.Dimension(105, 25));
        Node1XSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Node1XSpinnerStateChanged(evt);
            }
        });

        Node1YSpinner.setMaximumSize(new java.awt.Dimension(105, 25));
        Node1YSpinner.setMinimumSize(new java.awt.Dimension(105, 25));
        Node1YSpinner.setPreferredSize(new java.awt.Dimension(105, 25));
        Node1YSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Node1YSpinnerStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(Node1Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Node1XSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Node1YSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Node1Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(Node1XSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Node1YSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.setMaximumSize(new java.awt.Dimension(210, 25));
        jPanel6.setMinimumSize(new java.awt.Dimension(210, 25));
        jPanel6.setPreferredSize(new java.awt.Dimension(210, 25));

        RepeatEventLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        RepeatEventLabel.setText(" Repeat Event? ");
        RepeatEventLabel.setMaximumSize(new java.awt.Dimension(105, 25));
        RepeatEventLabel.setMinimumSize(new java.awt.Dimension(105, 25));
        RepeatEventLabel.setPreferredSize(new java.awt.Dimension(105, 25));

        RepeatEventComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        RepeatEventComboBox.setMaximumSize(new java.awt.Dimension(105, 25));
        RepeatEventComboBox.setMinimumSize(new java.awt.Dimension(105, 25));
        RepeatEventComboBox.setPreferredSize(new java.awt.Dimension(105, 25));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(RepeatEventLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(RepeatEventComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RepeatEventLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RepeatEventComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.setMaximumSize(new java.awt.Dimension(210, 100));
        jPanel7.setMinimumSize(new java.awt.Dimension(210, 100));
        jPanel7.setPreferredSize(new java.awt.Dimension(210, 100));

        jPanel8.setMaximumSize(new java.awt.Dimension(210, 25));
        jPanel8.setMinimumSize(new java.awt.Dimension(210, 25));
        jPanel8.setPreferredSize(new java.awt.Dimension(210, 25));

        TriggerStepLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        TriggerStepLabel.setText(" Trigger Step: ");
        TriggerStepLabel.setMaximumSize(new java.awt.Dimension(105, 25));
        TriggerStepLabel.setMinimumSize(new java.awt.Dimension(105, 25));
        TriggerStepLabel.setPreferredSize(new java.awt.Dimension(105, 25));

        TriggerStepTextField.setMaximumSize(new java.awt.Dimension(105, 25));
        TriggerStepTextField.setMinimumSize(new java.awt.Dimension(105, 25));
        TriggerStepTextField.setPreferredSize(new java.awt.Dimension(105, 25));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(TriggerStepLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TriggerStepTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(TriggerStepLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TriggerStepTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel9.setMaximumSize(new java.awt.Dimension(210, 50));
        jPanel9.setMinimumSize(new java.awt.Dimension(210, 50));
        jPanel9.setPreferredSize(new java.awt.Dimension(210, 50));

        Node2Label.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        Node2Label.setText(" Node 2 Coordinates: ");
        Node2Label.setMaximumSize(new java.awt.Dimension(105, 25));
        Node2Label.setMinimumSize(new java.awt.Dimension(105, 25));
        Node2Label.setPreferredSize(new java.awt.Dimension(105, 25));

        jPanel1.setMaximumSize(new java.awt.Dimension(105, 50));
        jPanel1.setMinimumSize(new java.awt.Dimension(105, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(105, 50));

        Node2SelectionComboBox.setMaximumSize(new java.awt.Dimension(105, 25));
        Node2SelectionComboBox.setMinimumSize(new java.awt.Dimension(105, 25));
        Node2SelectionComboBox.setPreferredSize(new java.awt.Dimension(105, 25));
        Node2SelectionComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Node2SelectionComboBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(Node2SelectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(Node2SelectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(Node2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Node2Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel10.setMaximumSize(new java.awt.Dimension(210, 25));
        jPanel10.setMinimumSize(new java.awt.Dimension(210, 25));
        jPanel10.setPreferredSize(new java.awt.Dimension(210, 25));

        ChangeLinePropertiesButton.setText("Change Line Properties");
        ChangeLinePropertiesButton.setMaximumSize(new java.awt.Dimension(210, 25));
        ChangeLinePropertiesButton.setMinimumSize(new java.awt.Dimension(210, 25));
        ChangeLinePropertiesButton.setPreferredSize(new java.awt.Dimension(210, 25));
        ChangeLinePropertiesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangeLinePropertiesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(ChangeLinePropertiesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(ChangeLinePropertiesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel11.setMaximumSize(new java.awt.Dimension(420, 25));
        jPanel11.setMinimumSize(new java.awt.Dimension(420, 25));
        jPanel11.setPreferredSize(new java.awt.Dimension(420, 25));

        CreateEventButton.setText("Create Event");
        CreateEventButton.setMaximumSize(new java.awt.Dimension(150, 25));
        CreateEventButton.setMinimumSize(new java.awt.Dimension(150, 25));
        CreateEventButton.setPreferredSize(new java.awt.Dimension(150, 25));
        CreateEventButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateEventButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(135, Short.MAX_VALUE)
                .addComponent(CreateEventButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(CreateEventButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action method for the create button. Checks, then enters all information
     * into the {@link PlaceLineEvent} object.
     */
    private void CreateEventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateEventButtonActionPerformed
        stepCount = checkTextField(TriggerStepTextField, "Trigger Step");
        eventName = EventNameTextField.getText();
        repeat = RepeatEventComboBox.getSelectedItem().equals("Yes");
        if (stepCount != -1) {
            if (temp == null) {
                parent.generateEvent();
            }//end if
            else {
                temp.setStepTarget(stepCount);
                temp.setEventName(eventName);
                temp.setRepeat(repeat);
                temp.setNode1(selectedNode1);
                temp.setNode2(selectedNode2);
                temp.setGti(gti);
                parent.updateRepeatEventList();
                parent.updateSingleEventList();
            }//end else
            this.dispose();
        }//end if
    }//GEN-LAST:event_CreateEventButtonActionPerformed

    /**
     * Action method for the change line properties button. Generates a
     * {@link CustomLineForm}.
     */
    private void ChangeLinePropertiesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangeLinePropertiesButtonActionPerformed
        SwingUtilities.invokeLater(new CustomLineForm(this));
    }//GEN-LAST:event_ChangeLinePropertiesButtonActionPerformed

    /**
     * Change method for the Node1X Spinner. Changes which nodes are highlighted
     * on the grid.
     */
    private void Node1XSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Node1XSpinnerStateChanged
        sim.getGraph().resetNodeAdjacents(selectedNode1);
        selectedNode1 = sim.getGraph().getNode((int) Node1XSpinner.getValue(), (int) Node1YSpinner.getValue());
        sim.getGraph().highlightNodeAdjacents(selectedNode1, GraphNode.LINE_EVENT_NODE1_COLOR, GraphNode.LINE_EVENT_NODE1_ADJACENT_COLOR);
        updateNode2ComboBox();
    }//GEN-LAST:event_Node1XSpinnerStateChanged

    /**
     * Change method for the Node1Y Spinner. Changes which nodes are highlighted
     * on the grid.
     */
    private void Node1YSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Node1YSpinnerStateChanged
        sim.getGraph().resetNodeAdjacents(selectedNode1);
        selectedNode1 = sim.getGraph().getNode((int) Node1XSpinner.getValue(), (int) Node1YSpinner.getValue());
        sim.getGraph().highlightNodeAdjacents(selectedNode1, GraphNode.LINE_EVENT_NODE1_COLOR, GraphNode.LINE_EVENT_NODE1_ADJACENT_COLOR);
        updateNode2ComboBox();
    }//GEN-LAST:event_Node1YSpinnerStateChanged

    /**
     * Change method for the Node2 combo box. Sets which node is the second node
     * for line creation, and highlights that node on the grid.
     */
    private void Node2SelectionComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Node2SelectionComboBoxItemStateChanged
        if (selectedNode2 != null) {
            sim.getGraph().highlightNodeAdjacents(selectedNode1, GraphNode.LINE_EVENT_NODE1_COLOR, GraphNode.LINE_EVENT_NODE1_ADJACENT_COLOR);
        }//end if
        if (Node2SelectionComboBox.getSelectedIndex() != -1) {
            GraphNode gn = adjacentNodes.get(Node2SelectionComboBox.getSelectedIndex());
            System.out.println(adjacentNodes.get(Node2SelectionComboBox.getSelectedIndex()).printCoordinates());
            sim.getGraph().highlightNode(adjacentNodes.get(Node2SelectionComboBox.getSelectedIndex()), GraphNode.LINE_EVENT_NODE2_COLOR);
            selectedNode2 = gn;
            System.out.println("hit");
        }//end if
    }//GEN-LAST:event_Node2SelectionComboBoxItemStateChanged

    /**
     * Checks a given {@link JTextField} to ensure that the input is numerical
     * and greater than 0.
     *
     * @param field The {@link JTextField} to be checked.
     * @param title The title for the error message, should it be generated.
     * @return The final integer value after checking is performed
     */
    private int checkTextField(JTextField field, String title) {
        int out;
        try {
            out = Integer.parseInt(field.getText());
            if (out <= 0) {
                JOptionPane.showMessageDialog(this, "Enter a number greater than 0!", title, JOptionPane.ERROR_MESSAGE);
                out = -1;
            }//end if
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "You must enter a number!", title, JOptionPane.ERROR_MESSAGE);
            out = -1;
        }//end tryCatch
        return out;
    }//end checkTextField

    /**
     * UPdates the contents of the Node2 selection combo box to fit nodes
     * adjacent to the selected Node1.
     */
    private void updateNode2ComboBox() {
        adjacentNodes = sim.getGraph().findAdjacentNodes(selectedNode1);
        ArrayList<String> adjacentNodesStrings = new ArrayList<>();
        for (GraphNode gn : adjacentNodes) {
            adjacentNodesStrings.add(gn.printCoordinates());
        }//end for
        DefaultComboBoxModel model = new DefaultComboBoxModel(adjacentNodesStrings.toArray());
        Node2SelectionComboBox.setModel(model);
        Node2SelectionComboBox.setSelectedIndex(-1);
    }//end updateNode2ComboBox

    /**
     * Populates the Node1 X and Y spinners with possible values.
     */
    private void setupNode1Spinners() {
        SpinnerNumberModel node1XModel = new SpinnerNumberModel(0, 0, sim.getGraph().getMatrix()[0].length - 1, 1);
        SpinnerNumberModel node1YModel = new SpinnerNumberModel(0, 0, sim.getGraph().getMatrix().length - 1, 1);
        Node1XSpinner.setModel(node1XModel);
        Node1YSpinner.setModel(node1YModel);
    }//end setupNode1Spinners

    @Override
    /**
     * Resets node color on window close.
     */
    public void dispose() {
        sim.getGraph().resetNodeAdjacents(selectedNode1);
        super.dispose();
    }//end dispose

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ChangeLinePropertiesButton;
    private javax.swing.JButton CreateEventButton;
    private javax.swing.JLabel EventNameLabel;
    private javax.swing.JTextField EventNameTextField;
    private javax.swing.JLabel Node1Label;
    private javax.swing.JSpinner Node1XSpinner;
    private javax.swing.JSpinner Node1YSpinner;
    private javax.swing.JLabel Node2Label;
    private javax.swing.JComboBox Node2SelectionComboBox;
    private javax.swing.JComboBox RepeatEventComboBox;
    private javax.swing.JLabel RepeatEventLabel;
    private javax.swing.JLabel TriggerStepLabel;
    private javax.swing.JTextField TriggerStepTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables

    @Override
    /**
     * Runs the form.
     */
    public void run() {
        if (sim != null && parent != null) {
            setVisible(true);
        }//end if
    }//end run
}
