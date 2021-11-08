package com.example.pawsties;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavsAdapter extends RecyclerView.Adapter<FavsList> {
    private Context context;
    private ArrayList<ImageView> images;

    public FavsAdapter (Context context, ArrayList<ImageView> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public FavsList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_favorite, parent, false);
        return new FavsList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavsList holder, int position) {
        ImageView image = images.get(position);
        //holder.bind(image.getDrawingCache());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}

