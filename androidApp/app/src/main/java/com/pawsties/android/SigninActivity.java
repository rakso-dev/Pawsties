package com.pawsties.android;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SigninActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button ingresar, registrarse;
    String email, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_form);

        getSupportActionBar().setTitle("Iniciar sesion");

        etEmail = findViewById(R.id.email_signin);
        etPassword = findViewById(R.id.password_signin);
        ingresar = findViewById(R.id.btnOkSignin);
        registrarse = findViewById(R.id.btnRegistrarseSignin);

        ingresar.setOnClickListener(v -> {
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
                Toast.makeText(SigninActivity.this, "Ingresa datos validos en los campos", Toast.LENGTH_LONG).show();
            else
                iniciarSesion(email, password);
        });
    }

    public void iniciarSesion(String correo, String contrasena){
        /** INICIAR SESION CON LA INFROMACION DE LA BD DE AZURE */

        //si todo es correcto, lanzara el main activity (poner el intento dentro de un condicional)
        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
