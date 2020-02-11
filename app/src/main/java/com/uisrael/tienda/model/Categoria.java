package com.uisrael.tienda.model;

public class Categoria {

    private String CatId;
    private String CatNombre;
    private String CatDescripcion;

    public Categoria() {
    }

    public String getCatId() {
        return CatId;
    }

    public void setCatId(String catId) {
        CatId = catId;
    }

    public String getCatNombre() {
        return CatNombre;
    }

    public void setCatNombre(String catNombre) {
        CatNombre = catNombre;
    }

    public String getCatDescripcion() {
        return CatDescripcion;
    }

    public void setCatDescripcion(String catDescripcion) {
        CatDescripcion = catDescripcion;
    }

    @Override
    public String toString() {
        return CatNombre;
    }
}
