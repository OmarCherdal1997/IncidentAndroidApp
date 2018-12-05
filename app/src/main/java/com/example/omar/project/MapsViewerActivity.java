package com.example.omar.project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsViewerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //private Double langitude,latitude;
    private Boolean status;

    private final String TAG = "MapsViewerActivity";
    private float defaultZoom = 15f;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATIONPERMISSIONGRANTED = 111;
    private static boolean mLOCATIONPERMISSIONGRANTED = false;
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_viewer);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocationPermission();


    }

    public void addIncidentPoints(Double longitude, Double latitude, Boolean status) {
        LatLng point = new LatLng(longitude, latitude);
        MarkerOptions markerOptions;
        if (status) {
            markerOptions = new MarkerOptions().position(point).title("longitude: " + MapsActivity.longitude + " latitude: " + MapsActivity.latitude).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        } else {
            markerOptions = new MarkerOptions().position(point).title("longitude: " + MapsActivity.longitude + " latitude: " + MapsActivity.latitude).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        }

        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 5));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //moveCamera(new LatLng(MapsActivity.devicelatitude,MapsActivity.deviceLongitude),defaultZoom,"My location");
        if (mLOCATIONPERMISSIONGRANTED) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
        }



        //addIncidentPoints(MapsActivity.longitude,MapsActivity.latitude,false);

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
    private void moveCamera(LatLng latLng, float defaultZoom, String title) {
        Log.d(TAG,"moveCamera: moving the camera to: LAt:"+latLng.latitude+",lng:" +latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,defaultZoom));
        MarkerOptions options;
        if (!title.equals("My location")){
             options=new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(options);
        }
        //MarkerOptions options=new MarkerOptions().position(latLng).title(title);
        //mMap.addMarker(options);
    }
    private void getLocationPermission(){
        String[] permission=new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION )==PackageManager.PERMISSION_GRANTED){
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION )==PackageManager.PERMISSION_GRANTED){
            mLOCATIONPERMISSIONGRANTED=true;
        }else{
            ActivityCompat.requestPermissions(this,permission,LOCATIONPERMISSIONGRANTED);
        }

        }else {
            ActivityCompat.requestPermissions(this,permission,LOCATIONPERMISSIONGRANTED);
            Log.d(TAG,"");
        }

    }
    private void initMap(){
        Log.d(TAG,"Initializing Map!!");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    private void getDeviceLocation(){
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);
        try{
        if(mLOCATIONPERMISSIONGRANTED){
            Task location=fusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Location currentLocation=(Location) task.getResult();
                        moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),defaultZoom,"My location");
                    }
                }
            });
        }}
        catch (SecurityException e){
            Log.e(TAG,"getDeviceLocation: securityException: "+e.getMessage());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLOCATIONPERMISSIONGRANTED=false;
        switch (requestCode){
            case LOCATIONPERMISSIONGRANTED:
                if(grantResults.length>0 ){
                    for (int i=0; i<grantResults.length;i++){
                        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                            mLOCATIONPERMISSIONGRANTED=true;
                            initMap();
                        }
                        else {
                            ActivityCompat.requestPermissions(this,permissions,LOCATIONPERMISSIONGRANTED);
                        }
                    }



                }
        }
    }
}
