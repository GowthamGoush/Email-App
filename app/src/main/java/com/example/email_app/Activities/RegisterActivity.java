package com.example.email_app.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.email_app.R;
import com.example.email_app.Utils.IntentHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email_id, password, confirmPassword;
    private Button registerBtn;
    private TextView loginText;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email_id = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        loginText = findViewById(R.id.login);
        loginText.setOnClickListener(this);

        registerBtn = findViewById(R.id.register);
        registerBtn.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    private void userRegister() {
        String userEmail = email_id.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        String userConfirmPassword = confirmPassword.getText().toString().trim();

        if (userEmail.isEmpty()) {
            email_id.setError("Email is required");
            email_id.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            email_id.setError("Please provide valid email!");
            email_id.requestFocus();
            return;
        }

        if (userPassword.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if (userConfirmPassword.isEmpty()) {
            confirmPassword.setError("Confirm password is required");
            confirmPassword.requestFocus();
            return;
        }

        if (!userConfirmPassword.equals(userPassword)) {
            confirmPassword.setError("Password does not match");
            confirmPassword.requestFocus();
            return;
        }

        if (userPassword.length() < 6) {
            password.setError("Min Password length should be 6 characters!");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();

                user.sendEmailVerification();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();

                IntentHelper intentHelper = new IntentHelper(RegisterActivity.this);
                intentHelper.GoToLogin();
                finish();

            } else {
                Toast.makeText(RegisterActivity.this, "Already registered!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                userRegister();
                break;
            case R.id.login:
                IntentHelper intentHelper = new IntentHelper(RegisterActivity.this);
                intentHelper.GoToLogin();
                finish();
                break;
        }
    }
}