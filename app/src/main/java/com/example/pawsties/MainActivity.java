package com.example.pawsties;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_LOCATION = 1001;
    BottomNavigationView navigationBar;

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
        int permision = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permision != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
            return;
        }
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

}