package com.pawsties.android;

import android.graphics.Bitmap;

public class UserModel {
    String telefono;
    String tipo;
    String correo;
    String contrasena;
    Bitmap foto;
    double latitud;
    double longitud;

    public UserModel(String telefono, String tipo, String correo, String contrasena, double latitud, double longitud) {
        this.telefono = telefono;
        this.tipo = tipo;
        this.correo = correo;
        this.contrasena = contrasena;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
