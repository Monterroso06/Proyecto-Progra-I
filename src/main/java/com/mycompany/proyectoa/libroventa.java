/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyectoa;

import java.awt.event.ItemEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author angel-monterroso
 */
public class libroventa extends javax.swing.JFrame {
    
   // private JTextField descuentoField;
   // private javax.swing.JTextField descuento; 

    /**
     * Creates new form libroventa
     */
    public libroventa() {
        initComponents();
        configurarTablaLibrosVendidos();
        llenarTablaLibros(); 
        
        jButton1.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt); 
    }
    
    
});
        jComboBox1.removeAllItems();
        jComboBox1.addItem("Descuento %");
        jComboBox1.addItem("Monto");
        
        jComboBox1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String seleccion = (String) jComboBox1.getSelectedItem();
                String valor = descuento.getText().replace("%", "").trim();
                if (seleccion.equals("Descuento %")) {
                    if (!valor.isEmpty()) {
                        descuento.setText(valor + "%");
                    }
                } else {
                    descuento.setText(valor);
                }
            }
        });
        
        llenarTablaLibros();
        
        jButton2.addActionListener(e -> {
        int filaSeleccionada = jTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un libro de la tabla.");
            return;
        }
               
        String codigoIngresado = codigo.getText().trim();
        String fechaIngresada = fechaVencimiento.getText().trim();
        //String descuentoIngresado = jTextField4.getText().trim();

     if (codigoIngresado.isEmpty() || fechaIngresada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar código y fecha de vencimiento.");
            return;
        }
     // Buscar el cupón
        Cupon cupon = null;
        for (Cupon c : ProyectoA.cupones) {
            if (c.codigo.equalsIgnoreCase(codigoIngresado)) {
                cupon = c;
                break;
            }
        }
    
    if (cupon == null) {
            JOptionPane.showMessageDialog(this, "Cupón no encontrado.");
            return;
        }

    
     // Validar la fecha
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaUsuario = sdf.parse(fechaIngresada);
            Date fechaCupon = sdf.parse(cupon.fechaVencimiento);

            if (fechaUsuario.after(fechaCupon)) {
                JOptionPane.showMessageDialog(this, "El cupón está vencido.");
                return;
            }

   /*  de aqui   // Obtener precio del libro
            double precioOriginal = Double.parseDouble(jTable1.getValueAt(filaSeleccionada, 3).toString());
            double precioFinal;

            if (cupon.tipoDescuento.trim().equalsIgnoreCase("Descuento %")) {
                precioFinal = precioOriginal * (1 - cupon.valorDescuento / 100.0);
            } else {
                precioFinal = precioOriginal - cupon.valorDescuento;
            }

        if (precioFinal < 0) precioFinal = 0;

            JOptionPane.showMessageDialog(this, "Precio original: Q" + precioOriginal +
                    "\nDescuento aplicado: " + cupon.valorDescuento + (cupon.tipoDescuento.equals("Descuento %") ? "%" : "Q") +
                    "\nPrecio final: Q" + String.format("%.2f", precioFinal));hasta aqui*/
                    // Obtener precio del libro 
        double precioOriginal = Double.parseDouble(jTable1.getValueAt(filaSeleccionada, 3).toString());

        double descuentoAplicado;
        if (cupon.tipoDescuento.trim().equalsIgnoreCase("Descuento %")) {
        descuentoAplicado = precioOriginal * (cupon.valorDescuento / 100.0);
    } else {
        descuentoAplicado = cupon.valorDescuento;
        }

        double precioFinal = precioOriginal - descuentoAplicado;
        if (precioFinal < 0) precioFinal = 0;
        
        JOptionPane.showMessageDialog(this, "Precio original: Q" + precioOriginal +
        "\nDescuento aplicado: Q" + String.format("%.2f", descuentoAplicado) +
        "\nPrecio final: Q" + String.format("%.2f", precioFinal));

    } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Fecha inválida. Usa formato dd/MM/yyyy");
        }
    });

                
        
        }
        private void llenarTablaLibros() {
        String[] encabezado = {"Título", "Autor", "Género", "Precio", "Cantidad en Stock"};
        DefaultTableModel modeloTabla = new DefaultTableModel(encabezado, ProyectoA.libros.size());
        jTable1.setModel(modeloTabla);
        TableModel tabla = jTable1.getModel();
        
        for (int i = 0; i < ProyectoA.libros.size(); i++) {
            Libro libro = ProyectoA.libros.get(i);
            tabla.setValueAt(libro.titulo, i, 0);
            tabla.setValueAt(libro.autor, i, 1);
            tabla.setValueAt(libro.genero, i, 2);
            tabla.setValueAt(libro.precio, i, 3);
            tabla.setValueAt(libro.cantidadEnStock, i, 4);
        }
         
    
    }
        private void configurarTablaLibrosVendidos() {
    DefaultTableModel modelo = new DefaultTableModel(
        new Object[][]{}, 
        new String[]{"Título", "Precio Unitario", "Cantidad Vendida", "Subtotal"}
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // para que el usuario no pueda editar las celdas manualmente
        }
    };
    LibrosVendidos.setModel(modelo);
}
        private String obtenerFechaActual() {
        java.time.LocalDate fechaActual = java.time.LocalDate.now();
        return fechaActual.toString();
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
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        codigo = new javax.swing.JTextField();
        fechaVencimiento = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        descuento = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        NomVendedor = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        NoNIT = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        DireccionFa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        NombreFa = new javax.swing.JTextField();
        AgregarLibro = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        LibrosVendidos = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jLabel1.setText("Inventario de Libros");

        jLabel2.setText("Pagina de venta");

        jButton1.setText("Registrar venta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Cant. de Libros a vender");

        jButton2.setText("Aplicar cupon");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        fechaVencimiento.setText("jTextField3");

        jLabel4.setText("Codigo");

        jLabel5.setText("Fecha de Vencimiento");

        jButton3.setText("Salir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        descuento.setText("jTextField4");

        jLabel6.setText("Venta Realizada Por:");

        jLabel7.setText("NIT:");

        jLabel8.setText("Direccion");

        jLabel9.setText("Nombre");

        AgregarLibro.setText("Agregar Libro");
        AgregarLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarLibroActionPerformed(evt);
            }
        });

        LibrosVendidos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(LibrosVendidos);

        jButton4.setText("Consultar las ventas");
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(131, 131, 131)
                        .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(164, 164, 164))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel7))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(NoNIT, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(DireccionFa)
                                                .addComponent(NombreFa, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jButton3))
                                .addGap(177, 177, 177)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jButton2))
                                .addGap(32, 32, 32)
                                .addComponent(fechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(NomVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(AgregarLibro))
                                .addGap(88, 88, 88))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jButton1)
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(fechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(NomVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AgregarLibro)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(DireccionFa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(NombreFa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(NoNIT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(77, 77, 77)
                        .addComponent(jButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addComponent(jButton4)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

      /*  int filaSeleccionada = jTable1.getSelectedRow();
        
        if (filaSeleccionada == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Selecciona un libro para vender.");
        return;
    }
        
         String cantidadTexto = jTextField1.getText().trim();
    int cantidadVendida;
    
    try {
        cantidadVendida = Integer.parseInt(cantidadTexto);
        if (cantidadVendida <= 0) {
            throw new NumberFormatException();
            }
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Ingresa una cantidad válida mayor a 0.");
        return;
    }
    
    Libro libroSeleccionado = ProyectoA.libros.get(filaSeleccionada);
    
    if (libroSeleccionado.cantidadEnStock < cantidadVendida) {
        javax.swing.JOptionPane.showMessageDialog(this, "No hay suficiente stock para esta venta.");
        return;
    }
    
    // Aplicar descuento si hay un cupun valido
    double precioTotal = libroSeleccionado.precio * cantidadVendida;
    
    String codigoIngresado = jTextField2.getText().trim();
    String fechaIngresada = jTextField3.getText().trim();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    boolean cuponValido = false;
    
    for (Cupon c : ProyectoA.cupones) {
    if (c.getCodigo().equalsIgnoreCase(codigoIngresado)) {
        try {
            Date fechaUsuario = sdf.parse(fechaIngresada);
            Date fechaCupon = sdf.parse(c.getFechaVencimiento());

            if (!fechaUsuario.after(fechaCupon)) {
                if (c.tipoDescuento.equals("Descuento %")) {
                    precioTotal *= (1 - c.getDescuento() / 100.0);
                } else {
                    precioTotal -= c.getDescuento();
                }
                cuponValido = true;
                break;
                } else {
                JOptionPane.showMessageDialog(this, "El cupón está vencido.");
                break;
            }
            
            } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa dd/MM/yyyy");
            return;
        }
    }
}

if (!cuponValido && !codigoIngresado.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Cupón no encontrado o inválido.");
}

    
    // Reducir stock
    libroSeleccionado.cantidadEnStock -= cantidadVendida;
    
    NomVendedor.setText(ProyectoA.nombreVendedorActual);
    
        // Mostrar mensaje de éxito
    javax.swing.JOptionPane.showMessageDialog(this, "Venta realizada con éxito.\nTotal: Q" + precioTotal);

    // Limpiar campos y actualizar tabla
    jTextField1.setText("");
    jTextField2.setText("");
    jTextField3.setText("");
    llenarTablaLibros();

    NomVendedor.setEditable(false);*/
      
      DefaultTableModel modelo = (DefaultTableModel) LibrosVendidos.getModel();
      int totalFilas = modelo.getRowCount();
      if (totalFilas == 0) {
        JOptionPane.showMessageDialog(this, "Agrega al menos un libro antes de registrar la venta.");
        return;
    }
      double totalVenta = 0.0;
      
      String fecha = obtenerFechaActual();

    for (int i = 0; i < totalFilas; i++) {
    Object subtotalObj = modelo.getValueAt(i, 3);
    double subtotal;
    if (subtotalObj instanceof Integer) {
        subtotal = ((Integer) subtotalObj).doubleValue();
    } else if (subtotalObj instanceof Double) {
        subtotal = (Double) subtotalObj;
    } else {
        subtotal = 0.0;
    }
    totalVenta += subtotal;
}
    
   
    NomVendedor.setText(ProyectoA.nombreVendedorActual);
    
    // Aquí puedes mostrar los datos del cliente y factura
    JOptionPane.showMessageDialog(this, 
        "Venta realizada con éxito.\nTotal a pagar: Q" + String.format("%.2f", totalVenta)
        + "\nNombre: " + NombreFa.getText()
        + "\nNIT: " + NoNIT.getText()
        + "\nDirección: " + DireccionFa.getText()
    );
    
    // Ahora guardamos la venta
    String nombre = NombreFa.getText();
    String nit = NoNIT.getText();
    String direccion = DireccionFa.getText();
    double totalSinIva = totalVenta / 1.12; 
    String vendedor = ProyectoA.nombreVendedorActual;
    
    // guardamos los libros vendidos
    List<LibroVendido> librosDeEstaVenta = new ArrayList<>();
    for (int i = 0; i < totalFilas; i++) {
        String titulo = (String) modelo.getValueAt(i, 0);
        
            Object cantidadObj = modelo.getValueAt(i, 1);
        int cantidad;
        if (cantidadObj instanceof Integer) {
        cantidad = (Integer) cantidadObj;
        } else if (cantidadObj instanceof Double) {
            cantidad = (int) Math.round((Double) cantidadObj);
        }else {
            cantidad = 0; // valor seguro por si es otra cosa
    }

            Object precioUnitarioObj = modelo.getValueAt(i, 2);
    double precioUnitario;
    if (precioUnitarioObj instanceof Integer) {
        precioUnitario = ((Integer) precioUnitarioObj).doubleValue();
    } else if (precioUnitarioObj instanceof Double) {
        precioUnitario = (Double) precioUnitarioObj;
    } else {
        precioUnitario = 0.0;
    }
            
            Object subtotalObj = modelo.getValueAt(i, 3);
        double subtotal;
        if (subtotalObj instanceof Integer) {
            subtotal = ((Integer) subtotalObj).doubleValue();
                } else if (subtotalObj instanceof Double) {
                subtotal = (Double) subtotalObj;
            } else {
            subtotal = 0.0;
    }
    
        librosDeEstaVenta.add(new LibroVendido(titulo, cantidad, precioUnitario, subtotal));
    }
    
    //Creamos y guardamos la venta completa
    VentaRealizada venta = new VentaRealizada(nombre, nit, direccion, totalVenta, totalSinIva, vendedor, fecha, librosDeEstaVenta);
    ProyectoA.ventasRealizadas.add(venta);
    
    // Limpiar tabla LibrosVendidos para la siguiente venta
    modelo.setRowCount(0);
 
    // Limpiar campos
    jTextField1.setText("");
    codigo.setText("");
    fechaVencimiento.setText("");
    
    
    llenarTablaLibros(); // Refrescar la tabla de stock

    NomVendedor.setEditable(false);
    

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        this.dispose();
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void AgregarLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarLibroActionPerformed

   
    int filaSeleccionada = jTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(libroventa.this, "Selecciona un libro para agregar.");
            return;
    }
        String cantidadTexto = jTextField1.getText().trim();
        int cantidadVendida;
        
        try {
            cantidadVendida = Integer.parseInt(cantidadTexto);
            if (cantidadVendida <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(libroventa.this, "Ingresa una cantidad válida mayor a 0.");
            return;
            }

        Libro libroSeleccionado = ProyectoA.libros.get(filaSeleccionada);
        if (libroSeleccionado.cantidadEnStock < cantidadVendida) {
            JOptionPane.showMessageDialog(libroventa.this, "No hay suficiente stock para esta venta.");
            return;
        }
        
        double precioUnitario = libroSeleccionado.precio;
        double subtotal = precioUnitario * cantidadVendida;
        
       // Agregar a la tabla LibrosVendidos
        DefaultTableModel modelo = (DefaultTableModel) LibrosVendidos.getModel();
        modelo.addRow(new Object[]{
            libroSeleccionado.titulo,
            //libroSeleccionado.autor,
            precioUnitario,
            cantidadVendida,
            subtotal
        });
         // Reducir stock en la tabla principal
        libroSeleccionado.cantidadEnStock -= cantidadVendida;
        llenarTablaLibros();
       

    }//GEN-LAST:event_AgregarLibroActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        ConsVentas c = new ConsVentas();
        c.setVisible(true);

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarLibro;
    private javax.swing.JTextField DireccionFa;
    private javax.swing.JTable LibrosVendidos;
    private javax.swing.JTextField NoNIT;
    private javax.swing.JTextField NomVendedor;
    private javax.swing.JTextField NombreFa;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField descuento;
    private javax.swing.JTextField fechaVencimiento;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
