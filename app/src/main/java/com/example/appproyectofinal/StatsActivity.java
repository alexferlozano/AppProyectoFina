package com.example.appproyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class StatsActivity extends AppCompatActivity {
    String token,sensor;
    public static final String URL_TEMP = "http://192.168.0.103:8000/api/temperatura";
    public static final String URL_HUM = "http://192.168.0.103:8000/api/humedad";
    public static final String URL_LUZ = "http://192.168.0.103:8000/api/luz";
    public static final String URL_DIS = "http://192.168.0.103:8000/api/distancia";
    public static final String URL_PRE = "http://192.168.0.103:8000/api/presencia";
    public static final String URL_LED = "http://192.168.0.103:8000/api/led";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        token=getIntent().getExtras().getString("token");
        sensor=getIntent().getExtras().getString("sensor");
        Toast.makeText(StatsActivity.this,sensor,Toast.LENGTH_SHORT).show();
    }
    private void obtenerDatos(){

    }
    /*private void ObtenerDatos()
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray resultados= response.getJSONArray("resultados");
                    Gson convertidor= new Gson();
                    Type tipolistaPersonas =new TypeToken<List<Persona>>(){}.getType();
                    List<Persona> personas=convertidor.fromJson(resultados.toString(),tipolistaPersonas);
                    PersonaAdapter personaAdapter= new PersonaAdapter(personas,activityEstadistica.this);
                    RecyclerView recyclerView= findViewById(R.id.reView);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(activityEstadistica.this));
                    recyclerView.setAdapter(personaAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activityEstadistica.this,"No se ha podido realizar la peticion",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }*/
}
