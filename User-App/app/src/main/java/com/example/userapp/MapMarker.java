package com.example.userapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MapMarker extends AppCompatActivity implements OnMapReadyCallback {
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private int ACCESS_LOCATION_REQUEST_CODE = 10001;
    Button markLoc;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    Location temp;
    Double lat, lon;
    GoogleMap mMap;
    String name,phone;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_marker);
        markLoc = findViewById(R.id.markLoc);
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        lat = getIntent().getDoubleExtra("lat",0);
        lon = getIntent().getDoubleExtra("lon",0);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        markLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLoc();
            }
        });
    }
    private void saveLoc(){
        Intent intent = new Intent(MapMarker.this,UserRegister.class);
        intent.putExtra("name",name);
        intent.putExtra("phone",phone);
        intent.putExtra("lat",lat);
        intent.putExtra("lon",lon);

        startActivity(intent);



    }



    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if(checkPermissions()){
            if(isLocationEnabled()){
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if(location == null){
                            requestNewLocationData();
                        }else{
                            temp = location;
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                            Toast.makeText(MapMarker.this, lat+","+lon, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(this, "Please turn on your location...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }else{
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            temp = mLastLocation;
            lat = mLastLocation.getLatitude();
            lon = mLastLocation.getLongitude();
            Toast.makeText(MapMarker.this, lat+","+lon, Toast.LENGTH_SHORT).show();
        }
    };

    private boolean checkPermissions(){
        return ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(mMap!=null){
                    mMap.clear();
                }
                mMap.addMarker(new MarkerOptions().position(latLng));
                lat = latLng.latitude;
                lon = latLng.longitude;
                Toast.makeText(MapMarker.this, lat+","+lon, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
