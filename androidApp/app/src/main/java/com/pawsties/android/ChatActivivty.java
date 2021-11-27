package com.pawsties.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatActivivty extends AppCompatActivity {
    Toolbar toolbar;
    ImageView profile_pic;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = findViewById(R.id.toolbar_chat);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        profile_pic = findViewById(R.id.profile_image_chat);
        username = findViewById(R.id.username_chat);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("username"));
        profile_pic.setImageResource(R.mipmap.ic_launcher_round);
    }
}