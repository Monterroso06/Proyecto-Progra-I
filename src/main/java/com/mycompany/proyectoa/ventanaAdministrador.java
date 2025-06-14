/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyectoa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
   // import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author angel-monterroso
 */
public class ventanaAdministrador extends javax.swing.JFrame {
    
  // private javax.swing.JButton Cargarserializables;
 //   private javax.swing.JButton Guardarserializables;


    /**
     * Creates new form ventanaAdministrador
     */
    public ventanaAdministrador() {
        initComponents();
        llenarTablaLibros();
        
     /*    // Guardar al cerrar la ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent evt) {
        guardarDatos();
    }
});
       // Conectar botones si no lo hiciste desde NetBeans GUI
        Cargarserializables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargarserializablesActionPerformed(evt);
            }
        });

        Guardarserializables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarserializablesActionPerformed(evt);
            }
        });*/
    }
        private void guardarDatos() {
    ArchivoBinario.guardar(ProyectoA.libros, "libros.dat");
    ArchivoBinario.guardar(ProyectoA.cupones, "cupones.dat");
    ArchivoBinario.guardar(ProyectoA.ventasRealizadas, "ventas.dat");
    ArchivoBinario.guardar(ProyectoA.usuario, "usuarios.dat");
    ArchivoBinario.guardar(ProyectoA.librosVendidosTemporales, "librosvendidos.dat");
    
    System.out.println("Datos guardados correctamente al cerrar.");
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

        private void cargarLibrosDesdeJSON(File archivoJSON) {
            try {
                String contenido = new String(Files.readAllBytes(archivoJSON.toPath()), StandardCharsets.UTF_8);
                JSONArray arreglo = new JSONArray(contenido);
                
                for (int i = 0; i < arreglo.length(); i++) {
                    JSONObject obj = arreglo.getJSONObject(i);
                    String titulo = obj.getString("titulo");
                    String autor = obj.getString("autor");
                    String genero = obj.getString("genero");
                    double precio = obj.getDouble("precio");
                    int cantidad = obj.getInt("cantidadEnStock");
                    
                    ProyectoA.libros.add(new Libro(titulo, autor, genero, precio, cantidad));
                }
                
                llenarTablaLibros();
                JOptionPane.showMessageDialog(this, "Libros cargados correctamente desde JSON.");
   
            } catch (IOException | JSONException e) {
                e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar el archivo JSON: " + e.getMessage());
                
            }
        }
        
        private void guardarLibrosComoJSON(File archivoJSON) {
            JSONArray arrayLibros = new JSONArray();
            
            for (Libro libro : ProyectoA.libros) {
                JSONObject obj = new JSONObject();
                obj.put("titulo", libro.titulo);
                obj.put("autor", libro.autor);
                obj.put("genero", libro.genero);
                obj.put("precio", libro.precio);
                obj.put("cantidadEnStock", libro.cantidadEnStock);
                arrayLibros.put(obj);
            }
            try (FileWriter fileWriter = new FileWriter(archivoJSON)) {
                fileWriter.write(arrayLibros.toString(4)); // Con sangría de 4 espacios
        JOptionPane.showMessageDialog(this, "Libros guardados exitosamente en JSON.");
   
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar JSON: " + e.getMessage());
    
            }
        }
        
        
        private void cargarLibrosDesdeCSV(File archivoCSV) {
         

    try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
        String linea;
       // ProyectoA.libros.clear();
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split("\\|");
            if (partes.length == 5) {
                String titulo = partes[0];
                String autor = partes[1];
                String genero = partes[2];
                double precio = Double.parseDouble(partes[3]);
                int cantidad = Integer.parseInt(partes[4]);
                Libro libro = new Libro(titulo, autor, genero, precio, cantidad);
                ProyectoA.libros.add(libro);
            }
        }
        llenarTablaLibros();
        JOptionPane.showMessageDialog(this, "Libros cargados desde CSV correctamente.");
    } catch (IOException | NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar CSV: " + e.getMessage());
    }
        }
        
        
        private void guardarLibrosEnCSV(File archivoCSV) throws IOException {
            try (PrintWriter pw = new PrintWriter(new FileWriter(archivoCSV))) {
                for (Libro libro : ProyectoA.libros) {
            String linea = libro.titulo + "|" +
                           libro.autor + "|" +
                           libro.genero + "|" +
                           libro.precio + "|" +
                           libro.cantidadEnStock;
            pw.println(linea);
            }
                JOptionPane.showMessageDialog(this, "Libros guardados exitosamente en el archivo CSV.");
  
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar en CSV: " + e.getMessage());
 
        }
      }
    
     
        private void guardarLibrosEnXML(File archivoXML) throws Exception {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            
            // Elemento raíz
        Element rootElement = doc.createElement("libros");
        doc.appendChild(rootElement);
        
        for (Libro libro : ProyectoA.libros) {
        Element libroElement = doc.createElement("libro");
        
        Element titulo = doc.createElement("titulo");
        titulo.appendChild(doc.createTextNode(libro.titulo));
        libroElement.appendChild(titulo);
        
        Element autor = doc.createElement("autor");
        autor.appendChild(doc.createTextNode(libro.autor));
        libroElement.appendChild(autor);
        
        Element genero = doc.createElement("genero");
        genero.appendChild(doc.createTextNode(libro.genero));
        libroElement.appendChild(genero);
        
        Element precio = doc.createElement("precio");
        precio.appendChild(doc.createTextNode(String.valueOf(libro.precio)));
        libroElement.appendChild(precio);

        Element stock = doc.createElement("cantidadEnStock");
        stock.appendChild(doc.createTextNode(String.valueOf(libro.cantidadEnStock)));
        libroElement.appendChild(stock);
        
        rootElement.appendChild(libroElement);
        }
        // Guardar en archivo
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(archivoXML);
    transformer.transform(source, result);
        }

        
        private void cargarLibrosDesdeXML(File archivoXML) throws Exception {
           // ProyectoA.libros.clear(); // Limpiar libros actuales
           
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivoXML);
            doc.getDocumentElement().normalize();
            
            NodeList listaLibros = doc.getElementsByTagName("libro");

            for (int i = 0; i < listaLibros.getLength(); i++) {
                Node nodo = listaLibros.item(i);
            
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;

                String titulo = elemento.getElementsByTagName("titulo").item(0).getTextContent();
                String autor = elemento.getElementsByTagName("autor").item(0).getTextContent();
                String genero = elemento.getElementsByTagName("genero").item(0).getTextContent();
                double precio = Double.parseDouble(elemento.getElementsByTagName("precio").item(0).getTextContent());
                int cantidad = Integer.parseInt(elemento.getElementsByTagName("cantidadEnStock").item(0).getTextContent());

            Libro libro = new Libro(titulo, autor, genero, precio, cantidad);
            ProyectoA.libros.add(libro);
            
                }
                }
                
                llenarTablaLibros();
        }
        
  /*      private void CargarserializablesActionPerformed(java.awt.event.ActionEvent evt) {
        ProyectoA.libros = (ArrayList<Libro>) (List<Libro>) ArchivoBinario.cargar("libros.dat");
        ProyectoA.cupones = (ArrayList<Cupon>) (List<Cupon>) ArchivoBinario.cargar("cupones.dat");
        ProyectoA.ventasRealizadas = (ArrayList<VentaRealizada>) (List<VentaRealizada>) ArchivoBinario.cargar("ventas.dat");
        ProyectoA.usuarios = (ArrayList<Usuario>) (List<Usuario>) ArchivoBinario.cargar("usuarios.dat");
        ProyectoA.librosVendidosTemporales = (ArrayList<LibroVendido>) (List<LibroVendido>) ArchivoBinario.cargar("librosvendidos.dat");

        JOptionPane.showMessageDialog(this, "Datos cargados correctamente.");
        actualizarTablaLibros(); // si tienes JTable1
    }

        private void GuardarserializablesActionPerformed(java.awt.event.ActionEvent evt) {
        guardarDatos();
        JOptionPane.showMessageDialog(this, "Datos guardados correctamente.");
    }
  

    private void actualizarTablaLibros() {
    DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
    modelo.setRowCount(0);  // Limpiar tabla

    for (Libro libro : ProyectoA.libros) {
        modelo.addRow(new Object[]{
            libro.getTitulo(),
            libro.getAutor(),
            libro.getGenero(),
            libro.getPrecio(),
            libro.getCantidadEnStock()
        });
    }
}*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        BncargarJson = new javax.swing.JButton();
        BnGuardarJson = new javax.swing.JButton();
        CargarCSV = new javax.swing.JButton();
        btnGuardarCSV = new javax.swing.JButton();
        Bncargarxml = new javax.swing.JButton();
        Bnguardarxml = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Bienvenido administrador");

        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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

        jButton2.setText("Nuevo Libro");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Modificar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel2.setText("Titulo");

        jLabel3.setText("Autor");

        jLabel4.setText("Genero");

        jLabel5.setText("Precio");

        jLabel6.setText("Cant. Stock");

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField2");

        jTextField3.setText("jTextField3");

        jTextField4.setText("jTextField4");

        jTextField5.setText("jTextField5");

        jButton5.setText("Cupon");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Ventana Proveedores");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        BncargarJson.setText("Cargar Json");
        BncargarJson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BncargarJsonActionPerformed(evt);
            }
        });

        BnGuardarJson.setText("Guardar Json");
        BnGuardarJson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BnGuardarJsonActionPerformed(evt);
            }
        });

        CargarCSV.setText("Cargar CSV");
        CargarCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargarCSVActionPerformed(evt);
            }
        });

        btnGuardarCSV.setText("Guardar CSV");
        btnGuardarCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCSVActionPerformed(evt);
            }
        });

        Bncargarxml.setText("Cargar xml");
        Bncargarxml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BncargarxmlActionPerformed(evt);
            }
        });

        Bnguardarxml.setText("Guardar xml");
        Bnguardarxml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BnguardarxmlActionPerformed(evt);
            }
        });

        jMenu1.setText("Usuario");

        jMenuItem1.setText("Consultar Usarios");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Mi Perfil");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(53, 53, 53))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(41, 41, 41))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addGap(44, 44, 44)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton3)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton5))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jButton6)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addGap(18, 18, 18)
                                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(Bncargarxml, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BncargarJson)
                    .addComponent(CargarCSV))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BnGuardarJson)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(Bnguardarxml, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardarCSV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(127, 127, 127))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton4)
                            .addComponent(jButton5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CargarCSV)
                            .addComponent(btnGuardarCSV))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Bncargarxml)
                            .addComponent(Bnguardarxml))))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BncargarJson)
                    .addComponent(BnGuardarJson))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        ConsultaUsuarios c = new ConsultaUsuarios();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

      this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        new LibroNuevo().setVisible(true);
    
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        int eliminar = jTable1.getSelectedRow();
        
        if(eliminar > 0){
            
            if(JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar")==0){
            
                ProyectoA.libros.remove(eliminar);
                llenarTablaLibros();
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Seleccione un Libro valido para eliminar");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        int filaSeleccionada = jTable1.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
        Libro libro = ProyectoA.libros.get(filaSeleccionada);
        
        jTextField1.setText(libro.titulo);
        jTextField2.setText(libro.autor);
        jTextField3.setText(libro.genero);
        jTextField4.setText(String.valueOf(libro.precio));
        jTextField5.setText(String.valueOf(libro.cantidadEnStock));
        
        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.addActionListener(e -> {
            
            libro.titulo = jTextField1.getText();
            libro.autor = jTextField2.getText();
            libro.genero = jTextField3.getText();
            libro.precio = Double.parseDouble(jTextField4.getText());
            libro.cantidadEnStock = Integer.parseInt(jTextField5.getText());
            
            llenarTablaLibros();
            JOptionPane.showMessageDialog(this, "Libro actualizado correctamente");
        });
        
         getContentPane().add(btnGuardar);
        btnGuardar.setBounds(50, 300, 150, 30); // Ajusta la posición según tu diseño
        repaint();
        } else {
        JOptionPane.showMessageDialog(this, "Seleccione un libro para modificar");

        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        new CrearCupones().setVisible(true);
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        miperfil c = new miperfil();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed


       Proveedores c = new Proveedores();
        c.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void BncargarJsonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BncargarJsonActionPerformed
       
       JFileChooser chooser = new JFileChooser();
    int resultado = chooser.showOpenDialog(this);
    if (resultado == JFileChooser.APPROVE_OPTION) {
    File archivo = chooser.getSelectedFile();
    cargarLibrosDesdeJSON(archivo);
    }
    
    /*   llamar fuera btnCargarJSON.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Cargar inventario como JSON");
        int seleccion = chooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            if (!archivo.getName().endsWith(".json")) {
                archivo = new File(archivo.getAbsolutePath() + ".json");
            }
            guardarLibrosComoJSON(archivo);
        }
    }
});*/
    }//GEN-LAST:event_BncargarJsonActionPerformed

    private void BnGuardarJsonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BnGuardarJsonActionPerformed

        JFileChooser chooser = new JFileChooser();
        int seleccion = chooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            if (!archivo.getName().endsWith(".json")) {
                archivo = new File(archivo.getAbsolutePath() + ".json");
            } guardarLibrosComoJSON(archivo);
        } 
        
   /*    llamar fuera btnGuardarJSON.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar inventario como JSON");
        int seleccion = chooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            if (!archivo.getName().endsWith(".json")) {
               
            }
            guardarLibrosComoJSON(archivo);
        }
    }
});*/
    }//GEN-LAST:event_BnGuardarJsonActionPerformed

    private void CargarCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargarCSVActionPerformed

       JFileChooser fileChooser = new JFileChooser();
int resultado = fileChooser.showOpenDialog(this);

if (resultado == JFileChooser.APPROVE_OPTION) {
    File archivoSeleccionado = fileChooser.getSelectedFile();
    cargarLibrosDesdeCSV(archivoSeleccionado);
    }

       /* JFileChooser chooser = new JFileChooser();
        int seleccion = chooser.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            cargarLibrosDesdeCSV(archivo);
        }
    }*/
       
     /* para llamar fuera btnCargarCSV.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecciona archivo CSV");
        int seleccion = chooser.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            cargarLibrosDesdeCSV(archivo);
        }
    }
});*/
    }//GEN-LAST:event_CargarCSVActionPerformed

    private void btnGuardarCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCSVActionPerformed
        
      
        JFileChooser chooser = new JFileChooser();
        int seleccion = chooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try {
                guardarLibrosEnCSV(archivo);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + ex.getMessage());
            }
        }
        
       /*llamar fuera btnGuardarCSV.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar libros en CSV");
        int seleccion = chooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try {
                guardarLibrosEnCSV(archivo);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + ex.getMessage());
            }
        }
    }
});*/
    }//GEN-LAST:event_btnGuardarCSVActionPerformed

    private void BncargarxmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BncargarxmlActionPerformed
        
        JFileChooser chooser = new JFileChooser();
        
        int seleccion = chooser.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try {
                cargarLibrosDesdeXML(archivo);
                JOptionPane.showMessageDialog(null, "Libros cargados correctamente desde XML.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al cargar XML: " + ex.getMessage());
            }
        }
        
      /* llamar fuera btnCargarXML.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Cargar libros desde XML");
        int seleccion = chooser.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try {
                cargarLibrosDesdeXML(archivo);
                JOptionPane.showMessageDialog(null, "Libros cargados correctamente desde XML.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al cargar XML: " + ex.getMessage());
            }
        }
    }
});*/

    }//GEN-LAST:event_BncargarxmlActionPerformed

    private void BnguardarxmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BnguardarxmlActionPerformed

       JFileChooser chooser = new JFileChooser();
        int seleccion = chooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try {
                guardarLibrosEnXML(archivo);
                JOptionPane.showMessageDialog(null, "Libros guardados exitosamente en XML.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar XML: " + ex.getMessage());
            }
        }
        
     /*  llamar fuera btnGuardarXML.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar libros en XML");
        int seleccion = chooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try {
                guardarLibrosEnXML(archivo);
                JOptionPane.showMessageDialog(null, "Libros guardados exitosamente en XML.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar XML: " + ex.getMessage());
            }
        }
    }
});*/
    }//GEN-LAST:event_BnguardarxmlActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BnGuardarJson;
    private javax.swing.JButton BncargarJson;
    private javax.swing.JButton Bncargarxml;
    private javax.swing.JButton Bnguardarxml;
    private javax.swing.JButton CargarCSV;
    private javax.swing.JButton btnGuardarCSV;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}

