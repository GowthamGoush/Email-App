package com.example.email_app.Utils;

import android.content.Context;
import android.content.Intent;

import com.example.email_app.Activities.ImageActivity;
import com.example.email_app.Activities.LoginActivity;
import com.example.email_app.Activities.RegisterActivity;

public class IntentHelper {
    Context context;

    public IntentHelper(Context context) {
        this.context = context;
    }

    public void GoToRegister() {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    public void GoToLogin() {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public void GoToImage() {
        context.startActivity(new Intent(context, ImageActivity.class));
    }
}