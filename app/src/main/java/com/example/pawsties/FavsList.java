package com.example.pawsties;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavsList extends RecyclerView.ViewHolder {
    private LinearLayout gallery;

    public FavsList(@NonNull View view) {
        super(view);
        gallery = (LinearLayout) view.findViewById(R.id.item_pet);
    }

//    void bind(Bitmap gal1) {
//        gallery.setImageBitmap(gal1);
//    }
}

