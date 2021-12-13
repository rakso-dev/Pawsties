package com.pawsties.android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class FavsAdapter extends RecyclerView.Adapter<FavsListVH> {
    private Context context;
    ArrayList<PetFavModel> pets;

    FavsAdapter (Context context, ArrayList<PetFavModel> pets) {
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

        final PetFavModel pet = pets.get(position);
        holder.bind(pet);

        holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ChatActivivty.class);
                intent.putExtra("mascota", pet.nMascota);
                intent.putExtra("sender", "joeljp");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return pets.size();
    }
}

