package com.pawsties.android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    EditText etName, etLastname, etTelefono, etNacimiento, etEmail, etPassword, etRFC;
    ImageButton setDate;
    RadioButton rbAdoptante, rbRescatista;
    RadioGroup rgType;
    Button registrarse;
    String telefono;
    String name="";
    String lastname="";
    String email="";
    String password="";
    boolean typeUser;
    String rfc="";
    Date nacimiento;
    LocationListener locationListener;
    LocationManager locationManager;
    AlertDialog gpsAlert = null, inputAlert = null;
    double latitud, longitud;
    int dia, mes, anho;
    UserModel usuario;
    public static Adoptante adoptante;
    public static Rescatista rescatista;
    public static JSONObject usuarioJSON = new JSONObject();
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        getSupportActionBar().setTitle("Registro");

        etName = findViewById(R.id.etNombrePet);
        etLastname = findViewById(R.id.etDescripcionPet);
        etTelefono = findViewById(R.id.etTelefonoEP);
        etNacimiento = findViewById(R.id.etFechaPet);
        etEmail = findViewById(R.id.etEmailEP);
        etPassword = findViewById(R.id.etContrasenaEP);
        etRFC = findViewById(R.id.etRFCep);
        rgType = findViewById(R.id.rgTipoPet);
        rbAdoptante = findViewById(R.id.rbGato);
        rbRescatista = findViewById(R.id.rbPerro);
        setDate = findViewById(R.id.btnSetDateSU);
        registrarse = findViewById(R.id.btnDonePet);

    }

    @Override
    protected void onResume() {
        super.onResume();

        auth = FirebaseAuth.getInstance();

        int permision = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        if (permision != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    MainActivity.REQUEST_CODE_LOCATION);
            return;
        }

        getLocation();

        setDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anho = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this, (view, year, month, dayOfMonth) ->
                    etNacimiento.setText(year+"-"+(month+1)+"-"+dayOfMonth), anho, mes, dia);

            datePickerDialog.show();
        });

        registrarse.setOnClickListener(v -> {
            getLocation();
            name = etName.getText().toString();
            telefono = etTelefono.getText().toString();//NO DEBE DE ESTAR VACIO ESTE CAMPO
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
            if (rgType.getCheckedRadioButtonId() == -1){
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setMessage("Elige un tipo de usuario valido")
                        .setCancelable(true)
                        .setPositiveButton("aceptar", (dialog, which) -> {});
                inputAlert = alertBuilder.create();
                inputAlert.show();
                return;
            }else{
                if (rgType.getCheckedRadioButtonId() == rbAdoptante.getId()) {
                    typeUser = true;
                    lastname = etLastname.getText().toString();
                    //valida la integridad de los datos ingresados
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(telefono) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(lastname) || TextUtils.isEmpty(etNacimiento.getText().toString())){
                        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                        alertBuilder.setMessage("Ingresa los datos solicitados")
                                .setCancelable(true)
                                .setPositiveButton("aceptar", (dialog, which) -> {});
                        inputAlert = alertBuilder.create();
                        inputAlert.show();
                        return;
                    }else {
                        nacimiento = Date.valueOf(etNacimiento.getText().toString());
                        adoptante = new Adoptante(telefono, typeUser, email, latitud, longitud, name, lastname, nacimiento);
                        registro(adoptante);
                    }
                }
                if (rgType.getCheckedRadioButtonId() == rbRescatista.getId()) {
                    rfc = etRFC.getText().toString();
                    typeUser = false;
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(telefono) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rfc)){
                        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                        alertBuilder.setMessage("Ingresa los datos solicitados")
                                .setCancelable(true)
                                .setPositiveButton("aceptar", (dialog, which) -> {});
                        inputAlert = alertBuilder.create();
                        inputAlert.show();
                        return;
                    }else {
                        rescatista = new Rescatista(telefono, typeUser, email, latitud, longitud, name, rfc);
                        registro(rescatista);
                    }
                }
            }
            Toast.makeText(SignupActivity.this, "El usuario es "+typeUser, Toast.LENGTH_SHORT).show();

        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && requestCode == MainActivity.REQUEST_CODE_LOCATION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation();
            }else {
                Toast.makeText(this, "Es un requisito obtener la ubicacion del usuario", Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation () {
        locationListener = new MyLocationListener(getBaseContext());
        locationManager = (LocationManager) getSystemService (LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage("Es necesario activar el GPS para obtener los datos de ubicacion del usuario")
                    .setCancelable(true)
                    .setPositiveButton("ok", (dialog, which) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .setNegativeButton("cancelar", (dialog, which) -> {
                        dialog.cancel();
                        Toast.makeText(getBaseContext(), "Es un requisito obtener la ubicacion del usuario", Toast.LENGTH_LONG).show();
                    });
            gpsAlert = alertBuilder.create();
            gpsAlert.show();
        }
        locationManager.requestLocationUpdates (LocationManager.GPS_PROVIDER,
                10000,
                100,
                locationListener);

        latitud = MyLocationListener.latitude;
        longitud = MyLocationListener.longitude;
    }

    public void registro(Adoptante rAdoptante){

        auth.createUserWithEmailAndPassword(rAdoptante.correo, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            assert user != null;
                            String userid = user.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Usuarios").child(userid);

                            HashMap <String, Object> hashMap = new HashMap<>();
                            hashMap.put("userid", userid);
                            hashMap.put("nombre", rAdoptante.nombre+" "+rAdoptante.apellidos);
                            hashMap.put("telefono", rAdoptante.telefono);
                            hashMap.put("tipo", rAdoptante.tipo);
                            hashMap.put("correo", rAdoptante.correo);
                            hashMap.put("latitud", latitud);
                            hashMap.put("longitud", longitud);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        launchMainActivity(userid, rAdoptante.nombre+" "+rAdoptante.apellidos);
                                }
                            });
                        }else {
                            Toast.makeText(SignupActivity.this, "No se pudo registrar el ususario :(", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void registro(Rescatista Rrescatista){

        auth.createUserWithEmailAndPassword(Rrescatista.correo, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            assert user != null;
                            String userid = user.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Usuarios").child(userid);

                            HashMap <String, Object> hashMap = new HashMap<>();
                            hashMap.put("userid", userid);
                            hashMap.put("nombre", Rrescatista.nombre);
                            hashMap.put("telefono", Rrescatista.telefono);
                            hashMap.put("tipo", Rrescatista.tipo);
                            hashMap.put("correo", Rrescatista.correo);
                            hashMap.put("latitud", latitud);
                            hashMap.put("longitud", longitud);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        launchMainActivity(userid, Rrescatista.nombre);
                                }
                            });
                        }else {
                            Toast.makeText(SignupActivity.this, "No se pudo registrar el ususario :(", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void launchMainActivity(String userid, String username){
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        intent.putExtra("userid", userid);
        intent.putExtra("username", username);
        intent.putExtra("typeUser", typeUser);
        intent.putExtra("activity", "up");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
