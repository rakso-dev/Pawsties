package com.pawsties.android;

import android.graphics.Bitmap;

import java.sql.Date;

public class PetModel {
    public int Petid;
    public boolean Sexo;
    public Date Edad;
    public int Rcolor;
    public boolean Vaxxed;
    public int Rtemper;
    public boolean Pelaje;
    public boolean Esterilizado;
    public boolean Discapacitado;
    public int Rrescatista;
    public String Nombre;
    public String Descripcion;

    public PetModel(String name, boolean sexo, Date edad, int color, boolean vacunado, int temperamento, boolean pelaje, boolean esterilizado, boolean discapacitado, int rescatista, String description) {
        this.Nombre = name;
        this.Sexo = sexo;
        this.Edad = edad;
        this.Rcolor = color;
        this.Vaxxed = vacunado;
        this.Rtemper = temperamento;
        this.Pelaje = pelaje;
        this.Esterilizado = esterilizado;
        this.Discapacitado = discapacitado;
        this.Rrescatista = rescatista;
        this.Descripcion = description;
    }

    public PetModel(){

    }
}
