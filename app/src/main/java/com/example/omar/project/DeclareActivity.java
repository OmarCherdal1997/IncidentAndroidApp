package com.example.omar.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class DeclareActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG ="DeclareActivity" ;
    ImageView map_btn;
    Button submit;
    static ImageView  camera_btn;
    Spinner input_detail, input_type;
    String type, detail,Description,contexte;
    EditText input_secteur,input_description;
    Bitmap bitmap;
    int detailListchoisi=0;
protected static TextView   langitude,latitude;
String[] typee=new String[]{"type d\\'incident","Accidents  liés aux usagers","Accidents liés aux intervenants","Accidents liés à l\\'environnement physique extérieur"};
String[] typee1=new String[]{"Detail","vitesse excessive","usagers en forte affluence","panne ou arrêt d\\'un véhicule","usagers circulant à contre sens","Autres.."};
String[] typee2=new String[]{"Detail","défaut d\\'organisation de l\\'exploitation","absence de formation du personnel","absence de mise en place de mesures particulières alors que les conditions minimales d\\'exploitation ne sont plus respectées","Autres.."};
String[] typee3=new String[]{"Detail","avalanche aux têtes","chute de pierres ou effondrements aux têtes","séisme","Autres.."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare);
        map_btn=(ImageView) findViewById(R.id.map_btn);
        camera_btn=(ImageView)findViewById(R.id.camera_btn);
        langitude=(TextView)findViewById(R.id.langitude);
        latitude=(TextView)findViewById(R.id.latitude);
        input_detail=(Spinner) findViewById(R.id.input_detail);
        input_secteur=(EditText)findViewById(R.id.input_secteur) ;
        input_description=(EditText)findViewById(R.id.input_description) ;
        submit=(Button)findViewById(R.id.submit_btn);
        submit.setOnClickListener(this);
        input_type=(Spinner) findViewById(R.id.input_type);
        map_btn.setOnClickListener(this);
        camera_btn.setOnClickListener(this);

        //Spinner adapters

         ArrayAdapter<CharSequence> typeAdapter=ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input_type.setAdapter(typeAdapter);
        input_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=typee[position];
                Log.d(TAG,"le type est : "+type);
                detailListchoisi=position;
                switch (typee[position]){
                    case "type d\\'incident":
                        ArrayAdapter<CharSequence> type0Adapter=ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.detail0, android.R.layout.simple_spinner_item);
                        type0Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        input_detail.setAdapter(type0Adapter);
                        break;
                    case "Accidents  liés aux usagers":
                        ArrayAdapter<CharSequence> type1Adapter=ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.type1, android.R.layout.simple_spinner_item);
                        type1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        input_detail.setAdapter(type1Adapter);
                        break;
                    case "Accidents liés aux intervenants":
                        ArrayAdapter<CharSequence> type2Adapter=ArrayAdapter.createFromResource(DeclareActivity.this,
                                R.array.type2, android.R.layout.simple_spinner_item);
                        type2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        input_detail.setAdapter(type2Adapter);
                        break;
                    case "Accidents liés à l\\'environnement physique extérieur":
                        ArrayAdapter<CharSequence> type3Adapter=ArrayAdapter.createFromResource(DeclareActivity.this,
                                R.array.type3, android.R.layout.simple_spinner_item);
                        type3Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        input_detail.setAdapter(type3Adapter);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*input_detail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(detailListchoisi==1){
                    detail=typee1[position];
                    Log.d(TAG,"le detail est : "+detail);
                }
                else if(detailListchoisi==2){
                    detail=typee2[position];
                    Log.d(TAG,"le detail est : "+detail);
                }
                else if(detailListchoisi==3){
                    detail=typee3[position];
                    Log.d(TAG,"le detail est : "+detail);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/




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
            case R.id.submit_btn:
                sendData();
                break;
        }
    }
    //Data sending Function
    private void sendData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         bitmap=(Bitmap)data.getExtras().get("data");
        camera_btn.setImageBitmap(bitmap);
    }
}

