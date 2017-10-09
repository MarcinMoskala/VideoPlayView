package com.marcinmoskala.videoplayview.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.marcinmoskala.videoplayview.R;
import com.marcinmoskala.videoplayview.VideoPlayView;
import com.squareup.picasso.Picasso;

public class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        VideoPlayView videoView = findViewById(R.id.videoPlayerView);
        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(videoView.getImageView());
    }
}
