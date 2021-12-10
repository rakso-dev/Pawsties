package com.pawsties.android;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Date;
import java.util.Calendar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_CAMERA = 1003;
    public static final int REQUEST_CODE_READ_STORAGE = 1004;
    int permission;
    Intent intent;
    EditText etName, etLastname, etTelefono, etNacimiento, etEmail, etPassword, etRFC;
    FloatingActionButton btnDone;
    ImageButton setDate, addPhoto, addImage;
    ImageView image;
    String telefono;
    String name="", lastname="",  email="", password="", rfc="";
    Date nacimiento;
    AlertDialog inputAlert = null;
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
        setDate = findViewById(R.id.btnSetDateEP);
        addPhoto = findViewById(R.id.ibAddPhotoEP);
        addImage = findViewById(R.id.ibAddImageEP);
        image = findViewById(R.id.imageViewEP);
        btnDone = findViewById(R.id.fbDoneEP);

        /** RECIBIR EL OBJETO JSON DEL USUARIO A EDITAR */
        //los datos recibidos del json seran establecidos en los edittexts
    }

    @Override
    protected void onResume() {
        super.onResume();

        addPhoto.setOnClickListener(v -> {
            permission = checkSelfPermission(Manifest.permission.CAMERA);
            if (permission != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new  String[] {Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA);
                return;
            }else {
                takePhoto();
            }
        });

        addImage.setOnClickListener(v -> {
            permission = checkSelfPermission((Manifest.permission.READ_EXTERNAL_STORAGE));
            if (permission != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_READ_STORAGE);
                return;
            }else {
                choseImage();
            }
        });

        setDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anho = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, (view, year, month, dayOfMonth) ->
                    etNacimiento.setText(year+"-"+(month+1)+"-"+dayOfMonth), anho, mes, dia);

            datePickerDialog.show();
        });

        btnDone.setOnClickListener(v -> {
            name = etName.getText().toString();
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
            telefono = etTelefono.getText().toString();
            if (MainActivity.typeUser){
                lastname = etLastname.getText().toString();

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
                    adoptante = new Adoptante(telefono, true, email, password, 0.2, 0.2, name, lastname, nacimiento);
                    try {
                        usuario.accumulate("image", null);
                        usuario.accumulate("mail", email);
                        usuario.accumulate("password", password);
                        usuario.accumulate("telephone", telefono);
                        usuario.accumulate("nombre", name);
                        usuario.accumulate("apellidos", lastname);
                        usuario.accumulate("fecha_de_nac", nacimiento);
                        updateAdoptante(usuario);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!MainActivity.typeUser){
                rfc = etRFC.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(telefono) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rfc)) {
                    final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setMessage("Ingresa los datos solicitados")
                            .setCancelable(true)
                            .setPositiveButton("aceptar", (dialog, which) -> {
                            });
                    inputAlert = alertBuilder.create();
                    inputAlert.show();
                    return;
                }else {
                    rescatista = new Rescatista(telefono, false, email, password, 0.2, 0.2, name, rfc);
                    try {
                        usuario.accumulate("image", null);
                        usuario.accumulate("mail", email);
                        usuario.accumulate("password", password);
                        usuario.accumulate("telephone", telefono);
                        usuario.accumulate("nombre_ent", name);
                        usuario.accumulate("rfc", rfc);
                        updateRescatista(usuario);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (image.getImageAlpha() != R.drawable.ic_baseline_image_24)
                AccountFragment.picProfile.setImageAlpha(image.getImageAlpha());
            AccountFragment.username.setText(name);
            Toast.makeText(EditProfileActivity.this, "Usuario 'actualizado'", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0){
            if (requestCode == REQUEST_CODE_CAMERA){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    takePhoto();
                else
                    Toast.makeText(EditProfileActivity.this, "Se necesita usar la camara para tomar una foto instantanea", Toast.LENGTH_LONG).show();

            }
            if (requestCode == REQUEST_CODE_READ_STORAGE){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    choseImage();
                else
                    Toast.makeText(EditProfileActivity.this, "Se necesita leer el almacenamiento para elegir una imagen existente", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imgBitmap);
        }

        if (requestCode == REQUEST_CODE_READ_STORAGE && resultCode == RESULT_OK){
            Uri img = data.getData();
            image.setImageURI(img);
        }
    }

    public void takePhoto(){
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        }
    }

    public void choseImage(){
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Continuar con..."), REQUEST_CODE_READ_STORAGE);
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
