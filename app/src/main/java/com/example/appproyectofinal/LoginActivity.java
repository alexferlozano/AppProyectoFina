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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    RequestQueue queue;
    TextView name,email,password;
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
                //Login();
                email.setText("");
                password.setText("");
                aber();
            }
        });

    }
    private void Login()
    {
        String url = "http://192.168.0.103:8000/api/login";
        JSONObject persona=new JSONObject();
        try {
            //persona.put("email",email.getText().toString());
            //persona.put("password",password.getText().toString());
            persona.put("email","abdeelit@gmail.com");
            persona.put("password","123456");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, url, persona, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(LoginActivity.this,"Has mandado tus datos correctamente",Toast.LENGTH_SHORT).show();
                try {
                    token=response.getString("token");
                    Intent intent=new Intent(LoginActivity.this,MenuActivity.class);
                    intent.putExtra("token",token);
                    /*intent.putExtra("email",correo);*/
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
        queue.add(request);
    }
    private void aber(){
        token="5|26HBl7AXvTJMF6blxX6RmB0FRCnhb4ucwHDhueLj";
        Intent intent=new Intent(LoginActivity.this,MenuActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }
}
