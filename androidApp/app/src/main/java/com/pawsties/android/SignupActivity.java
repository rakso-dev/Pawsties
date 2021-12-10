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

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.Calendar;

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
        setDate = findViewById(R.id.btnSetDateSU);
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
                        adoptante = new Adoptante(telefono, typeUser, email, password, latitud, longitud, name, lastname, nacimiento);
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
                        rescatista = new Rescatista(telefono, typeUser, email, password, latitud, longitud, name, rfc);
                        registro(rescatista);
                    }
                }
            }
            Toast.makeText(SignupActivity.this, "El usuario es "+typeUser, Toast.LENGTH_SHORT).show();

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
    }

    public void registro(Adoptante adoptante){
        /**aqui se va a hacer el registro del adoptante en la base de Azure*/

        try {
            usuarioJSON.accumulate("image", null);
            usuarioJSON.accumulate("mail", adoptante.correo);
            usuarioJSON.accumulate("password", adoptante.contrasena);
            usuarioJSON.accumulate("telephone", adoptante.telefono);
            usuarioJSON.accumulate("nombre", adoptante.nombre);
            usuarioJSON.accumulate("apellidos", adoptante.apellidos);
            usuarioJSON.accumulate("fecha_de_nac", adoptante.nacimiento);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /** hacer el registro del objeto JSON*/
        //si todo es correcto, esta funcion va a lanzar el main activity
        launchMainActivity();
    }

    public void registro(Rescatista rescatista){
        /**aqui se va a hacer el registro del rescatista en la base de Azure*/

        try {
            usuarioJSON.accumulate("image", null);
            usuarioJSON.accumulate("mail", rescatista.correo);
            usuarioJSON.accumulate("password", rescatista.contrasena);
            usuarioJSON.accumulate("telephone", rescatista.telefono);
            usuarioJSON.accumulate("nombre_ent", rescatista.nombre);
            usuarioJSON.accumulate("rfc", rescatista.rfc);
            usuarioJSON.accumulate("latitude", rescatista.latitud);
            usuarioJSON.accumulate("longitude", rescatista.longitud);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /** hacer el registro del objeto JSON */
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
