package com.pawsties.android;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class FavsAdapter extends RecyclerView.Adapter<FavsListVH> {
    private Context context, baseContext;
    private ArrayList<Pet> pets;

    FavsAdapter (Context mcontext, Context baseContext, ArrayList<Pet> pets) {
        this.context = mcontext;
        this.baseContext = baseContext;
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

        final Pet pet = pets.get(position);
        holder.bind(pet.pic, pet.name);

        //HAY PROBLEMITAS AL LANZAR EL CHAT ACTIVITY!!!
        //El problema se encuentra en el toolbar del chat activity
        holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ChatActivivty.class);
                intent.putExtra("username", "username");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return pets.size();
    }
}

