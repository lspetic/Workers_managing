package com.example.eri.workers_managing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eri.workers_managing.model.Gradiliste;

import java.util.ArrayList;


public class AdapterGradlista extends ArrayAdapter<Gradiliste> {
private ArrayList<Gradiliste>  gradiliste;
private TextView mTvName;
private TextView mTvAddress;
private TextView mTvLatLong;
private Context context;

    public AdapterGradlista(@NonNull Context context,  @NonNull ArrayList<Gradiliste> gradiliste) {
        super(context, R.layout.site_item_layout, gradiliste);
        this.context=context;
        this.gradiliste=gradiliste;

    }

    @Override
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent){
        Gradiliste gradiliste = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.site_item_layout, parent, false);
        }
        mTvName =  convertView.findViewById(R.id.tv_name);
        mTvAddress =  convertView.findViewById(R.id.tv_address);
        mTvLatLong = convertView.findViewById(R.id.tv_latlong);



        if (gradiliste!=null){

            mTvName.setText(gradiliste.getName());
            mTvAddress.setText(gradiliste.getAddress());
            mTvLatLong.setText("");

        }
        return convertView;
    }


}
