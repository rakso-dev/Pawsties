package com.pawsties.android;

public class Rescatista extends UserModel{
    int id;
    String nombre;
    String rfc;

    public Rescatista(long telefono, String tipo, String correo, String contrasena, double latitud, double longitud, String ciudad, String nombre, String rfc) {
        super(telefono, tipo, correo, contrasena, latitud, longitud, ciudad);
        this.nombre = nombre;
        this.rfc = rfc;
    }
}
