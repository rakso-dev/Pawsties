package com.pawsties.android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

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
    public static UserModel usuario;
    public static ArrayList<PetViewModel> pets;
    public static ArrayList<PetModel> profiles;
    public ArrayList<PerroModel> perros;
    public ArrayList<GatoModel> gatos;
    JSONObject usuarioJSON;
    public static FirebaseUser firebaseUser;
    public static DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBar = findViewById(R.id.bottom_navigation_bar);
        navigationBar.setOnNavigationItemSelectedListener(navigationListener);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Usuarios").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(UserModel.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profiles = new ArrayList<>();
        perros = new ArrayList<>();
        gatos = new ArrayList<>();

        GatoModel profile = new GatoModel("michi", true, Date.valueOf("2020-09-23"), 1, true, 2, false, false, false, 1, "un gato muy bonito xd");
        PerroModel profile2 = new PerroModel("Firulais", true, Date.valueOf("2020-09-23"), 3, true, 2, false, true, false, 1, "soy un perro xd", 0.5);
        GatoModel profile3 = new GatoModel("manchas", false, Date.valueOf("2020-09-23"), 2, true, 2, true, true, false, 1, "soy una gata");
        GatoModel profile4 = new GatoModel("Michifus", false, Date.valueOf("2021-10-25"), 3, true, 1, true, true, false, 1, "Soy un gato bebe que le gusta estar en casa y recibir carinitos");
        perros.add(profile2);
        gatos.add(profile);
        gatos.add(profile3);
        gatos.add(profile4);
        profiles.addAll(perros);
        profiles.addAll(gatos);

        Intent intent = getIntent();
        if (intent != null){
            typeUser = intent.getBooleanExtra("typeUser", true);
            //activity_parent = intent.getStringExtra("activity");
        }

        /** igualar el objeto json del main con el de signin o signup segun sea el coso */
        if (typeUser){//es adoptante
            getSupportActionBar().setTitle("Perfiles cercanos");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfilesFragment()).commit();
            //if (activity_parent.equals("up"))
                //adoptante = SignupActivity.adoptante;
            //if (activity_parent.equals("in"))
                //adoptante = SigninActivity.adoptante;
        }
        if (!typeUser) {//es rescatista
            getSupportActionBar().setTitle("Mis mascotas");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new PetsRescatistaFragment()).commit();
            //if (activity_parent.equals("up"))
                //rescatista = SignupActivity.rescatista;
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