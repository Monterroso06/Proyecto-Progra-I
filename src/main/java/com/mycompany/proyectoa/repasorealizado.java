/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoa;

import java.util.List;

/**
 *
 * @author angel-monterroso
 */
public class repasorealizado {

    static void remove(int filaSeleccionada) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public String color;
    public String nombre;
    public String edad;
    public String ciudad;
    public List<repasorealizado> repasosrealizados; // <--- Nueva lista de libros vendidos

    
    // Constructor
    public repasorealizado(String color, String nombre, String edad, String ciudad, List<repasorealizado> repasosrealizados) {
        this.color = color;
        this.nombre = nombre;
        this.edad = edad;
        this.ciudad = ciudad;
        this.repasosrealizados=repasosrealizados;
        
    }
    
}
    

