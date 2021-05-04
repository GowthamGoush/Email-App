package com.example.email_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.email_app.R;
import com.example.email_app.Utils.IntentHelper;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener{

    private LottieAnimationView lottieAnimationView;
    private TextView returnBackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        lottieAnimationView = findViewById(R.id.lottieImage);
        lottieAnimationView.setOnClickListener(this);

        returnBackText = findViewById(R.id.returnText);
        returnBackText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lottieImage:
                Toast.makeText(ImageActivity.this, "You have clicked!", Toast.LENGTH_LONG).show();
                break;
            case R.id.returnText:
                IntentHelper intentHelper = new IntentHelper(ImageActivity.this);
                intentHelper.GoToLogin();
                finish();
                break;
        }
    }
}