package com.example.appproyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MenuActivity extends AppCompatActivity {
    String token,sensor;
    RequestQueue queue;
    TextView text_grados,t_humedad,t_luz,t_distancia,t_presencia;
    Switch led1,led2;
    Timer timer;
    TimerTask timerTask;
    private CountDownTimer countDownTimer;
    public static final String URL_TEMP = "http://192.168.0.15:8000/api/sensor/temperatura";
    public static final String URL_HUM = "http://192.168.0.15:8000/api/sensor/humedad";
    public static final String URL_LUZ = "http://192.168.0.15:8000/api/sensor/luz";
    public static final String URL_DIS = "http://192.168.0.15:8000/api/sensor/distancia";
    public static final String URL_PRE = "http://192.168.0.15:8000/api/sensor/presencia";
    public static final String URL_LED1 = "http://192.168.0.15:8000/api/led/1";
    public static final String URL_LED2 = "http://192.168.0.15:8000/api/led/2";
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
        t_presencia=(TextView) findViewById(R.id.presencia);
        led1=(Switch) findViewById(R.id.switchLed1);
        led2=(Switch) findViewById(R.id.switchLed2);
        MostrarDatos(URL_TEMP,text_grados);
        MostrarDatos(URL_HUM,t_humedad);
        MostrarDatos(URL_LUZ,t_luz);
        MostrarDatos(URL_DIS,t_distancia);
        MostrarDatos(URL_PRE,t_presencia);
        //timer = new Timer();
        //startTimer();
    }

    /*private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                MostrarDatos(URL_TEMP,text_grados);
                MostrarDatos(URL_HUM,t_humedad);
                MostrarDatos(URL_LUZ,t_luz);
                MostrarDatos(URL_DIS,t_distancia);
                MostrarDatos(URL_PRE,t_presencia);
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 120000);
    }*/

    public void perfil(View view) {
        Intent intent=new Intent(MenuActivity.this, UserActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }
    public void cerrarSesion(View view) {
        CerrarSesion();
    }

    public void encenderLed(View view) {
        if(view.getId()==R.id.switchLed1)
        {
            if(led1.isChecked())
            {
                ledOnOFF(URL_LED1,"ON");
            }
            else
            {
                ledOnOFF(URL_LED1,"OFF");
            }
        }
        else
        {
            if(led2.isChecked())
            {
                ledOnOFF(URL_LED2,"ON");
            }
            else
            {
                ledOnOFF(URL_LED2,"OFF");
            }
        }
    }
    private void ledOnOFF(String url, String status)
    {
        JSONObject LED=new JSONObject();
        try {
            LED.put("value",status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, url, LED, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MenuActivity.this,response.toString(),Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map getHeaders() throws AuthFailureError{
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        queue.add(request);
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
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
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

        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        queue.add(request);
    }

    public void mirarDatos(View view) {
        switch(view.getId())
        {
            case R.id.Rtempe:
                sensor="Temperatura";
                break;
            case R.id.Rhume:
                sensor="Humedad";
                break;
            case R.id.Rluz:
                sensor="Luz";
                break;
            case R.id.Rdist:
                sensor="Distancia";
                break;
            case R.id.Rpres:
                sensor="Presencia";
                break;
        }
        Intent intent= new Intent(MenuActivity.this,StatsActivity.class);
        intent.putExtra("sensor",sensor);
        intent.putExtra("token",token);
        startActivity(intent);
    }

}
