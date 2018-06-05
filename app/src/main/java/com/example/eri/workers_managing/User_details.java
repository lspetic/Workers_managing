package com.example.eri.workers_managing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;

import com.example.eri.workers_managing.model.User;


public class User_details extends AppCompatActivity {

    private User data;

@Override
protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    Bundle extras = getIntent().getExtras();
    try {

        if (extras != null) {
              data = (User)extras.getSerializable("User_data");
             Log.d("++++", data.getEmail());
        }
    }catch(Exception e){
        Log.d("++error++",e.toString());
    }



}



}

