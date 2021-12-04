package com.pawsties.android;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button ingresar, registrarse;
    String email, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        getSupportActionBar().setTitle("Registro");

        /** LLAMAR LOS ELEMENTOS DEL LAYOUT ACTIVITY SIGN UP*/
        etEmail = findViewById(R.id.email_signin);
        etPassword = findViewById(R.id.password_signin);
        ingresar = findViewById(R.id.btnOkSignin);
        registrarse = findViewById(R.id.btnRegistrarseSignin);

        ingresar.setOnClickListener(v -> {
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
                Toast.makeText(SignupActivity.this, "Debes llenar todos los campos antes de continuar!!!", Toast.LENGTH_LONG).show();
            else if (password.length() < 8)
                Toast.makeText(SignupActivity.this, "La contraseña debe ser de almenos 8 caracteres", Toast.LENGTH_LONG).show();
            else
                registro(email, password);
        });

        registrarse.setOnClickListener(v -> {
            Intent intent;//lanzar activity signin
        });
    }

    public void registro(String correo, String contrasena){
        /**aqui se va a hacer el registro en la base de Azure*/

        //si todo es correcto, esta funcion va a lanzar el main activity
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
