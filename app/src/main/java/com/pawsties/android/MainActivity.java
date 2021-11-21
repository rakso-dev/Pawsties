package com.pawsties.android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    double latitude, longitude;
    LocationListener locationListener;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBar = findViewById(R.id.bottom_navigation_bar);
        navigationBar.setOnNavigationItemSelectedListener(navigationListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfilesFragment()).commit();
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
        //code requiere de localizacion GPS
    }


    @SuppressLint("MissingPermission")
    private void getLocation () {
        locationListener = new MyLocationListener ();
        locationManager = (LocationManager) getSystemService (LOCATION_SERVICE);
        locationManager.requestLocationUpdates (LocationManager.GPS_PROVIDER,
                5000,
                10,
                locationListener);
    }

    private OnNavigationItemSelectedListener navigationListener = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectFragment = null;

            switch (item.getItemId()){
                case R.id.nav_profiles:
                    //getSupportFragmentManager().beginTransaction().remove(selectFragment).commit();
                    selectFragment = new ProfilesFragment();
                    break;
                case R.id.nav_favorities:
                    selectFragment = new FavoritiesFragment();
                    break;
                case R.id.nav_account:
                    selectFragment = new AccountFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectFragment).commit();
            return true;
        }
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

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            //Toast.makeText (getBaseContext (), "Latitud: " + latitude + "\nLongitud: " + longitude, Toast.LENGTH_LONG).show ();

            Geocoder geocoder = new Geocoder (getBaseContext(), Locale.getDefault ());
            List<Address> addresses;
            String city = "";

            try {
                addresses = geocoder.getFromLocation (location.getLatitude(), location.getLongitude(), 1);
                if (addresses.size () > 0) {
                    //Log.i ("GEO", addresses.get (0).getLocality ());
                    city = addresses.get (0).getLocality();
                    Toast.makeText(getBaseContext(), "Mostrando perfiles cerca de: "+city, Toast.LENGTH_LONG).show();
                }
            } catch (IOException ex) {
                ex.printStackTrace ();
            }

            Log.i ("GEO", "City: " + city);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }
}