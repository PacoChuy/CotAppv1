package com.example.cotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_Usuario=findViewById(R.id.txt_Usuario);
        txt_Password=findViewById(R.id.txt_password);
        btnLogin= findViewById(R.id.btn_Login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View view) {
                validarUsuario( "http://192.168.0.4/coti/controller/login.php");
            }
        });
    }

    private  void validarUsuario (String URL ){
        StringRequest StringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                   Intent intent=  new Intent (getApplicationContext(), Home_activity.class);
                   startActivity(intent);
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
                parametros.put("Usuario",txt_Usuario.getText().toString());
                parametros.put("password",txt_Password.getText().toString());
                return  parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(StringRequest);
    }
}
