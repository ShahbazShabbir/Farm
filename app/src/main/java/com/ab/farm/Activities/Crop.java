package com.ab.farm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.farm.R;
import com.ngallazzi.highlightingview.HighlightingView;

public class Crop extends AppCompatActivity {

    ImageView back,contactus,notification;
    LinearLayout btn_layout_crop;
    TextView btn_crop_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        btn_layout_crop = findViewById(R.id.btn_layout_corn);
        btn_crop_status = findViewById(R.id.btn_crop_status);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_layout_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_layout_crop.setBackgroundColor(getResources().getColor(R.color.green_light));
                btn_crop_status.setText("OFF");
            }
        });

        contactus = findViewById(R.id.contactus);
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Crop.this, ContactUs.class);
                startActivity(intent);
            }
        });

        notification = findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Crop.this, Notification.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}