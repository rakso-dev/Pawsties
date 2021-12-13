package com.pawsties.android;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MascotasInterfaceAPI {

    //MASCOTAS
    @GET("mascotas")
    Call<PetsRequestAPIModel> loadProfiles();

    @GET("mascotas/get/{distance}")
    Call<PetModel> getByDistance(@Path("distance") JSONObject distance);

    @GET("mascotas/{petID}")
    public Call<PetModel> getById(@Path("petID") int petId);
}
