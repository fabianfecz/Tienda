package com.uisrael.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.uisrael.tienda.model.Categoria;
import com.uisrael.tienda.model.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class ProductoView extends AppCompatActivity {

    private List<Categoria> listCategoria = new ArrayList<Categoria>();
    ArrayAdapter<Categoria> arrayAdapterCategoria;

    private List<Producto> listProducto = new ArrayList<Producto>();
    ArrayAdapter<Producto> arrayAdapterProducto;

    Producto prodSelect;
    Categoria catSelect;

    TextView nombre, precio, catId;
    ImageButton guardar, actualizar, eliminar, cancelar;

    ListView listaCategoria,listaProducto;

    FirebaseDatabase fbDatabase;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_view);

        nombre = findViewById(R.id.txtNombreProducto);
        precio = findViewById(R.id.txtPrecioProducto);
        catId = findViewById(R.id.txtCategoriaIdP);

        listaProducto = findViewById(R.id.lvListaProductos);
        listaCategoria = findViewById(R.id.lvListaCategoriaP);

        listaProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                prodSelect = (Producto) parent.getItemAtPosition(position);
                nombre.setText(prodSelect.getProdNombre());
                precio.setText(prodSelect.getProdPrecio());
            }
        });
        listaCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                catSelect = (Categoria) parent.getItemAtPosition(position);
                catId.setText(catSelect.getCatId());
            }
        });

        guardar = findViewById(R.id.btnGuadarProducto);
        actualizar = findViewById(R.id.btnActualizarProducto);
        eliminar = findViewById(R.id.btnEliminarProducto);
        cancelar = findViewById(R.id.btnCancelarProducto);

        iniciarDb();
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();

            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();

            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();

            }
        });
        listar();
        listarCategoria();
    }

    private void iniciarDb() {
        FirebaseApp.initializeApp(this);
        fbDatabase = FirebaseDatabase.getInstance();
        dbReference = fbDatabase.getReference();
    }
    public void limpiar(){
        nombre.setText("");
        precio.setText("");
    }

    private void listar() {
        dbReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listProducto.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Producto p = objSnapshot.getValue(Producto.class);
                    listProducto.add(p);

                    arrayAdapterProducto = new ArrayAdapter<Producto>(ProductoView.this, android.R.layout.simple_list_item_1, listProducto);
                    listaProducto.setAdapter(arrayAdapterProducto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void listarCategoria() {
        dbReference.child("Categoria").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCategoria.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Categoria c = objSnapshot.getValue(Categoria.class);
                    listCategoria.add(c);

                    arrayAdapterCategoria = new ArrayAdapter<Categoria>(ProductoView.this, android.R.layout.simple_list_item_1, listCategoria);
                    listaCategoria.setAdapter(arrayAdapterCategoria);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void registrar() {
        String nombreV = nombre.getText().toString();
        String precioV = precio.getText().toString();
        String catIdV = catId.getText().toString();

        if (nombreV.equals("") || precioV.equals("")) {
            Toast.makeText(this, "Ingrese todos los campos.", Toast.LENGTH_LONG).show();
        } else {
            Producto p = new Producto();
            p.setProdId(UUID.randomUUID().toString());
            p.setProdNombre(nombreV);
            p.setProdPrecio(precioV);
            p.setCatId(catIdV);

            dbReference.child("Producto").child(p.getProdId()).setValue(p);

            limpiar();
            Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_LONG).show();
        }

    }

    public void actualizar(){
        if(!prodSelect.getProdId().equals(null)) {
            Producto p = new Producto();
            p.setProdId(prodSelect.getProdId());
            p.setProdNombre(nombre.getText().toString().trim());
            p.setProdPrecio(precio.getText().toString().trim());
            p.setCatId(catId.getText().toString().trim());

            dbReference.child("Producto").child(p.getProdId()).setValue(p);

            limpiar();
            Toast.makeText(this, "Actualizado.", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Seleccione un producto.", Toast.LENGTH_LONG).show();
        }
    }

    public void eliminar(){
        if(prodSelect.getProdId().equals(null)) {
            Toast.makeText(this, "Seleccione un producto.", Toast.LENGTH_LONG).show();
        }else {
            Producto p = new Producto();
            p.setProdId(prodSelect.getProdId());

            dbReference.child("Producto").child(p.getProdId()).removeValue();

            limpiar();
            Toast.makeText(this, "Eliminado.", Toast.LENGTH_LONG).show();
        }
    }

    public void cancelar() {
        Intent abrirM = new Intent(ProductoView.this, MenuView.class);
        startActivity(abrirM);
    }
}
