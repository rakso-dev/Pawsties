package com.pawsties.android;

import android.graphics.Bitmap;

import java.sql.Date;

public class GatoModel extends PetModel{

    public GatoModel(String name, boolean sexo, Date edad, int color, boolean vacunado, int temperamento, boolean pelaje, boolean esterilizado, boolean discapacitado, int rescatista, String description) {
        super(name, sexo, edad, color, vacunado, temperamento, pelaje, esterilizado, discapacitado, rescatista, description);
    }
}
