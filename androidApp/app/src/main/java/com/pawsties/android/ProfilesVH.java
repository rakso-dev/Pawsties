package com.pawsties.android;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.atomic.AtomicInteger;

public class ProfilesVH extends RecyclerView.ViewHolder {
    ImageView petPic;
    TextView petName;
    TextView petDescription;
    ImageButton btnBack, btnNext, btnFav;

    public ProfilesVH(@NonNull View itemView) {
        super(itemView);

        petPic = itemView.findViewById(R.id.pet_pic);
        petName = itemView.findViewById(R.id.pet_name);
        petDescription = itemView.findViewById(R.id.pet_description);
        btnBack = itemView.findViewById(R.id.btnBack);
        btnNext = itemView.findViewById(R.id.btnNext);
        btnFav = itemView.findViewById(R.id.btnLike);
    }

    AtomicInteger bind(Bitmap pic, String name, String description){
        AtomicInteger flag = new AtomicInteger();
        //petPic.setImageBitmap(pic);
        //petPic.setImageResource(R.mipmap.ic_launcher_round);//quitar cuando se obtengan de la BD
        petName.setText(name);
        petDescription.setText(description);

        btnFav.setOnClickListener(/**agregar a la BD el favorito de este VH*/ v -> {/** SI FUNCIONO XD*/
            Toast.makeText(v.getContext(), "Agregado a favoritos!!!", Toast.LENGTH_LONG).show();
        });
        btnNext.setOnClickListener(v -> {
            flag.set(1);
        });
        btnBack.setOnClickListener(v -> {
            flag.set(-1);
        });
        return flag;
    }

}
