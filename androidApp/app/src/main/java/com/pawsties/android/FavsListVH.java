package com.pawsties.android;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class FavsListVH extends RecyclerView.ViewHolder {
    private ImageView ivFavPetPic;
    private TextView tvFavPetName;

    public FavsListVH(@NonNull View view) {
        super(view);
        ivFavPetPic = view.findViewById(R.id.ivFavPetPic);
        tvFavPetName = view.findViewById(R.id.tvFavPetName);
    }

    void bind(PetFavModel pet) {
        //ivFavPetPic.setImageBitmap(pic);
        ivFavPetPic.setImageResource(R.mipmap.ic_launcher_round);//quitar cuando se obtengan de la BD
        tvFavPetName.setText(pet.nMascota);
    }
}

