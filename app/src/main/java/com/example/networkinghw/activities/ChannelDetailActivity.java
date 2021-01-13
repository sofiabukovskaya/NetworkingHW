package com.example.networkinghw.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.networkinghw.R;

public class ChannelDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra(Intent.EXTRA_TITLE);

        TextView titleView = findViewById(R.id.textViewVideoTitle);
        TextView desc = findViewById(R.id.textViewVideoDesc);
        TextView likes = findViewById(R.id.textViewVideoLikes);

        titleView.setText(title);
    }
}