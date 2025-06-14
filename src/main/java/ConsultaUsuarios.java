/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyectoa;


import java.io.File;
import javax.swing.JFileChooser;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
//import javax.swing.text.Document;
//import javax.swing.text.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *
 * @author angel-monterroso
 */
public class ConsultaUsuarios extends javax.swing.JFrame {

    private Usuario usuario;
    /**
     * Creates new form ConsultaUsuarios
     */
    
    public ConsultaUsuarios() {
        initComponents();

        pintarTabla();

        jComboBox1.removeAllItems();
        jComboBox1.addItem("Administrador");
        jComboBox1.addItem("Vendedor");

    }
   
    private void pintarTabla(){
        String encabezado[] = {"Nombre","Usuario","Password","Rol"} ; 
        DefaultTableModel t = new DefaultTableModel(encabezado,ProyectoA.usuarios.size());
        jTable1.setModel(t);
        TableModel tabla = jTable1.getModel();
        
        for(int i = 0; i<ProyectoA.usuarios.size();i++){
            Usuario u = ProyectoA.usuarios.get(i);
            tabla.setValueAt(u.nombre, i, 0);
            tabla.setValueAt(u.usuario, i, 1);
            tabla.setValueAt(u.password, i, 2);
            tabla.setValueAt(u.rol, i, 3);
        }
    }
    
   

    
    private void cargarUsuariosDesdeXML(File archivoXML) {
        try {
   // File archivoEntrada = archivoXML;
   // File archivoXML = new File("usuarios.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(archivoXML);
        doc.getDocumentElement().normalize();

        NodeList lista = doc.getElementsByTagName("usuario");

        for (int i = 0; i < lista.getLength(); i++) {
            Node nodo = lista.item(i);

            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                org.w3c.dom.Element elemento = (org.w3c.dom.Element) nodo;
                Usuario u = new Usuario();  
                
                // Validar y asignar nombre
            NodeList nombreList = elemento.getElementsByTagName("nombre");
            if (nombreList.getLength() > 0) {
                u.nombre = nombreList.item(0).getTextContent().trim();
            }

            // Validar y asignar usuario
            NodeList usuarioListInterno = elemento.getElementsByTagName("usuario");
            if (usuarioListInterno.getLength() > 0) {
                u.usuario = usuarioListInterno.item(0).getTextContent().trim();
            }

            // Validar y asignar password
            NodeList passwordList = elemento.getElementsByTagName("password");
            if (passwordList.getLength() > 0) {
                u.password = passwordList.item(0).getTextContent().trim();
            }

            // Validar y asignar rol
            NodeList rolList = elemento.getElementsByTagName("rol");
            if (rolList.getLength() > 0) {
                u.rol = rolList.item(0).getTextContent().trim();

           /*     Usuario u = new Usuario();
                u.nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
                u.usuario = elemento.getElementsByTagName("usuario").item(0).getTextContent();
                u.password = elemento.getElementsByTagName("password").item(0).getTextContent();
                u.rol = elemento.getElementsByTagName("rol").item(0).getTextContent();*/
            if (u.nombre != null && !u.nombre.isEmpty() && u.usuario != null && !u.usuario.isEmpty()) {
                ProyectoA.usuarios.add(u);
            }
                    
               } 
             //   ProyectoA.usuarios.add(u);
            }
        }

        JOptionPane.showMessageDialog(this, "Usuarios cargados correctamente.");
        pintarTabla();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar el archivo XML: " + e.getMessage());
    } 

} 
   
    
    public void guardarUsuariosEnXML(File archivoXML) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        
        // Crear elemento raíz
        org.w3c.dom.Element root = doc.createElement("usuarios");
        doc.appendChild(root);
        
        // Recorrer la lista de usuarios y crear nodos
        for (Usuario u : ProyectoA.usuarios) {
            org.w3c.dom.Element usuarioElem = doc.createElement("usuario");

            org.w3c.dom.Element nombreElem = doc.createElement("nombre");
            nombreElem.setTextContent(u.nombre);
            usuarioElem.appendChild(nombreElem);

            org.w3c.dom.Element usuarioIdElem = doc.createElement("usuario");
            usuarioIdElem.setTextContent(u.usuario);
            usuarioElem.appendChild(usuarioIdElem);

            org.w3c.dom.Element passwordElem = doc.createElement("password");
            passwordElem.setTextContent(u.password);
            usuarioElem.appendChild(passwordElem);

            org.w3c.dom.Element rolElem = doc.createElement("rol");
            rolElem.setTextContent(u.rol);
            usuarioElem.appendChild(rolElem);

            root.appendChild(usuarioElem);
        }
         // Guardar archivo XML
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // formato bonito
        
         DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(archivoXML);
        transformer.transform(source, result);

        JOptionPane.showMessageDialog(this, "Usuarios guardados correctamente en XML.");

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al guardar usuarios en XML: " + e.getMessage());
        
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
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButtonCargarXML = new javax.swing.JButton();
        jButtonGuardarXML = new javax.swing.JButton();

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

        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Modificar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("Nombre");

        jLabel2.setText("Usuario");

        jLabel3.setText("Password");

        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton4.setText("Guardar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel4.setText("Rol");

        jTextField2.setText("jTextField2");

        jTextField3.setText("jTextField3");

        jButtonCargarXML.setText("Cargar");
        jButtonCargarXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargarXMLActionPerformed(evt);
            }
        });

        jButtonGuardarXML.setText("Guardar");
        jButtonGuardarXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarXMLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(73, 73, 73)
                        .addComponent(jButton1))
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addContainerGap(120, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonCargarXML)
                    .addComponent(jButtonGuardarXML))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jButtonCargarXML)
                        .addGap(39, 39, 39)
                        .addComponent(jButtonGuardarXML)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton1))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    
    int eliminar = jTable1.getSelectedRow();
        
        if(eliminar > 0){
            
            if(JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar")==0){
            
                ProyectoA.usuarios.remove(eliminar);
                pintarTabla();
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Seleccione un usuario valido para eliminar");
        }
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

    if(usuario != null){
        usuario.nombre = jTextField1.getText();
        //usuario.usuario = jTextField2.getText();
        usuario.password = jTextField3.getText();


        String rol = jComboBox1.getSelectedItem().toString();

        if(rol.equals("Administrador")){
                usuario.rol = "A";
        }
        else{
            usuario.rol = "V";
        }
        
        usuario.usuario = jTextField2.getText();
        pintarTabla();

        JOptionPane.showMessageDialog(this, "Usuario modificado exitosamente");
        }
    
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    
        if(jTable1.getSelectedRow()>=0){
            usuario = ProyectoA.usuarios.get(jTable1.getSelectedRow());
            jTextField1.setText(usuario.nombre);
            jTextField2.setText(usuario.usuario);
            jTextField3.setText(usuario.password);
            
            if(usuario.rol.equals("A")){
                jComboBox1.setSelectedIndex(0);
            }
            else{
                jComboBox1.setSelectedIndex(1);
            }
            
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButtonCargarXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargarXMLActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showOpenDialog(this);
    if (seleccion == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();
        cargarUsuariosDesdeXML(archivo);
    }
    
   // File archivo = new File("usuarios.xml");
    //cargarUsuariosDesdeXML(archivo);
    
 /*   JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Seleccionar archivo XML");
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos XML", "xml"));

    int seleccion = fileChooser.showOpenDialog(this);

    if (seleccion == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();
        cargarUsuariosDesdeXML(archivo); // Método ya existente que lee el archivo
    }*/

    }//GEN-LAST:event_jButtonCargarXMLActionPerformed

    private void jButtonGuardarXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarXMLActionPerformed

    //    File archivo = new File("usuarios.xml");
    //guardarUsuariosEnXML(archivo);
    
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Guardar archivo XML");
    fileChooser.setSelectedFile(new File("usuarios.xml")); // nombre sugerido
    
    int seleccion = fileChooser.showSaveDialog(this);
    
     if (seleccion == JFileChooser.APPROVE_OPTION) {
         File archivo = fileChooser.getSelectedFile();
         
          // Asegura que tenga la extensión .xml
        if (!archivo.getName().toLowerCase().endsWith(".xml")) {
            archivo = new File(archivo.getParentFile(), archivo.getName() + ".xml");
        }
        guardarUsuariosEnXML(archivo);
     }
    }//GEN-LAST:event_jButtonGuardarXMLActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonCargarXML;
    private javax.swing.JButton jButtonGuardarXML;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}

