/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyectoa;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
/**
 *
 * @author angel-monterroso
 */
public class CrearCupones extends javax.swing.JFrame {
    
    private JTextField codigoField;
    private JTextField descuentoField;
    private JTextField fechaVencimientoField;
    private JTable cuponesTable;
    private JButton guardarButton;
    private JButton modificarButton;
    private JButton salirButton;

    /**
     * Creates new form CrearCupones
     */
    public CrearCupones() {
        
    initComponents();
    codigoField = codigo;
    descuentoField = descuento;
    fechaVencimientoField = fechaVencimiento;
    cuponesTable = jTable1;
    guardarButton = guardar;
    modificarButton = modificar;
    salirButton = salir;
    
    configurarTabla();
    agregarEventos();
    
    jComboBox1.removeAllItems();
        jComboBox1.addItem("Descuento %");
        jComboBox1.addItem("Monto");
        
        jComboBox1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String seleccion = (String) jComboBox1.getSelectedItem();
                String valor = descuentoField.getText().replace("%", "").trim();
                if (seleccion.equals("Descuento %")) {
                    if (!valor.isEmpty()) {
                        descuentoField.setText(valor + "%");
                    }
                } else {
                    descuentoField.setText(valor);
                }
            }
        });
        
      //  btnCargarCSV.addActionListener(e -> cargarCuponesDesdeCSV());
        
        btnGuardarCSV.addActionListener(e -> guardarCuponesEnCSV());
        
}
    private DefaultTableModel tableModel;
    private ArrayList<Cupon> cupones = ProyectoA.cupones;
    private int selectedRow = -1;

        private void configurarTabla() {
        tableModel = new DefaultTableModel(new String[]{"Código", "Descuento % - monto" ,"Fecha Vencimiento"}, 0);
        cuponesTable.setModel(tableModel);
    }
        
        private void agregarEventos() {
        guardarButton.addActionListener(this::guardarCupon);
        modificarButton.addActionListener(this::modificarCupon);
        cuponesTable.getSelectionModel().addListSelectionListener(event -> seleccionarCupon());
    }
        
             
        private void guardarCupon(ActionEvent e) {
        String codigo = codigoField.getText().trim();
        String descuentoTexto = descuentoField.getText().replace("%", "").trim();
        String fechaVencimiento = fechaVencimientoField.getText().trim();
       
        if (codigo.isEmpty() || descuentoTexto.isEmpty() || fechaVencimiento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }
        
    double descuento;
        try {
            descuento = Double.parseDouble(descuentoTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El descuento debe ser un número válido.");
            return;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(fechaVencimiento);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa dd/MM/yyyy.");
            return;
        }
        // para salir en el csv
       /* String tipo = (String) jComboBox1.getSelectedItem();
        tipo = tipo.equals("Descuento %") ? "porcentaje" : "monto";*/
       
       // Aquí asignamos el tipo según el JComboBox
    String tipo;
    if (jComboBox1.getSelectedItem().equals("Descuento %")) {
        tipo = "porcentaje";
    } else {
        tipo = "monto";
    }

      //  Cupon cupon = new Cupon(codigo, descuento, fechaVencimiento);
        //cupones.add(cupon);
        //tableModel.addRow(new Object[]{codigo, descuento, fechaVencimiento});
        
        Cupon cupon = new Cupon(codigo, descuento, fechaVencimiento, tipo);
        cupones.add(cupon);
//cupon.setValorDescuento(descuento);
        
        cupones.add(cupon);
        // en la tabla desde el csv
        String descuentoFormateado = tipo.equals("porcentaje") ? descuento + "%" : String.valueOf(descuento);
        tableModel.addRow(new Object[]{codigo, descuentoFormateado, fechaVencimiento});

       // cupones.add(cupon);
        
         limpiarCampos();
        JOptionPane.showMessageDialog(this, "Cupón guardado exitosamente.");
    }
        
        private void seleccionarCupon() {
            selectedRow = cuponesTable.getSelectedRow();
    if (selectedRow != -1) {
        Cupon cupon = cupones.get(selectedRow);
        
        codigoField.setText(cupon.getCodigo());
        fechaVencimientoField.setText(cupon.getFechaVencimiento());

        if (cupon.getTipoDescuento().equalsIgnoreCase("porcentaje")) {
            jComboBox1.setSelectedItem("Descuento %");
            descuentoField.setText(cupon.getDescuento() + "%");
            } else {
            jComboBox1.setSelectedItem("Monto");
            descuentoField.setText(String.valueOf(cupon.getDescuento()));
        }

        modificarButton.setEnabled(true);
    }
            
      /*  selectedRow = cuponesTable.getSelectedRow();
        if (selectedRow != -1) {
            codigoField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            descuentoField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            fechaVencimientoField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            modificarButton.setEnabled(true);
        }*/
    }
        private void modificarCupon(ActionEvent e) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cupón para modificar.");
            return;
        }
        
        String nuevoCodigo = codigoField.getText().trim();
        String nuevoDescuentoTexto = descuentoField.getText().replace("%", "").trim();
        String nuevaFechaVencimiento = fechaVencimientoField.getText().trim();

        
        if (nuevoCodigo.isEmpty() || nuevoDescuentoTexto.isEmpty() || nuevaFechaVencimiento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
            
    }
        
        double nuevoDescuento;
        try {
            nuevoDescuento = Double.parseDouble(nuevoDescuentoTexto);
             } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El descuento debe ser un número válido.");
            return;
        }
        
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
        try {
            sdf.parse(nuevaFechaVencimiento);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa dd/MM/yyyy.");
            return; 
        }
        
        // Nuevo código: tipo de descuento en el csv
    String tipo = (String) jComboBox1.getSelectedItem();
    tipo = tipo.equals("Descuento %") ? "porcentaje" : "monto";
    
    Cupon cupon = cupones.get(selectedRow);
    cupon.setCodigo(nuevoCodigo);
    cupon.setDescuento(nuevoDescuento);
    cupon.setFechaVencimiento(nuevaFechaVencimiento);
    cupon.setTipoDescuento(tipo);
    
      //  Cupon cupon = cupones.get(selectedRow);
     //   cupon.setCodigo(nuevoCodigo);
        //cupon.setDescuento(nuevoDescuento);
       // cupon.setFechaVencimiento(nuevaFechaVencimiento);
       
    String descuentoFormateado = tipo.equals("porcentaje") ? nuevoDescuento + "%" : String.valueOf(nuevoDescuento);
    tableModel.setValueAt(nuevoCodigo, selectedRow, 0);
    tableModel.setValueAt(descuentoFormateado, selectedRow, 1);
    tableModel.setValueAt(nuevaFechaVencimiento, selectedRow, 2); 
      //  tableModel.setValueAt(nuevoCodigo, selectedRow, 0);
       // tableModel.setValueAt(nuevoDescuento, selectedRow, 1);
        //tableModel.setValueAt(nuevaFechaVencimiento, selectedRow, 2);
        
        limpiarCampos();
    guardar.setEnabled(false);
    JOptionPane.showMessageDialog(this, "Cupón modificado exitosamente.");
    }
        
        private void limpiarCampos() {
        codigoField.setText("");
        descuentoField.setText("");
        fechaVencimientoField.setText("");
        cuponesTable.clearSelection();
        selectedRow = -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CrearCupones().setVisible(true));
    
        }
    
    private void cargarCuponesDesdeCSV(File archivo) {
    int cargados = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
         // Leer primera línea y verificar si es cabecera
        linea = br.readLine();
        if (linea != null && linea.contains("codigo") && linea.contains("fecha")) {
            // Es cabecera, saltarla
            } else {
            // No hay cabecera, usar esa línea como la primera
            procesarLinea(linea);
            cargados++;
        }
        while ((linea = br.readLine()) != null) {
            if (procesarLinea(linea)) {
                cargados++;
            }
        }
        JOptionPane.showMessageDialog(this, cargados + " cupón(es) cargado(s) correctamente.");

    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + ex.getMessage());
    }
    /*    JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();
            
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(archivo))){
                String linea;
                int contador = 0;
                while ((linea = br.readLine()) != null){
                    if (linea.trim().isEmpty() || linea.startsWith("codigo_descuento")) continue; 
                    String[] partes = linea.split("\\|");
                    
                    if (partes.length == 4){
                        String codigo = partes[0].trim();
                        double descuento = Double.parseDouble(partes[1].trim());
                        String tipo = partes[2].trim();
                        String fecha = partes[3].trim();
                        
                        Cupon cupon = new Cupon(codigo, descuento, fecha);
                        cupon.setTipoDescuento(tipo);
                        
                        cupones.add(cupon);
                        tableModel.addRow(new Object[]{
                            codigo,
                            tipo.equalsIgnoreCase("porcentaje") ? descuento + "%" : descuento,
                            fecha
                        });
                        contador++;
                    }
                }
                JOptionPane.showMessageDialog(this, contador + " cupon(es) cargado(s) correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + ex.getMessage());
            }
        }*/
        
    }
    
    private boolean procesarLinea(String linea) {
    if (linea == null || linea.trim().isEmpty()) return false;

    String[] partes = linea.split("\\|");
    if (partes.length < 4) return false; // Deben ser 4 columnas

    String codigo = partes[0].trim();
    String descuentoTexto = partes[1].trim();
    String tipo = partes[2].trim(); // "porcentaje" o "monto"
    String fecha = partes[3].trim();

    double descuento;
    try {
        descuento = Double.parseDouble(descuentoTexto);
    } catch (NumberFormatException e) {
        return false;
    }

    Cupon cupon = new Cupon(codigo, descuento, fecha, tipo);
    cupones.add(cupon);

   // cupon.setValorDescuento(descuento); // importante para que funcione

    cupones.add(cupon);

    // Mostrar en JTable correctamente
    String descuentoFormateado = tipo.equals("porcentaje") ? descuento + "%" : String.valueOf(descuento);
    tableModel.addRow(new Object[]{codigo, descuentoFormateado, fecha});

    return true;
}

    
    private void guardarCuponesEnCSV() {
        JFileChooser fileChoser = new JFileChooser();
        int resultado = fileChoser.showSaveDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChoser.getSelectedFile();
            
            try (java.io.PrintWriter pw = new java.io.PrintWriter(archivo)) {
             //   pw.print("codigo_descuento|descuento|tipo_descuento|fecha_vencimiento");
                //"codigo_descuento|descuento|tipo_descuento|fecha_vencimiento"
                for (Cupon cupon : cupones){
                    String linea = String.format("%s|%s||%s", //|%s|%s|%s|%s
                            cupon.getCodigo(),
                            cupon.getDescuento(),
                            //cupon.getTipoDescuento(),
                            cupon.getFechaVencimiento());
                    pw.println(linea);
                }
                
                JOptionPane.showMessageDialog(this, "Cupones guardados correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + ex.getMessage());
            }
            
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

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        guardar = new javax.swing.JButton();
        modificar = new javax.swing.JButton();
        codigo = new javax.swing.JTextField();
        descuento = new javax.swing.JTextField();
        fechaVencimiento = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        salir = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnCargarCSV = new javax.swing.JButton();
        btnGuardarCSV = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Código del Cupón");

        jLabel3.setText("Fecha de Vencimiento");

        guardar.setText("Guardar");
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });

        modificar.setText("Modificar");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        Eliminar.setText("Eliminar");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnCargarCSV.setText("Cargar");
        btnCargarCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarCSVActionPerformed(evt);
            }
        });

        btnGuardarCSV.setText("Guardar");
        btnGuardarCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCSVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(102, 102, 102)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(codigo, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(descuento)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(guardar)
                                .addGap(38, 38, 38)
                                .addComponent(modificar)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(Eliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(salir))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(fechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(83, 83, 83)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCargarCSV)
                    .addComponent(btnGuardarCSV))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 371, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCargarCSV))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(fechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(btnGuardarCSV)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guardar)
                    .addComponent(modificar)
                    .addComponent(salir)
                    .addComponent(Eliminar))
                .addGap(57, 57, 57))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        this.dispose();
    }//GEN-LAST:event_salirActionPerformed

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed

        int filaSeleccionada = cuponesTable.getSelectedRow();
    
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona un cupón para eliminar.");
        return;
    }   

    int confirm = JOptionPane.showConfirmDialog(this, 
        "¿Estás seguro de que deseas eliminar este cupón?", 
        "Confirmar eliminación", 
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        cupones.remove(filaSeleccionada); //Elimina del ArrayList
        tableModel.removeRow(filaSeleccionada); // Elimina de la tabla
        limpiarCampos(); // Limpia los campos
     //   modificarButton.setEnabled(false);
       // JOptionPane.showMessageDialog(this, "Cupón eliminado exitosamente.");
    }

       
    }//GEN-LAST:event_EliminarActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guardarActionPerformed

    private void btnCargarCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarCSVActionPerformed

    JFileChooser fileChooser = new JFileChooser();
int resultado = fileChooser.showOpenDialog(this);

if (resultado == JFileChooser.APPROVE_OPTION) {
    File archivoSeleccionado = fileChooser.getSelectedFile();
    cargarCuponesDesdeCSV(archivoSeleccionado);
    }
    }//GEN-LAST:event_btnCargarCSVActionPerformed

    private void btnGuardarCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCSVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarCSVActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton btnCargarCSV;
    private javax.swing.JButton btnGuardarCSV;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField descuento;
    private javax.swing.JTextField fechaVencimiento;
    private javax.swing.JButton guardar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton modificar;
    private javax.swing.JButton salir;
    // End of variables declaration//GEN-END:variables
}
