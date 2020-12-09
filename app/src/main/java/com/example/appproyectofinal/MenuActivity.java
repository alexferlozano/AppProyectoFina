package com.example.appproyectofinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
    public static final String IP = "192.168.0.103";
    public static final String PORT = "8000";
    public static final String URL_TEMP = "http://" + IP + ":" + PORT + "/api/sensor/temperatura";
    public static final String URL_HUM = "http://" + IP + ":" + PORT + "/api/sensor/humedad";
    public static final String URL_LUZ = "http://" + IP + ":" + PORT + "/api/sensor/luz";
    public static final String URL_DIS = "http://" + IP + ":" + PORT + "/api/sensor/distancia";
    public static final String URL_PRE = "http://" + IP + ":" + PORT + "/api/sensor/presencia";
    public static final String URL_LED1 = "http://" + IP + ":" + PORT + "/api/led/1";
    public static final String URL_LED2 = "http://" + IP + ":" + PORT + "/api/led/2";
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
        /*MostrarDatos(URL_TEMP,text_grados);
        MostrarDatos(URL_HUM,t_humedad);
        MostrarDatos(URL_LUZ,t_luz);
        MostrarDatos(URL_DIS,t_distancia);
        MostrarDatos(URL_PRE,t_presencia);*/
        timer = new Timer();
        startTimer();
    }

    private void startTimer() {
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
    }

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
                    datos.setText(response.getString("sensor"));
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
        String LOGOUT = "http://" + IP + ":" + PORT + "/api/logout";
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
        timer.cancel();
        timerTask.cancel();
    }

    public void mirarDatos(View view) {
        switch(view.getId())
        {
            case R.id.Rled1:
                sensor="Led 1";
                break;
            case R.id.Rled2:
                sensor="Led 2";
                break;
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
    //Controla la pulsación del boton atrás
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea cerrar la aplicación?").setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

}
