package SwingElements;

import graphvisualizer.Graph;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Form to change global runtime settings.
 */
public class PropertiesSelectionForm extends javax.swing.JFrame implements Runnable {

    private Base ref;                                       //Base object, used for reference
    private Canvas canvas;                                  //Canvas object, draws grid nodes/lines
    private Graph graph;                                    //Graph object, stores information regarding lines and nodes

    /**
     * Constructor.
     *
     * @param b {@link Base} object for reference
     */
    public PropertiesSelectionForm(Base b) {
        ref = b;
        canvas = ref.getCanvas();
        graph = ref.getGraph();
        ref.pause();
        this.setTitle("Properties");
        initComponents();
        setUpUIFields();
    }//end constructor

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CanvasVariablePanel = new javax.swing.JPanel();
        CanvasVariableLabel = new javax.swing.JLabel();
        PointSpacingLabel = new javax.swing.JLabel();
        PointSizeLabel = new javax.swing.JLabel();
        PointSpacingTextField = new javax.swing.JTextField();
        PointSizeTextField = new javax.swing.JTextField();
        ResizeGridButton = new javax.swing.JButton();
        SeedingVariablePanel = new javax.swing.JPanel();
        ColoringBookSeedOptionsLabel = new javax.swing.JLabel();
        OneLineLabel = new javax.swing.JLabel();
        OnePairLabel = new javax.swing.JLabel();
        TwoPairsLabel = new javax.swing.JLabel();
        FourPairsLabel = new javax.swing.JLabel();
        OnePairCheckBox = new javax.swing.JCheckBox();
        OneLineCheckBox = new javax.swing.JCheckBox();
        TwoPairsCheckBox = new javax.swing.JCheckBox();
        FourPairsCheckBox = new javax.swing.JCheckBox();
        RuntimeVariablePanel = new javax.swing.JPanel();
        GraphRuntimeVariablesLabel = new javax.swing.JLabel();
        TrimLinesLabel = new javax.swing.JLabel();
        ConsumeLabel = new javax.swing.JLabel();
        MutateHealthLabel = new javax.swing.JLabel();
        GrowthTypeLabel = new javax.swing.JLabel();
        GrowthTypeComboBox = new javax.swing.JComboBox();
        MutateColorLabel = new javax.swing.JLabel();
        MutateHealthComboBox = new javax.swing.JComboBox();
        MutateColorComboBox = new javax.swing.JComboBox();
        TrimComboBox = new javax.swing.JComboBox();
        ConsumeComboBox = new javax.swing.JComboBox();
        ButtonsPanel = new javax.swing.JPanel();
        ApplyButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Properties");
        setName("PropertiesFrame"); // NOI18N

        CanvasVariablePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        CanvasVariablePanel.setPreferredSize(new java.awt.Dimension(400, 125));

        CanvasVariableLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        CanvasVariableLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CanvasVariableLabel.setText("Canvas Variables");
        CanvasVariableLabel.setToolTipText("Variables that affect the grid and how it's displayed");
        CanvasVariableLabel.setAlignmentX(0.5F);
        CanvasVariableLabel.setInheritsPopupMenu(false);

        PointSpacingLabel.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        PointSpacingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PointSpacingLabel.setText("Point Spacing");
        PointSpacingLabel.setToolTipText("The amount of space between points on the grid");

        PointSizeLabel.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        PointSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PointSizeLabel.setText("Point Size");
        PointSizeLabel.setToolTipText("The size of points on the grid");

        PointSizeTextField.setMinimumSize(new java.awt.Dimension(82, 20));
        PointSizeTextField.setPreferredSize(new java.awt.Dimension(82, 20));

        ResizeGridButton.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        ResizeGridButton.setText("Resize Grid");
        ResizeGridButton.setToolTipText("Change the number of rows, columns, and the time between steps");
        ResizeGridButton.setMaximumSize(new java.awt.Dimension(90, 25));
        ResizeGridButton.setMinimumSize(new java.awt.Dimension(90, 25));
        ResizeGridButton.setPreferredSize(new java.awt.Dimension(90, 25));
        ResizeGridButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResizeGridButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CanvasVariablePanelLayout = new javax.swing.GroupLayout(CanvasVariablePanel);
        CanvasVariablePanel.setLayout(CanvasVariablePanelLayout);
        CanvasVariablePanelLayout.setHorizontalGroup(
                CanvasVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(CanvasVariableLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(CanvasVariablePanelLayout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addGroup(CanvasVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(PointSpacingTextField)
                                        .addComponent(PointSpacingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                                .addGroup(CanvasVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(PointSizeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(PointSizeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(78, 78, 78))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CanvasVariablePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ResizeGridButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(153, 153, 153))
        );
        CanvasVariablePanelLayout.setVerticalGroup(
                CanvasVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(CanvasVariablePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(CanvasVariableLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(CanvasVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(PointSpacingLabel)
                                        .addComponent(PointSizeLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CanvasVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(PointSpacingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(PointSizeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addComponent(ResizeGridButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(28, Short.MAX_VALUE))
        );

        SeedingVariablePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        SeedingVariablePanel.setMinimumSize(new java.awt.Dimension(800, 0));
        SeedingVariablePanel.setPreferredSize(new java.awt.Dimension(400, 125));

        ColoringBookSeedOptionsLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        ColoringBookSeedOptionsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ColoringBookSeedOptionsLabel.setText("Coloring Book Seed Options");
        ColoringBookSeedOptionsLabel.setToolTipText("Variables that affect the settings for random initial pattern configuration");

        OneLineLabel.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        OneLineLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OneLineLabel.setText("One Line");
        OneLineLabel.setToolTipText("Enables placing one line, on one line of symmetry");
        OneLineLabel.setMaximumSize(new java.awt.Dimension(82, 14));
        OneLineLabel.setMinimumSize(new java.awt.Dimension(82, 14));
        OneLineLabel.setPreferredSize(new java.awt.Dimension(82, 14));

        OnePairLabel.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        OnePairLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OnePairLabel.setText("One Pair");
        OnePairLabel.setToolTipText("Enables placing a pair of lines, on one line of symmetry");
        OnePairLabel.setMaximumSize(new java.awt.Dimension(82, 14));
        OnePairLabel.setMinimumSize(new java.awt.Dimension(82, 14));
        OnePairLabel.setPreferredSize(new java.awt.Dimension(82, 14));

        TwoPairsLabel.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        TwoPairsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TwoPairsLabel.setText("Two Pairs");
        TwoPairsLabel.setToolTipText("Enables placing two pairs of lines, on two lines of symmetry");
        TwoPairsLabel.setMaximumSize(new java.awt.Dimension(82, 14));
        TwoPairsLabel.setMinimumSize(new java.awt.Dimension(82, 14));
        TwoPairsLabel.setPreferredSize(new java.awt.Dimension(82, 14));

        FourPairsLabel.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        FourPairsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FourPairsLabel.setText("Four Pairs");
        FourPairsLabel.setToolTipText("Enables placing four pairs of lines, on all lines of symmetry");
        FourPairsLabel.setMaximumSize(new java.awt.Dimension(82, 14));
        FourPairsLabel.setMinimumSize(new java.awt.Dimension(82, 14));
        FourPairsLabel.setPreferredSize(new java.awt.Dimension(82, 14));

        OnePairCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        OneLineCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OneLineCheckBox.setMaximumSize(new java.awt.Dimension(82, 20));
        OneLineCheckBox.setMinimumSize(new java.awt.Dimension(82, 20));
        OneLineCheckBox.setPreferredSize(new java.awt.Dimension(82, 20));

        TwoPairsCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        FourPairsCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout SeedingVariablePanelLayout = new javax.swing.GroupLayout(SeedingVariablePanel);
        SeedingVariablePanel.setLayout(SeedingVariablePanelLayout);
        SeedingVariablePanelLayout.setHorizontalGroup(
                SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ColoringBookSeedOptionsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(SeedingVariablePanelLayout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addGroup(SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(OneLineLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(OneLineCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(96, 96, 96)
                                .addGroup(SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(OnePairLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(OnePairCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(95, 95, 95)
                                .addGroup(SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(TwoPairsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(TwoPairsCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(96, 96, 96)
                                .addGroup(SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(FourPairsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(FourPairsCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(94, Short.MAX_VALUE))
        );
        SeedingVariablePanelLayout.setVerticalGroup(
                SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(SeedingVariablePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(ColoringBookSeedOptionsLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(OneLineLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(OnePairLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(TwoPairsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(FourPairsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(OnePairCheckBox)
                                        .addComponent(TwoPairsCheckBox)
                                        .addComponent(FourPairsCheckBox)
                                        .addComponent(OneLineCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(44, Short.MAX_VALUE))
        );

        RuntimeVariablePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        RuntimeVariablePanel.setPreferredSize(new java.awt.Dimension(400, 125));

        GraphRuntimeVariablesLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        GraphRuntimeVariablesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        GraphRuntimeVariablesLabel.setText("Graph Runtime Variables");
        GraphRuntimeVariablesLabel.setToolTipText("Variables that affect how lines on the grid grow and mutate");

        TrimLinesLabel.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        TrimLinesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TrimLinesLabel.setText("Trim");
        TrimLinesLabel.setToolTipText("Determines if lines should decay and die");

        ConsumeLabel.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        ConsumeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ConsumeLabel.setText("Consume");
        ConsumeLabel.setToolTipText("Determines if lines should consume food on the nodes they touch");
        ConsumeLabel.setMaximumSize(new java.awt.Dimension(82, 14));
        ConsumeLabel.setMinimumSize(new java.awt.Dimension(82, 14));
        ConsumeLabel.setPreferredSize(new java.awt.Dimension(82, 14));

        MutateHealthLabel.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        MutateHealthLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MutateHealthLabel.setText("Mutate Health");
        MutateHealthLabel.setToolTipText("Determines if lines can pass different values for lifespan to their children");
        MutateHealthLabel.setMaximumSize(new java.awt.Dimension(85, 14));
        MutateHealthLabel.setMinimumSize(new java.awt.Dimension(85, 14));
        MutateHealthLabel.setPreferredSize(new java.awt.Dimension(85, 14));

        GrowthTypeLabel.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        GrowthTypeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        GrowthTypeLabel.setText("Growth Type");
        GrowthTypeLabel.setToolTipText("Determines the growth type for lines");
        GrowthTypeLabel.setMaximumSize(new java.awt.Dimension(82, 14));
        GrowthTypeLabel.setMinimumSize(new java.awt.Dimension(82, 14));
        GrowthTypeLabel.setPreferredSize(new java.awt.Dimension(82, 14));

        GrowthTypeComboBox.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        GrowthTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Regular", "Mutation", "Depth-Based Coloring", "Growth" }));

        MutateColorLabel.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        MutateColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MutateColorLabel.setText("Mutate Color");
        MutateColorLabel.setToolTipText("Determines if lines can pass on different colors to their children");
        MutateColorLabel.setMaximumSize(new java.awt.Dimension(85, 14));
        MutateColorLabel.setMinimumSize(new java.awt.Dimension(85, 14));
        MutateColorLabel.setPreferredSize(new java.awt.Dimension(85, 14));

        MutateHealthComboBox.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        MutateHealthComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "True", "False" }));

        MutateColorComboBox.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        MutateColorComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "True", "False" }));

        TrimComboBox.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        TrimComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "True", "False" }));

        ConsumeComboBox.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        ConsumeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "True", "False" }));

        javax.swing.GroupLayout RuntimeVariablePanelLayout = new javax.swing.GroupLayout(RuntimeVariablePanel);
        RuntimeVariablePanel.setLayout(RuntimeVariablePanelLayout);
        RuntimeVariablePanelLayout.setHorizontalGroup(
                RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(GraphRuntimeVariablesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                        .addGroup(RuntimeVariablePanelLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(RuntimeVariablePanelLayout.createSequentialGroup()
                                                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(TrimLinesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                                        .addComponent(TrimComboBox, 0, 100, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(ConsumeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(ConsumeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(GrowthTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(GrowthTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(RuntimeVariablePanelLayout.createSequentialGroup()
                                                .addGap(51, 51, 51)
                                                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(MutateHealthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(MutateHealthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(MutateColorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(MutateColorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RuntimeVariablePanelLayout.setVerticalGroup(
                RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(RuntimeVariablePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(GraphRuntimeVariablesLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(TrimLinesLabel)
                                        .addComponent(ConsumeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(GrowthTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(GrowthTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(TrimComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ConsumeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(MutateHealthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(MutateColorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(MutateHealthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(MutateColorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ApplyButton.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        ApplyButton.setText("Apply");
        ApplyButton.setMaximumSize(new java.awt.Dimension(75, 25));
        ApplyButton.setMinimumSize(new java.awt.Dimension(75, 25));
        ApplyButton.setPreferredSize(new java.awt.Dimension(75, 25));
        ApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplyButtonActionPerformed(evt);
            }
        });

        CancelButton.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        CancelButton.setText("Cancel");
        CancelButton.setMaximumSize(new java.awt.Dimension(75, 25));
        CancelButton.setMinimumSize(new java.awt.Dimension(75, 25));
        CancelButton.setPreferredSize(new java.awt.Dimension(75, 25));
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ButtonsPanelLayout = new javax.swing.GroupLayout(ButtonsPanel);
        ButtonsPanel.setLayout(ButtonsPanelLayout);
        ButtonsPanelLayout.setHorizontalGroup(
                ButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(ButtonsPanelLayout.createSequentialGroup()
                                .addComponent(ApplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ButtonsPanelLayout.setVerticalGroup(
                ButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(ButtonsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(ButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(ApplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(CanvasVariablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RuntimeVariablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(SeedingVariablePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 807, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(ButtonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(300, 300, 300))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(CanvasVariablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                        .addComponent(RuntimeVariablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                .addGap(5, 5, 5)
                                .addComponent(SeedingVariablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(ButtonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action method for the cancel button. Closes the form.
     */
    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    /**
     * Action method for the apply button. Updates settings, and closes the
     * form.
     */
    private void ApplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApplyButtonActionPerformed
        canvas.setMinSpacing(checkTextField(PointSpacingTextField, "Point Spacing", canvas.getMinSpacing()));
        canvas.setMinPointSize(checkTextField(PointSizeTextField, "Point Size", canvas.getMinPointSize()));
        graph.setTrim(TrimComboBox.getSelectedIndex() == 0);
        graph.setConsume(ConsumeComboBox.getSelectedIndex() == 0);
        graph.setMode(GrowthTypeComboBox.getSelectedIndex());
        graph.setMutateHealth(MutateHealthComboBox.getSelectedIndex() == 0);
        graph.setMutateColor(MutateColorComboBox.getSelectedIndex() == 0);
        graph.setSeed1(OneLineCheckBox.isSelected());
        graph.setSeed2(OnePairCheckBox.isSelected());
        graph.setSeed4(TwoPairsCheckBox.isSelected());
        graph.setSeed8(FourPairsCheckBox.isSelected());
        canvas.setResized(true);
        ref.getSettingsManager().writeSettings();
        this.dispose();
    }//GEN-LAST:event_ApplyButtonActionPerformed

    /**
     * Action method for the resize grid button. Opens the
     * {@link GridSelectionFrame}, to be used in setting new grid dimensions, as
     * well as a new interval between steps when auto-running steps.
     */
    private void ResizeGridButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResizeGridButtonActionPerformed
        GridSelectionFrame frame = new GridSelectionFrame(ref);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        SwingUtilities.invokeLater(frame);
    }//GEN-LAST:event_ResizeGridButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ApplyButton;
    private javax.swing.JPanel ButtonsPanel;
    private javax.swing.JButton CancelButton;
    private javax.swing.JLabel CanvasVariableLabel;
    private javax.swing.JPanel CanvasVariablePanel;
    private javax.swing.JLabel ColoringBookSeedOptionsLabel;
    private javax.swing.JComboBox ConsumeComboBox;
    private javax.swing.JLabel ConsumeLabel;
    private javax.swing.JCheckBox FourPairsCheckBox;
    private javax.swing.JLabel FourPairsLabel;
    private javax.swing.JLabel GraphRuntimeVariablesLabel;
    private javax.swing.JComboBox GrowthTypeComboBox;
    private javax.swing.JLabel GrowthTypeLabel;
    private javax.swing.JComboBox MutateColorComboBox;
    private javax.swing.JLabel MutateColorLabel;
    private javax.swing.JComboBox MutateHealthComboBox;
    private javax.swing.JLabel MutateHealthLabel;
    private javax.swing.JCheckBox OneLineCheckBox;
    private javax.swing.JLabel OneLineLabel;
    private javax.swing.JCheckBox OnePairCheckBox;
    private javax.swing.JLabel OnePairLabel;
    private javax.swing.JLabel PointSizeLabel;
    private javax.swing.JTextField PointSizeTextField;
    private javax.swing.JLabel PointSpacingLabel;
    private javax.swing.JTextField PointSpacingTextField;
    private javax.swing.JButton ResizeGridButton;
    private javax.swing.JPanel RuntimeVariablePanel;
    private javax.swing.JPanel SeedingVariablePanel;
    private javax.swing.JComboBox TrimComboBox;
    private javax.swing.JLabel TrimLinesLabel;
    private javax.swing.JCheckBox TwoPairsCheckBox;
    private javax.swing.JLabel TwoPairsLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * Checks a given {@link JTextField} to ensure that the input is numerical
     * and greater than 0.
     *
     * @param field The {@link JTextField} to be checked.
     * @param title The title for the error message, should it be generated.
     * @param emptyValue The default value, should a non-numerical value be
     * entered.
     * @return The final integer value after checking is performed
     */
    private int checkTextField(JTextField field, String title, int emptyValue) {
        int out;
        try {
            out = Integer.parseInt(field.getText());
            if (out <= 0) {
                JOptionPane.showMessageDialog(this, "Enter a number greater than 0!", title, JOptionPane.ERROR_MESSAGE);
                out = -1;
            }//end if
        } catch (NumberFormatException e) {
            out = emptyValue;
        }//end tryCatch
        return out;
    }//end checkTextField

    /**
     * Sets up UI fields to reflect the current grid settings.
     */
    private void setUpUIFields() {
        PointSpacingTextField.setText("" + canvas.getMinSpacing());
        PointSizeTextField.setText("" + canvas.getMinPointSize());
        if (!Graph.TRIM) {
            TrimComboBox.setSelectedIndex(1);
        }//end if
        if (!Graph.CONSUME) {
            ConsumeComboBox.setSelectedIndex(1);
        }//end if
        switch (graph.getMode()) {
            case 0:
                GrowthTypeComboBox.setSelectedIndex(0);
                break;
            case 1:
                GrowthTypeComboBox.setSelectedIndex(1);
                break;
            case 2:
                GrowthTypeComboBox.setSelectedIndex(2);
                break;
            case 3:
                GrowthTypeComboBox.setSelectedIndex(3);
                break;
            default:
                break;
        }//end switch
        if (!Graph.MUTATE_HEALTH) {
            MutateHealthComboBox.setSelectedIndex(1);
        }//end if
        if (!Graph.MUTATE_COLOR) {
            MutateColorComboBox.setSelectedIndex(1);
        }//end if
        if (graph.getSeed1()) {
            OneLineCheckBox.setSelected(true);
        }//end if
        if (graph.getSeed2()) {
            OnePairCheckBox.setSelected(true);
        }//end if
        if (graph.getSeed4()) {
            TwoPairsCheckBox.setSelected(true);
        }//end if
        if (graph.getSeed8()) {
            FourPairsCheckBox.setSelected(true);
        }//end if
    }//end setUpUIFields

    @Override
    /**
     * Runs the form.
     */
    public void run() {
        if (ref != null) {
            this.setVisible(true);
        }//end if
    }//end run
}//end PropertiesSelectionForm