package com.pawsties.android;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;

public class AddPetActivity extends AppCompatActivity {
    EditText etNombre, etDescripcion, etFecha;
    ImageButton btnFecha;
    RadioButton rbPerro, rbGato;
    RadioGroup rgTipoPet;
    FloatingActionButton crear;
    String nombre, descripcion;
    String fecha;
    boolean tipo;
    int dia, mes, anho;
    AlertDialog alert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        getSupportActionBar().setTitle("Nueva mascota");

        etNombre = findViewById(R.id.etNombrePet);
        etDescripcion = findViewById(R.id.etDescripcionPet);
        etFecha = findViewById(R.id.etFechaPet);
        rbPerro = findViewById(R.id.rbPerro);
        rbGato = findViewById(R.id.rbGato);
        rgTipoPet = findViewById(R.id.rgTipoPet);
        btnFecha = findViewById(R.id.btnDatePet);
        crear = findViewById(R.id.btnDonePet);
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnFecha.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anho = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddPetActivity.this, (view, year, month, dayOfMonth) ->
                    etFecha.setText(year+"-"+(month+1)+"-"+dayOfMonth), anho, mes, dia);

            datePickerDialog.show();
        });

        crear.setOnClickListener(v -> {
            nombre = etNombre.getText().toString();
            descripcion = etDescripcion.getText().toString();

            if (rgTipoPet.getCheckedRadioButtonId() == -1){
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setMessage("Elige un tipo de mascota valido")
                        .setCancelable(true)
                        .setPositiveButton("aceptar", (dialog, which) -> {});
                alert = alertBuilder.create();
                alert.show();
                return;
            }else {
                if (rgTipoPet.getCheckedRadioButtonId() == rbPerro.getId()) {
                    tipo = true;
                }
                if (rgTipoPet.getCheckedRadioButtonId() == rbGato.getId()) {
                    tipo = false;
                }
                if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(descripcion) || TextUtils.isEmpty(etFecha.getText().toString())){
                    final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setMessage("Ingresa los datos solicitados")
                            .setCancelable(true)
                            .setPositiveButton("aceptar", (dialog, which) -> {});
                    alert = alertBuilder.create();
                    alert.show();
                    return;
                }else {
                    fecha = etFecha.getText().toString();
                    PetViewModel petViewModel = new PetViewModel(nombre, descripcion, fecha, tipo);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("rescatista", MainActivity.firebaseUser.getUid());
                    hashMap.put("nombre", nombre);
                    hashMap.put("descripcion", descripcion);
                    hashMap.put("nacimiento", fecha);
                    hashMap.put("tipo", tipo);

                    reference.child("Mascotas").push().setValue(hashMap);

                    Toast.makeText(AddPetActivity.this, "Mascota Creada!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
