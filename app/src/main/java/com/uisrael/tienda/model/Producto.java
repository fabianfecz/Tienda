package com.uisrael.tienda.model;

public class Producto {

    private String ProdId;
    private String CatId;
    private String ProdNombre;
    private String ProdPrecio;

    public Producto() {
    }

    public String getProdId() {
        return ProdId;
    }

    public void setProdId(String prodId) {
        ProdId = prodId;
    }

    public String getCatId() {
        return CatId;
    }

    public void setCatId(String catId) {
        CatId = catId;
    }

    public String getProdNombre() {
        return ProdNombre;
    }

    public void setProdNombre(String prodNombre) {
        ProdNombre = prodNombre;
    }

    public String getProdPrecio() {
        return ProdPrecio;
    }

    public void setProdPrecio(String prodPrecio) {
        ProdPrecio = prodPrecio;
    }

    @Override
    public String toString() {
        return ProdNombre;
    }
}
