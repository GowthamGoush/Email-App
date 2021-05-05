package com.example.email_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.email_app.R;
import com.example.email_app.Receivers.Email;
import com.example.email_app.Utils.IntentHelper;
import com.google.firebase.auth.FirebaseAuth;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener{

    private LottieAnimationView lottieAnimationView;
    private TextView returnBackText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        lottieAnimationView = findViewById(R.id.lottieImage);
        lottieAnimationView.setOnClickListener(this);

        returnBackText = findViewById(R.id.returnText);
        returnBackText.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    private void sendEmail(){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, Email.class);
        intent.putExtra("UserEmail",mAuth.getCurrentUser().getEmail());

        int currentTimeMillis = (int) System.currentTimeMillis();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, currentTimeMillis, intent, 0);

        alarmManager.set(AlarmManager.RTC, currentTimeMillis + 300000, pendingIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.lottieImage:
                sendEmail();
                Toast.makeText(ImageActivity.this, "A mail will be sent in 5 minutes!", Toast.LENGTH_LONG).show();
                break;

            case R.id.returnText:
                mAuth.signOut();
                IntentHelper intentHelper = new IntentHelper(ImageActivity.this);
                intentHelper.GoToLogin();
                finish();
                break;
        }
    }
}