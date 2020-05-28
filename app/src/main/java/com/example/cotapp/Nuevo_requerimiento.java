package com.example.cotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Nuevo_requerimiento extends AppCompatActivity {

    Spinner spinnerUn;
    List<String> listaUnidad;
    EditText nombre, descripcion,serie,modelo,marca,cantidad;
    Spinner spinnerUnidad;
    Button btnAgregar;
    Button btnFinalizar;
    String solicitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_requerimiento);
        spinnerUn=(Spinner)findViewById(R.id.sp_uni);
        btnAgregar=(Button) findViewById(R.id.btn_Buscar);
        nombre=(EditText)findViewById(R.id.txt_Nombre_Producto);
        descripcion=(EditText)findViewById(R.id.txt_Descripcion_Producto);
        serie=(EditText)findViewById(R.id.txt_Numero_Serie);
        modelo=(EditText)findViewById(R.id.txt_Modelo_Producto);
        marca=(EditText)findViewById(R.id.txt_Marca_Producto);
        cantidad=(EditText)findViewById(R.id.txt_Cantidad_Producto);
        spinnerUnidad=(Spinner)findViewById(R.id.sp_uni);
        btnFinalizar=(Button)findViewById(R.id.btn_Finalizar);

        cargarUnidad();
        recuperarPreferencias();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nombre.length()==0 ||cantidad.length()==0)
                {
                    Toast.makeText(Nuevo_requerimiento.this, "Informacion pendiente por llenar ", Toast.LENGTH_LONG).show();
                }
                else {
                    ejecutarServicio("Insert_request_detail.php");
                }

            }
        });


        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ejecutarcierre("close_request.php");

            }
        });

    }
    public void cargarUnidad(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="Fill_Spinner_Unidad.php?funcion=U";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, conexion.URL_WEB_SERVICES+url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        obtenerUnidad(jsonArray);

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

    // Carga de unidades en el Spinner
    public void  obtenerUnidad(JSONArray jsonArray){

        listaUnidad = new ArrayList<String>();
        for(int i=0; i<jsonArray.length(); i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String Rol =jsonObject.getString("Nombre");

                listaUnidad.add(Rol);
            }catch (JSONException jsnEx2){
                Toast.makeText(getApplicationContext(),jsnEx2.toString(),Toast.LENGTH_LONG).show();

            }

        }
        ArrayAdapter<String> adapterRoles = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaUnidad);
        spinnerUn.setAdapter(adapterRoles);

    }

    //insertamos los detalles
    private void  ejecutarServicio(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES +URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No se agrego a la solicitud", Toast.LENGTH_SHORT).show();
                }
                else{
                    nombre.setText("");
                    descripcion.setText("");
                    serie.setText("");
                    modelo.setText("");
                    marca.setText("");
                    cantidad.setText("");

                    Toast.makeText(getApplicationContext(), "Agregado a la Solicitud", Toast.LENGTH_SHORT).show();
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
                parametros.put("Nombre_producto",nombre.getText().toString());
                parametros.put("Descripcion_Producto",descripcion.getText().toString());
                parametros.put("Serie_Producto", serie.getText().toString());
                parametros.put("Modelo_Producto",modelo.getText().toString());
                parametros.put("Marca_Producto",marca.getText().toString());
                parametros.put("Cantidad_Producto",cantidad.getText().toString());
                parametros.put("Unidad_Producto",spinnerUnidad.getSelectedItem().toString());
                parametros.put("solicitud",solicitud);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
//cierre de solucitud
    private void  ejecutarcierre(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES +URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No se finalizop la solicitud", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), Home_activity.class);
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
                Map<String,String>parametros=new HashMap<>();
                parametros.put("solicitud",solicitud);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void  recuperarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("preferenciasolicitud", Context.MODE_PRIVATE);
        solicitud = preferences.getString("solicitud", "ID");
    }


}