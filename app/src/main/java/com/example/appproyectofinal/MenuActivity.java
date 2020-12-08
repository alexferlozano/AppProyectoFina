package com.example.appproyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {
    String token;
    RequestQueue queue;
    TextView text_grados,t_humedad,t_luz,t_distancia,t_presencia;
    public static final String URL_TEMP = "http://192.168.0.15:8000/api/sensor/temperatura";
    public static final String URL_HUM = "http://192.168.0.15:8000/api/sensor/humedad";
    public static final String URL_LUZ = "http://192.168.0.15:8000/api/sensor/luz";
    public static final String URL_DIS = "http://192.168.0.15:8000/api/sensor/distancia";
    public static final String URL_PRE = "http://192.168.0.15:8000/api/sensor/presencia";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue= Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_menu);
        token=getIntent().getExtras().getString("token");
        text_grados=(TextView) findViewById(R.id.grados);
        t_humedad=(TextView) findViewById(R.id.humedad);
        t_luz=(TextView) findViewById(R.id.luz);
        t_distancia=(TextView) findViewById(R.id.distancia);
        MostrarDatos(URL_TEMP,text_grados);
        MostrarDatos(URL_HUM,t_humedad);
        MostrarDatos(URL_LUZ,t_luz);
        MostrarDatos(URL_DIS,t_distancia);
    }

    public void perfil(View view) {
        Intent intent=new Intent(MenuActivity.this, UserActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }
    public void cerrarSesion(View view) {
        CerrarSesion();
    }

    private void MostrarDatos(String url, TextView datos)
    {
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    datos.setText(response.getString("Response"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        queue.add(request);
    }
    private void CerrarSesion()
    {
        String LOGOUT = "http://192.168.0.15:8000/api/logout";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.DELETE, LOGOUT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MenuActivity.this,"Has cerrado Sesión",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MenuActivity.this,LoginActivity.class);
                intent.putExtra("token","");
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        queue.add(request);
    }
}
