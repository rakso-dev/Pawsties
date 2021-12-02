package com.pawsties.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfilesFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProfilesAdapter adapter;
    ArrayList<Pet> profiles;
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

        //ELEMENTO MASCOTA DE PRUEBA (borrar cuando se obtengan de la BD)
        Pet profile = new Pet();
        Pet profile2 = new Pet();
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
