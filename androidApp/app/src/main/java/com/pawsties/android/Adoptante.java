package com.pawsties.android;

import java.sql.Date;

public class Adoptante extends UserModel{
    int id;
    String apellidos;
    Date nacimiento;

    public Adoptante(String telefono, boolean tipo, String correo, double latitud, double longitud, String nombre, String apellidos, Date nacimiento) {
        super(nombre, telefono, tipo, correo, latitud, longitud);
        this.apellidos = apellidos;
        this.nacimiento = nacimiento;
    }
}
