package com.pawsties.android;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Date;
import java.util.Calendar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {
    EditText etName, etLastname, etTelefono, etNacimiento, etEmail, etPassword, etRFC;
    FloatingActionButton btnDone;
    ImageButton setDate;
    String telefono;
    String name="", lastname="",  email="", password="", rfc="";
    Date nacimiento;
    //tal vez no sea necesario tener los objetos heredados de UserModel aqui xd
    Adoptante adoptante;
    Rescatista rescatista;
    JSONObject usuario = new JSONObject();
    int dia, mes, anho;


    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setTitle("Editar perfil");

        etName = findViewById(R.id.etNombreEP);
        etLastname = findViewById(R.id.etApellidosEP);
        etTelefono = findViewById(R.id.etTelefonoEP);
        etNacimiento = findViewById(R.id.etFechaEP);
        etEmail = findViewById(R.id.etEmailEP);
        etPassword = findViewById(R.id.etContrasenaEP);
        etRFC = findViewById(R.id.etRFCep);
        setDate = findViewById(R.id.btnSetDate);
        btnDone = findViewById(R.id.fbDoneEP);

        setDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anho = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, (view, year, month, dayOfMonth) ->
                    etNacimiento.setText(year+"-"+(month+1)+"-"+dayOfMonth), anho, mes, dia);

            datePickerDialog.show();
        });

        /** RECIBIR EL OBJETO JSON DEL USUARIO A EDITAR */
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnDone.setOnClickListener(v -> {
            name = etName.getText().toString();
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
            telefono = etTelefono.getText().toString();
            if (MainActivity.typeUser.equals("A")){
                lastname = etLastname.getText().toString();
                nacimiento = Date.valueOf(etNacimiento.getText().toString());
                adoptante = new Adoptante(telefono, "A", email, password, 0.2, 0.2, name, lastname, nacimiento);

                try {
                    usuario.accumulate("image", null);
                    usuario.accumulate("mail", email);
                    usuario.accumulate("telephone", telefono);
                    //usuario.accumulate("adoptanteid", null);
                    usuario.accumulate("nombre", name);
                    usuario.accumulate("apellidos", lastname);
                    usuario.accumulate("fecha_de_nac", nacimiento);
                    updateAdoptante(usuario);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (MainActivity.typeUser.equals("R")){
                rfc = etRFC.getText().toString();
                rescatista = new Rescatista(telefono, "R", email, password, 0.2, 0.2, name, rfc);
                try {
                    usuario.accumulate("image", null);
                    usuario.accumulate("mail", email);
                    usuario.accumulate("telephone", telefono);
                    usuario.accumulate("nombre_ent", name);
                    usuario.accumulate("rfc", rfc);
                    updateRescatista(usuario);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(EditProfileActivity.this, "Usuario 'actualizado'", Toast.LENGTH_LONG).show();
        });
    }

    public void updateAdoptante(JSONObject adoptanteJSON){
        /**ESTA FUNCION SE VA A COMUNICAR CON LA SENTENCIA HTTP
         * QUE ACTUALIZA EN LA API */
    }

    public void updateRescatista(JSONObject rescatistaJSON){
        /**ESTA FUNCION SE VA A COMUNICAR CON LA SENTENCIA HTTP
         * QUE ACTUALIZA EN LA API */
    }
}
