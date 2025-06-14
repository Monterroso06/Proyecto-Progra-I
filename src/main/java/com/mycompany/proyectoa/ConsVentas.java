/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyectoa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import org.w3c.dom.*;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author angel-monterroso
 */
public class ConsVentas extends javax.swing.JFrame {
    

    /**
     * Creates new form ConsVentas
     */
    public ConsVentas() {
        initComponents(); // 1. Primero comenzar con los componentes 
        configurarTabla();// 2. Configurar encabezados de tabla
        configurarTablaLibros();
        cargarVentas();    // 3. llenar tabla con datos 
        
        // Listener para seleccionar fila y mostrar libros
        TabladeVentas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = TabladeVentas.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    mostrarLibrosDeVenta(filaSeleccionada);
                }
            }
        });
    

      
       
    }
       // Método para configurar las columnas de la tabla
    private void configurarTabla() {
        String[] encabezado = {"Nombre", "Nit", "Dirección", "Total", "Total sin Iva", "Vendedor", "Fecha"};
        DefaultTableModel modelo = new DefaultTableModel(encabezado, 0); // 0 filas iniciales
        TabladeVentas.setModel(modelo);
    }
    
    // Configura columnas de la tabla de libros vendidos
    private void configurarTablaLibros() {
        String[] encabezado = {"Título", "Cantidad", "Precio", "Fecha"}; //"Título", "Cantidad", "Precio", "Subtotal"
        DefaultTableModel modelo = new DefaultTableModel(encabezado, 0);
        tablaLibrosVenta.setModel(modelo);
    }
    
    
     // Método para cargar los datos de ventas
    private void cargarVentas() {
        DefaultTableModel modelo = (DefaultTableModel) TabladeVentas.getModel();
        modelo.setRowCount(0); // Limpiar antes de llenar
        
        for (VentaRealizada venta : ProyectoA.ventasRealizadas) {
           // for (LibroVendido libro : venta.librosVendidos) {
            modelo.addRow(new Object[]{
                venta.nombre,      // Estos campos deben existir en VentaRealizada
                venta.nit,
                venta.direccion,
                venta.subtotal,
                venta.totalSinIva,
                venta.vendedor,
                venta.fecha
            });
        }
        
}
    
    
    // me muestra los libros de la venta 
    private void mostrarLibrosDeVenta(int filaVenta) {
        VentaRealizada venta = ProyectoA.ventasRealizadas.get(filaVenta);
        DefaultTableModel modelo = (DefaultTableModel) tablaLibrosVenta.getModel();
        modelo.setRowCount(0); // limpiar 

        for (LibroVendido libro : venta.librosVendidos) {
            modelo.addRow(new Object[]{
                libro.titulo,
                //libro.precioUnitario,
                libro.cantidad,
                libro.subtotal,
                venta.fecha
            });
        }
    }

   
    private void guardarVentasEnCSV(File archivo) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
        for (VentaRealizada venta : ProyectoA.ventasRealizadas) {
            String linea = venta.nombre + "," + venta.nit + "," + venta.direccion + "," +
                           venta.subtotal + "," + venta.totalSinIva + "," +
                           venta.vendedor + "," + venta.fecha;
            writer.write(linea);
            writer.newLine();
        }
        JOptionPane.showMessageDialog(this, "Ventas guardadas en CSV correctamente.");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error al guardar ventas en CSV: " + e.getMessage());
    }
}
    
    private void cargarVentasDesdeCSV(File archivo) {
    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        ProyectoA.ventasRealizadas.clear();
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split("\\,");
            if (partes.length == 7) {
                VentaRealizada venta = new VentaRealizada();
                venta.nombre = partes[0];
                venta.nit = partes[1];
                venta.direccion = partes[2];
                venta.subtotal = Double.parseDouble(partes[3]);
                venta.totalSinIva = Double.parseDouble(partes[4]);
                venta.vendedor = partes[5];
                venta.fecha = partes[6];
                ProyectoA.ventasRealizadas.add(venta);
            }
        }
        cargarVentas(); // refrescar tabla
        JOptionPane.showMessageDialog(this, "Ventas cargadas desde CSV correctamente.");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar ventas desde CSV: " + e.getMessage());
    }
}
    
    private void guardarLibrosDeVentaEnCSV(File archivo, int filaVenta) {
    if (filaVenta < 0 || filaVenta >= ProyectoA.ventasRealizadas.size()) return;
    VentaRealizada venta = ProyectoA.ventasRealizadas.get(filaVenta);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
        for (LibroVendido libro : venta.librosVendidos) {
            String linea = libro.titulo + "," + libro.cantidad + "," + libro.subtotal + "," + venta.fecha;
            writer.write(linea);
            writer.newLine();
        }
        JOptionPane.showMessageDialog(this, "Libros vendidos guardados correctamente.");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error al guardar libros vendidos: " + e.getMessage());
    }
}
    
    private void cargarLibrosDeVentaDesdeCSV(File archivo, int filaVenta) {
    if (filaVenta < 0 || filaVenta >= ProyectoA.ventasRealizadas.size()) return;
    VentaRealizada venta = ProyectoA.ventasRealizadas.get(filaVenta);
    venta.librosVendidos.clear();
    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split("\\,");
            if (partes.length >= 3) {
                LibroVendido libro = new LibroVendido();
                libro.titulo = partes[0];
                libro.cantidad = Integer.parseInt(partes[1]);
                libro.subtotal = Double.parseDouble(partes[2]);
                venta.fecha = partes.length > 3 ? partes[3] : "";
                venta.librosVendidos.add(libro);
            }
        }
        mostrarLibrosDeVenta(filaVenta);
        JOptionPane.showMessageDialog(this, "Libros vendidos cargados correctamente.");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar libros vendidos: " + e.getMessage());
    }
}
    
    private void GuardarinflibroxmlActionPerformed(File archivoXML) {
        try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element rootElement = doc.createElement("LibrosVendidos");
        doc.appendChild(rootElement);

        DefaultTableModel modelo = (DefaultTableModel) tablaLibrosVenta.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Element libroElement = doc.createElement("Libro");

            Element titulo = doc.createElement("Titulo");
            titulo.setTextContent(modelo.getValueAt(i, 0).toString());
            libroElement.appendChild(titulo);

            Element cantidad = doc.createElement("Cantidad");
            cantidad.setTextContent(modelo.getValueAt(i, 1).toString());
            libroElement.appendChild(cantidad);

            Element subtotal = doc.createElement("Subtotal");
            subtotal.setTextContent(modelo.getValueAt(i, 2).toString());
            libroElement.appendChild(subtotal);

            Element fecha = doc.createElement("Fecha");
            fecha.setTextContent(modelo.getValueAt(i, 3).toString());
            libroElement.appendChild(fecha);

            rootElement.appendChild(libroElement);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(archivoXML);
        transformer.transform(source, result);

        JOptionPane.showMessageDialog(this, "Libros exportados a XML correctamente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al guardar XML: " + e.getMessage());
    }

    }
    
    
    private void CargarinflibroxmlActionPerformed(File archivoXML) {
        try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(archivoXML);

        doc.getDocumentElement().normalize();
        NodeList listaLibros = doc.getElementsByTagName("Libro");

        DefaultTableModel modelo = (DefaultTableModel) tablaLibrosVenta.getModel();
        //modelo.setRowCount(0); // Limpiar tabla antes de cargar

        for (int i = 0; i < listaLibros.getLength(); i++) {
            Node nodo = listaLibros.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;

                String titulo = elemento.getElementsByTagName("Titulo").item(0).getTextContent();
                int cantidad = Integer.parseInt(elemento.getElementsByTagName("Cantidad").item(0).getTextContent());
                double subtotal = Double.parseDouble(elemento.getElementsByTagName("Subtotal").item(0).getTextContent());
                String fecha = elemento.getElementsByTagName("Fecha").item(0).getTextContent();

                modelo.addRow(new Object[]{titulo, cantidad, subtotal, fecha});
            }
        }

        JOptionPane.showMessageDialog(this, "Libros importados desde XML correctamente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar XML: " + e.getMessage());
    }
}
    
    private void GuardarinfventaxmlActionPerformed(File archivo) {
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element rootElement = doc.createElement("VentasRealizadas");
        doc.appendChild(rootElement);

        DefaultTableModel modelo = (DefaultTableModel) TabladeVentas.getModel(); // <-- usa la tabla principal
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Element ventaElement = doc.createElement("Venta");

            Element nombre = doc.createElement("Nombre");
            nombre.setTextContent(modelo.getValueAt(i, 0).toString());
            ventaElement.appendChild(nombre);

            Element nit = doc.createElement("Nit");
            nit.setTextContent(modelo.getValueAt(i, 1).toString());
            ventaElement.appendChild(nit);

            Element direccion = doc.createElement("Direccion");
            direccion.setTextContent(modelo.getValueAt(i, 2).toString());
            ventaElement.appendChild(direccion);

            Element subtotal = doc.createElement("Subtotal");
            subtotal.setTextContent(modelo.getValueAt(i, 3).toString());
            ventaElement.appendChild(subtotal);

            Element totalSinIVA = doc.createElement("TotalSinIVA");
            totalSinIVA.setTextContent(modelo.getValueAt(i, 4).toString());
            ventaElement.appendChild(totalSinIVA);

            Element vendedor = doc.createElement("Vendedor");
            vendedor.setTextContent(modelo.getValueAt(i, 5).toString());
            ventaElement.appendChild(vendedor);

            Element fecha = doc.createElement("Fecha");
            fecha.setTextContent(modelo.getValueAt(i, 6).toString());
            ventaElement.appendChild(fecha);

            rootElement.appendChild(ventaElement);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(archivo);
        transformer.transform(source, result);

        JOptionPane.showMessageDialog(this, "Ventas exportadas correctamente a XML.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al guardar XML: " + e.getMessage());
    }
}
    
    private void CargarinfventaxmlActionPerformed(File archivo) {
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(archivo);
        doc.getDocumentElement().normalize();

        NodeList listaVentas = doc.getElementsByTagName("Venta");

        DefaultTableModel modelo = (DefaultTableModel) TabladeVentas.getModel();
        modelo.setRowCount(0); // Limpiar tabla antes de cargar

        for (int i = 0; i < listaVentas.getLength(); i++) {
            Node nodo = listaVentas.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;

                String nombre = elemento.getElementsByTagName("Nombre").item(0).getTextContent();
                String nit = elemento.getElementsByTagName("Nit").item(0).getTextContent();
                String direccion = elemento.getElementsByTagName("Direccion").item(0).getTextContent();
                double subtotal = Double.parseDouble(elemento.getElementsByTagName("Subtotal").item(0).getTextContent());
                double totalSinIVA = Double.parseDouble(elemento.getElementsByTagName("TotalSinIVA").item(0).getTextContent());
                String vendedor = elemento.getElementsByTagName("Vendedor").item(0).getTextContent();
                String fecha = elemento.getElementsByTagName("Fecha").item(0).getTextContent();

                modelo.addRow(new Object[]{nombre, nit, direccion, subtotal, totalSinIVA, vendedor, fecha});
            }
        }

        JOptionPane.showMessageDialog(this, "Ventas cargadas correctamente desde XML.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar XML: " + e.getMessage());
    }
}
  //  }

    
  /*private void TabladeVentas() {
     String encabezado[] = {"Nombre","Nit","Direccion","Total", "Total sin Iva", "Vendedor", "Fecha"} ; 
        DefaultTableModel t = new DefaultTableModel(encabezado,ProyectoA.ConsVentas.size());
        TabladeVentas.setModel(t);
        TableModel tabla = TabladeVentas.getModel();
        
      
        }/*

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabladeVentas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaLibrosVenta = new javax.swing.JTable();
        Cargarinfcsv = new javax.swing.JButton();
        guardarinfcsv = new javax.swing.JButton();
        Cargarinflibro = new javax.swing.JButton();
        Guardarinflibro = new javax.swing.JButton();
        CargarXML = new javax.swing.JButton();
        GuardaeXML = new javax.swing.JButton();
        Guardarxml = new javax.swing.JButton();
        Cargarxml = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Consulta de ventas");

        TabladeVentas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(TabladeVentas);

        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tablaLibrosVenta.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tablaLibrosVenta);

        Cargarinfcsv.setText("Cargar CSV");
        Cargarinfcsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargarinfcsvActionPerformed(evt);
            }
        });

        guardarinfcsv.setText("Guardar CSV");
        guardarinfcsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarinfcsvActionPerformed(evt);
            }
        });

        Cargarinflibro.setText("Cargar csv");
        Cargarinflibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargarinflibroActionPerformed(evt);
            }
        });

        Guardarinflibro.setText("Guardar csv");
        Guardarinflibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarinflibroActionPerformed(evt);
            }
        });

        CargarXML.setText("Cargarxml");
        CargarXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargarXMLActionPerformed(evt);
            }
        });

        GuardaeXML.setText("Guardarxml");
        GuardaeXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardaeXMLActionPerformed(evt);
            }
        });

        Guardarxml.setText("Guardarxml");
        Guardarxml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarxmlActionPerformed(evt);
            }
        });

        Cargarxml.setText("Cargarxml");
        Cargarxml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargarxmlActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Cargarinfcsv)
                                .addComponent(guardarinfcsv))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(Guardarxml)
                                        .addComponent(Cargarxml))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(Guardarinflibro)
                                        .addComponent(Cargarinflibro)
                                        .addComponent(GuardaeXML)
                                        .addComponent(CargarXML)))
                                .addGap(15, 15, 15)))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Cargarinfcsv)
                        .addGap(18, 18, 18)
                        .addComponent(guardarinfcsv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Cargarxml)
                        .addGap(18, 18, 18)
                        .addComponent(Guardarxml))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jButton1)
                        .addGap(24, 273, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Cargarinflibro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Guardarinflibro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(CargarXML)
                                .addGap(18, 18, 18)
                                .addComponent(GuardaeXML))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void guardarinfcsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarinfcsvActionPerformed

            JFileChooser fileChooser = new JFileChooser();
    if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();
        guardarVentasEnCSV(archivo);
    }
    }//GEN-LAST:event_guardarinfcsvActionPerformed

    private void CargarinfcsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargarinfcsvActionPerformed

        JFileChooser fileChooser = new JFileChooser();
    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();
        cargarVentasDesdeCSV(archivo);
    }

    }//GEN-LAST:event_CargarinfcsvActionPerformed

    private void CargarinflibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargarinflibroActionPerformed
        
         JFileChooser fileChooser = new JFileChooser();
    int seleccion = fileChooser.showOpenDialog(this);

    if (seleccion == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            DefaultTableModel modelo = (DefaultTableModel) tablaLibrosVenta.getModel();
            modelo.setRowCount(0); // Limpiar tabla

            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split("\\,");
                if (datos.length == 4) {
                    modelo.addRow(new Object[]{
                        datos[0], // título
                        Integer.parseInt(datos[1]), // cantidad
                        Double.parseDouble(datos[2]), // subtotal
                        datos[3] // fecha
                    });
                }
            }

            JOptionPane.showMessageDialog(this, "Libros cargados correctamente.");
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el archivo: " + e.getMessage());
        }
    }  
    }//GEN-LAST:event_CargarinflibroActionPerformed

    private void GuardarinflibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarinflibroActionPerformed

        JFileChooser fileChooser = new JFileChooser();
    int seleccion = fileChooser.showSaveDialog(this);

    if (seleccion == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            DefaultTableModel modelo = (DefaultTableModel) tablaLibrosVenta.getModel();

            for (int i = 0; i < modelo.getRowCount(); i++) {
                String titulo = modelo.getValueAt(i, 0).toString();
                String cantidad = modelo.getValueAt(i, 1).toString();
                String subtotal = modelo.getValueAt(i, 2).toString();
                String fecha = modelo.getValueAt(i, 3).toString();

                writer.println(titulo + "," + cantidad + "," + subtotal + "," + fecha);
            }

            JOptionPane.showMessageDialog(this, "Libros guardados correctamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_GuardarinflibroActionPerformed

    private void CargarXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargarXMLActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showOpenDialog(this);
    if (seleccion == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();
        CargarinflibroxmlActionPerformed(archivo);
    }
    }//GEN-LAST:event_CargarXMLActionPerformed

    private void GuardaeXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardaeXMLActionPerformed
        JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Guardar archivo XML");
  //  fileChooser.setSelectedFile(new File("usuarios.xml")); // nombre sugerido
    
    int seleccion = fileChooser.showSaveDialog(this);
    
     if (seleccion == JFileChooser.APPROVE_OPTION) {
         File archivo = fileChooser.getSelectedFile();
         
          // Asegura que tenga la extensión .xml
    /*    if (!archivo.getName().toLowerCase().endsWith(".xml")) {
            archivo = new File(archivo.getParentFile(), archivo.getName() + ".xml");
        }*/
        GuardarinflibroxmlActionPerformed(archivo);
     }
    }//GEN-LAST:event_GuardaeXMLActionPerformed

    private void GuardarxmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarxmlActionPerformed
       JFileChooser chooser = new JFileChooser();
    int seleccion = chooser.showSaveDialog(this);
    if (seleccion == JFileChooser.APPROVE_OPTION) {
        File archivo = chooser.getSelectedFile();
        GuardarinfventaxmlActionPerformed(archivo);
    }
    }//GEN-LAST:event_GuardarxmlActionPerformed

    private void CargarxmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargarxmlActionPerformed
    JFileChooser fileChooser = new JFileChooser();
    int seleccion = fileChooser.showOpenDialog(this);
    if (seleccion == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();
        CargarinfventaxmlActionPerformed(archivo);
    }
    }//GEN-LAST:event_CargarxmlActionPerformed

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
            java.util.logging.Logger.getLogger(ConsVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConsVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConsVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConsVentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CargarXML;
    private javax.swing.JButton Cargarinfcsv;
    private javax.swing.JButton Cargarinflibro;
    private javax.swing.JButton Cargarxml;
    private javax.swing.JButton GuardaeXML;
    private javax.swing.JButton Guardarinflibro;
    private javax.swing.JButton Guardarxml;
    private javax.swing.JTable TabladeVentas;
    private javax.swing.JButton guardarinfcsv;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaLibrosVenta;
    // End of variables declaration//GEN-END:variables
}
