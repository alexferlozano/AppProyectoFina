package com.example.appproyectofinal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PermissionsActivity extends AppCompatActivity {
    List<ListElement> permisos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        int p1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int p2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if(p1 == PackageManager.PERMISSION_GRANTED && p2 == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
        }

        permisos = new ArrayList<>();
        permisos.add(new ListElement("#00AEFF", "Llamadas", "call_black", Manifest.permission.CALL_PHONE));
        permisos.add(new ListElement("#4b5497", "Cámara", "sd_storage_black", Manifest.permission.CAMERA));

        ListAdapter listAdapter = new ListAdapter(permisos, this.getApplicationContext(), PermissionsActivity.this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int p1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int p2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        switch (requestCode) {
            case 10 :
            case 11 :
                if(permissions.length >=1){
                    for (int grantResult : grantResults) {
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            break;
                        }
                    }
                }
                if(p1 == PackageManager.PERMISSION_GRANTED && p2 == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent (this, MainActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}