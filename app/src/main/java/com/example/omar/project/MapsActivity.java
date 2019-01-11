package com.example.omar.project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.DecimalFormat;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private static final String TAG = "MapsActivity";
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    protected static double devicelatitude, deviceLongitude;
    protected static double longitude, latitude;
    ImageView location_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        location_btn = (ImageView) findViewById(R.id.location_btn);
        location_btn.setOnClickListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        getDeviceLocation();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);

    }
    private void getDeviceLocation(){
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);
        try{
            if(MainActivity.locationPermissionGranted){
               final  Task<Location> location=fusedLocationProviderClient.getLastLocation();
               location.addOnCompleteListener(new OnCompleteListener<Location>() {
                   @Override
                   public void onComplete(@NonNull Task<Location> task) {
                     if (task.isSuccessful()){
                         Log.d(TAG,"onComplete: found location ! ");
                         Location currentLocation=(Location) task.getResult();
                         devicelatitude=currentLocation.getLatitude();
                         deviceLongitude=currentLocation.getLongitude();
                         moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),DEFAULT_ZOOM,"My location");
                         latitude=devicelatitude;
                         longitude=deviceLongitude;
                         DecimalFormat f = new DecimalFormat();
                         DeclareActivity.langitude.setText(""+f.format(deviceLongitude));
                         DeclareActivity.latitude.setText(""+f.format(devicelatitude));
                     }
                     else {
                         Log.d(TAG,"onComplete:current location is null! ");
                         Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                     }
                   }
               });

            }
        }catch (SecurityException Sexp){
            Log.e(TAG,"getDeviceLocation: " +Sexp.getMessage());
        }


    }

    private void moveCamera(LatLng latLng, float defaultZoom, String title) {
        Log.d(TAG,"moveCamera: moving the camera to: LAt:"+latLng.latitude+",lng:" +latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,defaultZoom));

        if (!title.equals("My location")){
            MarkerOptions options=new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(options);
        }
       // MarkerOptions options=new MarkerOptions().position(latLng).title(title);
        //mMap.addMarker(options);
    }
    private void AddLocation(){
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions marker=new MarkerOptions().position(new LatLng(latLng.latitude,latLng.longitude)).title("incident place");

                marker.draggable(true);


                mMap.addMarker(marker);
                Log.d(TAG,"addMarker:marker added");
                DecimalFormat f = new DecimalFormat();
                f.setMaximumFractionDigits(5);
                 latitude=latLng.longitude;

                longitude=latLng.latitude;
                DeclareActivity.langitude.setText(""+f.format(longitude));
                DeclareActivity.latitude.setText(""+f.format(latitude));

                Toast.makeText(MapsActivity.this, "Incident added successfully!!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.location_btn:
                AddLocation();

                //startActivity(new Intent(MapsActivity.this,DeclareActivity.class));
        }


    }
}
