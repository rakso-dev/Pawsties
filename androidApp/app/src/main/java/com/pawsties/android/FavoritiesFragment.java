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
    RecyclerView recyclerView;
    Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_favorities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = getActivity();
        if (activity == null)
            return;

        loadPets();
    }

    private void loadPets(){
        ArrayList<Pet> pets = new ArrayList<>();
        //en esta funcion darle valores al array list en base a la informacion obtenida de una BD

        recyclerView = activity.findViewById (R.id.rvFavorities);
        recyclerView.setLayoutManager (new LinearLayoutManager(activity));
        recyclerView.addItemDecoration (new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator (new DefaultItemAnimator());

        FavsAdapter adapter = new FavsAdapter (this.getContext(), pets);
        //adapter.setOnPlanetSelectedListener (listener);

        recyclerView.setAdapter (adapter);
    }

}
