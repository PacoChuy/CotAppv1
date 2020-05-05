package com.example.cotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Home_Supplier extends AppCompatActivity {
    public static final String nombre="nombre";

    TextView cajaBienvenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__supplier);
        cajaBienvenido=(TextView)findViewById(R.id.txt_bienvenido);
        String nombre =getIntent().getStringExtra("nombre");
        cajaBienvenido.setText("Hola "+ nombre +" !");
    }

    //Metodo para boton Datos de Usuarios
    public void datosUsuario (View view ){
        Intent datos = new Intent(this, Datos_Usuario.class);
        startActivity(datos);
    }
}
