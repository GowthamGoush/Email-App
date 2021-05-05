package com.example.email_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.email_app.Activities.LoginActivity;
import com.example.email_app.Activities.RegisterActivity;
import com.example.email_app.Utils.IntentHelper;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        IntentHelper intentHelper = new IntentHelper(SplashScreen.this);

        new Handler().postDelayed(() -> {
            if (mAuth.getCurrentUser() == null){
                intentHelper.GoToRegister();
            } else {
                intentHelper.GoToImage();
            }
            finish();
        },2200);

    }
}