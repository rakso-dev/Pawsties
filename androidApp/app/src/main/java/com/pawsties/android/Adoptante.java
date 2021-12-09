package com.pawsties.android;

import java.sql.Date;

public class Adoptante extends UserModel{
    int id;
    String nombre;
    String apellidos;
    Date nacimiento;

    public Adoptante(String telefono, String tipo, String correo, String contrasena, double latitud, double longitud, String nombre, String apellidos, Date nacimiento) {
        super(telefono, tipo, correo, contrasena, latitud, longitud);
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nacimiento = nacimiento;
    }
}
