package com.example.cotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class detalle_producto extends AppCompatActivity {
    String solicitud;
    List<String> listaProducto;
    List<String>listaDescripcion;
    ListView lsvProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        lsvProducto=(ListView)findViewById(R.id.lsv_Lista_Producto);
        recuperarPreferencias();
        cargarLista("Fill_List_Product.php");
    }


    //-------------------------CARGAR  LISTA DE PRODUCTOS -------------------------------------------------------------------//
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
                parametros.put("solicitud",solicitud);
                return parametros;
            }
        }  ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void  obtenerLista(JSONArray jsonArray){
        listaProducto = new ArrayList<String>();
        listaDescripcion = new ArrayList<String>();

        for(int i=0; i<jsonArray.length(); i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String descripcion =jsonObject.getString("descripcion" );
                String producto  =jsonObject.getString("producto" );

                listaDescripcion.add(descripcion);
                listaProducto.add(producto);

            }catch (JSONException jsnEx2){
                Toast.makeText(getApplicationContext(),jsnEx2.toString(),Toast.LENGTH_LONG).show();
            }
        }
        ArrayAdapter<String> adapterDescripcion = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaDescripcion);
        lsvProducto.setAdapter(adapterDescripcion);



        lsvProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               SharedPreferences preferences=getSharedPreferences("preferenciasproducto", Context.MODE_PRIVATE );
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("numero",listaProducto.get(position) );
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), cotizacion.class);
                startActivity(intent);
                finish();
                // tv1.setText("La "+lsvDetalle.getItemAtPosition(position)+"hola"+ listaSolicitud.get(position) +"mundo");
            }
        });
    }

    //-------------------------RECUPERAMOS LAS PRECENCIAS DE LA SOLICITUD -------------------------------------------------------------------//
// ---------------------------------------------------------------------------------------------------------------------//
    private void  recuperarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("preferenciasnumero", Context.MODE_PRIVATE);
        solicitud =preferences.getString("numero","0");
    }

}
