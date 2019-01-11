package com.example.omar.project;

import android.content.Context;
import android.support.v7.widget.ActivityChooserView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Reclamation> L;
    public CustomAdapter(Context context,ArrayList<Reclamation> L) {
        super(context,0,L);
        this.L=L;
        this.context=context;

    }
    static class ViewHolder{
        ImageView img;
        TextView type_txt;
        TextView detaille_txt;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View v=convertView;
        ViewHolder viewHolder;
        if(v==null){
            v=LayoutInflater.from(getContext()).inflate(R.layout.infowindow,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.img=(ImageView) v.findViewById(R.id.incident_img);
            viewHolder.detaille_txt=(TextView) v.findViewById(R.id.incident_detail_response);
            viewHolder.type_txt=(TextView) v.findViewById(R.id.incident_type_response);
            v.setTag(viewHolder);
        }
        else {viewHolder=(ViewHolder)v.getTag();}
        Reclamation rec=L.get(position);
        ImageView img=viewHolder.img;

        img.setImageBitmap(rec.getBitmap());
        TextView type=viewHolder.type_txt;
        type.setText(rec.getType());
        TextView detail=viewHolder.detaille_txt;
        detail.setText(rec.getDetail());
        return v;
    }
}
