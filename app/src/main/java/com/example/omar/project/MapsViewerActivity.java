package com.example.omar.project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Calendar;
import java.util.Date;

public class MapsViewerActivity extends FragmentActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    //private Double langitude,latitude;
    private Boolean status;
    DatabaseHelper databaseHelper;
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
        databaseHelper=new DatabaseHelper(this.getApplicationContext());





    }
    public void setPoint(){
       // Cursor data=databaseHelper.getDataFilter(FilterFragment.detailFilter,FilterFragment.typeFilter);
        int n=FilterFragment.PointList.size();

        for(int i=0;i<5;i++)
    {
        double lat=FilterFragment.PointList.get(i).getLatitude();
        double longi=FilterFragment.PointList.get(i).getLongitude();
        while (lat!=0 & longi!=0){
        addIncidentPoints(lat,longi,false);}
    }

    }

    public  void addIncidentPoints(Double longitude, Double latitude, Boolean status) {
        LatLng point = new LatLng(longitude, latitude);
        MarkerOptions markerOptions;
        if (status) {
            markerOptions = new MarkerOptions().position(point).title("longitude: " + longitude + " latitude: " + latitude).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        } else {
            markerOptions = new MarkerOptions().position(point).title("longitude: " + longitude + " latitude: " + latitude).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

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


         /*mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
             @Override
             public void onMapLoaded() {
                 setPoint();
             }
         });*/
        int n=FilterFragment.PointList.size();
        double lat=FilterFragment.PointList.get(n-1).getLatitude();
        double longi=FilterFragment.PointList.get(n-1).getLongitude();
        double lat1=FilterFragment.PointList.get(n-2).getLatitude();
        double longi1=FilterFragment.PointList.get(n-2).getLongitude();
        double lat2=FilterFragment.PointList.get(n-3).getLatitude();
        double longi2=FilterFragment.PointList.get(n-3).getLongitude();
        double lat3=FilterFragment.PointList.get(n-3).getLatitude();
        double longi3=FilterFragment.PointList.get(n-3).getLongitude();
        double lat4=FilterFragment.PointList.get(n-4).getLatitude();
        double longi4=FilterFragment.PointList.get(n-4).getLongitude();
        double lat5=FilterFragment.PointList.get(n-5).getLatitude();
        double longi5=FilterFragment.PointList.get(n-5).getLongitude();
        double lat6=FilterFragment.PointList.get(n-6).getLatitude();
        double longi6=FilterFragment.PointList.get(n-6).getLongitude();
        double lat7=FilterFragment.PointList.get(n-7).getLatitude();
        double longi7=FilterFragment.PointList.get(n-7).getLongitude();
            addIncidentPoints(lat,longi,false);
            addIncidentPoints(lat1,longi1,false);
            addIncidentPoints(lat2,longi2,false);
            addIncidentPoints(lat3,longi3,false);
            addIncidentPoints(lat4,longi4,false);
            addIncidentPoints(lat5,longi5,false);
            addIncidentPoints(lat6,longi6,false);
            addIncidentPoints(lat7,longi7,false);






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
