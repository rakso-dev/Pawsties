package com.pawsties.android;

public class Rescatista extends UserModel{
    int id;
    String entidad;
    String rfc;

    public Rescatista(long telefono, String tipo, String correo, String contrasena, double latitud, double longitud, String ciudad, String entidad, String rfc) {
        super(telefono, tipo, correo, contrasena, latitud, longitud, ciudad);
        this.entidad = entidad;
        this.rfc = rfc;
    }
}
