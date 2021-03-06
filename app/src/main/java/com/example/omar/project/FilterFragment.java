package com.example.omar.project;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment  {
    static ArrayList<Reclamation> RecLamatioList=new ArrayList<Reclamation>();
    DatabaseHelper databaseHelper;
    MapsViewerActivity mapsViewerActivity=new MapsViewerActivity();
    public double longi;
    public double lat;
 static  String typeFilter,detailFilter;
 static ArrayList<Point> PointList=new ArrayList<Point>();
int detailListchoisi=0;
    Spinner detail,type;
    String[] typee=new String[]{"type d\\'incident","Accidents  liés aux usagers","Accidents liés aux intervenants","Accidents liés à l\\'environnement physique extérieur"};
    String[] typee1=new String[]{"Detail","vitesse excessive","usagers en forte affluence","panne ou arrêt d\\'un véhicule","usagers circulant à contre sens","Autres.."};
    String[] typee2=new String[]{"Detail","défaut d\\'organisation de l\\'exploitation","absence de formation du personnel","absence de mise en place de mesures particulières alors que les conditions minimales d\\'exploitation ne sont plus respectées","Autres.."};
    String[] typee3=new String[]{"Detail","avalanche aux têtes","chute de pierres ou effondrements aux têtes","séisme","Autres.."};
    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_filter, container, false);
        Button search=(Button)view.findViewById(R.id.search_btn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();
            }
        });
        type=(Spinner)view.findViewById(R.id.filter_type);
        detail=(Spinner)view.findViewById(R.id.filter_detail);
        ArrayAdapter<CharSequence> adapter_type=ArrayAdapter.createFromResource(getContext(),R.array.type,android.R.layout.simple_spinner_item);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter_type);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeFilter=typee[position];
                Log.d(TAG,"le type est : "+typeFilter);
                detailListchoisi=position;
                switch (typee[position]){
                    case "type d\\'incident":
                        ArrayAdapter<CharSequence> type0Adapter=ArrayAdapter.createFromResource(getContext(),
                                R.array.detail0, android.R.layout.simple_spinner_item);
                        type0Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        detail.setAdapter(type0Adapter);
                        break;
                    case "Accidents  liés aux usagers":
                        ArrayAdapter<CharSequence> type1Adapter=ArrayAdapter.createFromResource(getContext(),
                                R.array.type1, android.R.layout.simple_spinner_item);
                        type1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        detail.setAdapter(type1Adapter);
                        break;
                    case "Accidents liés aux intervenants":
                        ArrayAdapter<CharSequence> type2Adapter=ArrayAdapter.createFromResource(getContext(),
                                R.array.type2, android.R.layout.simple_spinner_item);
                        type2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        detail.setAdapter(type2Adapter);
                        break;
                    case "Accidents liés à l\\'environnement physique extérieur":
                        ArrayAdapter<CharSequence> type3Adapter=ArrayAdapter.createFromResource(getContext(),
                                R.array.type3, android.R.layout.simple_spinner_item);
                        type3Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        detail.setAdapter(type3Adapter);
                        break;}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        detail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(detailListchoisi==1){
                    detailFilter=typee1[position];
                    Log.d(TAG,"le detail est : "+detailFilter);
                }
                else if(detailListchoisi==2){
                    detailFilter=typee2[position];
                    Log.d(TAG,"le detail est : "+detailFilter);
                }
                else if(detailListchoisi==3){
                    detailFilter=typee3[position];
                    Log.d(TAG,"le detail est : "+detailFilter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        databaseHelper=new DatabaseHelper(this.getContext());
        return view;

    }

    public void fetchData(){
        Cursor data=databaseHelper.getData();
        if(data.getCount()==0){
            Log.d(TAG,"Filter Fragment: Fetchdata la table est Vide");
        }
        else {
            Log.d(TAG,"Filter Fragment: Fetchdata le nombre d'enregistrement: "+data.getCount());

   while (data.moveToNext()) {
       Log.d(TAG, "Filter Fragment: Fetchdata  latitude: " + data.getString(4));
       Log.d(TAG, "Filter Fragment: Fetchdata longitude: " + data.getString(5));
       Log.d(TAG, "Filter Fragment: Fetchdata longitude: " + data.getString(1));
       Log.d(TAG, "Filter Fragment: Fetchdata longitude: " + data.getString(2));
       Log.d(TAG, "Filter Fragment: Fetchdata longitude: " + data.getString(6));
       PointList.add(new Point(Double.parseDouble(data.getString(4)), Double.parseDouble(data.getString(5))));
       RecLamatioList.add(new Reclamation(databaseHelper.stringToBitmap(data.getString(6)),data.getString(2),data.getString(1)));
       // longi=Double.parseDouble(data.getString(5));
       // lat=Double.parseDouble(data.getString(4));


   }
        }
    }
}
