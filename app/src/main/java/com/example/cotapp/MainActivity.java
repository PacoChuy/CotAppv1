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
                validarUsuario( "http://192.168.0.5/coti/controller/login.php");
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"No se permiten campos vacios",Toast.LENGTH_SHORT).show();
                        }
            }
        });
    }

    private  void validarUsuario (String URL ){
        StringRequest StringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    guardarPreferencias();
                   Intent intent=  new Intent (getApplicationContext(), Home_activity.class);
                   startActivity(intent);
                   finish();
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

}
