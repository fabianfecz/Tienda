package com.uisrael.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uisrael.tienda.model.Usuario;

import java.util.UUID;

public class RegistroView extends AppCompatActivity {

    TextView nombre, apellido, telefono, correo, nick, pass;
    Button guardar, cancelar;
    CheckBox tipo;

    FirebaseDatabase fbDatabase;
    DatabaseReference dbReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
        telefono = findViewById(R.id.txtTelefono);
        correo = findViewById(R.id.txtCorreo);
        nick = findViewById(R.id.txtNick);
        pass = findViewById(R.id.txtContraseña);
        tipo = findViewById(R.id.chbTienda);

        guardar = findViewById(R.id.btnGuardarRegistro);
        cancelar = findViewById(R.id.btnCancelar);

        iniciarDb();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }
        });
    }

    private void iniciarDb(){
        FirebaseApp.initializeApp(this);
        fbDatabase = FirebaseDatabase.getInstance();
        dbReference = fbDatabase.getReference();
    }
    public void limpiar(){
        nombre.setText("");
        apellido.setText("");
        telefono.setText("");
        correo.setText("");
        nick.setText("");
        pass.setText("");
    }

    public void registrar(){
        String nombreV = nombre.getText().toString();
        String apellidoV = apellido.getText().toString();
        String telefonoV = telefono.getText().toString();
        String correoV = correo.getText().toString();
        String nickV = nick.getText().toString();
        String passV = pass.getText().toString();
        String tipoV = tipo.isChecked()? "tienda" : "cliente";

        if(nombreV.equals("") || apellidoV.equals("") || telefonoV.equals("") ||
                correoV.equals("") || nickV.equals("") || passV.equals("") ){
            Toast.makeText(this,"Llene todos los campos.",Toast.LENGTH_LONG).show();
        }else{
            Usuario u = new Usuario();
            u.setUsuarioId(UUID.randomUUID().toString());
            u.setNombre(nombreV);
            u.setApellido(apellidoV);
            u.setTelefono(telefonoV);
            u.setCorreo(correoV);
            u.setNick(nickV);
            u.setContraseña(passV);
            u.setTipo(tipoV);

            dbReference.child("Usuario").child(u.getUsuarioId()).setValue(u);

            limpiar();
            Toast.makeText(this,"Registro exitoso.",Toast.LENGTH_LONG).show();
            regresar();
        }

    }
    public void regresar(){
        limpiar();
        Intent abrir = new Intent(RegistroView.this, MainActivity.class);
        startActivity(abrir);
    }
}
