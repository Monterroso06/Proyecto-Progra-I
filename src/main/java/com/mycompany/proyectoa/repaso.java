/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyectoa;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;


/**
 *
 * @author angel-monterroso
 */
public class repaso extends javax.swing.JFrame {

    /**
     * Creates new form repaso
     */
    public repaso() {
        initComponents();
        TablaRepaso();
        repasotablaedit();
    }
     private void limpiarCampos() {
        jcolor.setText("");
        jnombre.setText("");
        jedad.setText("");
        jciudad.setText("");
        selectedRow = -1;
    }
    
     private DefaultTableModel TablaRepaso;
    private static ArrayList<repasorealizado> repasosrealizados = new ArrayList<>();
    private int selectedRow = -1;
    
    private void TablaRepaso() {
        String[] encabezado = {"Color", "Nombre", "Edad", "Ciudad"};
        DefaultTableModel modelo = new DefaultTableModel(encabezado, 0); // 0 filas iniciales
        Tablerepaso.setModel(modelo);
    }
    
    private void repasotablaedit() {
        DefaultTableModel modelo = (DefaultTableModel) Tablerepaso.getModel();
        modelo.setRowCount(0); // Limpiar antes de llenar
        
        for (repasorealizado repaso : ProyectoA.repasosrealizados) {
           // for (LibroVendido libro : venta.librosVendidos) {
            modelo.addRow(new Object[]{
                repaso.color,      // Estos campos deben existir en VentaRealizada
                repaso.nombre,
                repaso.edad,
                repaso.ciudad
                
            });
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
        Tablerepaso = new javax.swing.JTable();
        jmodificar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        nombre = new javax.swing.JLabel();
        edad = new javax.swing.JLabel();
        ciudad = new javax.swing.JLabel();
        jcolor = new javax.swing.JTextField();
        jnombre = new javax.swing.JTextField();
        jedad = new javax.swing.JTextField();
        jciudad = new javax.swing.JTextField();
        jagregar = new javax.swing.JButton();
        color = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Tablerepaso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(Tablerepaso);

        jmodificar.setText("Modificar");
        jmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmodificarActionPerformed(evt);
            }
        });

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        nombre.setText("Nombre");

        edad.setText("Edad");

        ciudad.setText("Cuidad");

        jcolor.setText("jTextField1");

        jnombre.setText("jTextField2");

        jedad.setText("jTextField3");

        jciudad.setText("jTextField4");

        jagregar.setText("Agregar");
        jagregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jagregarActionPerformed(evt);
            }
        });

        color.setText("Color");

        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Consultar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jmodificar)
                .addGap(32, 32, 32)
                .addComponent(jButton3)
                .addGap(33, 33, 33)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(193, 193, 193))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jagregar)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ciudad)
                            .addComponent(edad)
                            .addComponent(nombre)
                            .addComponent(color))
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcolor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jedad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jciudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(color)
                            .addComponent(jcolor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nombre)
                            .addComponent(jnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edad)
                            .addComponent(jedad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ciudad)
                            .addComponent(jciudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(jagregar)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jmodificar)
                        .addComponent(jButton1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jButton4)))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmodificarActionPerformed
int fila = Tablerepaso.getSelectedRow();
    if (fila >= 0) {
        TableModel model = Tablerepaso.getModel();
        model.setValueAt(jcolor.getText(), fila, 0);
        model.setValueAt(jnombre.getText(), fila, 1);
        model.setValueAt(jedad.getText(), fila, 2);
        model.setValueAt(jciudad.getText(), fila, 3);
        limpiarCampos();
        
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione una fila para modificar.");
    }

    }//GEN-LAST:event_jmodificarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
TablaRepaso();
        repaso2 p2 = new repaso2();
        p2.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jagregarActionPerformed

        
        
            DefaultTableModel model = (DefaultTableModel) Tablerepaso.getModel();
    String color = jcolor.getText();
    String nombre = jnombre.getText();
    String edad = jedad.getText();
    String ciudad = jciudad.getText();

    if (color.isEmpty() || nombre.isEmpty() || edad.isEmpty() || ciudad.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
    } else {
        model.addRow(new Object[]{color, nombre, edad, ciudad});
       limpiarCampos();
    }


    }//GEN-LAST:event_jagregarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

                                   
        DefaultTableModel model = (DefaultTableModel) Tablerepaso.getModel();
    int fila = Tablerepaso.getSelectedRow();
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "¿Estás seguro de que deseas eliminar a esta persona?", 
        "Confirmar eliminación", 
        JOptionPane.YES_NO_OPTION);
    if (fila >= 0) {
        model.removeRow(fila);
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione una fila para borrar.");
               limpiarCampos();

    }

   
    
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

      int fila = Tablerepaso.getSelectedRow();
    if (fila >= 0) {
        TableModel model = Tablerepaso.getModel();
        jcolor.setText(model.getValueAt(fila, 0).toString());
        jnombre.setText(model.getValueAt(fila, 1).toString());
        jedad.setText(model.getValueAt(fila, 2).toString());
        jciudad.setText(model.getValueAt(fila, 3).toString());
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione una fila para consultar.");
               limpiarCampos();

    }
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(repaso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(repaso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(repaso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(repaso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new repaso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tablerepaso;
    private javax.swing.JLabel ciudad;
    private javax.swing.JLabel color;
    private javax.swing.JLabel edad;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jagregar;
    private javax.swing.JTextField jciudad;
    private javax.swing.JTextField jcolor;
    private javax.swing.JTextField jedad;
    private javax.swing.JButton jmodificar;
    private javax.swing.JTextField jnombre;
    private javax.swing.JLabel nombre;
    // End of variables declaration//GEN-END:variables
}
