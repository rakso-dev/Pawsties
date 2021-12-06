package com.pawsties.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageVH> {
    public static final int MESSAGE_TYPE_RECEIVED = 0;
    public static final int MESSAGE_TYPE_SENT = 1;
    private Context context;
    private ArrayList<Message> messages;
    //FirebaseUser fuser;

    public MessageAdapter (Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (viewType == MESSAGE_TYPE_SENT)
            view = inflater.inflate(R.layout.item_message_sent, parent, false);
        else
            view = inflater.inflate(R.layout.item_message_received, parent, false);
        return new MessageVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageVH holder, int position) {

        Message message = messages.get(position);
        holder.message.setText(message.message);

        //holder.bind(message.message);

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        /**se tienen que obtener los usuarios de Azure*/
        //fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (messages.get(position).sender.equals("sender"/*fuser.getUid()*/))//el parametro que define el diseño de los mensajes
            return MESSAGE_TYPE_SENT;
        else
            return MESSAGE_TYPE_RECEIVED;
    }
}
