package com.ab.farm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ab.farm.Activities.AboutUS;
import com.ab.farm.Activities.ContactUs;
import com.ab.farm.Activities.Crop;
import com.ab.farm.Activities.Level;
import com.ab.farm.Activities.Live;
import com.ab.farm.Activities.Map;
import com.ab.farm.Activities.Mode;
import com.ab.farm.Activities.Weed;
import com.skydoves.elasticviews.ElasticCardView;

public class HomeScreen extends AppCompatActivity {

    ElasticCardView live,mode,crop,weed,level,aboutus,map,contactus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        live = findViewById(R.id.live);
        mode = findViewById(R.id.mode);
        crop = findViewById(R.id.crop);
        weed = findViewById(R.id.weed);
        level = findViewById(R.id.level);
        aboutus = findViewById(R.id.aboutus);
        map = findViewById(R.id.map);
        contactus = findViewById(R.id.contactus);

        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Live.class);
                startActivity(intent);
            }
        });

        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Mode.class);
                startActivity(intent);
            }
        });

        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Crop.class);
                startActivity(intent);
            }
        });

        weed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Weed.class);
                startActivity(intent);
            }
        });

        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Level.class);
                startActivity(intent);
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, AboutUS.class);
                startActivity(intent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Map.class);
                startActivity(intent);
            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, ContactUs.class);
                startActivity(intent);
            }
        });
    }
}