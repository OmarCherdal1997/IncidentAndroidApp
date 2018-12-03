package com.example.omar.project;

import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsViewerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //private Double langitude,latitude;
    private Boolean status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_viewer);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    public void addIncidentPoints(Double longitude, Double latitude, Boolean status) {
        LatLng point=new LatLng(longitude,latitude);
        MarkerOptions markerOptions;
        if (status){
             markerOptions=new MarkerOptions().position(point).title("longitude: "+MapsActivity.longitude+" latitude: "+MapsActivity.latitude).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }
        else {
             markerOptions=new MarkerOptions().position(point).title("longitude: "+MapsActivity.longitude+" latitude: "+MapsActivity.latitude).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        }

        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,5));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        addIncidentPoints(MapsActivity.longitude,MapsActivity.latitude,false);

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
    
}
