package com.pawsties.android;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ProfilesVH extends RecyclerView.ViewHolder {
    ImageView petPic;
    TextView petName;
    TextView petDescription;
    ImageButton btnBack, btnNext, btnFav;
    PetModel petToChat;

    public ProfilesVH(@NonNull View itemView) {
        super(itemView);

        petPic = itemView.findViewById(R.id.pet_pic);
        petName = itemView.findViewById(R.id.pet_name);
        petDescription = itemView.findViewById(R.id.pet_description);
        btnBack = itemView.findViewById(R.id.btnBack);
        btnNext = itemView.findViewById(R.id.btnNext);
        btnFav = itemView.findViewById(R.id.btnLike);
    }

    AtomicInteger bind(PetModel profile){
        AtomicInteger flag = new AtomicInteger();
        petName.setText(profile.name);
        petDescription.setText(profile.description);

        btnFav.setOnClickListener(v -> {
            petToChat = profile;
            //FavoritiesFragment.pets.add(petToChat);
            /**agregar a la BD el favorito de este VH para que persistan los datos */
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("usuario", "joeljp");
            hashMap.put("nMascota", profile.name);

            reference.child("Favoritos").push().setValue(hashMap);

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
