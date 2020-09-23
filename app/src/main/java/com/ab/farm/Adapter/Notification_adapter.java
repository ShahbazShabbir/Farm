package com.ab.farm.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notification_adapter extends RecyclerView.Adapter<Notification_adapter.GithubViewHolder> {
    private Context context;
    private ArrayList<String> title;
    private ArrayList<String> desc;
    public Notification_adapter(Context context, ArrayList<String> title,ArrayList<String> desc){
        this.context = context;
        this.desc= desc;
        this.title=title;
    }

    @NonNull
    @Override
    public Notification_adapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_notification,viewGroup,false);
        return new Notification_adapter.GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Notification_adapter.GithubViewHolder holder, final int position) {

        holder.Title.setText(title.get(position));
        holder.Description.setText(desc.get(position));

    }
    @Override
    public int getItemCount() {
        return title.size();
    }
    public class GithubViewHolder extends RecyclerView.ViewHolder{

        TextView Title,Description;


        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.title_id);
            Description = itemView.findViewById(R.id.desc_id);
        }
    }
}
