package com.uisrael.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuView extends AppCompatActivity {

    Button tiendas, categorias, productos, promos, miInfo;
    ImageButton salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tiendas = findViewById(R.id.btnTienda);
        categorias = findViewById(R.id.btnCategoria);
        productos = findViewById(R.id.btnProducto);
        promos = findViewById(R.id.btnPromo);
        miInfo = findViewById(R.id.btnMiInfoTienda);
        salir = findViewById(R.id.btnSalir);

        tiendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiendasVista();

            }
        });
        categorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoriasVista();

            }
        });
        productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProductosVista();

            }
        });
        promos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promosVista();

            }
        });
        miInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miInfo();

            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();

            }
        });

    }

    public void tiendasVista(){
        Intent abrirT = new Intent(MenuView.this, TiendasView.class);
        startActivity(abrirT);
    }
    public void categoriasVista(){
        Intent abrirC = new Intent(MenuView.this, CategoriaView.class);
        startActivity(abrirC);
    }
    public void setProductosVista(){
        Intent abrirP = new Intent(MenuView.this, ProductoView.class);
        startActivity(abrirP);
    }
    public void promosVista(){
        //Intent abrirPr = new Intent(MenuView.this, PromoView.class);
        //startActivity(abrirPr);
    }
    public void miInfo(){
        Intent abrirPr = new Intent(MenuView.this, InfoTiendaView.class);
        startActivity(abrirPr);
    }
    public void salir(){
        Intent abrir = new Intent(MenuView.this, MainActivity.class);
        startActivity(abrir);
    }
}
