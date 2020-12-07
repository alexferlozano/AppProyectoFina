package com.example.appproyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_menu);
        token=getIntent().getExtras().getString("token");
    }

    public void perfil(View view) {
        Intent intent=new Intent(MenuActivity.this,MenuActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }
}
