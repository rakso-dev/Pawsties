package com.pawsties.android;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InterfaceAPI {

    //MASCOTAS
    @GET("mascotas")
    Call<PetsRequestAPIModel> loadProfiles();

    @GET("mascotas/get/{distance}")
    Call<PetModel> getByDistance(@Path("distance") JSONObject distance);

    @GET("mascotas/{petID}")
    public Call<PetModel> getById(@Path("petID") int petId);

    //ADOPTANTE
    @GET("adoptante/{adoptanteid}")
    public Call<Adoptante> adoptanteById(@Path("adoptanteid") int adoptanteid);

    //RESCATISTA
    @GET("rescatista/{id}")
    Call<Rescatista> rescatistaById(@Path("id") int id);

    @PUT("rescatista/{id}")
    Call<Rescatista> actualizarRescatista(@Path("id") JSONObject r, int id);

    @GET("adopcion/{id}")
    Call<AdopcionModel> modelById(@Path("id") int id);

    @POST("adopcion")
    Call<AdopcionModel> adopcion(@Path("adopcion") JSONObject adoption);
}
