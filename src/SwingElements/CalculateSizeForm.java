/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwingElements;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;

/**
 *
 * @author Redpox
 */
public class CalculateSizeForm extends javax.swing.JFrame implements Runnable {

    Base ref;

    public CalculateSizeForm(Base in) {
        ref = in;
        initComponents();
    }//end constructor

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        BoundingSquareSizeLabel = new javax.swing.JLabel();
        BoundingSquareSizeTextField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        NodeNumberLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        NodeCountList = new javax.swing.JList();
        jPanel7 = new javax.swing.JPanel();
        NodeSizeSpacingLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        NodeSizeSpacingList = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(null);
        setPreferredSize(null);

        BoundingSquareSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BoundingSquareSizeLabel.setText("Bounding Square Size:");

        BoundingSquareSizeTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BoundingSquareSizeTextFieldKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BoundingSquareSizeTextField)
                    .addComponent(BoundingSquareSizeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BoundingSquareSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BoundingSquareSizeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        NodeNumberLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NodeNumberLabel.setText("Possible #s of Nodes");

        NodeCountList.setModel(new DefaultListModel<String>());
        NodeCountList.setMaximumSize(new java.awt.Dimension(33, 70));
        NodeCountList.setMinimumSize(new java.awt.Dimension(33, 70));
        NodeCountList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                NodeCountListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(NodeCountList);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(NodeNumberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NodeNumberLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        NodeSizeSpacingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NodeSizeSpacingLabel.setText("Node Size/Spacing");

        NodeSizeSpacingList.setModel(new DefaultListModel<String>()
        );
        jScrollPane5.setViewportView(NodeSizeSpacingList);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(NodeSizeSpacingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NodeSizeSpacingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Apply");
        jButton1.setMaximumSize(new java.awt.Dimension(60, 25));
        jButton1.setMinimumSize(new java.awt.Dimension(60, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(60, 25));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NodeCountListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_NodeCountListValueChanged
        NodeSizeSpacingList.clearSelection();
        if (!NodeCountList.isSelectionEmpty()) {
            int nodes = Integer.parseInt(NodeCountList.getSelectedValue().toString());
            HashMap<Integer, Integer> values = calculateNodeWidths(Integer.parseInt(BoundingSquareSizeTextField.getText()), nodes);
            DefaultListModel model = (DefaultListModel) NodeSizeSpacingList.getModel();
            model.removeAllElements();
            for (Integer i : values.keySet()) {
                model.addElement(i + "," + values.get(i));
            }//end for
        }//end if
    }//GEN-LAST:event_NodeCountListValueChanged

    private void BoundingSquareSizeTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BoundingSquareSizeTextFieldKeyReleased
        String text = BoundingSquareSizeTextField.getText();
        NodeCountList.clearSelection();
        NodeSizeSpacingList.clearSelection();
        DefaultListModel temp = (DefaultListModel)NodeSizeSpacingList.getModel();
        temp.removeAllElements();
        try {
            ArrayList<Integer> listItems = findFactors(Integer.parseInt(text));
            DefaultListModel model = (DefaultListModel) (NodeCountList.getModel());
            model.removeAllElements();
            for (Integer i : listItems) {
                model.addElement(Integer.toString(i));
            }//end for
        } catch (NumberFormatException e) {
        }//end catch
    }//GEN-LAST:event_BoundingSquareSizeTextFieldKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (NodeCountList.getSelectedValue() != null && NodeSizeSpacingList.getSelectedValue() != null) {
            System.out.println(NodeCountList.getSelectedValue() + " " + NodeSizeSpacingList.getSelectedValue());
        }//end if
        else {
            System.out.println("Something isn't selected");
        }//end else
    }//GEN-LAST:event_jButton1ActionPerformed

    private ArrayList<Integer> findFactors(int in) {
        ArrayList<Integer> factors = new ArrayList<>();
        for (int i = 1; i <= in; i++) {
            if (in % i == 0) {
                factors.add(i + 1);
            }//end if
        }//end for
        return factors;
    }//end findFactors

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ApplyButton1;
    private javax.swing.JLabel BoundingSquareSizeLabel;
    private javax.swing.JTextField BoundingSquareSizeTextField;
    private javax.swing.JList NodeCountList;
    private javax.swing.JLabel NodeNumberLabel;
    private javax.swing.JLabel NodeSizeSpacingLabel;
    private javax.swing.JList NodeSizeSpacingList;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JList jList4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        if (ref != null) {
            setVisible(true);
        }//end if
    }//end run

    private HashMap<Integer, Integer> calculateNodeWidths(double rectangleSide, double nodes) {
        HashMap<Integer, Integer> out = new HashMap<>();
        double width;
        for (int i = 1, c = 0; i < 250 && c < 100; i++) {
            width = (rectangleSide / (nodes - 1)) - i;
            if (width <= 0) {
                break;
            }//end if
            if (width == Math.floor(width) && !Double.isInfinite(width)) {
                c++;
                out.put(i, (int) width);
            }//end if
        }//end for
        return out;
    }//end calculateNodeWidths

}
