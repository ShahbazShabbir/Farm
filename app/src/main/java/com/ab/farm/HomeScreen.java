package com.ab.farm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.ab.farm.Activities.AboutUS;
import com.ab.farm.Activities.ContactUs;
import com.ab.farm.Activities.Crop;
import com.ab.farm.Activities.Level;
import com.ab.farm.Activities.Live;
import com.ab.farm.Activities.Map;
import com.ab.farm.Activities.Mode;
import com.ab.farm.Activities.Notification;
import com.ab.farm.Activities.Weed;
import com.skydoves.elasticviews.ElasticCardView;

public class HomeScreen extends AppCompatActivity {

    ElasticCardView live,mode,crop,weed,level,aboutus,map,contactus;
    ImageView back,contactusimg,notification;

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

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaydialog alert = new displaydialog();
                alert.showDialog(HomeScreen.this);
            }
        });

        contactusimg = findViewById(R.id.contactusimg);
        contactusimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, ContactUs.class);
                startActivity(intent);
            }
        });

        notification = findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Notification.class);
                startActivity(intent);
            }
        });


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


    public class displaydialog {

        public void showDialog(Activity activity) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialogbox);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            FrameLayout mDialogNo = dialog.findViewById(R.id.frmNo);
            mDialogNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);
            mDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    dialog.cancel();
                }
            });

            dialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}