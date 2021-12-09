package com.pawsties.android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    EditText etName, etLastname, etTelefono, etNacimiento, etEmail, etPassword, etRFC;
    RadioButton rbAdoptante, rbRescatista;
    RadioGroup rgType;
    Button registrarse;
    long telefono;
    String name="", lastname="",  email="", password="", typeUser="", city="", sexo="", rfc="";
    long nacimiento;
    LocationListener locationListener;
    LocationManager locationManager;
    AlertDialog gpsAlert = null;
    double latitud, longitud;
    public static Adoptante adoptante;
    public static Rescatista rescatista;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        getSupportActionBar().setTitle("Registro");

        etName = findViewById(R.id.etNombreEP);
        etLastname = findViewById(R.id.etApellidosEP);
        etTelefono = findViewById(R.id.etTelefonoEP);
        etNacimiento = findViewById(R.id.etFechaEP);
        etEmail = findViewById(R.id.etEmailEP);
        etPassword = findViewById(R.id.etContrasenaEP);
        etRFC = findViewById(R.id.etRFCep);
        rgType = findViewById(R.id.rgTipoUsuario);
        rbAdoptante = findViewById(R.id.rbAdoptante);
        rbRescatista = findViewById(R.id.rbRescatista);
        registrarse = findViewById(R.id.btnOkSignup);

    }

    @Override
    protected void onResume() {
        super.onResume();

        int permision = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        if (permision != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    MainActivity.REQUEST_CODE_LOCATION);
            return;
        }

        registrarse.setOnClickListener(v -> {
            getLocation();
            name = etName.getText().toString();
            telefono = Long.parseLong(etTelefono.getText().toString());//NO DEBE DE ESTAR VACIO ESTE CAMPO
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
            if (rgType.getCheckedRadioButtonId() != -1){
                if (rgType.getCheckedRadioButtonId() == rbAdoptante.getId()) {
                    typeUser = "A";
                    lastname = etLastname.getText().toString();
                    nacimiento = Long.parseLong(etNacimiento.getText().toString());
                    adoptante = new Adoptante(telefono, typeUser, email, password, latitud, longitud, city, name, lastname, nacimiento);
                    registro(adoptante);
                }
                if (rgType.getCheckedRadioButtonId() == rbRescatista.getId()) {
                    rfc = etRFC.getText().toString();
                    typeUser = "R";
                    rescatista = new Rescatista(telefono, typeUser, email, password, latitud, longitud, city, name, rfc);
                    registro(rescatista);
                }
            }else {
                Toast.makeText(SignupActivity.this, "Por favor elige un tipo de usuario", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(SignupActivity.this, "El usuario es "+typeUser, Toast.LENGTH_SHORT).show();

//            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
//                Toast.makeText(SignupActivity.this, "Debes llenar todos los campos antes de continuar!!!", Toast.LENGTH_LONG).show();
//            else if (password.length() < 8)
//                Toast.makeText(SignupActivity.this, "La contraseña debe ser de almenos 8 caracteres", Toast.LENGTH_LONG).show();
//            else
            //registro(email, password);
        });

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
        city = MyLocationListener.city;
    }

    public void registro(Adoptante adoptante){
        /**aqui se va a hacer el registro del adoptante en la base de Azure*/

        //si todo es correcto, esta funcion va a lanzar el main activity
        launchMainActivity();
    }

    public void registro(Rescatista rescatista){
        /**aqui se va a hacer el registro del rescatista en la base de Azure*/

        //si todo es correcto, esta funcion va a lanzar el main activity
        launchMainActivity();
    }

    public void launchMainActivity(){
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        intent.putExtra("typeUser", typeUser);
        intent.putExtra("activity", "up");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
