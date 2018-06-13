package com.example.eri.workers_managing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.eri.workers_managing.model.User;

public class User_spec extends AppCompatActivity{
    private User user;
    private TextView mTvName;
    private TextView mTvEmail;
    private TextView mTvProf;
    private TextView mTvSurname;
    private TextView mTvAddress;
    private TextView mTvPhone;
    protected void onCreate(Bundle savedInstanceState){
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.myprofile2);
        super.onCreate(savedInstanceState);
        try {

            if (extras != null) {
                user = (User)extras.getSerializable("User_data");
                Log.d("++++", user.getEmail());
            }
        }catch(Exception e){
            Log.d("++error++",e.toString());

        }
        mTvName =  findViewById(R.id.tv_name);
        mTvEmail =  findViewById(R.id.tv_email);

        mTvProf =  findViewById(R.id.tv_prof);
        // mTvSurname =  findViewById(R.id.tv_surname);
        mTvAddress =  findViewById(R.id.tv_address);
        mTvPhone =  findViewById(R.id.tv_phone);
//        mTvTimeS =  findViewById(R.id.tv_vremena1);
//        mTvTimeE =  findViewById(R.id.tv_vremena2);
//        mTvGradiliste =findViewById(R.id.tv_grad);
        mTvAddress = findViewById(R.id.tv_address);
        mTvPhone = findViewById(R.id.tv_phone);
        mTvSurname=findViewById(R.id.et_surname);

        mTvPhone.setVisibility(View.VISIBLE);
        mTvAddress.setVisibility(View.VISIBLE);

        mTvName.setText(user.getName());
        mTvEmail.setText(user.getEmail());
     //   mTvSurname.setText(user.getSurname());
        mTvPhone.setText(user.getPhone());
        mTvAddress.setText(user.getAddress());
        mTvProf.setText(user.getProfession());


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
