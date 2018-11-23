package com.example.omar.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class DeclareActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG ="DeclareActivity" ;
    ImageView map_btn;
    static ImageView  camera_btn;
    Spinner input_detail, input_province, input_type;
protected static TextView   langitude,latitude;
String[] provinces=new String[]{"Agadir Ida-Outanane"," Al Haouz"," Al Hoceïma "," Aousserd"," Assa-Zag"," Azilal","Béni-Mellal"," Benslimane",
" Berrechid","Berkane","Boujdour "," Boulemane "," Casablanca","Chefchaouen","Chichaoua","Chtouka-Aït Baha ","Driouch ","El Hajeb "," El Jadida","Errachidia "," Essaouira"," Es-Semara","Fès","Kénitra","Rabat ","Tétouan","Tanger-Asilah"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare);
        map_btn=(ImageView) findViewById(R.id.map_btn);
        camera_btn=(ImageView)findViewById(R.id.camera_btn);
        langitude=(TextView)findViewById(R.id.langitude);
        latitude=(TextView)findViewById(R.id.latitude);
        input_detail=(Spinner) findViewById(R.id.input_detail);
        input_province=(Spinner) findViewById(R.id.input_province);
        input_type=(Spinner) findViewById(R.id.input_type);
        map_btn.setOnClickListener(this);
        camera_btn.setOnClickListener(this);
        ArrayAdapter<CharSequence> provinceAdapter=ArrayAdapter.createFromResource(this,
                R.array.province, android.R.layout.simple_spinner_item);
         provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         input_province.setAdapter(provinceAdapter);




    }
    private void getImage(){
        Log.d(TAG,"getImage: openning dialog to choose new photo");
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.map_btn:
                startActivity(new Intent(DeclareActivity.this,MapsActivity.class));
                break;
            case R.id.camera_btn:
                getImage();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap=(Bitmap)data.getExtras().get("data");
        camera_btn.setImageBitmap(bitmap);
    }
}
