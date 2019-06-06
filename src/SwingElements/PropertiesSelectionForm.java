package SwingElements;

import graphvisualizer.Graph;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.*;

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

        javax.swing.JPanel canvasVariablePanel = new javax.swing.JPanel();
        javax.swing.JLabel canvasVariableLabel = new javax.swing.JLabel();
        javax.swing.JLabel pointSpacingLabel = new javax.swing.JLabel();
        javax.swing.JLabel pointSizeLabel = new javax.swing.JLabel();
        PointSpacingTextField = new javax.swing.JTextField();
        PointSizeTextField = new javax.swing.JTextField();
        javax.swing.JButton resizeGridButton = new javax.swing.JButton();
        javax.swing.JPanel seedingVariablePanel = new javax.swing.JPanel();
        javax.swing.JLabel coloringBookSeedOptionsLabel = new javax.swing.JLabel();
        javax.swing.JLabel oneLineLabel = new javax.swing.JLabel();
        javax.swing.JLabel onePairLabel = new javax.swing.JLabel();
        javax.swing.JLabel twoPairsLabel = new javax.swing.JLabel();
        javax.swing.JLabel fourPairsLabel = new javax.swing.JLabel();
        OnePairCheckBox = new javax.swing.JCheckBox();
        OneLineCheckBox = new javax.swing.JCheckBox();
        TwoPairsCheckBox = new javax.swing.JCheckBox();
        FourPairsCheckBox = new javax.swing.JCheckBox();
        javax.swing.JPanel runtimeVariablePanel = new javax.swing.JPanel();
        javax.swing.JLabel graphRuntimeVariablesLabel = new javax.swing.JLabel();
        javax.swing.JLabel trimLinesLabel = new javax.swing.JLabel();
        javax.swing.JLabel consumeLabel = new javax.swing.JLabel();
        javax.swing.JLabel mutateHealthLabel = new javax.swing.JLabel();
        javax.swing.JLabel growthTypeLabel = new javax.swing.JLabel();
        GrowthTypeComboBox = new javax.swing.JComboBox();
        javax.swing.JLabel mutateColorLabel = new javax.swing.JLabel();
        MutateHealthComboBox = new javax.swing.JComboBox();
        MutateColorComboBox = new javax.swing.JComboBox();
        TrimComboBox = new javax.swing.JComboBox();
        ConsumeComboBox = new javax.swing.JComboBox();
        javax.swing.JPanel buttonsPanel = new javax.swing.JPanel();
        // Variables declaration - do not modify//GEN-BEGIN:variables
        javax.swing.JButton applyButton = new javax.swing.JButton();
        javax.swing.JButton cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Properties");
        setName("PropertiesFrame"); // NOI18N

        canvasVariablePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        canvasVariablePanel.setPreferredSize(new java.awt.Dimension(400, 125));

        canvasVariableLabel.setFont(new java.awt.Font("Trebuchet MS", Font.BOLD, 14)); // NOI18N
        canvasVariableLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        canvasVariableLabel.setText("Canvas Variables");
        canvasVariableLabel.setToolTipText("Variables that affect the grid and how it's displayed");
        canvasVariableLabel.setAlignmentX(0.5F);
        canvasVariableLabel.setInheritsPopupMenu(false);

        pointSpacingLabel.setFont(new java.awt.Font("Arial Black", Font.PLAIN, 11)); // NOI18N
        pointSpacingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pointSpacingLabel.setText("Point Spacing");
        pointSpacingLabel.setToolTipText("The amount of space between points on the grid");

        pointSizeLabel.setFont(new java.awt.Font("Arial Black", Font.PLAIN, 11)); // NOI18N
        pointSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pointSizeLabel.setText("Point Size");
        pointSizeLabel.setToolTipText("The size of points on the grid");

        PointSizeTextField.setMinimumSize(new java.awt.Dimension(82, 20));
        PointSizeTextField.setPreferredSize(new java.awt.Dimension(82, 20));

        resizeGridButton.setFont(new java.awt.Font("Arial", Font.PLAIN, 11)); // NOI18N
        resizeGridButton.setText("Resize Grid");
        resizeGridButton.setToolTipText("Change the number of rows, columns, and the time between steps");
        resizeGridButton.setMaximumSize(new java.awt.Dimension(90, 25));
        resizeGridButton.setMinimumSize(new java.awt.Dimension(90, 25));
        resizeGridButton.setPreferredSize(new java.awt.Dimension(90, 25));
        resizeGridButton.addActionListener(this::ResizeGridButtonActionPerformed);

        javax.swing.GroupLayout CanvasVariablePanelLayout = new javax.swing.GroupLayout(canvasVariablePanel);
        canvasVariablePanel.setLayout(CanvasVariablePanelLayout);
        CanvasVariablePanelLayout.setHorizontalGroup(
            CanvasVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvasVariableLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(CanvasVariablePanelLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(CanvasVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PointSpacingTextField)
                    .addComponent(pointSpacingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addGroup(CanvasVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PointSizeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pointSizeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(78, 78, 78))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CanvasVariablePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(resizeGridButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(153, 153, 153))
        );
        CanvasVariablePanelLayout.setVerticalGroup(
            CanvasVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CanvasVariablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(canvasVariableLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CanvasVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pointSpacingLabel)
                    .addComponent(pointSizeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CanvasVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PointSpacingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PointSizeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(resizeGridButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        seedingVariablePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        seedingVariablePanel.setMinimumSize(new java.awt.Dimension(800, 0));
        seedingVariablePanel.setPreferredSize(new java.awt.Dimension(400, 125));

        coloringBookSeedOptionsLabel.setFont(new java.awt.Font("Trebuchet MS", Font.BOLD, 14)); // NOI18N
        coloringBookSeedOptionsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        coloringBookSeedOptionsLabel.setText("Coloring Book Seed Options");
        coloringBookSeedOptionsLabel.setToolTipText("Variables that affect the settings for random initial pattern configuration");

        oneLineLabel.setFont(new java.awt.Font("Arial Black", Font.PLAIN, 11)); // NOI18N
        oneLineLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        oneLineLabel.setText("One Line");
        oneLineLabel.setToolTipText("Enables placing one line, on one line of symmetry");
        oneLineLabel.setMaximumSize(new java.awt.Dimension(82, 14));
        oneLineLabel.setMinimumSize(new java.awt.Dimension(82, 14));
        oneLineLabel.setPreferredSize(new java.awt.Dimension(82, 14));

        onePairLabel.setFont(new java.awt.Font("Arial Black", Font.PLAIN, 11)); // NOI18N
        onePairLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        onePairLabel.setText("One Pair");
        onePairLabel.setToolTipText("Enables placing a pair of lines, on one line of symmetry");
        onePairLabel.setMaximumSize(new java.awt.Dimension(82, 14));
        onePairLabel.setMinimumSize(new java.awt.Dimension(82, 14));
        onePairLabel.setPreferredSize(new java.awt.Dimension(82, 14));

        twoPairsLabel.setFont(new java.awt.Font("Arial Black", Font.PLAIN, 11)); // NOI18N
        twoPairsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        twoPairsLabel.setText("Two Pairs");
        twoPairsLabel.setToolTipText("Enables placing two pairs of lines, on two lines of symmetry");
        twoPairsLabel.setMaximumSize(new java.awt.Dimension(82, 14));
        twoPairsLabel.setMinimumSize(new java.awt.Dimension(82, 14));
        twoPairsLabel.setPreferredSize(new java.awt.Dimension(82, 14));

        fourPairsLabel.setFont(new java.awt.Font("Arial Black", Font.PLAIN, 11)); // NOI18N
        fourPairsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fourPairsLabel.setText("Four Pairs");
        fourPairsLabel.setToolTipText("Enables placing four pairs of lines, on all lines of symmetry");
        fourPairsLabel.setMaximumSize(new java.awt.Dimension(82, 14));
        fourPairsLabel.setMinimumSize(new java.awt.Dimension(82, 14));
        fourPairsLabel.setPreferredSize(new java.awt.Dimension(82, 14));

        OnePairCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        OneLineCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OneLineCheckBox.setMaximumSize(new java.awt.Dimension(82, 20));
        OneLineCheckBox.setMinimumSize(new java.awt.Dimension(82, 20));
        OneLineCheckBox.setPreferredSize(new java.awt.Dimension(82, 20));

        TwoPairsCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        FourPairsCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout SeedingVariablePanelLayout = new javax.swing.GroupLayout(seedingVariablePanel);
        seedingVariablePanel.setLayout(SeedingVariablePanelLayout);
        SeedingVariablePanelLayout.setHorizontalGroup(
            SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(coloringBookSeedOptionsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(SeedingVariablePanelLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(oneLineLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(OneLineCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(96, 96, 96)
                .addGroup(SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(onePairLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OnePairCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(95, 95, 95)
                .addGroup(SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(twoPairsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TwoPairsCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96)
                .addGroup(SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fourPairsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FourPairsCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        SeedingVariablePanelLayout.setVerticalGroup(
            SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SeedingVariablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(coloringBookSeedOptionsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oneLineLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onePairLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(twoPairsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fourPairsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SeedingVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(OnePairCheckBox)
                    .addComponent(TwoPairsCheckBox)
                    .addComponent(FourPairsCheckBox)
                    .addComponent(OneLineCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        runtimeVariablePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        runtimeVariablePanel.setPreferredSize(new java.awt.Dimension(400, 125));

        graphRuntimeVariablesLabel.setFont(new java.awt.Font("Trebuchet MS", Font.BOLD, 14)); // NOI18N
        graphRuntimeVariablesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        graphRuntimeVariablesLabel.setText("Graph Runtime Variables");
        graphRuntimeVariablesLabel.setToolTipText("Variables that affect how lines on the grid grow and mutate");

        trimLinesLabel.setFont(new java.awt.Font("Arial Black", Font.PLAIN, 11)); // NOI18N
        trimLinesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        trimLinesLabel.setText("Trim");
        trimLinesLabel.setToolTipText("Determines if lines should decay and die");

        consumeLabel.setFont(new java.awt.Font("Arial Black", Font.PLAIN, 11)); // NOI18N
        consumeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        consumeLabel.setText("Consume");
        consumeLabel.setToolTipText("Determines if lines should consume food on the nodes they touch");
        consumeLabel.setMaximumSize(new java.awt.Dimension(82, 14));
        consumeLabel.setMinimumSize(new java.awt.Dimension(82, 14));
        consumeLabel.setPreferredSize(new java.awt.Dimension(82, 14));

        mutateHealthLabel.setFont(new java.awt.Font("Arial Black", Font.PLAIN, 11)); // NOI18N
        mutateHealthLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mutateHealthLabel.setText("Mutate Health");
        mutateHealthLabel.setToolTipText("Determines if lines can pass different values for lifespan to their children");
        mutateHealthLabel.setMaximumSize(new java.awt.Dimension(85, 14));
        mutateHealthLabel.setMinimumSize(new java.awt.Dimension(85, 14));
        mutateHealthLabel.setPreferredSize(new java.awt.Dimension(85, 14));

        growthTypeLabel.setFont(new java.awt.Font("Arial Black", Font.PLAIN, 11)); // NOI18N
        growthTypeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        growthTypeLabel.setText("Growth Type");
        growthTypeLabel.setToolTipText("Determines the growth type for lines");
        growthTypeLabel.setMaximumSize(new java.awt.Dimension(82, 14));
        growthTypeLabel.setMinimumSize(new java.awt.Dimension(82, 14));
        growthTypeLabel.setPreferredSize(new java.awt.Dimension(82, 14));

        GrowthTypeComboBox.setFont(new java.awt.Font("Arial", Font.PLAIN, 11)); // NOI18N
        GrowthTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Regular", "Mutation", "Depth-Based Coloring", "Growth" }));

        mutateColorLabel.setFont(new java.awt.Font("Arial Black", Font.PLAIN, 11)); // NOI18N
        mutateColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mutateColorLabel.setText("Mutate Color");
        mutateColorLabel.setToolTipText("Determines if lines can pass on different colors to their children");
        mutateColorLabel.setMaximumSize(new java.awt.Dimension(85, 14));
        mutateColorLabel.setMinimumSize(new java.awt.Dimension(85, 14));
        mutateColorLabel.setPreferredSize(new java.awt.Dimension(85, 14));

        MutateHealthComboBox.setFont(new java.awt.Font("Arial", Font.PLAIN, 11)); // NOI18N
        MutateHealthComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "True", "False" }));

        MutateColorComboBox.setFont(new java.awt.Font("Arial", Font.PLAIN, 11)); // NOI18N
        MutateColorComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "True", "False" }));

        TrimComboBox.setFont(new java.awt.Font("Arial", Font.PLAIN, 11)); // NOI18N
        TrimComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "True", "False" }));

        ConsumeComboBox.setFont(new java.awt.Font("Arial", Font.PLAIN, 11)); // NOI18N
        ConsumeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "True", "False" }));

        javax.swing.GroupLayout RuntimeVariablePanelLayout = new javax.swing.GroupLayout(runtimeVariablePanel);
        runtimeVariablePanel.setLayout(RuntimeVariablePanelLayout);
        RuntimeVariablePanelLayout.setHorizontalGroup(
            RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(graphRuntimeVariablesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
            .addGroup(RuntimeVariablePanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RuntimeVariablePanelLayout.createSequentialGroup()
                        .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(trimLinesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(TrimComboBox, 0, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ConsumeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(consumeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(growthTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(GrowthTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(RuntimeVariablePanelLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(MutateHealthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mutateHealthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mutateColorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MutateColorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RuntimeVariablePanelLayout.setVerticalGroup(
            RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RuntimeVariablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(graphRuntimeVariablesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trimLinesLabel)
                    .addComponent(consumeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(growthTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrowthTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TrimComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConsumeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mutateHealthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mutateColorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(RuntimeVariablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MutateHealthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MutateColorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        applyButton.setFont(new java.awt.Font("Arial", Font.PLAIN, 11)); // NOI18N
        applyButton.setText("Apply");
        applyButton.setMaximumSize(new java.awt.Dimension(75, 25));
        applyButton.setMinimumSize(new java.awt.Dimension(75, 25));
        applyButton.setPreferredSize(new java.awt.Dimension(75, 25));
        applyButton.addActionListener(this::ApplyButtonActionPerformed);

        cancelButton.setFont(new java.awt.Font("Arial", Font.PLAIN, 11)); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.setMaximumSize(new java.awt.Dimension(75, 25));
        cancelButton.setMinimumSize(new java.awt.Dimension(75, 25));
        cancelButton.setPreferredSize(new java.awt.Dimension(75, 25));
        cancelButton.addActionListener(this::CancelButtonActionPerformed);

        javax.swing.GroupLayout ButtonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(ButtonsPanelLayout);
        ButtonsPanelLayout.setHorizontalGroup(
            ButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ButtonsPanelLayout.createSequentialGroup()
                .addComponent(applyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ButtonsPanelLayout.setVerticalGroup(
            ButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ButtonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(applyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(canvasVariablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(runtimeVariablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(seedingVariablePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 807, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(300, 300, 300))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(canvasVariablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(runtimeVariablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(seedingVariablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private javax.swing.JComboBox ConsumeComboBox;
    private javax.swing.JCheckBox FourPairsCheckBox;
    private javax.swing.JComboBox GrowthTypeComboBox;
    private javax.swing.JComboBox MutateColorComboBox;
    private javax.swing.JComboBox MutateHealthComboBox;
    private javax.swing.JCheckBox OneLineCheckBox;
    private javax.swing.JCheckBox OnePairCheckBox;
    private javax.swing.JTextField PointSizeTextField;
    private javax.swing.JTextField PointSpacingTextField;
    private javax.swing.JComboBox TrimComboBox;
    private javax.swing.JCheckBox TwoPairsCheckBox;
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
    public void run() {
        if (ref != null) {
            this.setVisible(true);
        }//end if
    }//end run
}//end PropertiesSelectionForm
