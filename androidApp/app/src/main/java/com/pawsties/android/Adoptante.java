package com.pawsties.android;

import java.util.Date;

public class Adoptante extends UserModel{
    int id;
    String nombre;
    String apellidos;
    long nacimiento;
    String sexo;

    public Adoptante(long telefono, String tipo, String correo, String contrasena, double latitud, double longitud, String ciudad, String nombre, String apellidos, long nacimiento, String sexo) {
        super(telefono, tipo, correo, contrasena, latitud, longitud, ciudad);
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nacimiento = nacimiento;
        this.sexo = sexo;
    }
}
