package com.example.cotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
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
    List<String> listaCategoria;
    List<String>listaCiudades;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_seleccion_categoria);
        spinnerCat=(Spinner)findViewById(R.id.sp_Categoria);
        spinnerCd=(Spinner)findViewById(R.id.sp_ciudad);

        cargarCategoria();
        cargarCiudad();
        super.onCreate(savedInstanceState);
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
}