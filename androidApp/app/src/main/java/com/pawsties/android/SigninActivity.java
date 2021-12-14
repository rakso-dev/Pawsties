package com.pawsties.android;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

public class SigninActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button ingresar;
    String email, password;
    boolean typeUser;
    AlertDialog inputAlert;
    public GoogleSignInClient googleClient;
    public GoogleSignInAccount googleAccount;
    public GoogleSignInOptions googleOptions;
    public GoogleSignInOptionsExtensionParcelable googleXtendedOptions;
    public GoogleApiClient googleApiClient;
    FirebaseAuth firebaseAuth;
    //LocalAuthentication localAuthentication;
    boolean biometric = false;
    public static Adoptante adoptante;
    public static Rescatista rescatista;
    public static JSONObject usuarioJSON;
    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_form);

        getSupportActionBar().setTitle("Iniciar sesion");

        etEmail = findViewById(R.id.email_signin);
        etPassword = findViewById(R.id.password_signin);
        ingresar = findViewById(R.id.btnOkSignin);

        auth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onResume() {
        super.onResume();

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

        auth.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            launcMainActivity();
                        else
                            Toast.makeText(SigninActivity.this, "Autenticacion fallida!!!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void launcMainActivity(){
        typeUser = true;
        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
        intent.putExtra("typeUser", typeUser);
        intent.putExtra("activity", "in");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
