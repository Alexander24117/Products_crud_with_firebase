package com.example.productos.estructural;

public class Categoria {
    private int codigo;
    private String categoria;

    public Categoria() {
    }

    public Categoria(int codigo, String categoria) {
        this.codigo = codigo;
        this.categoria = categoria;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getCategoria() {
        return categoria;
    }

}
