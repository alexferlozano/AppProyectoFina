package com.example.appproyectofinal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        token=getIntent().getExtras().getString("token");
    }
}
