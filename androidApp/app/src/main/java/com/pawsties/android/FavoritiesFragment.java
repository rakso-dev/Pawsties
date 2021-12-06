package com.pawsties.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritiesFragment extends Fragment {
    private RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DividerItemDecoration dividerItemDecoration;
    private FavsAdapter adapter;
    public static ArrayList<PetModel> pets = new ArrayList<>();
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
        recyclerView.setLayoutManager(linearLayoutManager);
        dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        loadPets();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = getActivity();
        if (activity == null)
            return;
    }

    private void loadPets(){

        adapter = new FavsAdapter (getContext(), pets);

        /**
         * aqui se tienen que cargar los datos de los perfiles FAVORITOS de la bd en azure
         * */

        recyclerView.setAdapter (adapter);
    }

}
