package com.ab.farm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ab.farm.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class Live extends AppCompatActivity {

    ImageView back,contactus,notification;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        contactus = findViewById(R.id.contactus);
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Live.this, ContactUs.class);
                startActivity(intent);
            }
        });

        notification = findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Live.this, Notification.class);
                startActivity(intent);
            }
        });

        videoView = (VideoView)findViewById(R.id.videoview);


        String path1="https://www.youtube.com/watch?v=TngjJXBsg1w";
        String VideoURL = "https://www.androidbegin.com/tutorial/AndroidCommercial.3gp";

        MediaController mediacontroller = new MediaController(
                Live.this);
        mediacontroller.setAnchorView(videoView);
        // Get the URL from String VideoURL
        Uri video = Uri.parse(VideoURL);
        videoView.setMediaController(mediacontroller);
        videoView.setVideoURI(video);
        videoView.start();

//        Uri uri=Uri.parse(path1);
//        videoView.setMediaController(new MediaController(this));
//        videoView.setVideoURI(uri);
//        videoView.requestFocus();
//        videoView.start();

//        videoView.setUp(path1
//                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "Live Streeam");


    }

}