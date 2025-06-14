/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectoa;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angel-monterroso
 */
public class ProyectoA {
    
    
   // public static ArrayList<Proveedor> Proveedores = new ArrayList<>();
    public static ArrayList<repasorealizado> repasosrealizados = new ArrayList<>();
    public static ArrayList<repasohecho> repasoshechos = new ArrayList<>();
    public static ArrayList<VentaRealizada> ventasRealizadas = new ArrayList<>();
    public static ArrayList<Libro> libros = new ArrayList<>();
    public static ArrayList<Cupon> cupones = new ArrayList<>();
    public static ArrayList<Usuario> usuario = new ArrayList<>();
    public static ArrayList<LibroVendido> librosVendidosTemporales = new ArrayList<>();
    //static Iterable<Cupon> cupones;
    public static String nombreVendedorActual = "";

    public static void inicializarLibros() {
        libros.add(new Libro("Cien años de soledad", "Gabriel Garcia Marquez", "Realismo magico", 15.99, 10));
        libros.add(new Libro("1984", "George Orwell", "Ciencia ficción", 12.50, 5));
        libros.add(new Libro("El principito", "Antoine de Saint-Exupery", "Infantil", 8.99, 20));
        libros.add(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "Clasico", 18.75, 7));
    }
   /*   public static void inicializarLibros() {
        libros.add(new Libro("Cien años de soledad", "Gabriel Garcia Marquez", "Realismo magico", 15.99, 10, "15476g", "7ma Ave 2-29", "(+502) 4578523", "Juan"));
        libros.add(new Libro("1984", "George Orwell", "Ciencia ficción", 12.50, 5, "4151655y", "6ta calle 9-89", "(+502) 5585655554", "Maria"));
        libros.add(new Libro("El principito", "Antoine de Saint-Exupery", "Infantil", 8.99, 20, "578542g", "8va ave 8-99", "(+502) 46864554", "Julian"));
        libros.add(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "Clasico", 18.75, 7, "9246565t", "6ta ave 5-99", "(+502) 5458412","Mario"));
    }*/
    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    
    
 //   public static void TablaProveedores() {
     //   libros.add(new Libro("15476g", "7ma Ave 2-29", "(+502) 4578523", 15.99, 10));
  //      libros.add(new Libro("4151655y", "6ta calle 9-89", "(+502) 5585655554", 12.50, 5));
    //    libros.add(new Libro("578542g", "8va ave 8-99", "(+502) 46864554", 8.99, 20));
   //     libros.add(new Libro("9246565t", "6ta ave 5-99", "(+502) 5458412", 18.75, 7));
  // }
    
    public static void main(String[] args) {
       inicializarLibros();
       new ventanaAdministrador().setVisible(true);
       Usuario admin = new Usuario();
       admin.nombre = "admin";
       admin.usuario = "admin";
       admin.password = "admin";
       admin.rol = "A";
       
       new ventanaAdministrador().setVisible(true);
       Usuario vendedor = new Usuario();
       vendedor.nombre = "vendedor";
       vendedor.usuario = "vendedor";
       vendedor.password = "12345Aa";
       vendedor.rol = "V";
       usuarios.add(vendedor);
       System.out.println("Usuarios en el sistema: " + usuarios.size());
        login l = new login();
        l.setVisible(true);
     
    }
    
   // public static ArrayList<Cupon> cupones = new ArrayList<>();
    
    
    public static void inicializarCupones() {
    inicializarLibros();
    new ventanaAdministrador().setVisible(true);

    cupones.add(new Cupon("123ABC", 20, "20/02/2025", "Descuento %"));

    for (Cupon c : ProyectoA.cupones) {
        System.out.println("Código: " + c.codigo +
            ", Tipo: " + c.tipoDescuento +
            ", Valor: " + c.valorDescuento +
            ", Vence: " + c.fechaVencimiento);
    }
}

    public static void inicializarProveedores() {
     //   Proveedores.add(new Proveedor("Cien años de soledad", "Gabriel Garcia Marquez", "Realismo magico", "q"));
        libros.add(new Libro("1984", "George Orwell", "Ciencia ficción", 12.50, 5));
        libros.add(new Libro("El principito", "Antoine de Saint-Exupery", "Infantil", 8.99, 20));
        libros.add(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "Clasico", 18.75, 7));
    }

  /*  public static void inicializarlosCupones() {
    // Cargar libros
    Object librosCargados = ArchivoBinario.cargar("libros.dat");
    if (librosCargados != null) {
        libros = (ArrayList<Libro>) (List<Libro>) librosCargados;
    }

    // Cargar cupones
    Object cuponesCargados = ArchivoBinario.cargar("cupones.dat");
    if (cuponesCargados != null) {
        cupones = (ArrayList<Cupon>) (List<Cupon>) cuponesCargados;
    }

    // Cargar ventas
    Object ventasCargadas = ArchivoBinario.cargar("ventas.dat");
    if (ventasCargadas != null) {
        ventasRealizadas = (ArrayList<VentaRealizada>) (List<VentaRealizada>) ventasCargadas;
    }

    new ventanaAdministrador().setVisible(true);
}

    // Guardar
public static void guardarDatos() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datos_proyecto.dat"))) {
        oos.writeObject(libros);
        oos.writeObject(cupones);
        oos.writeObject(ventasRealizadas);
        oos.writeObject(usuarios);
        oos.writeObject(new ArrayList<LibroVendido>()); // opcional
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public static void cargarDatos() {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("datos_proyecto.dat"))) {
        libros = (ArrayList<Libro>) (List<Libro>) ois.readObject();
        cupones = (ArrayList<Cupon>) (List<Cupon>) ois.readObject();
        ventasRealizadas = (ArrayList<VentaRealizada>) (List<VentaRealizada>) ois.readObject();
        usuarios = (ArrayList<Usuario>) (List<Usuario>) ois.readObject();
        ois.readObject(); // Leer librosVendidos si lo usas (opcional)
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}
*/

}    
     