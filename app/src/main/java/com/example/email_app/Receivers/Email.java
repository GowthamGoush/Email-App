package com.example.email_app.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Email extends BroadcastReceiver {

    private FirebaseAuth mAuth;
    private String email;

    @Override
    public void onReceive(Context context, Intent intent) {

        mAuth = FirebaseAuth.getInstance();
        email = intent.getStringExtra("UserEmail");

        if (email.isEmpty()){
            Toast.makeText(context, "Failed to send the email!", Toast.LENGTH_LONG).show();
        } else {
            mAuth.sendPasswordResetEmail(email);
        }

    }
}
