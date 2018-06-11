package com.example.eri.workers_managing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eri.workers_managing.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

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
    private SimpleDateFormat dateFormatter;
    private String start;
    private String end;
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
        dateFormatter = new SimpleDateFormat("yyyy-dd-MM", Locale.getDefault());
        //String date=dateFormatter.format(user.getCreated_at());
        if (user.getEnd_job()!=null || user.getStart_job() !=null){
             start=user.getStart_job();
             end=user.getEnd_job();
        }


        if (user!=null){

            mTvName.setText(user.getName());
            mTvEmail.setText(user.getEmail());
            mTvDate.setText("");
            mTvProf.setText(user.getProfession());
            mTvTimeS.setText(start);
            mTvTimeE.setText(end);
            mTvGradiliste.setText(user.getGradiliste());

        }



        return convertView;
    }
}
