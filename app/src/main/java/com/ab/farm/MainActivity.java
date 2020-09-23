package com.ab.farm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                getLocation();
                Intent mainIntent = new Intent(MainActivity.this,HomeScreen.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, 3000);



    }

    private void getLocation() {

        final SharedPreferences locationss=getSharedPreferences("location",MODE_PRIVATE);
        final SharedPreferences.Editor leditor=locationss.edit();

        FusedLocationProviderClient fusedLocationClient;
        LocationManager locationManager;


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
        ){

            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION
            },2);
        }else {

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(android.location.Location location) {

                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object

                                Double latitude = location.getLatitude();
                                Double longitude = location.getLongitude();

//                                Toast.makeText(MainActivity.this, latitude+longitude+"", Toast.LENGTH_SHORT).show();

                                leditor.putString("lat", latitude.toString());
                                leditor.putString("long", longitude.toString());
                                leditor.apply();

//                                double laty = Double.parseDouble(lati);
//                                double longy = Double.parseDouble(longi);
//                                Geocoder geocoder = new Geocoder(SplashActivity.this, Locale.getDefault());
//                                List<Address> addresses = new ArrayList<>();
//                                try {
//                                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                                String cityName = addresses.get(0).getLocality();
//
//                                leditor.putString("city",cityName);
//                                leditor.apply();

                                location.reset();

                            } else {
//                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                                startActivity(intent);
                            }

                        }

                    });
        }
    }
}