package com.ab.farm.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.farm.Adapter.CropAdapter;
import com.ab.farm.Adapter.crop_interface;
import com.ab.farm.Constant;
import com.ab.farm.Model.CropModel;
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
import com.ngallazzi.highlightingview.HighlightingView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Crop extends AppCompatActivity implements crop_interface {

    ImageView back,contactus,notification;
    LinearLayout btn_layout_crop;
    TextView btn_crop_status;
    RecyclerView recyclerView;
    ArrayList<CropModel> cropModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        recyclerView = findViewById(R.id.recycler);

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

        getData();

    }

    private void getData() {
        final android.app.AlertDialog loading = new ProgressDialog(Crop.this);
        loading.setMessage("Loading...");
        loading.show();

        RequestQueue requestQueue= Volley.newRequestQueue(Crop.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.Base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            CropModel model = new CropModel();
                            model.setId(jsonObject1.getString("id"));
                            model.setNaem(jsonObject1.getString("name"));
                            model.setImage(jsonObject1.getString("image"));
                            model.setStatus(jsonObject1.getString("status"));
                            model.setType(jsonObject1.getString("type"));
                            cropModels.add(model);
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(Crop.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(new CropAdapter(Crop.this, cropModels,Crop.this));

                        loading.dismiss();
                    } catch (JSONException e) {
                        loading.dismiss();
                        e.printStackTrace();
                    }
                }
                else{
                    loading.dismiss();
                    Toast.makeText(Crop.this, "No Record Found", Toast.LENGTH_SHORT).show();
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

                Map<String,String> map = new HashMap<>();
                map.put("action", "getCrop");
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
        finish();
    }

    @Override
    public void onclick() {
        cropModels.clear();
        getData();
    }
}