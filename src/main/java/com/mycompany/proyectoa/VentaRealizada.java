/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angel-monterroso
 */
public class VentaRealizada implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String nombre;
    public String nit;
    public String direccion;
    public double subtotal;
    public double totalSinIva;
    public String vendedor;
    public String fecha;
    public List<LibroVendido> librosVendidos; // <--- Nueva lista de libros vendidos

    
    // Constructor con parametros 
    public VentaRealizada(String nombre, String nit, String direccion, double subtotal, double totalSinIva, String vendedor, String fecha, List<LibroVendido> librosVendidos) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.subtotal = subtotal;
        this.totalSinIva = totalSinIva;
        this.vendedor = vendedor;
        this.fecha = fecha;
        this.librosVendidos = librosVendidos;
    }
    
    // Constructor vacío (para cargar desde CSV)
    public VentaRealizada() {
        this.librosVendidos = new ArrayList<>();
    }
    
}


