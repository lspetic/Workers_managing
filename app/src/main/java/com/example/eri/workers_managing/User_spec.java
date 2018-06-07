package com.example.eri.workers_managing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.eri.workers_managing.model.User;

public class User_spec extends AppCompatActivity{
    private User user;

    protected void onCreate(Bundle savedInstanceState){
        Bundle extras = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        try {

            if (extras != null) {
                user = (User)extras.getSerializable("User_data");
                Log.d("++++", user.getEmail());
            }
        }catch(Exception e){
            Log.d("++error++",e.toString());

        }
if(user!=null){
    Log.d("++++",user.getName());
}
try{
    Log.d("++++",user.getStart_job());

}catch (Exception e){
            Log.d("+++",e.toString());
}
       //


    }

}
