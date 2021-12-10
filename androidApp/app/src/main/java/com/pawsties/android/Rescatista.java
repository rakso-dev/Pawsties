package com.pawsties.android;

public class Rescatista extends UserModel{
    int id;
    String nombre;
    String rfc;

    public Rescatista(String telefono, boolean tipo, String correo, String contrasena, double latitud, double longitud, String nombre, String rfc) {
        super(telefono, tipo, correo, contrasena, latitud, longitud);
        this.nombre = nombre;
        this.rfc = rfc;
    }
}
