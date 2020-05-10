package com.example.cotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



public class Nueva_Solicitud extends AppCompatActivity {
    User usuario = new User();
    public static final String nombre="nombre";
 TextView txt_Fecha;
 DatePickerDialog.OnDateSetListener setListener;
 Button btn_crear;
 EditText txt_nombre;
    String usuarios;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nueva__solicitud);
        txt_Fecha = findViewById(R.id.txt_Fecha);
        btn_crear= findViewById(R.id.btn_crear);
        txt_nombre=findViewById(R.id.txt_Nombre_Solicitud);
        recuperarPreferencias();


                Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);



        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (txt_Fecha.length()==0||txt_nombre.length()==0){
                Toast.makeText(Nueva_Solicitud.this, "Informacion pendiente por llenar ", Toast.LENGTH_LONG).show();
            }
            else {

                ejecutarServicio("Insert_request.php");
            }
            }
        });
        txt_Fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Nueva_Solicitud.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener,year,month,day);
                        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = year +"-"+ month +"-"+ dayOfMonth ;
                txt_Fecha.setText(date);

            }
        };
    }

    private void  ejecutarServicio(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES +URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Esta Solicitud ya esta registrada", Toast.LENGTH_SHORT).show();
                }
                else{
                    txt_Fecha.setText("");
                    txt_nombre.setText("");

                    Intent intent = new Intent(getApplicationContext(), Home_Supplier.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                User usuario = new User();
                String nombre = usuario.getUsuario();

                Map<String,String>parametros=new HashMap<>();
                parametros.put("Nombre_Solicitud",txt_nombre.getText().toString());
                parametros.put("Fecha_Fin",txt_Fecha.getText().toString());
               parametros.put("usuario",usuarios);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void  recuperarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("preferenciasusuarios", Context.MODE_PRIVATE);
        usuarios = preferences.getString("usuario", "ID");
    }

}
