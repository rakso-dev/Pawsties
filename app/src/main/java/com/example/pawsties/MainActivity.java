package com.example.pawsties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationBar = findViewById(R.id.bottom_navigation_bar);
        navigationBar.setOnNavigationItemSelectedListener(navigationListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfilesFragment()).commit();
    }

    private OnNavigationItemSelectedListener navigationListener = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectFragment = null;

            switch (item.getItemId()){
                case R.id.nav_profiles:
                    selectFragment = new ProfilesFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectFragment).commit();
            return true;
        }
    };
}