package com.ab.farm.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.Toast;
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

import java.util.ArrayList;
import java.util.Collections;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ArrayList<Marker> markerList = new ArrayList();
    ArrayList<LatLng> points = new ArrayList<>();
    LatLng sydney;
    GoogleMap mMap;
    Double latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {

            sydney = new LatLng(latitude, longitude);

            float zoomLevel = (float) 16.0;
            mMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title("Your Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));

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
            Toast.makeText(Map.this, "Connection Error", Toast.LENGTH_SHORT).show();
        }

    }

    public void addMarker(LatLng latLng) {

        MarkerOptions options = new MarkerOptions().position(latLng).draggable(true);
        Marker marker = mMap.addMarker(options);
        marker.setTag(latLng);

        markerList.add(marker);
        points.add(latLng);
        drawPolygon();
        Toast.makeText(Map.this,
                String.valueOf(SphericalUtil.computeArea(points)),Toast.LENGTH_SHORT).show();

    }
    private void drawPolygon() {

        Polygon polygon = null;

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