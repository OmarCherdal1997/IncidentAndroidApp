package com.example.omar.project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG ="MainActivity" ;
    Button start;
 TelephonyManager telephonyManager;
private final int  MY_PERMISSIONS_REQUEST_READ_PHONE_STATE=200;
private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
private static final int LOCATION_PERMISSION_REQUEST_CAMERA = 1235;
private ImageView logo;

protected static boolean locationPermissionGranted=false;

 public static String EMEI;
 private String [] permissions=new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getEMEINumber();
        geolocationPermission();
        cameraPermissions();
        start=(Button)findViewById(R.id.start_btn);
        start.setOnClickListener(this);
        logo=(ImageView)findViewById(R.id.welcome_img);
        Animation logoAnimation=AnimationUtils.loadAnimation(this,R.anim.rotate);
        logo.startAnimation(logoAnimation);

    }
    private void getEMEINumber(){
        telephonyManager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE)){

            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }

        }
        else{
            EMEI=telephonyManager.getDeviceId();

        }

    }
    private void geolocationPermission(){
        String [] permissions=new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                locationPermissionGranted=true;
            }else{ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);}
        }else{ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);}



    }
    private void cameraPermissions(){
        String[] permissions=new String []{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
        if(ContextCompat.checkSelfPermission(this,permissions[0])==PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,permissions[1])==PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,permissions[1])==PackageManager.PERMISSION_GRANTED   ){
            }else{
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission Granted");
                } else {
                    Log.d(TAG, "Permission Refused");
                }
            }
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            locationPermissionGranted = false;
                            Log.d(TAG, "location Denied");
                            return;
                        }
                    }
                    locationPermissionGranted = true;
                    Log.d(TAG, "location Granted");
                }

            }
            case LOCATION_PERMISSION_REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {

                            Log.d(TAG, "Camera Denied");
                            return;
                        }
                    }

                    Log.d(TAG, "Camera Granted");
                }
        }}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_btn:
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }

    }
}
