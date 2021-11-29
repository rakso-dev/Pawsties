package com.pawsties.android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        holder.bind(profile.pic, profile.name, profile.description);

//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, ChatActivivty.class);
//            intent.putExtra("username", "username");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }
}
