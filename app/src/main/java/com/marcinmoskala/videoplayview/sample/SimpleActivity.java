package com.marcinmoskala.videoplayview.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;

import com.marcinmoskala.videoplayview.R;
import com.marcinmoskala.videoplayview.VideoPlayView;
import com.squareup.picasso.Picasso;

public class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        VideoPlayView videoView = findViewById(R.id.picassoVideoView);
        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(videoView.getImageView());
        ScrollView scroll = findViewById(R.id.scrollView);
        scroll.post(() -> scroll.fullScroll(View.FOCUS_UP));
    }
}
