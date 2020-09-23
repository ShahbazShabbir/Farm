package com.ab.farm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.farm.Constant;
import com.ab.farm.HomeScreen;
import com.ab.farm.R;
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
import java.util.Map;

public class Mode extends AppCompatActivity {

    ImageView back,contactus,notification;
    TextView textView;
    String Mode_status;
    ElasticCardView Detection,Everywher;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Detection = findViewById(R.id.detection);
        Everywher = findViewById(R.id.everywhere);
        textView = findViewById(R.id.textview);

        sharedPreferences=getSharedPreferences("Mode",MODE_PRIVATE);

        String current_mode = sharedPreferences.getString("mode",null);
//
//        if (current_mode == null){
//            textView.setText("Mode is Unselected");
//        }
//        else if (current_mode.equals("detection")){
//            textView.setText("Mode is Detective");
//            Detection.setCardBackgroundColor(getResources().getColor(R.color.green_light));
//        }
//        else if (current_mode.equals("everywhere")){
//            textView.setText("Mode is Everywhere");
//            Everywher.setCardBackgroundColor(getResources().getColor(R.color.green_light));
//        }
//        else {
//            textView.setText("Mode is Unselected");
//        }

        getData();
        Detection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getApi("1");

            }
        });
        Everywher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getApi2("0");
            }
        });


        contactus = findViewById(R.id.contactus);
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mode.this, ContactUs.class);
                startActivity(intent);
            }
        });

        notification = findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mode.this, Notification.class);
                startActivity(intent);
            }
        });
    }

    private void getApi2(final String s) {
        final android.app.AlertDialog loading = new ProgressDialog(Mode.this);
        loading.setMessage("Changing...");
        loading.show();

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                Constant.Base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    if (response.equals("1")) {
                        Toast.makeText(Mode.this, "Mode is changed", Toast.LENGTH_SHORT).show();

                        textView.setText("Mode is Everwhere");

                        getData2();
                        makenotification();

//                        SharedPreferences.Editor editor=sharedPreferences.edit();
//                        editor.putString("mode","everywhere");
//                        editor.apply();
                        loading.dismiss();

                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(Mode.this, "Mode is not changed", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    loading.dismiss();
                    Toast.makeText(Mode.this, "Connection Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mode.this, "Connection Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                loading.dismiss();
            }
        }){

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<>();
                map.put("action","changeMode");
                map.put("mode",s);
                return map;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

    private void getApi(final String mody) {

        final android.app.AlertDialog loading = new ProgressDialog(Mode.this);
        loading.setMessage("Changing...");
        loading.show();

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                Constant.Base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    if (response.equals("1")) {
                        Toast.makeText(Mode.this, "Mode is changed", Toast.LENGTH_SHORT).show();
//                        Detection.setCardBackgroundColor(getResources().getColor(R.color.green_light));
//                        Everywher.setCardBackgroundColor(getResources().getColor(R.color.white));

                        getData2();
                        makenotification();

                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("mode","detection");
                        editor.apply();
                        textView.setText("Mode is Detective");
                        loading.dismiss();
                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(Mode.this, "Mode is not changed", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    loading.dismiss();
                    Toast.makeText(Mode.this, "Connection Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mode.this, "Connection Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                loading.dismiss();
            }
        }){

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<>();
                map.put("action","changeMode");
                map.put("mode",mody);
                return map;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

    private void makenotification() {
        final android.app.AlertDialog loading = new ProgressDialog(Mode.this);
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
                Toast.makeText(Mode.this, "Connection Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                loading.dismiss();
            }
        }){

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<>();
                map.put("action","makeNotification");
                map.put("title","Mode");
                map.put("description","The Mode of Robot is changed");
                return map;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }


    private void getData() {

        final android.app.AlertDialog loading = new ProgressDialog(Mode.this);
        loading.setMessage("Loading...");
        loading.show();

        RequestQueue requestQueue= Volley.newRequestQueue(Mode.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.Base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                            id = jsonObject1.getString("id");
//                            Status = jsonObject1.getString("status");
                            Mode_status = jsonObject1.getString("mode");
//                            Liveurl = jsonObject1.getString("live_url");
//                            b_level = jsonObject1.getString("b_level");;
//                            p_level = jsonObject1.getString("p_level");;
//                            is_spray = jsonObject1.getString("is_spray");
//                            Area = jsonObject1.getString("area");
                        }
                        if (Mode_status.equals("1")){
                            textView.setText("Mode is Detective");
                            Detection.setCardBackgroundColor(getResources().getColor(R.color.green_light));
                            Everywher.setCardBackgroundColor(getResources().getColor(R.color.white));
                        }
                        else {
                            textView.setText("Mode is Everywhere");
                            Everywher.setCardBackgroundColor(getResources().getColor(R.color.green_light));
                            Detection.setCardBackgroundColor(getResources().getColor(R.color.white));
                        }

                        loading.dismiss();
                    } catch (JSONException e) {
                        loading.dismiss();
                        e.printStackTrace();
                    }
                }
                else{
                    loading.dismiss();
                    Toast.makeText(Mode.this, "No Record Found", Toast.LENGTH_SHORT).show();
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

    private void getData2() {

        final android.app.AlertDialog loading = new ProgressDialog(Mode.this);
        loading.setMessage("Loading...");
     //   loading.show();

        RequestQueue requestQueue= Volley.newRequestQueue(Mode.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.Base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                            id = jsonObject1.getString("id");
//                            Status = jsonObject1.getString("status");
                            Mode_status = jsonObject1.getString("mode");
//                            Liveurl = jsonObject1.getString("live_url");
//                            b_level = jsonObject1.getString("b_level");;
//                            p_level = jsonObject1.getString("p_level");;
//                            is_spray = jsonObject1.getString("is_spray");
//                            Area = jsonObject1.getString("area");
                        }
                        if (Mode_status.equals("1")){
                            textView.setText("Mode is Detective");
                            Detection.setCardBackgroundColor(getResources().getColor(R.color.green_light));
                            Everywher.setCardBackgroundColor(getResources().getColor(R.color.white));
                        }
                        else {
                            textView.setText("Mode is Everywhere");
                            Everywher.setCardBackgroundColor(getResources().getColor(R.color.green_light));
                            Detection.setCardBackgroundColor(getResources().getColor(R.color.white));
                        }

                        loading.dismiss();
                    } catch (JSONException e) {
                        loading.dismiss();
                        e.printStackTrace();
                    }
                }
                else{
                    loading.dismiss();
                    Toast.makeText(Mode.this, "No Record Found", Toast.LENGTH_SHORT).show();
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
}