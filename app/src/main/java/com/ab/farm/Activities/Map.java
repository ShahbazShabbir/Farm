package com.ab.farm.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.farm.Constant;
import com.ab.farm.HomeScreen;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.maps.android.SphericalUtil;

import com.ab.farm.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button ChangeMap;
    TextView Clear;
    String Area;
    Polygon polygon = null;
    ArrayList<Marker> markerList = new ArrayList();
    ArrayList<LatLng> points = new ArrayList<>();
    LatLng sydney;
    GoogleMap mMap;
    Double latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ChangeMap = findViewById(R.id.changeMap);
        Clear = findViewById(R.id.clear);

        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMap.clear();
                points.clear();
                markerList.clear();
//                if (polygon.equals(null)){
//
//                }
//                else {
//                    polygon.remove();
//                }

            }
        });

        //getData();

        ChangeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (points.size() > 2){

//                    Toast.makeText(Map.this,String.valueOf(points),Toast.LENGTH_SHORT).show();

                    String tempy = String.valueOf(points);
                    tempy = tempy.substring(1, tempy.length() - 1);

                    String pepe = tempy.replaceAll("lat/lng:","");
                    String pepe2 = pepe.replaceAll("[\\p{Ps}\\p{Pe}]","");

                    //Toast.makeText(Map.this,pepe2,Toast.LENGTH_SHORT).show();

                    changeArea(pepe2);

                }
                else {
                    Toast.makeText(Map.this,"Select Atleast 3 Points For Area",Toast.LENGTH_SHORT).show();
                }
            }
        });


        sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);
        try {

            getLocation();


//            FusedLocationProviderClient fusedLocationClient;
//            LocationManager locationManager;
//
//            fusedLocationClient = LocationServices.getFusedLocationProviderClient(Map.this);
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(android.location.Location location) {
//
//                            if (location != null) {
//
////                                latitude = location.getLatitude();
////                                longitude = location.getLongitude();
//                                editor = sharedPreferences.edit();
//                                editor.putString("lat", String.valueOf(location.getLatitude()));
//                                editor.putString("long", String.valueOf(location.getLongitude()));
//                                editor.apply();
//
//                                location.reset();
//
//                            } else {
//                                Toast.makeText(Map.this, "Location not Get", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });

            String lat = sharedPreferences.getString("lat", null);
            String longi = sharedPreferences.getString("long", null);

            latitude = Double.parseDouble(lat);
            longitude = Double.parseDouble(longi);

            //Toast.makeText(Map.this, latitude+" "+longitude, Toast.LENGTH_SHORT).show();

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            Toast.makeText(Map.this, "Longitude/Latitude not Found", Toast.LENGTH_SHORT).show();
        }

    }

    public void getData() {
        final android.app.AlertDialog loading = new ProgressDialog(Map.this);
        loading.setMessage("Loading...");
        loading.show();

        RequestQueue requestQueue= Volley.newRequestQueue(Map.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.Base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                            id = jsonObject1.getString("id");
////                            Status = jsonObject1.getString("status");
//                            Mode_status = jsonObject1.getString("mode");
//                            Liveurl = jsonObject1.getString("live_url");
//                            b_level = jsonObject1.getString("b_level");;
//                            p_level = jsonObject1.getString("p_level");;
//                            is_spray = jsonObject1.getString("is_spray");
                            Area = jsonObject1.getString("area");
                        }

                        loading.dismiss();
                    } catch (JSONException e) {
                        loading.dismiss();
                        e.printStackTrace();
                    }
                }
                else{
                    loading.dismiss();
                    Toast.makeText(Map.this, "No Record Found", Toast.LENGTH_SHORT).show();
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

    private void changeArea(final String area) {
        final android.app.AlertDialog loading = new ProgressDialog(Map.this);
        loading.setMessage("Loading...");
        loading.show();

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        final StringRequest stringRequest=new StringRequest(Request.Method.POST,
                Constant.Base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    if (response.equals("1")) {

                        Toast.makeText(Map.this, "Area is Changed", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                        Intent intent = new Intent(Map.this, HomeScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(Map.this, "Area is not changed", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    loading.dismiss();
                    Toast.makeText(Map.this, "Connection Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Map.this, "Connection Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                loading.dismiss();
            }
        }){

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {

                java.util.Map<String,String> map = new HashMap<>();
                map.put("action","changeArea");
                map.put("area",area);
                return map;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {

            //getData();

            sydney = new LatLng(latitude, longitude);
            float zoomLevel = (float) 16.0;

            Area = getIntent().getStringExtra("Area");
            //Toast.makeText(Map.this, Area, Toast.LENGTH_SHORT).show();

            if (Area.equals("abc")){
                mMap.addMarker(new MarkerOptions()
                        .position(sydney)
                        .title("Your Current Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
            }
            else {

                String[] separated = Area.split(",");
                String lat1 = separated[0];
                String lat2 = separated[2];
                String lat3 = separated[4];
                String lon1 = separated[1];
                String lon2 = separated[3];
                String lon3 = separated[5];

                LatLng latLng1 = new LatLng(Double.parseDouble(lat1),Double.parseDouble(lon1));
                LatLng latLng2 = new LatLng(Double.parseDouble(lat2),Double.parseDouble(lon2));
                LatLng latLng3 = new LatLng(Double.parseDouble(lat3),Double.parseDouble(lon3));
                mMap.addMarker(new MarkerOptions()
                        .position(latLng1)
                        .title("Your Area"));
                mMap.addMarker(new MarkerOptions()
                        .position(latLng2)
                        .title("Your Area"));
                mMap.addMarker(new MarkerOptions()
                        .position(latLng3)
                        .title("Your Area"));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng2));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng3));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, zoomLevel));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2, zoomLevel));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng3, zoomLevel));


            }


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

//                    mMap.addCircle(new CircleOptions()
//                            .center(latLng)
//                            .radius(250)
//                            .strokeWidth(0f)
//                            .fillColor(0x550000FF));
                    addMarker(latLng);

                    //double area = SphericalUtil.computeArea(Collections.singletonList(latLng));

//                    Toast.makeText(Map.this,
//                            String.valueOf(SphericalUtil.computeArea(Collections.singletonList(latLng))),Toast.LENGTH_SHORT).show();
//                    mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title(latLng.toString()));

                }
            });

//            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                @Override
//                public boolean onMarkerClick(Marker marker) {
//                    marker.remove();
//                    markerList.remove(marker);
//                    points.remove(points.size()-1);
//                    polygon.remove();
//                    if (points.size() > 0){
//                        drawPolygon();
//                    }
//                    return true;
//                }
//            });

            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {
                }
                @Override
                public void onMarkerDrag(Marker marker) {
                    updateMarkerLocation(marker,false);
                }

                private void updateMarkerLocation(Marker marker, boolean b) {
                    LatLng latLng = (LatLng) marker.getTag();
                    int position = points.indexOf(latLng);
                    points.set(position, marker.getPosition());
                    marker.setTag(marker.getPosition());
                    drawPolygon();
                    Toast.makeText(Map.this,
                            String.valueOf(SphericalUtil.computeArea(Collections.singletonList(latLng))),Toast.LENGTH_SHORT).show();

                    //if (b)
                        //setAreaLength(points);
                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    updateMarkerLocation(marker,true);
                }
            });

        } catch (Exception e) {
            Toast.makeText(Map.this, "Map Error", Toast.LENGTH_SHORT).show();
        }

    }

    public void addMarker(LatLng latLng) {

        if (points.size() == 3){
            Toast.makeText(Map.this,
                    "You can't add more than 3 points", Toast.LENGTH_SHORT).show();
        }
        else {

            MarkerOptions options = new MarkerOptions().position(latLng).draggable(true);
            Marker marker = mMap.addMarker(options);
            marker.setTag(latLng);

            markerList.add(marker);
            points.add(latLng);
            drawPolygon();

        }

    }
    private void drawPolygon() {

        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.fillColor(Color.argb(0, 0, 0, 0));
        polygonOptions.strokeColor(Color.argb(255, 0, 0, 0));
        polygonOptions.strokeWidth(10);
        polygonOptions.addAll(points);
        polygon = mMap.addPolygon(polygonOptions);
    }

    public void getLocation() {

        if (ActivityCompat.checkSelfPermission(Map.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Map.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            }, 2);
        }
    }
}