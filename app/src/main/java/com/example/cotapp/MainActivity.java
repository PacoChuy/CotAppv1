package com.example.cotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText txt_Usuario, txt_Password;
    Button btnLogin;
    String usuario,password;
    Spinner spinnerCategoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_Usuario=findViewById(R.id.txt_Usuario);
        txt_Password=findViewById(R.id.txt_password);
        btnLogin= findViewById(R.id.btn_Login);
        recuperarPreferencias();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View view) {
                usuario=txt_Usuario.getText().toString();
                password=txt_Password.getText().toString();
                        if(!usuario.isEmpty() && !password.isEmpty()){
                validarUsuario( "login.php?f=datos&u="+txt_Usuario.getText().toString()+"&p="+txt_Password.getText().toString());
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"No se permiten campos vacios",Toast.LENGTH_SHORT).show();
                        }
            }
        });
    }

    public void validarUsuario(String URL){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, conexion.URL_WEB_SERVICES+URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    try {
                        guardarPreferencias();
                        JSONArray jsonArray = new JSONArray(response);
                        obtenerdatos(jsonArray);
                    }catch (JSONException jsnex1){

                        Toast.makeText(getApplicationContext(),jsnex1.toString(),Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText( MainActivity.this, "usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();
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
    public void  obtenerdatos(JSONArray jsonArray){
        User usuario = new User();
        for(int i=0; i<jsonArray.length(); i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                usuario.setUsuario(jsonObject.getString("usuario"));
                usuario.setNombre(jsonObject.getString("nombre"));
                usuario.setApellido(jsonObject.getString("apellido"));
                usuario.setTelefono(jsonObject.getString("telefono"));
                usuario.setCorreo(jsonObject.getString("correo"));
                usuario.setRol(jsonObject.getString("rol"));
                usuario.setCiudad(jsonObject.getString("ciudad"));
                usuario.setEmpresa(jsonObject.getString("empresa"));
            }catch (JSONException jsnEx2){
                Toast.makeText(getApplicationContext(),jsnEx2.toString(),Toast.LENGTH_LONG).show();
            }
        }
      if(usuario.getRol().equals("3")) {

          SharedPreferences preferences=getSharedPreferences("preferenciasusuarios",Context.MODE_PRIVATE );
          SharedPreferences.Editor editor = preferences.edit();
          editor.putString("usuario",usuario.getUsuario());
          editor.putString("nombre",usuario.getNombre());
          editor.putString("apellido",usuario.getApellido());
          editor.putString("telefono",usuario.getTelefono());
          editor.putString("correo",usuario.getCorreo());
          editor.putString("rol",usuario.getRol());
          editor.putString("ciudad",usuario.getCiudad());
          editor.putString("empresa",usuario.getEmpresa());
          editor.putBoolean("sesion",true);
          editor.commit();
            Intent intent = new Intent(getApplicationContext(), Home_activity.class);
            startActivity(intent);
            finish();
       }
      else
      {
          guardarPreferencias();
          Intent intent = new Intent(getApplicationContext(), Home_Supplier.class);
          intent.putExtra(Home_Supplier.nombre, usuario.getNombre());
          startActivity(intent);
          finish();

       }
    }

    private  void  guardarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario",usuario);
        editor.putString("password",password);
        editor.putBoolean("sesion",true);
        editor.commit();
    }

    private void  recuperarPreferencias(){
    SharedPreferences preferences = getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
    txt_Usuario.setText(preferences.getString("usuario", "Correo"));
    txt_Password.setText(preferences.getString("password","Password"));

    }

    public void registro_usuario (View view ){
        Intent datos = new Intent(this, Registro_Usuario.class);
        startActivity(datos);
    }


}
