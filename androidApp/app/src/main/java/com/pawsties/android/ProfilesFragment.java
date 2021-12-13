package com.pawsties.android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfilesFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProfilesAdapter adapter;
    Activity activity;
    public Retrofit retrofit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_favorities, container, false);
        recyclerView = view.findViewById(R.id.rvFavorities);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager (new LinearLayoutManager(activity));
        recyclerView.setItemAnimator (new DefaultItemAnimator());
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //getById(3);
        loadProfiles();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = getActivity();
        if (activity == null)
            return;
    }

    private void loadProfiles(){

        adapter = new ProfilesAdapter(getContext(), MainActivity.profiles);

        recyclerView.setAdapter(adapter);
    }

    public void getMascotas(){
        retrofit = new Retrofit.Builder().baseUrl(getString(R.string.url_base_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceAPI service = retrofit.create(InterfaceAPI.class);
        Call<PetsRequestAPIModel> mascotas = service.loadProfiles();

        mascotas.enqueue(new Callback<PetsRequestAPIModel>() {
            @Override
            public void onResponse(Call<PetsRequestAPIModel> call, Response<PetsRequestAPIModel> response) {
                if (response.isSuccessful()){
                    PetsRequestAPIModel petsRequest = response.body();
                    ArrayList<PetModel> listaMascotas = petsRequest.mascotas;

                    Log.i("list size", ""+listaMascotas.size());
                    for (int i = 0; i < listaMascotas.size(); i++){
                        PetModel p = listaMascotas.get(i);
                        Log.i("RECIBIDA DE LA API", "Mascota: "+p.Nombre);
                    }
                }else {
                    Toast.makeText(activity, "ERROR: "+response.errorBody(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PetsRequestAPIModel> call, Throwable t) {
                Toast.makeText(activity, "ERROR: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //Call<ArrayList<JSONObject>> call = InterfaceAPI.loadProfiles();
    }

    public void getByDistance(double latitude, double longitude){
        retrofit = new Retrofit.Builder().baseUrl(getString(R.string.url_base_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceAPI service = retrofit.create(InterfaceAPI.class);

        JSONObject distance = new JSONObject();
        try {
            distance.accumulate("longitude", longitude);
            distance.accumulate("latitude", latitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<PetModel> call = service.getByDistance(distance);

        call.enqueue(new Callback<PetModel>() {
            @Override
            public void onResponse(Call<PetModel> call, Response<PetModel> response) {
                try {
                    if (response.isSuccessful()){
                        PetModel p = response.body();
                        //guardar/mostrar los datos de p
                    }else {
                        Toast.makeText(activity, "No existe la mascota", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PetModel> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getById(int petId){
        retrofit = new Retrofit.Builder().baseUrl(getString(R.string.url_base_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceAPI service = retrofit.create(InterfaceAPI.class);

        Call<PetModel> call = service.getById(petId);

        call.enqueue(new Callback<PetModel>() {
            @Override
            public void onResponse(Call<PetModel> call, Response<PetModel> response) {
                try {
                    if (response.isSuccessful()){
                        //PetModel p = response.body();
                        Log.i("RECIBIDA DE LA API", "ID Mascota: "+response.body().Petid);
                    }else {
                        Toast.makeText(activity, "No existe la mascota", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PetModel> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
