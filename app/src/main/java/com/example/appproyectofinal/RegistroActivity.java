package com.example.appproyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.appproyectofinal.MainActivity.IP;
import static com.example.appproyectofinal.MainActivity.PORT;

public class RegistroActivity extends AppCompatActivity {
    RequestQueue queue;
    TextView name,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        queue= Volley.newRequestQueue(this);
        Button btnRegre = (Button) findViewById(R.id.reg_regresar);
        name= findViewById(R.id.reg_name);
        email=findViewById(R.id.reg_correo);
        password=findViewById(R.id.reg_pass);
        btnRegre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        Button btn = (Button) findViewById(R.id.reg_registro);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrarse();
                name.setText("");
                email.setText("");
                password.setText("");
            }
        });
    }
    private void Registrarse()
    {
        String url = "http://" + IP + ":" + PORT + "/api/signin";
        JSONObject persona=new JSONObject();
        try {
            persona.put("name", name.getText().toString());
            persona.put("email",email.getText().toString());
            persona.put("password",password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, url, persona, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(RegistroActivity.this,"Te has registrado correctamente",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistroActivity.this, error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
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
}