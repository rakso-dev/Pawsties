package com.pawsties.android;

import java.sql.Date;

public class PetViewModel {
    public String rescatista;
    public String nombre;
    public String descripcion;
    public String nacimiento;
    public boolean tipo; //true si perro, false si gato

    public PetViewModel(String nombre, String descripcion, String nacimiento, boolean tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nacimiento = nacimiento;
        this.tipo = tipo;
    }

    public PetViewModel() {

    }
}
