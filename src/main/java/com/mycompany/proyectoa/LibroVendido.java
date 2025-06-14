    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoa;

import java.io.Serializable;

/**
 *
 * @author angel-monterroso
 */
public class LibroVendido implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String titulo;
    public int cantidad;
    public double precioUnitario;
    public double subtotal;
    
    // contructor con los parametros 
    public LibroVendido(String titulo, int cantidad, double precioUnitario, double subtotal) {
        this.titulo = titulo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }
    
        // Constructor vacío (para cargar desde CSV)
    public LibroVendido() {
    }
}
