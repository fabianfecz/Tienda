package com.uisrael.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button ingresar, registrarse;
    EditText usuario, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ingresar = findViewById(R.id.btnIngresar);
        registrarse = findViewById(R.id.btnRegistrar);

        usuario = findViewById(R.id.txtUsuario);
        pass = findViewById(R.id.txtPass);
    }

    public void base(View v){
        try {
            String dbhost = "jdbc:mysql://192.168.56.101:3306/APP_EC?zeroDateTimeBehavior=convertToNull";
            Class.forName("com.mysql.jdbc.Driver");
            // "jdbc:mysql://IP:PUERTO/DB", "USER", "PASSWORD");
            // Si estás utilizando el emulador de android y tenes el mysql en tu misma PC no utilizar 127.0.0.1 o localhost como IP, utilizar 10.0.2.2
            Connection conn = DriverManager.getConnection(dbhost, "fecz", "Fecz@9375");
            //En el stsql se puede agregar cualquier consulta SQL deseada.
            String stsql = "Select * FROM estudiante;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(stsql);
            rs.next();
            usuario.setText(rs.toString());

            //Toast.makeText(getApplicationContext(),rs.getString(1), Toast.LENGTH_LONG).show();
            //System.out.println( rs.getString(1) );
            conn.close();
        } catch (SQLException se) {
            usuario.setText(se.toString());
            Toast.makeText(getApplicationContext(),"Error1: "+se.toString(), Toast.LENGTH_LONG).show();
            //System.out.println("oops! No se puede conectar. Error: " + se.toString());
        } catch (ClassNotFoundException e) {
            usuario.setText(e.toString());
            Toast.makeText(getApplicationContext(),"Error2: "+e.toString(), Toast.LENGTH_LONG).show();
            //System.out.println("oops! No se encuentra la clase. Error: " + e.getMessage());
        }
    }
}
