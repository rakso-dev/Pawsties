package com.example.pawsties;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = getActivity();
        if (activity == null)
            return;

        loadPets();;
    }

    private void loadPets(){
        recyclerView = activity.findViewById (R.id.rvFavorities);
        recyclerView.setLayoutManager (new LinearLayoutManager(activity));
        recyclerView.addItemDecoration (new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator (new DefaultItemAnimator());

//        FavsAdapter adapter = new FavsAdapter (activity);
//        adapter.setOnPlanetSelectedListener (listener);
//
//        recyclerView.setAdapter (adapter);
    }

}
