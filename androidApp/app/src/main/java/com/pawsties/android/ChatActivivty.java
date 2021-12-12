package com.pawsties.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivivty extends AppCompatActivity {
    Toolbar toolbar;
    ImageView profile_pic;
    TextView username;
    Button adoptar;
    ImageButton btn_send;
    EditText et_message;
    ArrayList<Message> messages;
    MessageAdapter msgAdapter;
    RecyclerView rvDisplayMessages;
    String user;
    JSONObject adopcionjson;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MainActivity.typeUser)
            setContentView(R.layout.activity_chat);
        if (!MainActivity.typeUser){
            setContentView(R.layout.activity_chat_rescatista);
            adoptar = findViewById(R.id.btnRealizarAdopcion);
            adoptar.setOnClickListener(v -> {
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setMessage("Desea dar la mascota en adopcion a este usuario")
                        .setCancelable(true)
                        .setPositiveButton("sí", (dialog, which) -> {realizarAdopcion();})
                        .setNegativeButton("no", ((dialog, which) -> {}));
                alert = alertBuilder.create();
                alert.show();
            });
        }

        toolbar = findViewById(R.id.toolbar_chat);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        rvDisplayMessages = findViewById(R.id.rvMessages);
        rvDisplayMessages.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());//getcontext()
        linearLayoutManager.setStackFromEnd(true);
        rvDisplayMessages.setLayoutManager(linearLayoutManager);

        profile_pic = findViewById(R.id.profile_image_chat);
        username = findViewById(R.id.username_chat);
        btn_send = findViewById(R.id.btnSend);
        et_message = findViewById(R.id.etMessage);

        //aqui se van a recibir los datos proveidos de la BD
        Intent intent = getIntent();
        user = intent.getStringExtra("username");
        username.setText(user);
        profile_pic.setImageResource(R.mipmap.ic_launcher_round);

        btn_send.setOnClickListener(v -> {
            String message = et_message.getText().toString();
            if (!message.equals(""))
                sendMessage("sender", user, message);/**aqui se le deben de pasar usuarios de la BD*/
            et_message.setText("");
        });

        loadMessages("sender", user);//parametros de prueba
    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }

    private void loadMessages(String id, String user){
        messages = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Message message = snapshot1.getValue(Message.class);
                    if (message.receiver.equals(id) && message.sender.equals(user) ||
                        message.receiver.equals(user) && message.sender.equals(id))
                        messages.add(message);

                    msgAdapter = new MessageAdapter(ChatActivivty.this, messages);
                    rvDisplayMessages.setAdapter(msgAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void realizarAdopcion(){
        adopcionjson = new JSONObject();
        try {
            adopcionjson.accumulate("idMascota", "manchas");
            adopcionjson.accumulate("idusuario", "joeljp");
            adopcionjson.accumulate("idrescatista", "rakso");
            adopcionjson.accumulate("fecha", System.currentTimeMillis());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /** pasarle a la API un JSON con los datos de la adopcion */

        Toast.makeText(ChatActivivty.this, "Adopcion realizada", Toast.LENGTH_LONG).show();
    }
}