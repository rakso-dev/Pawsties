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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_LOCATION = 1001;
    BottomNavigationView navigationBar;
    ImageButton btnChat;
    LocationListener locationListener;
    LocationManager locationManager;
    AlertDialog gpsAlert = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Perfiles cercanos");

        navigationBar = findViewById(R.id.bottom_navigation_bar);
        navigationBar.setOnNavigationItemSelectedListener(navigationListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfilesFragment()).commit();

        btnChat = findViewById(R.id.btnChat);
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

        loadProfiles();
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
            alertBuilder.setMessage("Debe activar el GPS para obtener los perfiles cercanos")
                    .setCancelable(true)
                    .setPositiveButton("Ok", (dialog, which) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .setNegativeButton("No", (dialog, which) -> {
                        dialog.cancel();
                        Toast.makeText(getBaseContext(), "Se necesita acceder a la ubicacion para poder mostrar los perfiles", Toast.LENGTH_LONG).show();
                    });
            gpsAlert = alertBuilder.create();
            gpsAlert.show();
        }
        locationManager.requestLocationUpdates (LocationManager.GPS_PROVIDER,
                5000,
                10,
                locationListener);
    }

    private OnNavigationItemSelectedListener navigationListener = item -> {
        Fragment selectFragment = null;

        switch (item.getItemId()){
            case R.id.nav_profiles:
                //getSupportFragmentManager().beginTransaction().remove(selectFragment).commit();
                selectFragment = new ProfilesFragment();
                getSupportActionBar().setTitle("Perfiles cercanos");
                break;
            case R.id.nav_favorities:
                selectFragment = new FavoritiesFragment(getBaseContext());
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