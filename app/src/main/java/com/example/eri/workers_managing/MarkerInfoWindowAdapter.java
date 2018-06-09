package com.example.eri.workers_managing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.eri.workers_managing.model.Gradiliste;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
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
        TextView tvLat =  v.findViewById(R.id.tv_lat);
        TextView tvLng =  v.findViewById(R.id.tv_lng);
        Gradiliste data=(Gradiliste) arg0.getTag();
        String info=data.getName();
        String ad=data.getAddress();



        tvLat.setText(info);
        tvLng.setText(ad);

      //  tvLat.setText("Latitude:" + latLng.latitude);
     //   tvLng.setText("Longitude:"+ latLng.longitude);
        return v;
    }
}
