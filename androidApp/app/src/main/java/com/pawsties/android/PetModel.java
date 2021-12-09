package com.pawsties.android;

import android.graphics.Bitmap;

public class PetModel {
    int id;
    String name;
    boolean sexo;
    int edad;//cambiar por un formato de fecha
    int color;
    boolean vacunado;
    int temperamento;
    boolean pelaje;
    boolean esterilizado;
    boolean discapacitado;
    int rescatista;
    String description;

    Bitmap pic;

    public PetModel(String name, boolean sexo, int edad, int color, boolean vacunado, int temperamento, boolean pelaje, boolean esterilizado, boolean discapacitado, int rescatista, String description) {
        this.name = name;
        this.sexo = sexo;
        this.edad = edad;
        this.color = color;
        this.vacunado = vacunado;
        this.temperamento = temperamento;
        this.pelaje = pelaje;
        this.esterilizado = esterilizado;
        this.discapacitado = discapacitado;
        this.rescatista = rescatista;
        this.description = description;
    }
}
