/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.GUI;

import campeonato.SRV.CampeonatoSRV;
import campeonato.SRV.EquipoSRV;
import campeonato.SRV.MODELOS.Equipo;
import campeonato.SRV.MODELOS.Partido;
import campeonato.SRV.MODELOS.TablaPosiciones;
import campeonato.SRV.PartidosSRV;
import campeonato.SRV.TablaPosicionesSRV;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lara
 */
public class partidosGUI extends javax.swing.JDialog {

    /**
     * Creates new form partidosGUI
     */
    int idCampeonato = 0;
    public partidosGUI(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        asignarEquiposBTN1.setVisible(false);
        asignarEquiposBTN.setVisible(true);
        asignarEquiposBTN2.setVisible(false);
        cargarTablaPosiciones();
    }

    public void cargarDatos(int id){
        idCampeonato = id;
        cargarTablaPosiciones();
        cargarResultados();
    }
    
    public void cargarTablaPosiciones(){
        
        TablaPosicionesSRV posicionesSRV = new TablaPosicionesSRV();
        DefaultTableModel model = (DefaultTableModel) tablaPosicionesTBL.getModel();
        //Borrar elementos anteriores
        for(int i = model.getRowCount() - 1; i >= 0 ; i--)
            model.removeRow(i);
        
        int n = 1;
        int grupo = Integer.parseInt((String) gruposCB.getSelectedItem());
        for(TablaPosiciones e : posicionesSRV.get(grupo, idCampeonato, (String) etapaCB.getSelectedItem())){
            model.addRow(new Object[]{n++, e.getNombre(), e.getPartidosJugados(), e.getPartidosGanados(), e.getPartidosEmpatados(),
            e.getPartidosPerdidos(), e.getGolesFavor(), e.getGolesContra(), e.getPuntos(), e.getGolesDiferencia()});
    }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPosicionesTBL = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        etapaCB = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        gruposCB = new javax.swing.JComboBox<>();
        asignarEquiposBTN = new javax.swing.JButton();
        asignarEquiposBTN1 = new javax.swing.JButton();
        asignarEquiposBTN2 = new javax.swing.JButton();
        consultarBTN = new javax.swing.JButton();
        goleadoresBTN = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        partidosTBL = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Partidos");
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        tablaPosicionesTBL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nro", "Equipo", "PJ", "PG", "PE", "PP", "GF", "GC", "Puntos", "GD"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaPosicionesTBL);
        if (tablaPosicionesTBL.getColumnModel().getColumnCount() > 0) {
            tablaPosicionesTBL.getColumnModel().getColumn(0).setResizable(false);
            tablaPosicionesTBL.getColumnModel().getColumn(0).setPreferredWidth(15);
            tablaPosicionesTBL.getColumnModel().getColumn(2).setResizable(false);
            tablaPosicionesTBL.getColumnModel().getColumn(2).setPreferredWidth(15);
            tablaPosicionesTBL.getColumnModel().getColumn(3).setResizable(false);
            tablaPosicionesTBL.getColumnModel().getColumn(3).setPreferredWidth(15);
            tablaPosicionesTBL.getColumnModel().getColumn(4).setResizable(false);
            tablaPosicionesTBL.getColumnModel().getColumn(4).setPreferredWidth(15);
            tablaPosicionesTBL.getColumnModel().getColumn(5).setResizable(false);
            tablaPosicionesTBL.getColumnModel().getColumn(5).setPreferredWidth(15);
            tablaPosicionesTBL.getColumnModel().getColumn(6).setResizable(false);
            tablaPosicionesTBL.getColumnModel().getColumn(6).setPreferredWidth(15);
            tablaPosicionesTBL.getColumnModel().getColumn(7).setResizable(false);
            tablaPosicionesTBL.getColumnModel().getColumn(7).setPreferredWidth(15);
            tablaPosicionesTBL.getColumnModel().getColumn(8).setPreferredWidth(20);
            tablaPosicionesTBL.getColumnModel().getColumn(9).setResizable(false);
            tablaPosicionesTBL.getColumnModel().getColumn(9).setPreferredWidth(20);
        }

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setText("Partidos");

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setText("Tabla de posiciones");

        etapaCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Primera Ronda" }));

        jLabel3.setText("Etapa");

        jLabel4.setText("Grupo");

        gruposCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        gruposCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gruposCBActionPerformed(evt);
            }
        });

        asignarEquiposBTN.setText("Primera Fecha");
        asignarEquiposBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asignarEquiposBTNActionPerformed(evt);
            }
        });

        asignarEquiposBTN1.setText("Segunda Fecha");
        asignarEquiposBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asignarEquiposBTN1ActionPerformed(evt);
            }
        });

        asignarEquiposBTN2.setText("Tercera Fecha");
        asignarEquiposBTN2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asignarEquiposBTN2ActionPerformed(evt);
            }
        });

        consultarBTN.setText("Cosultar clasificados");
        consultarBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarBTNActionPerformed(evt);
            }
        });

        goleadoresBTN.setText("Tabla de Goleadores");
        goleadoresBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goleadoresBTNActionPerformed(evt);
            }
        });

        partidosTBL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nro", "Equipo Local", "Marcador Local", "Marcador Visita", "Equipo Visitante"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(partidosTBL);
        if (partidosTBL.getColumnModel().getColumnCount() > 0) {
            partidosTBL.getColumnModel().getColumn(0).setPreferredWidth(15);
            partidosTBL.getColumnModel().getColumn(2).setPreferredWidth(15);
            partidosTBL.getColumnModel().getColumn(3).setPreferredWidth(15);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(etapaCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(gruposCB, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(asignarEquiposBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(asignarEquiposBTN1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(asignarEquiposBTN2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(consultarBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(goleadoresBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 425, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(383, 383, 383))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(gruposCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(etapaCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(asignarEquiposBTN)
                    .addComponent(consultarBTN)
                    .addComponent(goleadoresBTN)
                    .addComponent(asignarEquiposBTN1)
                    .addComponent(asignarEquiposBTN2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void gruposCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gruposCBActionPerformed
        // TODO add your handling code here:
        cargarTablaPosiciones();
    }//GEN-LAST:event_gruposCBActionPerformed

    private void asignarEquiposBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asignarEquiposBTNActionPerformed
        try {
            // TODO add your handling code here:
            CampeonatoSRV campeonatoSRV = new CampeonatoSRV();
            campeonatoSRV.JugarRondaInicial(1, 2, 3, 4, idCampeonato);
  //          campeonatoSRV.rondaInicial(1, 2, 3, 4, idCampeonato);
            JOptionPane.showMessageDialog(rootPane, "Primera fecha jugada", "Mensaje", JOptionPane.OK_OPTION);
            asignarEquiposBTN1.setVisible(true);
            asignarEquiposBTN.setVisible(false);
            asignarEquiposBTN2.setVisible(false);
            cargarTablaPosiciones();
            cargarResultados();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.OK_OPTION);
        }
        
    }//GEN-LAST:event_asignarEquiposBTNActionPerformed

    private void asignarEquiposBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asignarEquiposBTN1ActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            CampeonatoSRV campeonatoSRV = new CampeonatoSRV();
            campeonatoSRV.JugarRondaInicial(1, 3, 2, 4, idCampeonato);
            //campeonatoSRV.rondaInicial(1, 3, 2, 4, idCampeonato);
            JOptionPane.showMessageDialog(rootPane, "Segunda fecha jugada", "Mensaje", JOptionPane.OK_OPTION);
            asignarEquiposBTN1.setVisible(false);
            asignarEquiposBTN.setVisible(false);
            asignarEquiposBTN2.setVisible(true);
            cargarTablaPosiciones();
            cargarResultados();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_asignarEquiposBTN1ActionPerformed

    private void asignarEquiposBTN2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asignarEquiposBTN2ActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            CampeonatoSRV campeonatoSRV = new CampeonatoSRV();
            campeonatoSRV.JugarRondaInicial(1, 4, 2, 3, idCampeonato);
            //campeonatoSRV.rondaInicial(1, 4, 2, 3, idCampeonato);
            JOptionPane.showMessageDialog(rootPane, "Tercera fecha jugada", "Mensaje", JOptionPane.OK_OPTION);
            asignarEquiposBTN2.setVisible(false);
            cargarTablaPosiciones();
            cargarResultados();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_asignarEquiposBTN2ActionPerformed

    private void consultarBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultarBTNActionPerformed
        // TODO add your handling code here:
        FasesFinalesGUI fasesFinalesGUI = new FasesFinalesGUI(null, rootPaneCheckingEnabled);
        fasesFinalesGUI.CargarDatos(idCampeonato);
        fasesFinalesGUI.setLocationRelativeTo(this);
        fasesFinalesGUI.setVisible(true);
    }//GEN-LAST:event_consultarBTNActionPerformed

    private void goleadoresBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goleadoresBTNActionPerformed
        // TODO add your handling code here:
        GoleadoresGUI gUI = new GoleadoresGUI(null, rootPaneCheckingEnabled);
        gUI.setLocationRelativeTo(this);
        gUI.cargarDatos(idCampeonato);
        gUI.setVisible(true);
    }//GEN-LAST:event_goleadoresBTNActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
        cargarResultados();
        cargarTablaPosiciones();
    }//GEN-LAST:event_formFocusGained

    private void cargarResultados(){
        PartidosSRV partidosSRV = new PartidosSRV();
        EquipoSRV equipoSRV  = new EquipoSRV();
        DefaultTableModel model = (DefaultTableModel) partidosTBL.getModel();
        //Borrar elementos anteriores
        for(int i = model.getRowCount() - 1; i >= 0 ; i--)
            model.removeRow(i);
        
        int n = 1;
        for(Partido p : partidosSRV.get(idCampeonato, (String) etapaCB.getSelectedItem()))
            model.addRow(new Object[]{n++, p.getIdEquipoL().getNombre(), p.getMarcadorL(), p.getMarcadorV(), p.getIdEquipoV().getNombre()});
    }
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
            java.util.logging.Logger.getLogger(partidosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(partidosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(partidosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(partidosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                partidosGUI dialog = new partidosGUI(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton asignarEquiposBTN;
    private javax.swing.JButton asignarEquiposBTN1;
    private javax.swing.JButton asignarEquiposBTN2;
    private javax.swing.JButton consultarBTN;
    private javax.swing.JComboBox<String> etapaCB;
    private javax.swing.JButton goleadoresBTN;
    private javax.swing.JComboBox<String> gruposCB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable partidosTBL;
    private javax.swing.JTable tablaPosicionesTBL;
    // End of variables declaration//GEN-END:variables
}
