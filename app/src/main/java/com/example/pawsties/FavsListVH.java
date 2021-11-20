package com.example.pawsties;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

class FavsListVH extends RecyclerView.ViewHolder {
    private ImageView ivFavPetPic;
    private TextView tvFavPetName;

    public FavsListVH(@NonNull View view) {
        super(view);
        ivFavPetPic = view.findViewById(R.id.ivFavPetPic);
        tvFavPetName = view.findViewById(R.id.tvFavPetName);
    }

    void bind(Bitmap pic, String name) {
        ivFavPetPic.setImageBitmap(pic);
        tvFavPetName.setText(name);
    }
}

