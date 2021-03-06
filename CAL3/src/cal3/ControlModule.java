package cal3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author jgome
 */
public class ControlModule extends javax.swing.JFrame {
    private Client cliente;
    /**
     * Creates new form ControlModul
     */
    public ControlModule() throws IOException {
        cliente=new Client();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        StopButcher = new javax.swing.JButton();
        ResumeButcher = new javax.swing.JButton();
        StopSuperMarket = new javax.swing.JButton();
        ResumeSuperMarket = new javax.swing.JButton();
        ResumeFishMonger = new javax.swing.JButton();
        StopFishMonger = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 102));
        jLabel1.setText("Great Exhibition");

        StopButcher.setText("Stop");
        StopButcher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopButcherActionPerformed(evt);
            }
        });

        ResumeButcher.setText("Resume");
        ResumeButcher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResumeButcherActionPerformed(evt);
            }
        });

        StopSuperMarket.setText("Stop");
        StopSuperMarket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopSuperMarketActionPerformed(evt);
            }
        });

        ResumeSuperMarket.setText("Resume");
        ResumeSuperMarket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResumeSuperMarketActionPerformed(evt);
            }
        });

        ResumeFishMonger.setText("Resume");
        ResumeFishMonger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResumeFishMongerActionPerformed(evt);
            }
        });

        StopFishMonger.setText("Stop");
        StopFishMonger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopFishMongerActionPerformed(evt);
            }
        });

        jLabel2.setText("SuperMarket control");

        jLabel3.setText("Buther control");

        jLabel4.setText("FishMonger control");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(276, 276, 276)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(82, 82, 82)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(StopFishMonger, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101)
                                .addComponent(ResumeFishMonger, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(StopButcher, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(StopSuperMarket, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(101, 101, 101)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ResumeSuperMarket, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ResumeButcher, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(149, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StopSuperMarket, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ResumeSuperMarket, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StopButcher, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ResumeButcher, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ResumeFishMonger, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StopFishMonger, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(38, 38, 38))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void StopButcherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopButcherActionPerformed
        try {
            // TODO add your handling code here:
            cliente.stop("Butcher");
        } catch (IOException ex) {
            Logger.getLogger(ControlModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_StopButcherActionPerformed

    private void ResumeButcherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResumeButcherActionPerformed
        try {
            // TODO add your handling code here:
            cliente.resume("Butcher");
        } catch (IOException ex) {
            Logger.getLogger(ControlModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ResumeButcherActionPerformed

    private void StopSuperMarketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopSuperMarketActionPerformed
        try {
            // TODO add your handling code here:
            cliente.stop("SuperMarket");
        } catch (IOException ex) {
            Logger.getLogger(ControlModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_StopSuperMarketActionPerformed

    private void ResumeSuperMarketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResumeSuperMarketActionPerformed
        try {
            // TODO add your handling code here:
            cliente.resume("SuperMarket");
        } catch (IOException ex) {
            Logger.getLogger(ControlModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ResumeSuperMarketActionPerformed

    private void ResumeFishMongerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResumeFishMongerActionPerformed
        try {
            // TODO add your handling code here:
            cliente.resume("FishMonger");
        } catch (IOException ex) {
            Logger.getLogger(ControlModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ResumeFishMongerActionPerformed

    private void StopFishMongerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopFishMongerActionPerformed
        try {
            // TODO add your handling code here:
            cliente.stop("FishMonger");
        } catch (IOException ex) {
            Logger.getLogger(ControlModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_StopFishMongerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ControlModule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControlModule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControlModule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControlModule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ControlModule().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(ControlModule.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ResumeButcher;
    private javax.swing.JButton ResumeFishMonger;
    private javax.swing.JButton ResumeSuperMarket;
    private javax.swing.JButton StopButcher;
    private javax.swing.JButton StopFishMonger;
    private javax.swing.JButton StopSuperMarket;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
