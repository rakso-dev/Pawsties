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

import java.util.ArrayList;

public class PetsRescatistaFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProfilesAdapter adapter;
    ArrayList<PetModel> profiles;
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

        //ELEMENTO MASCOTA DE PRUEBA (borrar cuando se obtengan de la BD)
        PetModel profile = new PetModel();
        PetModel profile2 = new PetModel();
        profile.name = "michi";
        profile.description = "gato xd";
        profile.pic = null;
        profiles.add(profile);
        profile2.name = "firulais";
        profile2.description = "perrito";
        profile2.pic = null;
        profiles.add(profile2);
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
