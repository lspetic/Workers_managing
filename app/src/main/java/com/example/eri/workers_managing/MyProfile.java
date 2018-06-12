package com.example.eri.workers_managing;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.eri.workers_managing.model.Response;
import com.example.eri.workers_managing.model.User;
import com.example.eri.workers_managing.network.NetworkUtil;
import com.example.eri.workers_managing.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MyProfile extends AppCompatActivity {
    private CompositeSubscription subscription;
    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;

    private TextView mTvName;
    private TextView mTvEmail;
    private TextView mTvDate;
    private TextView mTvProf;
    private TextView mTvSurname;
    private TextView mTvAddress;
    private TextView mTvPhone;
    private TextView mTvTimeS;
    private TextView mTvTimeE;
    private TextView mTvGradiliste;
    private SimpleDateFormat dateFormatter;
    private String start;
    private String end;
    private User u;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile2);

        subscription=new CompositeSubscription();

        mTvName =  findViewById(R.id.tv_name);
        mTvEmail =  findViewById(R.id.tv_email);
        mTvDate = findViewById(R.id.tv_date);
        mTvProf =  findViewById(R.id.tv_prof);
       // mTvSurname =  findViewById(R.id.tv_surname);
        mTvAddress =  findViewById(R.id.tv_address);
        mTvPhone =  findViewById(R.id.tv_phone);
        mTvTimeS =  findViewById(R.id.tv_vremena1);
        mTvTimeE =  findViewById(R.id.tv_vremena2);
        mTvGradiliste =findViewById(R.id.tv_grad);

        initSharedPreferences();
        load();
Log.d("aaaaaa","vbbbbbbbbb");
    }

    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mEmail = mSharedPreferences.getString(Constants.EMAIL,"");
        Log.d("aaaaaaaaaaaaa",mEmail);


    }
    private  void load(){                                           //prikaz dostupnih / nedostupnih radnika


        subscription.add(NetworkUtil.getRetrofit().getProfileMy(mEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handle,this::handleErr));
    }



    private void handle(User user){                      //prihvat odgovora od retrofita,dobijemo listu i postavljamu ju na adapter

        Log.d("u++++u",user.getEmail());
        Log.d("u++++u",user.getName());
        String name_surname;
        if(user.getSurname()!=null){
            name_surname=user.getName()+user.getSurname();
        }else{
            name_surname=user.getName();
        }


            String adr;
            String phone;

            if(user.getAddress()==null){
                adr="Dodaj adresu";

            }else{
                adr=user.getAddress();
            }
            if(user.getPhone()==null){
                phone="Dodaj telefon";

            }else{
                phone=user.getPhone();
            }

            mTvName.setText(name_surname);
            mTvEmail.setText(user.getEmail());

            mTvProf.setText(user.getProfession());
         //   mTvSurname.setText(user.getSurname());
          mTvAddress.setText(adr);
            mTvPhone.setText(phone);
          /*    mTvTimeS.setText(start);
            mTvTimeE.setText(end);*/
           // mTvGradiliste.setText(user.getGradiliste());




    }
    private void handleErr(Throwable error){

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }
    private void showSnackBarMessage(String message) {

        Snackbar.make(findViewById(R.id.myprofile2),message,Snackbar.LENGTH_SHORT).show();

    }
}
