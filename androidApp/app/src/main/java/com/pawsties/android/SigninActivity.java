package com.pawsties.android;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

public class SigninActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button ingresar;
    ImageButton signWithGoogle;
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
    public static final int REQUEST_CODE_SIGN_WITH_GOOGLE = 100;
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
        signWithGoogle = findViewById(R.id.btnSignInGoogle);

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

        signWithGoogle.setOnClickListener(v -> {
             GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                     .requestIdToken("751265962071-e6gn0a3f2uq92i69cv2jj1v73h8anqh6.apps.googleusercontent.com")
                     .requestEmail()
                     .build();

             googleClient = GoogleSignIn.getClient(SigninActivity.this, gso);

             Intent googleIntent = googleClient.getSignInIntent();
             startActivityForResult(googleIntent, REQUEST_CODE_SIGN_WITH_GOOGLE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SIGN_WITH_GOOGLE){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                googleAccount = task.getResult(ApiException.class);
//            }catch (ApiException e){
//                Toast.makeText(SigninActivity.this, "ERROR: "+e, Toast.LENGTH_LONG).show();
//            }
            launcMainActivity();
        }
    }

    public void iniciarSesion(String correo, String contrasena){
        /** INICIAR SESION CON LA INFROMACION DE LA BD DE AZURE */
        //de acuerdo con la informacion, obtener el objeto JSON de ese usuario
        //almacenar en el objeto correspondiente a el TIPO de usuario que regrese la BD
        /** VALIDAR QUE DE LOS OBJETOS RECIBIDOS EL QUE COINCIDA CON EL CORREO Y CONTRASEÑA
         * ESE SE PASARA AL LANZAR EL MAIN ACTIVITY,
         * SI NO ES ASI, INDICAR QUE LOS DATOS INGRESADOS SON INCORRECTOS (CON UN ALERT)
         * GUARDAR EL ESTADO DE USUARIO AUTENTICADO*/
        //el typeuser lo tiene que recibir de la BD
        //typeUser = usuarioJSON.get("userType");

        //si todo es correcto, lanzara el main activity (poner el intento dentro de un condicional)
        launcMainActivity();
    }

    public void launcMainActivity(){
        typeUser = false;
        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
        intent.putExtra("typeUser", typeUser);
        intent.putExtra("activity", "in");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
