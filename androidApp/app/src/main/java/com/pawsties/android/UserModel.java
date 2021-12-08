package com.pawsties.android;

import android.graphics.Bitmap;

public class UserModel {
    long telefono;
    String tipo;
    String correo;
    String contrasena;
    Bitmap foto;
    double latitud;
    double longitud;
    String ciudad;

    public UserModel(long telefono, String tipo, String correo, String contrasena, double latitud, double longitud, String ciudad) {
        this.telefono = telefono;
        this.tipo = tipo;
        this.correo = correo;
        this.contrasena = contrasena;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ciudad = ciudad;
    }
}
