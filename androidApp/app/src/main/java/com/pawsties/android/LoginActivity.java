package com.pawsties.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button sigin, signup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Bienvenido");

        sigin = findViewById(R.id.btnSignin);
        signup = findViewById(R.id.btnSignup);

        sigin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
            startActivity(intent);
        });

        signup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

}
