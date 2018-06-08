package com.example.eri.workers_managing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eri.workers_managing.model.User;

import java.util.ArrayList;

public class ProfileAdapter extends ArrayAdapter<User> {
    private Context context;
    private ArrayList<User> user;
    private TextView mTvName;
    private TextView mTvEmail;
    private TextView mTvDate;
    private TextView mTvProf;
    private TextView mTvTimeS;
    private TextView mTvTimeE;
    private TextView mTvGradiliste;
    ProfileAdapter(Context context, ArrayList<User> user){

        super(context, R.layout.list_item_layout, user);
        this.context=context;
        this.user=user;
    }
    @Override
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent){
        User user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }
        mTvName =  convertView.findViewById(R.id.tv_name);
        mTvEmail =  convertView.findViewById(R.id.tv_email);
        mTvDate = convertView.findViewById(R.id.tv_date);
        mTvProf =  convertView.findViewById(R.id.tv_prof);
        mTvTimeS =  convertView.findViewById(R.id.tv_vremena1);
        mTvTimeE =  convertView.findViewById(R.id.tv_vremena2);
        mTvGradiliste = convertView.findViewById(R.id.tv_grad);


        if (user!=null){

            mTvName.setText(user.getName());
            mTvEmail.setText(user.getEmail());
            mTvDate.setText(user.getCreated_at());
            mTvProf.setText(user.getProfession());
            mTvTimeS.setText(user.getStart_job());
            mTvTimeE.setText(user.getEnd_job());
            mTvGradiliste.setText(user.getGradiliste());
        }



        return convertView;
    }
}
