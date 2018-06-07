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

        if (user!=null){

            mTvName.setText(user.getName());
            mTvEmail.setText(user.getEmail());
            mTvDate.setText(user.getCreated_at());
            mTvProf.setText(user.getProfession());
        }



        return convertView;
    }
}
