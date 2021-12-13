package com.pawsties.android;

import java.sql.Date;

public class AdopcionModel {
    public int RAdoptante;
    public int RMascota;
    public Date Fecha;

    public AdopcionModel(int RAdoptante, int RMascota, Date fecha) {
        this.RAdoptante = RAdoptante;
        this.RMascota = RMascota;
        Fecha = fecha;
    }

    public AdopcionModel() {

    }
}
