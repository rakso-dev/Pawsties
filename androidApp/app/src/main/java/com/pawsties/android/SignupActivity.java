package com.pawsties.android;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class SignupActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    RadioButton rbAdoptante, rbRescatista;
    Button registrarse;
    String name="", lastname="",  email="", password="", typeUser="";
    Date nacimiento;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        getSupportActionBar().setTitle("Registro");

        /** LLAMAR A TODS LOS ELEMENTOS DEL LAYOUT ACTIVITY SIGN UP PARA OBTENER LOS DATOS*/
        rbAdoptante = findViewById(R.id.rbAdoptante);
        rbRescatista = findViewById(R.id.rbRescatista);
        registrarse = findViewById(R.id.btnOkSignup);

        registrarse.setOnClickListener(v -> {
            /**descomentar despues para hacer las validaciones*/
            if (rbAdoptante.isActivated())//no lo hace
                typeUser = "A";
            if (rbRescatista.isActivated())
                typeUser = "R";
            Toast.makeText(SignupActivity.this, "El usuario es "+typeUser, Toast.LENGTH_SHORT).show();
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
//
//            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
//                Toast.makeText(SignupActivity.this, "Debes llenar todos los campos antes de continuar!!!", Toast.LENGTH_LONG).show();
//            else if (password.length() < 8)
//                Toast.makeText(SignupActivity.this, "La contraseña debe ser de almenos 8 caracteres", Toast.LENGTH_LONG).show();
//            else
                registro(email, password);
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
