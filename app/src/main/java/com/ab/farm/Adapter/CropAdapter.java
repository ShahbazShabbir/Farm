package com.ab.farm.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.ab.farm.Activities.Mode;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_SHORT;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.GithubViewHolder> {
    private Context context;
    private ArrayList<CropModel> data;
    crop_interface crop_interface;
    public CropAdapter(Context context, ArrayList<CropModel> data,crop_interface crop_interface){
        this.context = context;
        this.data= data;
        this.crop_interface=crop_interface;
    }

    @NonNull
    @Override
    public CropAdapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.crop_cardview,viewGroup,false);
        return new CropAdapter.GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubViewHolder holder, final int position) {

        holder.Name.setText(data.get(position).getNaem());

        Glide.with(context).load(data.get(position).getImage()).into(holder.imageView);

        if (data.get(position).getStatus().equals("1")) {
            holder.Status.setText("ON");
            holder.linearLayoutl.setBackgroundColor(Color.parseColor("#00e600"));
        } else {
            holder.Status.setText("OFF");
            holder.linearLayoutl.setBackgroundColor(Color.parseColor("#ff0000"));
        }

        holder.linearLayoutl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.app.AlertDialog loading = new ProgressDialog(context);
                loading.setMessage("Changing...");
                loading.show();

                RequestQueue requestQueue= Volley.newRequestQueue(context);
                StringRequest stringRequest=new StringRequest(Request.Method.POST,
                        Constant.Base_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            if (response.equals("1")) {
                                Toast.makeText(context, "Status is changed", Toast.LENGTH_SHORT).show();

                                crop_interface.onclick();
                                loading.dismiss();

                            }
                            else {
                                loading.dismiss();
                                Toast.makeText(context, "Status is not changed", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            loading.dismiss();
                            Toast.makeText(context, "Connection Error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Connection Error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        loading.dismiss();
                    }
                }){

                    @Override
                    protected java.util.Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> map = new HashMap<>();
                        map.put("action","changeFieldStatus");
                        if (data.get(position).getStatus().equals("1")){
                            map.put("status","0");
                        }
                        else {
                            map.put("status","1");

                        }
                        map.put("id",data.get(position).getId());
                        return map;
                    }
                };
                int socketTimeout = 30000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                requestQueue.add(stringRequest);

            }
        });

    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public class GithubViewHolder extends RecyclerView.ViewHolder{

        TextView Name,Status;
        ImageView imageView;
        LinearLayout linearLayoutl;


        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.crop_name);
            Status = itemView.findViewById(R.id.btn_crop_status);
            imageView=itemView.findViewById(R.id.crop_image);
            linearLayoutl=itemView.findViewById(R.id.btn_layout_corn);
            }
    }
}
