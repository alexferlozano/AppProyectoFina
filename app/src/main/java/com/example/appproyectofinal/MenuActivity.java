package com.example.appproyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue= Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_menu);
        token=getIntent().getExtras().getString("token");
        //Button btn = (Button) findViewById(R.id);
        //btn.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {
              //  xd();
            //}
        //});
    }

    public void perfil(View view) {
        Intent intent=new Intent(MenuActivity.this, UserActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }

    private void xd()
    {
        String url = "http://192.168.0.103:8000/api/sensor/temperatura";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
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
