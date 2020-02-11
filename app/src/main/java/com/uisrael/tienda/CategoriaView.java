package com.uisrael.tienda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoriaView extends AppCompatActivity {
    private List<Categoria> listCategoria = new ArrayList<Categoria>();
    ArrayAdapter<Categoria> arrayAdapterCategoria;

    Categoria catSelect;

    TextView nombre, descripcion;
    ImageButton guardar, actualizar, eliminar, cancelar;

    ListView listaCategoria;

    FirebaseDatabase fbDatabase;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        nombre = findViewById(R.id.txtNombreCategoria);
        descripcion = findViewById(R.id.txtDescCategoria);

        listaCategoria = findViewById(R.id.lvCategoria);
        listaCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                catSelect = (Categoria) parent.getItemAtPosition(position);
                nombre.setText(catSelect.getCatNombre());
                descripcion.setText(catSelect.getCatDescripcion());
            }
        });

        guardar = findViewById(R.id.btnGuardarCategoria);
        actualizar = findViewById(R.id.btnActualizarCategoria);
        eliminar = findViewById(R.id.btnEliminarCategoria);
        cancelar = findViewById(R.id.btnCancelarCategoria);

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
    }

    private void iniciarDb() {
        FirebaseApp.initializeApp(this);
        fbDatabase = FirebaseDatabase.getInstance();
        dbReference = fbDatabase.getReference();
    }
    public void limpiar(){
        nombre.setText("");
        descripcion.setText("");
    }

    private void listar() {
        dbReference.child("Categoria").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCategoria.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Categoria c = objSnapshot.getValue(Categoria.class);
                    listCategoria.add(c);

                    arrayAdapterCategoria = new ArrayAdapter<Categoria>(CategoriaView.this, android.R.layout.simple_list_item_1, listCategoria);
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
        String descripcionV = descripcion.getText().toString();

        if (nombreV.equals("") || descripcionV.equals("")) {
            Toast.makeText(this, "Llene todos los campos.", Toast.LENGTH_LONG).show();
        } else {
            Categoria c = new Categoria();
            c.setCatId(UUID.randomUUID().toString());
            c.setCatNombre(nombreV);
            c.setCatDescripcion(descripcionV);

            dbReference.child("Categoria").child(c.getCatId()).setValue(c);

            limpiar();
            Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_LONG).show();
        }

    }

    public void actualizar(){
        if(!catSelect.getCatId().equals(null)) {
            Categoria c = new Categoria();
            c.setCatId(catSelect.getCatId());
            c.setCatNombre(nombre.getText().toString().trim());
            c.setCatDescripcion(descripcion.getText().toString().trim());

            dbReference.child("Categoria").child(c.getCatId()).setValue(c);

            limpiar();
            Toast.makeText(this, "Actualizado.", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Seleccione una categoría.", Toast.LENGTH_LONG).show();
        }
    }

    public void eliminar(){
        if(!catSelect.getCatId().equals(null)) {
            Categoria c = new Categoria();
            c.setCatId(catSelect.getCatId());

            dbReference.child("Categoria").child(c.getCatId()).removeValue();

            limpiar();
            Toast.makeText(this, "Eliminado.", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Seleccione una categoría.", Toast.LENGTH_LONG).show();
        }
    }

    public void cancelar() {
        limpiar();
        Intent abrir = new Intent(CategoriaView.this, MenuView.class);
        startActivity(abrir);
    }
}
