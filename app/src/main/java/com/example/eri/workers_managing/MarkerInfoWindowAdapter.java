package com.example.eri.workers_managing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.eri.workers_managing.model.Gradiliste;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.Formatter;
import java.util.Locale;

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
   // private StringBuilder sb = new StringBuilder();
   // private Formatter formatter = new Formatter(sb, Locale.getDefault() );

    public MarkerInfoWindowAdapter(Context context) {

        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }

    @Override
    public View getInfoContents(Marker arg0) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View v =  inflater.inflate(R.layout.info_map, null);

        LatLng latLng = arg0.getPosition();

        TextView tvName =  v.findViewById(R.id.tv_lat);
        TextView tvAdd =  v.findViewById(R.id.tv_lng);
        TextView tvBrojRadnika =  v.findViewById(R.id.tv_br_radnika);

        Gradiliste data=(Gradiliste) arg0.getTag();
        String info=data.getName();
        String ad=data.getAddress();
        Integer br;

        if(data.getBr_radnika()==null){
            br=0;
        }else
            br=data.getBr_radnika();




       // String buf= formatter.format("Broj radnika: b",br).toString();
        tvName.setText(info);
        tvAdd.setText(ad);
        tvBrojRadnika.setText(br.toString());

      //  tvLat.setText("Latitude:" + latLng.latitude);
     //   tvLng.setText("Longitude:"+ latLng.longitude);
        return v;
    }
}
