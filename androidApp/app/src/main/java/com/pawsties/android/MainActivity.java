package com.pawsties.android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_LOCATION = 1001;
    public static final boolean ADOPTANTE = true;
    public static final boolean RESCATISTA = false;
    BottomNavigationView navigationBar;
    LocationListener locationListener;
    LocationManager locationManager;
    AlertDialog gpsAlert = null;
    public static boolean typeUser;
    String activity_parent;
    Adoptante adoptante;
    Rescatista rescatista;
    JSONObject usuarioJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBar = findViewById(R.id.bottom_navigation_bar);
        navigationBar.setOnNavigationItemSelectedListener(navigationListener);

        Intent intent = getIntent();
        typeUser = intent.getBooleanExtra("typeUser", true);
        activity_parent = intent.getStringExtra("activity");

        /** igualar el objeto json del main con el de signin o signup segun sea el coso */
        if (typeUser){//es adoptante
            getSupportActionBar().setTitle("Perfiles cercanos");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfilesFragment()).commit();
            if (activity_parent.equals("up"))
                adoptante = SignupActivity.adoptante;
            //if (activity_parent.equals("in"))
                //adoptante = SigninActivity.adoptante;
        }
        if (!typeUser) {//es rescatista
            getSupportActionBar().setTitle("Mis mascotas");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new PetsRescatistaFragment()).commit();
            if (activity_parent.equals("up"))
                rescatista = SignupActivity.rescatista;
            //if (activity_parent.equals("in"))
                //rescatista = SigninActivity.rescatista;
        }

        //btnChat = findViewById(R.id.btnChat);
        //ProfilesFragment.btnChat.setOnClickListener (view -> {
          //  Fragment chat = new ChatFragment();
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, chat).commit();
//            Intent intent = new Intent (getBaseContext (), ChatFragment.class);
////            intent.putExtra(NAME, edtName.getText().toString());
////            intent.putExtra(LASTNAME, edtLastname.getText().toString());
////            intent.putExtra(AGE, edtAge.getText().toString());
////            intent.putExtra(ADDRESS, edtAddress.getText().toString());
//            startActivity (intent);
        //});
    }

    @Override
    protected void onResume() {
        super.onResume();

        //pedir permiso de ubicacion
        int permision = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        if (permision != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_LOCATION);
            return;
        }

        loadProfiles();//los perfiles se estan obteniendo en el fragment, por lo que tal vez no sea necesario este metodo pero si la obtencion de la ubicacion
    }

    private void loadProfiles() {
        getLocation();
    }


    @SuppressLint("MissingPermission")
    private void getLocation () {
        locationListener = new MyLocationListener(getBaseContext());
        locationManager = (LocationManager) getSystemService (LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage("Es necesario activar el GPS para ciertas funciones")
                    .setCancelable(true)
                    .setPositiveButton("Ok", (dialog, which) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .setNegativeButton("cancelar", (dialog, which) -> {
                        dialog.cancel();
                        Toast.makeText(getBaseContext(), "Se necesita acceder a la ubicacion para una mejor experiencia", Toast.LENGTH_LONG).show();
                    });
            gpsAlert = alertBuilder.create();
            gpsAlert.show();
        }
        locationManager.requestLocationUpdates (LocationManager.GPS_PROVIDER,
                10000,
                100,
                locationListener);
    }

    private OnNavigationItemSelectedListener navigationListener = item -> {
        Fragment selectFragment = null;

        switch (item.getItemId()){
            case R.id.nav_profiles:
                //getSupportFragmentManager().beginTransaction().remove(selectFragment).commit();
                if (typeUser){
                    selectFragment = new ProfilesFragment();
                    getSupportActionBar().setTitle("Perfiles cercanos");
                }
                if (!typeUser){
                    selectFragment = new PetsRescatistaFragment();
                    getSupportActionBar().setTitle("Mis mascotas");
                }
                break;
            case R.id.nav_favorities:
                selectFragment = new FavoritiesFragment();
                getSupportActionBar().setTitle("Favoritos y chats");
                break;
            case R.id.nav_account:
                selectFragment = new AccountFragment();
                getSupportActionBar().setTitle("Cuenta y ajustes");
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectFragment).commit();
        return true;
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //permiso de locacion
        if (grantResults.length > 0 && requestCode == REQUEST_CODE_LOCATION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                loadProfiles();
            }else {
                Toast.makeText(this, "Se necesita acceder a la ubicacion para mostrar perfiles cercanos", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    locationManager.removeUpdates(locationListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (gpsAlert != null)
            gpsAlert.dismiss();
    }
}