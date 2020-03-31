package com.example.cotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);
    }


    //Metodo para boton Datos de Usuarios
    public void datosUsuario (View view ){
        Intent datos = new Intent(this, Datos_Usuario.class);
        startActivity(datos);
    }
}
