package com.pawsties.android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class FavsAdapter extends RecyclerView.Adapter<FavsListVH> {
    private Context context;
    private Pet pet = new Pet();
    private ArrayList<Pet> pets;

    FavsAdapter (Context context, ArrayList<Pet> pets) {
        this.context = context;
        this.pets = pets;
    }

    @NonNull
    @Override
    public FavsListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_favorite, parent, false);
        return new FavsListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavsListVH holder, int position) {
        Log.i("VH_item", "OnBindVH");
        pet = pets.get(position);
        holder.bind(pet.pic, pet.name);
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }
}

