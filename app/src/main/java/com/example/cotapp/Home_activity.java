package com.example.cotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home_activity extends AppCompatActivity {

    TextView cajaBienvenido;
    Button btnSolicitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);
        cajaBienvenido=(TextView)findViewById(R.id.txt_Producto);
        recuperarPreferencias();
        btnSolicitud = (Button)findViewById(R.id.btn_Buscar_Solicitud);

        btnSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Nueva_Solicitud.class);
                startActivity(intent);
                finish();
            }
        });
    }


    //Metodo para boton Datos de Usuarios
    public void datosUsuario (View view ){
        Intent datos = new Intent(this, Datos_Usuario.class);
        startActivity(datos);
    }

    private void  recuperarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("preferenciasusuarios", Context.MODE_PRIVATE);
        cajaBienvenido.setText(preferences.getString("nombre","nombre"));
    }

}
