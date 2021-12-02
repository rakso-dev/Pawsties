package com.pawsties.android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ProfilesAdapter extends RecyclerView.Adapter<ProfilesVH> {
    private Context context;
    private ArrayList<Pet> profiles;

    public ProfilesAdapter(Context context, ArrayList<Pet> profiles){
        this.context = context;
        this.profiles = profiles;
    }

    @NonNull
    @Override
    public ProfilesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_profile, parent, false);
        return new ProfilesVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilesVH holder, int position) {
        final Pet profile = profiles.get(position);
        AtomicInteger flag;//el atomic era para cambiar con el boton pero no funciono, pero tampoco trono la app ggg
        flag = holder.bind(profile.pic, profile.name, profile.description);
        if (flag.equals(1))
            position++;
        if (flag.equals(-1))
            onBindViewHolder(holder, position--);//esto tampoco sirvio
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }
}
