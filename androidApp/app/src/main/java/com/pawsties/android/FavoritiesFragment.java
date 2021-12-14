package com.pawsties.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoritiesFragment extends Fragment {
    private RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DividerItemDecoration dividerItemDecoration;
    private FavsAdapter adapter;
    ArrayList<PetFavModel> pets;
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

        loadPets(MainActivity.firebaseUser.getUid());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = getActivity();
        if (activity == null)
            return;
    }

    private void loadPets(String userId){
        pets = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Favoritos");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pets.clear();
                PetFavModel pet;
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    //Log.d("xdxdxd", snapshot1.getKey()+" - "+snapshot1.getValue().toString());
                    pet = snapshot1.getValue(PetFavModel.class);
                    if (pet.usuario.equals(userId))
                        pets.add(pet);

                }
                adapter = new FavsAdapter(getContext(), pets);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
