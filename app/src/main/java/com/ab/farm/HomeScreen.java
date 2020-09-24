package com.ab.farm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
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
import com.ab.farm.Adapter.CropAdapter;
import com.ab.farm.Model.CropModel;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skydoves.elasticviews.ElasticCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class HomeScreen extends AppCompatActivity {

    ElasticCardView live,mode,crop,weed,level,aboutus,map,contactus;
    ImageView back,contactusimg,notification,Spray;
    String id ;
    Switch aSwitch;
    String Status;
    String Mode;
    String Liveurl;
    String b_level;
    String p_level;
    String is_spray;
    String Area;
    int check = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        aSwitch = findViewById(R.id.switch_id);
        Spray = findViewById(R.id.spray);
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

        getData();


        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Live.class);
                intent.putExtra("videosurl",Liveurl);
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
                intent.putExtra("Area",Area);
//                Toast.makeText(HomeScreen.this,Area,Toast.LENGTH_SHORT).show();
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

        Spray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_spray.equals("1")){
                    changeSpray("0");
                }
                else {
                    changeSpray("1");
                }
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (Area.isEmpty()) {
                    aSwitch.setChecked(false);
                    if (check++ > 1){
                        Toast.makeText(HomeScreen.this, "Please Select Area First", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (check++ > 1) {
                            if (b) {
                                changestatus("1");
                            } else {
                                changestatus("0");
                            }
                    } else {
                        check++;
                    }
                }
            }
        });
    }

    private void changestatus(final String s) {
        final android.app.AlertDialog loading = new ProgressDialog(HomeScreen.this);
        loading.setMessage("Changing...");
//        loading.show();

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                Constant.Base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    if (response.equals("1")) {
//                        Toast.makeText(HomeScreen.this, "Status Changed", Toast.LENGTH_SHORT).show();
                        getData();
                        makenotificationsrobot();
                        loading.dismiss();

                    }
                    else {
                        loading.dismiss();
//                        Toast.makeText(HomeScreen.this, "Status is not changed", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    loading.dismiss();
                    Toast.makeText(HomeScreen.this, "Connection Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeScreen.this, "Connection Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                loading.dismiss();
            }
        }){

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {

                java.util.Map<String,String> map = new HashMap<>();
                map.put("action","changeStatus");
                map.put("status",s);
                return map;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void changeSpray(final String s) {
        final android.app.AlertDialog loading = new ProgressDialog(HomeScreen.this);
        loading.setMessage("Changing...");
        loading.show();

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                Constant.Base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    if (response.equals("1")) {
                        Toast.makeText(HomeScreen.this, "Spray Changed", Toast.LENGTH_SHORT).show();
                        getData();
                        makenotificationspray();
                        loading.dismiss();

                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(HomeScreen.this, "Spray is not changed", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    loading.dismiss();
                    Toast.makeText(HomeScreen.this, "Connection Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeScreen.this, "Connection Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                loading.dismiss();
            }
        }){

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {

                java.util.Map<String,String> map = new HashMap<>();
                map.put("action","changeIsSpray");
                map.put("is_spray",s);
                return map;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void getData() {

        final android.app.AlertDialog loading = new ProgressDialog(HomeScreen.this);
        loading.setMessage("Loading...");
        loading.show();

        RequestQueue requestQueue= Volley.newRequestQueue(HomeScreen.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.Base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                             id = jsonObject1.getString("id");
                             Status = jsonObject1.getString("status");
                             Mode = jsonObject1.getString("mode");
                             Liveurl = jsonObject1.getString("live_url");
                             b_level = jsonObject1.getString("b_level");;
                             p_level = jsonObject1.getString("p_level");;
                             is_spray = jsonObject1.getString("is_spray");
                             Area = jsonObject1.getString("area");
                        }

                        if (Status.equals("1")){
                            aSwitch.setTextOn("ON");
                            aSwitch.setChecked(true);
                        }
                        else {
                            aSwitch.setChecked(false);
                            aSwitch.setTextOff("OFF");
                        }
                        if (is_spray.equals("1")){
                            Spray.setBackground(getDrawable(R.drawable.rocketgreen));
                        }
                        else {
                            Spray.setBackground(getDrawable(R.drawable.rocketred));
                        }


                        loading.dismiss();
                    } catch (JSONException e) {
                        loading.dismiss();
                        e.printStackTrace();
                    }
                }
                else{
                    loading.dismiss();
                    Toast.makeText(HomeScreen.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                loading.dismiss();
            }
        }){

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {

                java.util.Map<String,String> map = new HashMap<>();
                map.put("action", "getRobot");
                return map;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
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
    private void getData2() {

//        final android.app.AlertDialog loading = new ProgressDialog(HomeScreen.this);
//        loading.setMessage("Loading...");
//        loading.show();

        RequestQueue requestQueue= Volley.newRequestQueue(HomeScreen.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.Base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            id = jsonObject1.getString("id");
                            Status = jsonObject1.getString("status");
                            Mode = jsonObject1.getString("mode");
                            Liveurl = jsonObject1.getString("live_url");
                            b_level = jsonObject1.getString("b_level");;
                            p_level = jsonObject1.getString("p_level");;
                            is_spray = jsonObject1.getString("is_spray");
                            Area = jsonObject1.getString("area");
                        }

                        if (Status.equals("1")){
                            aSwitch.setTextOn("ON");
                            aSwitch.setChecked(true);
                        }
                        else {
                            aSwitch.setChecked(false);
                            aSwitch.setTextOff("OFF");
                        }
                        if (is_spray.equals("1")){
                            Spray.setBackground(getDrawable(R.drawable.rocketgreen));
                        }
                        else {
                            Spray.setBackground(getDrawable(R.drawable.rocketred));
                        }


//                        loading.dismiss();
                    } catch (JSONException e) {
//                        loading.dismiss();
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(HomeScreen.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {

                java.util.Map<String,String> map = new HashMap<>();
                map.put("action", "getRobot");
                return map;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void makenotificationspray() {
        final android.app.AlertDialog loading = new ProgressDialog(HomeScreen.this);
        loading.setMessage("Changing...");
//        loading.show();

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                Constant.Base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
//                    if (response!=null) {

//                        Toast.makeText(Mode.this, "Mode is changed", Toast.LENGTH_SHORT).show();
//
//                        textView.setText("Mode is Everwhere");
//
//                        getData2();
//                        makenotification();

//                        SharedPreferences.Editor editor=sharedPreferences.edit();
//                        editor.putString("mode","everywhere");
//                        editor.apply();
                    loading.dismiss();

//                    }
//                    else {
//                        loading.dismiss();
//                        //Toast.makeText(Mode.this, "Mode is not changed", Toast.LENGTH_SHORT).show();
//                    }
                }catch (Exception e){
                    loading.dismiss();
                    //Toast.makeText(Mode.this, "Connection Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeScreen.this, "Connection Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                loading.dismiss();
            }
        }){

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {

                java.util.Map<String,String> map = new HashMap<>();
                map.put("action","makeNotification");
                map.put("title","Spray");
                map.put("description","The Spray of Robot is changed");
                return map;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }


    private void makenotificationsrobot() {
        final android.app.AlertDialog loading = new ProgressDialog(HomeScreen.this);
        loading.setMessage("Changing...");
//        loading.show();

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                Constant.Base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
//                    if (response!=null) {

//                        Toast.makeText(Mode.this, "Mode is changed", Toast.LENGTH_SHORT).show();
//
//                        textView.setText("Mode is Everwhere");
//
//                        getData2();
//                        makenotification();

//                        SharedPreferences.Editor editor=sharedPreferences.edit();
//                        editor.putString("mode","everywhere");
//                        editor.apply();
                    loading.dismiss();

//                    }
//                    else {
//                        loading.dismiss();
//                        //Toast.makeText(Mode.this, "Mode is not changed", Toast.LENGTH_SHORT).show();
//                    }
                }catch (Exception e){
                    loading.dismiss();
                    //Toast.makeText(Mode.this, "Connection Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeScreen.this, "Connection Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                loading.dismiss();
            }
        }){

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {

                java.util.Map<String,String> map = new HashMap<>();
                map.put("action","makeNotification");
                map.put("title","Robot");
                map.put("description","The Power of Robot is changed");
                return map;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}