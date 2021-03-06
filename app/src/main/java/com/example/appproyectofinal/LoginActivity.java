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

public class LoginActivity extends AppCompatActivity {
    RequestQueue queue;
    TextView email,password;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        queue= Volley.newRequestQueue(this);
        email=findViewById(R.id.log_email);
        password=findViewById(R.id.log_correo);

        Button btnRegre = (Button) findViewById(R.id.log_regresar);
        btnRegre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        Button login = (Button) findViewById(R.id.log_iniciar);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
                email.setText("");
                password.setText("");
            }
        });
    }
    private void Login()
    {
        String url = "http://" + IP + ":" + PORT + "/api/login";
        JSONObject persona=new JSONObject();
        try {
            persona.put("email",email.getText().toString());
            persona.put("password",password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, url, persona, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(LoginActivity.this,"Has iniciado sesión",Toast.LENGTH_SHORT).show();
                try {
                    token=response.getString("token");
                    Intent intent=new Intent(LoginActivity.this,MenuActivity.class);
                    intent.putExtra("token",token);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
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
