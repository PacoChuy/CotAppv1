package com.example.cotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText txt_Usuario, txt_Password;
    Button btnLogin;
    String usuario,password;
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
               // validarUsuario( "login.php?u="+txt_Usuario.getText().toString()+"&p="+txt_Password.getText().toString());
                           // validarUsuario( "login.php?f=u=123@gmail.com&p=15");
                            validarUsuario( "login.php");
                        }

                        else
                        {
                            Toast.makeText(MainActivity.this,"No se permiten campos vacios",Toast.LENGTH_SHORT).show();
                        }
            }
        });
    }

    private  void validarUsuario (String URL ){
        StringRequest StringRequest= new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES + URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.isEmpty()){

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    User usuario = new User();
                    JSONObject jsonObject=null;
                    jsonObject =jsonArray.getJSONObject(0);
                    usuario.setUsuario(jsonObject.optString("usuario"));
                    usuario.setNombre(jsonObject.optString("nombre"));
                    usuario.setApellido(jsonObject.optString("apellido"));
                    usuario.setTelefono(jsonObject.optString("telefono"));
                    usuario.setCorreo(jsonObject.optString("correo"));
                    usuario.setRol(jsonObject.optString("rol"));
                    usuario.setCiudad(jsonObject.optString("ciudad"));
                    usuario.setEmpresa(jsonObject.optString("empresa"));
                    guardarPreferencias();

                    Intent intent=  new Intent (getApplicationContext(), Home_activity.class);
                    intent.putExtra(Home_activity.nombres,usuario.getNombre());
                    startActivity(intent);
                    finish();
                }catch (JSONException jsnex1){

                    Toast.makeText(getApplicationContext(),jsnex1.toString(),Toast.LENGTH_LONG).show();
                }
                }else{
                   Toast.makeText( MainActivity.this, "usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> parametros =new HashMap<String, String>();
                parametros.put("usuario",usuario);
                parametros.put("password",password);
                return  parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(StringRequest);
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
