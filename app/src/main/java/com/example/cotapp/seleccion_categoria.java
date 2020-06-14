package com.example.cotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class seleccion_categoria extends AppCompatActivity {
    Spinner spinnerCat;
    Spinner spinnerCd;
    ListView lsvDetalle;
    Button btnBuscar;


    List<String> listaCategoria;
    List<String>listaCiudades;
    List<String>listaSolicitud;
    List<String>listaDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_seleccion_categoria);
        spinnerCat=(Spinner)findViewById(R.id.sp_Categoria);
        spinnerCd=(Spinner)findViewById(R.id.sp_ciudad);
        lsvDetalle=(ListView)findViewById(R.id.lsv_Lista_Producto);
        btnBuscar=(Button) findViewById(R.id.btn_Buscar);


        cargarCategoria();
        cargarCiudad();
        super.onCreate(savedInstanceState);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarLista("Find_Cab_Solicitud.php");
            }
        });



    }
    //-------------------------CARGA DE SPINNER DE CATEGORIA-------------------------------------------------------------//
// ---------------------------------------------------------------------------------------------------------------------//
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
//-------------------------CARGA DE SPINNER DE CIUDAD--------------------------------------------------------------------//
// ---------------------------------------------------------------------------------------------------------------------//
    public void cargarCiudad(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="Fill_Spinner_Ciudad.php?funcion=cd";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, conexion.URL_WEB_SERVICES+url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        obtenerCiudad(jsonArray);

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
    public void  obtenerCiudad(JSONArray jsonArray){
        listaCiudades = new ArrayList<String>();
        for(int i=0; i<jsonArray.length(); i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String Ciudad =jsonObject.getString("Nombre");

                listaCiudades.add(Ciudad);
            }catch (JSONException jsnEx2){
                Toast.makeText(getApplicationContext(),jsnEx2.toString(),Toast.LENGTH_LONG).show();

            }

        }
        ArrayAdapter<String>adapterRoles = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaCiudades);
        spinnerCd.setAdapter(adapterRoles);

    }

    //-------------------------CARGA  lISTA DE SOLICITUD -------------------------------------------------------------------//
// ---------------------------------------------------------------------------------------------------------------------//
    public void cargarLista(String URL){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES+URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        obtenerLista(jsonArray);

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
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>parametros=new HashMap<>();
                parametros.put("Categoria",spinnerCat.getSelectedItem().toString());
                parametros.put("Ciudad",spinnerCd.getSelectedItem().toString());
                return parametros;
            }
        }  ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    public void  obtenerLista(JSONArray jsonArray){
        listaSolicitud = new ArrayList<String>();
        listaDescripcion = new ArrayList<String>();

        for(int i=0; i<jsonArray.length(); i++){
        try{
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        String descripcion =jsonObject.getString("descripcion" );
        String solicitud  =jsonObject.getString("solicitud" );

        listaDescripcion.add(descripcion);
        listaSolicitud.add(solicitud);

        }catch (JSONException jsnEx2){
        Toast.makeText(getApplicationContext(),jsnEx2.toString(),Toast.LENGTH_LONG).show();
        }
        }
        ArrayAdapter<String>adapterDescripcion = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaDescripcion);
        lsvDetalle.setAdapter(adapterDescripcion);



        lsvDetalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
@Override
public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
    SharedPreferences preferences=getSharedPreferences("preferenciasnumero", Context.MODE_PRIVATE );
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString("numero",listaSolicitud.get(position) );
    editor.commit();
    Intent intent = new Intent(getApplicationContext(), detalle_producto.class);
    startActivity(intent);
    finish();
       // tv1.setText("La "+lsvDetalle.getItemAtPosition(position)+"hola"+ listaSolicitud.get(position) +"mundo");
        }
        });
        }
        }