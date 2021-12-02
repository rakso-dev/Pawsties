package com.pawsties.android;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

public class MessageVH extends RecyclerView.ViewHolder {
    public TextView message;

    public MessageVH(@NonNull View itemView){
        super(itemView);

        message = itemView.findViewById(R.id.message);
    }

    void bind(String msg){/**/
        message.setText(msg);
    }
}
