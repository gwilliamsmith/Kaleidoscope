package SwingElements;

import graphvisualizer.Base;
import javax.swing.JOptionPane;

public class SettingsSelectionForm extends javax.swing.JFrame {

    private Base parent;

    public SettingsSelectionForm(Base b) {
        parent = b;
        JOptionPane.showMessageDialog(parent, "This has not been implemented yet!");
        initComponents();
    }//end constructor

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TrimLabel = new javax.swing.JLabel();
        TrimComboBox = new javax.swing.JComboBox();
        ConsumeLabel = new javax.swing.JLabel();
        ConsumeComboBox = new javax.swing.JComboBox();
        GeneralMutateLabel = new javax.swing.JLabel();
        GeneralMutateComboBox = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        MutateColorLabel = new javax.swing.JLabel();
        MutateColorComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        MutateHealthComboBox = new javax.swing.JComboBox();
        GrowthTypeLabel = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        TrimLabel.setText("Trim: ");

        TrimComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "True","False" }));

        ConsumeLabel.setText("Consume: ");

        ConsumeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "True","False" }));

        GeneralMutateLabel.setText("General Mutate:");

        GeneralMutateComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "True","False" }));

        MutateColorLabel.setText("Mutate Color:");

        MutateColorComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "True","False" }));

        jLabel1.setText("Mutate Health: ");

        MutateHealthComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "True", "False"}));

        GrowthTypeLabel.setText("Growth Type:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Highest Health Node"}));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TrimLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TrimComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(ConsumeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ConsumeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(GeneralMutateLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GeneralMutateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(MutateColorLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel1)
                                        .addGap(1, 1, 1)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(MutateColorComboBox, 0, 75, Short.MAX_VALUE)
                                    .addComponent(MutateHealthComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(GrowthTypeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TrimComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TrimLabel)
                    .addComponent(ConsumeLabel)
                    .addComponent(ConsumeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GeneralMutateLabel)
                    .addComponent(GeneralMutateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GrowthTypeLabel)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MutateColorLabel)
                    .addComponent(MutateColorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(MutateHealthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(174, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox ConsumeComboBox;
    private javax.swing.JLabel ConsumeLabel;
    private javax.swing.JComboBox GeneralMutateComboBox;
    private javax.swing.JLabel GeneralMutateLabel;
    private javax.swing.JLabel GrowthTypeLabel;
    private javax.swing.JComboBox MutateColorComboBox;
    private javax.swing.JLabel MutateColorLabel;
    private javax.swing.JComboBox MutateHealthComboBox;
    private javax.swing.JComboBox TrimComboBox;
    private javax.swing.JLabel TrimLabel;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
