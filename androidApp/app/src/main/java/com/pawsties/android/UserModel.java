package com.pawsties.android;

import android.graphics.Bitmap;

public class UserModel {
    public String userid;
    public String nombre;
    public String telefono;
    public boolean tipo;//true si es adoptante, false si es rescatista
    public String correo;
    public double latitud;
    public double longitud;

    public UserModel(String nombre, String telefono, boolean tipo, String correo, double latitud, double longitud) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.tipo = tipo;
        this.correo = correo;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public UserModel() {

    }
}
