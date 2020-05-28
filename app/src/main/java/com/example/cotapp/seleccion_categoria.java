package com.example.cotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.List;

public class seleccion_categoria extends AppCompatActivity {
    Spinner spinnerCat;
    Spinner spinnerCd;
    ListView lsvDetalle;
    Button btnBuscar;

    List<String> listaCategoria;
    List<String>listaCiudades;
    List<String>listaSolicitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_seleccion_categoria);
        spinnerCat=(Spinner)findViewById(R.id.sp_Categoria);
        spinnerCd=(Spinner)findViewById(R.id.sp_ciudad);
        lsvDetalle=(ListView)findViewById(R.id.lsv_Lista);
        btnBuscar=(Button) findViewById(R.id.btn_Buscar);

        cargarCategoria();
        cargarCiudad();
        super.onCreate(savedInstanceState);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarLista();
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
    public void cargarLista(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="Fill_List_Detail.php?f=datos&ci="+spinnerCat.getSelectedItem().toString()+"&ca="+spinnerCd.getSelectedItem().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, conexion.URL_WEB_SERVICES+url, new Response.Listener<String>() {
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
        });
        queue.add(stringRequest);

    }
    public void  obtenerLista(JSONArray jsonArray){
        listaSolicitud = new ArrayList<String>();
        for(int i=0; i<jsonArray.length(); i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String solicitud =jsonObject.getString("descripcion" );

                listaSolicitud.add(solicitud);
            }catch (JSONException jsnEx2){
                Toast.makeText(getApplicationContext(),jsnEx2.toString(),Toast.LENGTH_LONG).show();
            }
        }
        ArrayAdapter<String>adapterSolicitudes = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaSolicitud);
        lsvDetalle.setAdapter(adapterSolicitudes);
    }
}