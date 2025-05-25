/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoa;

/**
 *
 * @author angel-monterroso
 */
public class Libro {
    public String titulo;
    public String autor;
    public String genero;
    public double precio;
    public int cantidadEnStock;
 /*   public String Nit;
    public String Direccion;
    public String Telefono;
    public String Nombre;*/
    
   // public Libro(String titulo, String autor, String genero, double precio, int cantidadEnStock, String nit, String direccion, String telefono, String nombre ) {
    public Libro(String titulo, String autor, String genero, double precio, int cantidadEnStock ) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.precio = precio;
        this.cantidadEnStock = cantidadEnStock;
       /* this.Nit = nit;
        this.Direccion = direccion;
        this.Telefono = telefono;
        this.Nombre = nombre;*/
        }


   public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidadEnStock() {
        return cantidadEnStock;
    }

    // Setters si los necesitas
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCantidadEnStock(int cantidadEnStock) {
        this.cantidadEnStock = cantidadEnStock;
    }
}
