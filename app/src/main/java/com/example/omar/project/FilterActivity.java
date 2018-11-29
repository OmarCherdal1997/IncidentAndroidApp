package com.example.omar.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import static android.content.ContentValues.TAG;

public class FilterActivity extends AppCompatActivity {
    String[] typee=new String[]{"Accidents  liés aux usagers","Accidents liés aux intervenants","Accidents liés à l\\'environnement physique extérieur"};
    String[] typee1=new String[]{"vitesse excessive","usagers en forte affluence","panne ou arrêt d\\'un véhicule","usagers circulant à contre sens","Autres.."};
    String[] typee2=new String[]{"défaut d\\'organisation de l\\'exploitation","absence de formation du personnel","absence de mise en place de mesures particulières alors que les conditions minimales d\\'exploitation ne sont plus respectées","Autres.."};
    String[] typee3=new String[]{"avalanche aux têtes","chute de pierres ou effondrements aux têtes","séisme","Autres.."};
    Spinner input_detail, input_type;
    private String type;
    private int detailListchoisi=0;
    private String detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        input_detail=(Spinner) findViewById(R.id.input_detail1);
        input_type=(Spinner) findViewById(R.id.input_type1);
        ArrayAdapter<CharSequence> typeAdapter=ArrayAdapter.createFromResource(this,
                R.array.typeFilter, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input_type.setAdapter(typeAdapter);
        input_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=typee[position];
                Log.d(TAG,"le type est : "+type);
                detailListchoisi=position;
                switch (position){
                    case 0:
                        ArrayAdapter<CharSequence> type1Adapter=ArrayAdapter.createFromResource(FilterActivity.this,
                                R.array.type1, android.R.layout.simple_spinner_item);
                        type1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        input_detail.setAdapter(type1Adapter);
                    case 1:
                        ArrayAdapter<CharSequence> type2Adapter=ArrayAdapter.createFromResource(FilterActivity.this,
                                R.array.type2, android.R.layout.simple_spinner_item);
                        type2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        input_detail.setAdapter(type2Adapter);
                    case 2:
                        ArrayAdapter<CharSequence> type3Adapter=ArrayAdapter.createFromResource(FilterActivity.this,
                                R.array.type3, android.R.layout.simple_spinner_item);
                        type3Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        input_detail.setAdapter(type3Adapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        input_detail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        });
    }
}
