package com.pawsties.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ProfilesFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProfilesAdapter adapter;
    ArrayList<PetModel> profiles;
    ArrayList<PerroModel> perros;
    ArrayList<GatoModel> gatos;
    Activity activity;

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

        profiles = new ArrayList<>();
        perros = new ArrayList<>();
        gatos = new ArrayList<>();

        //ELEMENTO MASCOTA DE PRUEBA (borrar cuando se obtengan de la BD)
        GatoModel profile = new GatoModel("michi", true, 2, 1, true, 2, false, false, false, 1, "un gato muy bonito xd");
        PerroModel profile2 = new PerroModel("Firulais", true, 1, 3, true, 2, false, true, false, 1, "soy un perro xd", 0.5);
        GatoModel profile3 = new GatoModel("manchas", false, 2, 2, true, 2, true, true, false, 1, "soy una gata de tejado");
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
