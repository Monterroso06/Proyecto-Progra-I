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
public class Cupon implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public  String codigo;
    private  double descuento;
    public  String fechaVencimiento;
    double valorDescuento;
    public String tipoDescuento;
    
    /*public Cupon(String codigo, double valorDescuento, String fechaVencimiento, String tipoDescuento) {
    this.codigo = codigo;
    this.valorDescuento = valorDescuento;
    this.fechaVencimiento = fechaVencimiento;
    this.tipoDescuento = tipoDescuento;
}*/
    
    public Cupon(String codigo, double valorDescuento, String fechaVencimiento, String tipoDescuento) {
    this.codigo = codigo;
    this.valorDescuento = valorDescuento;
    this.fechaVencimiento = fechaVencimiento;
    this.tipoDescuento = tipoDescuento;
}


    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }

    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public String getTipoDescuento() { return tipoDescuento; }
    public void setTipoDescuento(String tipoDescuento) { this.tipoDescuento = tipoDescuento; }

    public double getValorDescuento() { return valorDescuento; }
    public void setValorDescuento(double valorDescuento) { this.valorDescuento = valorDescuento; }
}
