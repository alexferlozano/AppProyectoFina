package com.example.appproyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class InicioActivity extends AppCompatActivity {
    private CountDownTimer countDownTimer;
    private Intent main, menu;
    String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        main = new Intent(this, MainActivity.class);
        menu = new Intent(this, MenuActivity.class);

        countDownTimer = new CountDownTimer(4000, 4000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (token == ""){
                    startActivity(main);
                }
                else {
                    startActivity(menu);
                }
            }
        };
        countDownTimer.start();
    }
}