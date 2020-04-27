package com.example.cotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
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





public class Registro_Usuario extends AppCompatActivity {

    List<String>listaIdSucursales; //guarda el id de los Roles
    List<String>listaRoles;// guarda el nombre de la sucursal
    EditText nombre,apellido,correo,password,confirm_password;
    Button btnAgregar;
    Spinner spinnerRol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__usuario);
        nombre=(EditText)findViewById(R.id.txt_Nombre);
        apellido=(EditText)findViewById(R.id.txt_Apellido);
        correo=(EditText)findViewById(R.id.txt_Correo);
        password=(EditText)findViewById(R.id.txt_password);
        confirm_password=(EditText)findViewById(R.id.txt_con_pass);
        btnAgregar=(Button) findViewById(R.id.btn_registar);
        spinnerRol=(Spinner)findViewById(R.id.sp_Rol);
        cargarRoles();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombre.length()==0 ||apellido.length()==0||correo.length()==0||password.length()==0||confirm_password.length()==0)
                {
                    Toast.makeText(Registro_Usuario.this, "Informacion pendiente por llenar ", Toast.LENGTH_LONG).show();
                }
                else {
                    ejecutarServicio("Insert_User.php");
                }

            }
        });
    }


    public void cargarRoles(){
        RequestQueue queue = Volley.newRequestQueue(this);
    String url ="Fill_Spinner_Rol.php?funcion=obtenerRol";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, conexion.URL_WEB_SERVICES+url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            if (response.length()>0){
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    obtenerRoles(jsonArray);

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

    public void  obtenerRoles(JSONArray jsonArray){
        listaIdSucursales=new ArrayList<String>();
        listaRoles = new ArrayList<String>();
        for(int i=0; i<jsonArray.length(); i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String Rol =jsonObject.getString("Rol");
                listaIdSucursales.add(id);
                listaRoles.add(Rol);
            }catch (JSONException jsnEx2){
                Toast.makeText(getApplicationContext(),jsnEx2.toString(),Toast.LENGTH_LONG).show();

            }

        }
        ArrayAdapter<String>adapterRoles = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaRoles);
        spinnerRol.setAdapter(adapterRoles);

    }

    private void  ejecutarServicio(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES +URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Este usuario ya esta registrado", Toast.LENGTH_SHORT).show();
                }
                else{
                    nombre.setText("");
                    apellido.setText("");
                    correo.setText("");
                    password.setText("");
                    confirm_password.setText("");
                    Toast.makeText(getApplicationContext(), "Usuario Registrado", Toast.LENGTH_SHORT).show();
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
                parametros.put("Nombre_usuario",nombre.getText().toString());
                parametros.put("Apellido_usuario",apellido.getText().toString());
                parametros.put("Correo_usuario",correo.getText().toString());
                parametros.put("Password_usuario",password.getText().toString());
                parametros.put("$reg_Rol_u",spinnerRol.getSelectedItem().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}