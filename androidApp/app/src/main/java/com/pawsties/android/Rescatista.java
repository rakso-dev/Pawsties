package com.pawsties.android;

public class Rescatista extends UserModel{
    int id;
    String rfc;

    public Rescatista(String telefono, boolean tipo, String correo, double latitud, double longitud, String nombre, String rfc) {
        super(nombre, telefono, tipo, correo, latitud, longitud);
        this.rfc = rfc;
    }
}
