package com.example.appproyectofinal;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.appproyectofinal.MainActivity.IP;
import static com.example.appproyectofinal.MainActivity.PORT;

public class StatsActivity extends AppCompatActivity {
    String token,sensor;
    RequestQueue queue;
    TextView titulo;
    public static final String URL_TEMP = "http://" + IP + ":" + PORT + "/api/temperatura";
    public static final String URL_HUM = "http://" + IP + ":" + PORT + "/api/humedad";
    public static final String URL_LUZ = "http://" + IP + ":" + PORT + "/api/luz";
    public static final String URL_DIS = "http://" + IP + ":" + PORT + "/api/distancia";
    public static final String URL_PRE = "http://" + IP + ":" + PORT + "/api/presencia";
    public static final String URL_LED = "http://" + IP + ":" + PORT + "/api/led";
    public static final String URL_LED1 = "http://" + IP + ":" + PORT + "/api/led1";
    public static final String URL_LED2 = "http://" + IP + ":" + PORT + "/api/led2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        queue= Volley.newRequestQueue(this);
        token=getIntent().getExtras().getString("token");
        sensor=getIntent().getExtras().getString("sensor");
        titulo = findViewById(R.id.titulo);
        titulo.setText(sensor);
        switch (sensor)
        {
            case "Led 1":
                ObtenerDatos(URL_LED1);
                break;
            case "Led 2":
                ObtenerDatos(URL_LED2);
                break;
            case "Temperatura":
                ObtenerDatos(URL_TEMP);
                break;
            case "Humedad":
                ObtenerDatos(URL_HUM);
                break;
            case "Luz":
                ObtenerDatos(URL_LUZ);
                break;
            case "Distancia":
                ObtenerDatos(URL_DIS);
                break;
            case "Presencia":
                ObtenerDatos(URL_PRE);
                break;
            case "Led":
                ObtenerDatos(URL_LED);
                break;
        }
    }
    private void ObtenerDatos(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray resultados= response.getJSONArray("sensores");
                    Gson convertidor= new Gson();
                    Type tipolistaDatos =new TypeToken<List<Datos>>(){}.getType();
                    List<Datos> datos=convertidor.fromJson(resultados.toString(),tipolistaDatos);
                    AdapterDatos personaAdapter= new AdapterDatos(datos);
                    RecyclerView recyclerView= findViewById(R.id.reView);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(StatsActivity.this));
                    recyclerView.setAdapter(personaAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StatsActivity.this,"No se ha podido realizar la peticion",Toast.LENGTH_SHORT).show();
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
