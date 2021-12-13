package com.pawsties.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PetsRescatistaFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProfilesAdapter adapter;
    ArrayList<PetModel> profiles;
    ArrayList<PerroModel> perros;
    ArrayList<GatoModel> gatos;
    Activity activity;
    FloatingActionButton addPet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pets_rescatista, container, false);
        addPet = view.findViewById(R.id.add_pet);
        recyclerView = view.findViewById(R.id.rvPetsRescatista);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator (new DefaultItemAnimator());
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        addPet.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddPetActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        profiles = new ArrayList<>();
        perros = new ArrayList<>();
        gatos = new ArrayList<>();

        //ELEMENTO MASCOTA DE PRUEBA (borrar cuando se obtengan de la BD)
        GatoModel profile = new GatoModel("michi", true, Date.valueOf("2020-09-23T00:00:00"), 1, true, 2, false, false, false, 1, "un gato muy bonito xd");
        PerroModel profile2 = new PerroModel("Firulais", true, Date.valueOf("2020-09-23T00:00:00"), 3, true, 2, false, true, false, 1, "soy un perro xd", 0.5);
        GatoModel profile3 = new GatoModel("manchas", false, Date.valueOf("2020-09-23T00:00:00"), 2, true, 2, true, true, false, 1, "soy una gata de tejado");
        perros.add(profile2);
        gatos.add(profile);
        gatos.add(profile3);
        profiles.addAll(perros);
        profiles.addAll(gatos);
        //=========================================================================

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
        adapter = new ProfilesAdapter(getContext(), profiles);

        /**
         * cargar perfiles cercanos de la BD en base a su latitud y longitud
         * */

        recyclerView.setAdapter(adapter);
    }
}
