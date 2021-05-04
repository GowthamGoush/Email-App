package com.example.email_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.email_app.Activities.LoginActivity;
import com.example.email_app.Activities.RegisterActivity;
import com.example.email_app.Utils.IntentHelper;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                IntentHelper intentHelper = new IntentHelper(SplashScreen.this);
                intentHelper.GoToRegister();
            }
        },2000);
    }
}