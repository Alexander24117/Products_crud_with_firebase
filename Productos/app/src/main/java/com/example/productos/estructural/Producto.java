package com.example.productos.estructural;

import java.util.Date;

public class Producto {
    public static final String NAME_COLLECTION = "productos";
    public static final String STATUS_AC = "AC";
    public static final String STATUS_IN = "IN";
    private int codigo;
    private String nombre;
    private String categoria;
    private double precioCompra;
    private double iva;
    private double precioVenta;
    private Date fechaVencimiento;
    private int cantidad;
    private String status;


    public Producto() {
    }

    public Producto(int codigo, String nombre, String categoria, double precioCompra, double iva, double precioVenta, Date fechaVencimiento, int cantidad, String status) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.iva = iva;
        this.precioVenta = precioVenta;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
        this.status = status;

    }

    public Producto(int codigo, String nombre, String categoria, double precioCompra, double iva, double precioVenta, Date fechaVencimiento, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.iva = iva;
        this.precioVenta = precioVenta;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

}
