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

    FavsAdapter (Context context, Context baseContext, ArrayList<Pet> pets) {
        this.context = context;
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
        Log.i("VH_item", "OnBindVH");
        Toast.makeText(baseContext, "OnViewHolder", Toast.LENGTH_LONG).show();

        final Pet pet = pets.get(position);
        holder.bind(pet.pic, pet.name);

        //HAY PROBLEMITAS AL LANZAR EL CHAT ACTIVITY!!!
        Toast.makeText(baseContext, "listo para seleccionar chat", Toast.LENGTH_LONG).show();
        holder.itemView.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(baseContext, ChatActivivty.class);
                intent.putExtra("username", "username");
                Toast.makeText(baseContext, "lanzando intent", Toast.LENGTH_LONG).show();
                baseContext.startActivity(intent);
            }catch (Exception e){
                Toast.makeText(baseContext, "error: "+e, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }
}

