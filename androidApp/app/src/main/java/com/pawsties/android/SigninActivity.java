package com.pawsties.android;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class SigninActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button ingresar;
    String email, password;
    boolean typeUser;
    AlertDialog inputAlert;
    public static Adoptante adoptante;
    public static Rescatista rescatista;
    public static JSONObject usuarioJSON;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_form);

        getSupportActionBar().setTitle("Iniciar sesion");

        etEmail = findViewById(R.id.email_signin);
        etPassword = findViewById(R.id.password_signin);
        ingresar = findViewById(R.id.btnOkSignin);

        ingresar.setOnClickListener(v -> {
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setMessage("Ingresa los datos solicitados")
                        .setCancelable(true)
                        .setPositiveButton("aceptar", (dialog, which) -> {});
                inputAlert = alertBuilder.create();
                inputAlert.show();
            }
            else
                iniciarSesion(email, password);
        });
    }

    public void iniciarSesion(String correo, String contrasena){
        /** INICIAR SESION CON LA INFROMACION DE LA BD DE AZURE */
        //de acuerdo con la informacion, obtener el objeto JSON de ese usuario
        //almacenar en el objeto correspondiente a el TIPO de usuario que regrese la BD
        /** VALIDAR QUE DE LOS OBJETOS RECIBIDOS EL QUE COINCIDA CON EL CORREO Y CONTRASEÑA
         * ESE SE PASARA AL LANZAR EL MAIN ACTIVITY,
         * SI NO ES ASI, INDICAR QUE LOS DATOS INGRESADOS SON INCORRECTOS (CON UN ALERT)*/
        typeUser = true;//EJEMPLO: esto lo tiene que recibir de la BD
        //typeUser = usuarioJSON.get("userType");

        //si todo es correcto, lanzara el main activity (poner el intento dentro de un condicional)
        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
        intent.putExtra("typeUser", typeUser);
        intent.putExtra("activity", "in");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
