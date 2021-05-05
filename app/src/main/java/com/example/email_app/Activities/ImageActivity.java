package com.example.email_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.email_app.R;
import com.example.email_app.Receivers.Email;
import com.example.email_app.Utils.IntentHelper;
import com.example.email_app.Utils.NetworkConnection;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener{

    private LottieAnimationView lottieAnimationView;
    private MaterialButton returnBackText;
    private FirebaseAuth mAuth;
    private NetworkConnection networkConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        lottieAnimationView = findViewById(R.id.lottieImage);
        lottieAnimationView.setOnClickListener(this);

        returnBackText = findViewById(R.id.returnText);
        returnBackText.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        View parentLayout = findViewById(android.R.id.content);
        networkConnection = new NetworkConnection(parentLayout);

    }

    private void sendEmail(){

        if (!networkConnection.isConnected(ImageActivity.this)) {
            networkConnection.ShowNoConnection();
            return;
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, Email.class);
        intent.putExtra("UserEmail", mAuth.getCurrentUser().getEmail());

        int requestCode = (int) System.currentTimeMillis();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);

        alarmManager.setExact(AlarmManager.RTC, System.currentTimeMillis() + 300000, pendingIntent);

        Toast.makeText(ImageActivity.this, "The mail will be sent in 5 minutes!", Toast.LENGTH_LONG).show();
    }

    private void Logout(){

        if (!networkConnection.isConnected(ImageActivity.this)) {
            networkConnection.ShowNoConnection();
            return;
        }

        mAuth.signOut();
        IntentHelper intentHelper = new IntentHelper(ImageActivity.this);
        intentHelper.GoToLogin();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.lottieImage:
                sendEmail();
                break;

            case R.id.returnText:
                Logout();
                break;
        }
    }
}