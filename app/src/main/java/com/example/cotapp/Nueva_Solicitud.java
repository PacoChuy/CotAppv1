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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Nueva_Solicitud extends AppCompatActivity {
    User usuario = new User();

    public static final String nombre="nombre";
 TextView txt_Fecha;
 DatePickerDialog.OnDateSetListener setListener;
 Button btn_crear;
 EditText txt_nombre;
    EditText txt_coti;
    String usuarios;
    Spinner spinnerCat;
    Spinner spinnerCre;
    List<String> listaCategoria;// guarda el nombre de la categoria

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nueva__solicitud);
        txt_Fecha = findViewById(R.id.txt_Fecha);
        btn_crear= findViewById(R.id.btn_crear);
        txt_nombre=findViewById(R.id.txt_Nombre_Solicitud);
        spinnerCat=(Spinner)findViewById(R.id.sp_Categoria);
        spinnerCre=(Spinner)findViewById(R.id.sp_Credito);
        txt_coti=findViewById(R.id.txt_Numero_Coti);
        recuperarPreferencias();
        cargarCategoria();
        cargarCredito();



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

    public void cargarCategoria(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="Fill_Spinner_Categoria.php?funcion=C";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, conexion.URL_WEB_SERVICES+url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        obtenerCategoria(jsonArray);

                    }catch (JSONException jsnex1){

                        Toast.makeText(getApplicationContext(),jsnex1.toString(),Toast.LENGTH_LONG).show();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        });
        queue.add(stringRequest);

    }

    public void  obtenerCategoria(JSONArray jsonArray){

        listaCategoria = new ArrayList<String>();
        for(int i=0; i<jsonArray.length(); i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String Rol =jsonObject.getString("Nombre");

                listaCategoria.add(Rol);
            }catch (JSONException jsnEx2){
                Toast.makeText(getApplicationContext(),jsnEx2.toString(),Toast.LENGTH_LONG).show();

            }

        }
        ArrayAdapter<String> adapterRoles = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaCategoria);
        spinnerCat.setAdapter(adapterRoles);

    }


    public void cargarCredito(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="Fill_Spinner_Credito.php?funcion=C";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, conexion.URL_WEB_SERVICES+url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        obtenerCredito(jsonArray);

                    }catch (JSONException jsnex1){

                        Toast.makeText(getApplicationContext(),jsnex1.toString(),Toast.LENGTH_LONG).show();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        });
        queue.add(stringRequest);

    }

    public void  obtenerCredito(JSONArray jsonArray){

        listaCategoria = new ArrayList<String>();
        for(int i=0; i<jsonArray.length(); i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String Rol =jsonObject.getString("Nombre");

                listaCategoria.add(Rol);
            }catch (JSONException jsnEx2){
                Toast.makeText(getApplicationContext(),jsnEx2.toString(),Toast.LENGTH_LONG).show();

            }

        }
        ArrayAdapter<String> adapterRoles = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaCategoria);
        spinnerCre.setAdapter(adapterRoles);

    }

    private void  ejecutarServicio(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES +URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        obtenerdatos(jsonArray);
                    }catch (JSONException jsnex1){

                        Toast.makeText(getApplicationContext(),jsnex1.toString(),Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Esta Solicitud ya esta registrada", Toast.LENGTH_SHORT).show();
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

                Map<String,String>parametros=new HashMap<>();
                parametros.put("Nombre_Solicitud",txt_nombre.getText().toString());
                parametros.put("Fecha_Fin",txt_Fecha.getText().toString());
                parametros.put("usuario",usuarios);
                parametros.put("credito",spinnerCre.getSelectedItem().toString());
                parametros.put("categoria",spinnerCat.getSelectedItem().toString());
                parametros.put("num_cotizacion",txt_coti.getText().toString());

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



    public void  obtenerdatos(JSONArray jsonArray){
        Req solicitud = new Req();

        for(int i=0; i<jsonArray.length(); i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                solicitud.setSolicitud(jsonObject.getString("solicitud"));
            }catch (JSONException jsnEx2){
                Toast.makeText(getApplicationContext(),jsnEx2.toString(),Toast.LENGTH_LONG).show();
            }
        }
            SharedPreferences preferences=getSharedPreferences("preferenciasolicitud",Context.MODE_PRIVATE );
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("solicitud",solicitud.getSolicitud());
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), Nuevo_requerimiento.class);
            startActivity(intent);
            finish();

    }








}
