package com.pawsties.android;

import android.graphics.Bitmap;

public class UserModel {
    String nombre;
    String apellidos;
    String fNacimiento;
    int telefono;
    String tipo;
    String correo;
    String contrasena;
    Bitmap foto;

    public UserModel(String nombre, String apellidos, String fNacimiento, int telefono, String tipo, String correo, String contrasena, Bitmap foto) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fNacimiento = fNacimiento;
        this.telefono = telefono;
        this.tipo = tipo;
        this.correo = correo;
        this.contrasena = contrasena;
        this.foto = foto;
    }
}
