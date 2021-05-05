package com.example.email_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.email_app.R;
import com.example.email_app.Utils.ExitDialog;
import com.example.email_app.Utils.IntentHelper;
import com.example.email_app.Utils.NetworkConnection;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText loginEmail_id, loginPassword;
    private Button loginBtn;
    private TextView registerText;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private NetworkConnection networkConnection;
    private ExitDialog exitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail_id = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);

        loginBtn = findViewById(R.id.userLogin);
        loginBtn.setOnClickListener(this);

        registerText = findViewById(R.id.loginText);
        registerText.setOnClickListener(this);

        progressBar = findViewById(R.id.loginProgressBar);

        mAuth = FirebaseAuth.getInstance();

        View parentLayout = findViewById(android.R.id.content);
        networkConnection = new NetworkConnection(parentLayout);

        exitDialog = new ExitDialog(LoginActivity.this);
    }

    private void userLogin() {
        String email = loginEmail_id.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (!networkConnection.isConnected(LoginActivity.this)) {
            networkConnection.ShowNoConnection();
            return;
        }

        if (email.isEmpty()) {
            loginEmail_id.setError("Email is required");
            loginEmail_id.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            loginPassword.setError("Password is required");
            loginPassword.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmail_id.setError("Please provide valid email!");
            loginEmail_id.requestFocus();
            return;
        }

        if (password.length() < 6) {
            loginPassword.setError("Min Password length is 6 characters!");
            loginPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task1 -> {

            if (task1.getResult().getSignInMethods().isEmpty()) {

                loginEmail_id.setError("Email id is incorrect");
                loginEmail_id.requestFocus();
                progressBar.setVisibility(View.GONE);

            } else {

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task2 -> {

                    if (task2.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if (user.isEmailVerified()) {
                            IntentHelper intentHelper = new IntentHelper(LoginActivity.this);
                            intentHelper.GoToImage();
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        loginPassword.setError("Password is incorrect");
                        loginPassword.requestFocus();
                    }
                    progressBar.setVisibility(View.GONE);

                });
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.userLogin:
                userLogin();
                break;

            case R.id.loginText:
                IntentHelper intentHelper = new IntentHelper(LoginActivity.this);
                intentHelper.GoToRegister();
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        exitDialog.showExitDialog();
    }
}