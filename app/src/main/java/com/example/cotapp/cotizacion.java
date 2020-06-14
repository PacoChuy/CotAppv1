package com.example.cotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
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

public class cotizacion extends AppCompatActivity {
    Spinner spinnerUnidad;
    Spinner spinnerMoneda;
    Spinner spinnerCredito;
    List<String> listaUnidad;
    List<String> listaMoneda;
    List<String> listaCredito;
    String producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotizacion);
        spinnerUnidad = (Spinner) findViewById(R.id.sp_Unidad);
        spinnerMoneda = (Spinner) findViewById(R.id.sp_Moneda);
        spinnerCredito = (Spinner) findViewById(R.id.sp_Credito);

        cargarUnidad();
        cargarMoneda();
        cargarCredito();
    }


    //-------------------------CARGA DE SPINNER DE CREDITO--------------------------------------------------------------------//
// ---------------------------------------------------------------------------------------------------------------------//
    public void cargarCredito() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "Fill_Spinner_Credito.php?funcion=C";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, conexion.URL_WEB_SERVICES + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 0) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        obtenerCredito(jsonArray);

                    } catch (JSONException jsnex1) {

                        Toast.makeText(getApplicationContext(), jsnex1.toString(), Toast.LENGTH_LONG).show();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        queue.add(stringRequest);

    }

    public void obtenerCredito(JSONArray jsonArray) {

        listaCredito = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String Cre = jsonObject.getString("Nombre");

                listaCredito.add(Cre);
            } catch (JSONException jsnEx2) {
                Toast.makeText(getApplicationContext(), jsnEx2.toString(), Toast.LENGTH_LONG).show();

            }

        }
        ArrayAdapter<String> adapterRoles = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listaCredito);
        spinnerCredito.setAdapter(adapterRoles);

    }

    //-------------------------CARGA DE SPINNER DE UNIDAD-------------------------------------------------------------------//
// ---------------------------------------------------------------------------------------------------------------------//
    public void cargarUnidad() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "Fill_Spinner_Unidad.php?funcion=U";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, conexion.URL_WEB_SERVICES + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 0) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        obtenerUnidad(jsonArray);

                    } catch (JSONException jsnex1) {

                        Toast.makeText(getApplicationContext(), jsnex1.toString(), Toast.LENGTH_LONG).show();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        queue.add(stringRequest);

    }

    // Carga de unidades en el Spinner
    public void obtenerUnidad(JSONArray jsonArray) {
        listaUnidad = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String unidad = jsonObject.getString("Nombre");
                listaUnidad.add(unidad);
            } catch (JSONException jsnEx2) {
                Toast.makeText(getApplicationContext(), jsnEx2.toString(), Toast.LENGTH_LONG).show();

            }

        }
        ArrayAdapter<String> adapterRoles = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listaUnidad);
        spinnerUnidad.setAdapter(adapterRoles);

    }
    //-------------------------CARGA DE SPINNER DE Moneda------------------------------------------------------------------//
// ---------------------------------------------------------------------------------------------------------------------//
    public void cargarMoneda() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "Fill_Spinner_Moneda.php?funcion=U";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, conexion.URL_WEB_SERVICES + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 0) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        obtenerMoneda(jsonArray);

                    } catch (JSONException jsnex1) {

                        Toast.makeText(getApplicationContext(), jsnex1.toString(), Toast.LENGTH_LONG).show();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        queue.add(stringRequest);

    }

    // Carga de unidades en el Spinner
    public void obtenerMoneda(JSONArray jsonArray) {
        listaMoneda= new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String moneda= jsonObject.getString("Nombre");
                listaMoneda.add(moneda);
            } catch (JSONException jsnEx2) {
                Toast.makeText(getApplicationContext(), jsnEx2.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ArrayAdapter<String> adapterMoneda = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listaMoneda);
        spinnerMoneda.setAdapter(adapterMoneda);
    }

//-------------------------RECUPERAR LAS PREFERENCIAS -------------------------------------------------------------------//
// ---------------------------------------------------------------------------------------------------------------------//
    private void  recuperarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("preferenciasproducto", Context.MODE_PRIVATE);
        producto = preferences.getString("numero","0");
    }


}