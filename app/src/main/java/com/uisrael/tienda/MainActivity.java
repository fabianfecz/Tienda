package com.uisrael.tienda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uisrael.tienda.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private List<Usuario> listUsuario = new ArrayList<Usuario>();
    ArrayAdapter<Usuario> arrayAdapterUsuario;

    Button ingresar, registrarse;
    EditText usuario, pass;

    FirebaseDatabase fbDatabase;
    DatabaseReference dbReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ingresar = findViewById(R.id.btnIngresar);
        registrarse = findViewById(R.id.btnRegistrar);

        usuario = findViewById(R.id.txtUsuario);
        pass = findViewById(R.id.txtPass);

        iniciarDb();
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();

            }
        });
    }
    private void iniciarDb() {
        FirebaseApp.initializeApp(this);
        fbDatabase = FirebaseDatabase.getInstance();
        dbReference = fbDatabase.getReference();
    }
    public void limpiar(){
        usuario.setText("");
        pass.setText("");
    }
    String usu, pas;

    public void validar() {
        dbReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUsuario.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Usuario c = objSnapshot.getValue(Usuario.class);
                    usu = c.getNick();
                    pas = c.getContraseña();

                    /*listUsuario.add(c);

                    arrayAdapterUsuario = new ArrayAdapter<Usuario>(MainActivity.this, android.R.layout.simple_list_item_1, listUsuario);
                    listaUsuario.setAdapter(arrayAdapterCategoria);*/
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(!usu.equals(null) && !pas.equals(null)){
            Toast.makeText(this, usu+pas, Toast.LENGTH_LONG).show();
            /*Intent abrir = new Intent(MainActivity.this, MenuView.class);
            startActivity(abrir);*/
        }else{
            Toast.makeText(this, "Usuario o contraseña erróneo.", Toast.LENGTH_LONG).show();
        }
    }

}
