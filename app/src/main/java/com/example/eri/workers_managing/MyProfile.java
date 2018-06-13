package com.example.eri.workers_managing;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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
    private EditText etAddress,etPhone;
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
        etAddress = findViewById(R.id.et_adresa);
        etPhone = findViewById(R.id.et_phone);
        mTvSurname=findViewById(R.id.et_surname);

        initSharedPreferences();
        load();



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


        mTvName.setText(user.getName());
        mTvSurname.setText(user.getSurname());
        mTvEmail.setText(user.getEmail());

        mTvProf.setText(user.getProfession());

           String adr;
           String phone;

            if(user.getAddress()==null){

                etAddress.setVisibility(View.VISIBLE);
                etAddress.setHint("Dodaj adresu");
                etAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(event.getKeyCode()==KeyEvent.KEYCODE_ENTER){
                           String address=etAddress.getText().toString();
                           user.setAddress(address);
                           sendDataAd(user);
                        }
                        return false;
                    }
                });



            }else{
                adr=user.getAddress();
                mTvAddress.setVisibility(View.VISIBLE);
                mTvAddress.setText(adr);
            }
            if(user.getPhone()==null){

                etPhone.setVisibility(View.VISIBLE);
                etPhone.setHint("Dodaj telefon");
                etPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(event.getKeyCode()==KeyEvent.KEYCODE_ENTER){
                            String phone=etPhone.getText().toString();
                            user.setPhone(phone);
                            sendDataPh(user);
                        }
                        return false;
                    }
                });

            }else{
                phone=user.getPhone();
                mTvPhone.setVisibility(View.VISIBLE);
                mTvPhone.setText(phone);

            }



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
    private  void sendDataAd(User data){                                           //prikaz dostupnih / nedostupnih radnika


        subscription.add(NetworkUtil.getRetrofit().putProfileAddress(mEmail,data)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleRes,this::handleErr));
    }

    private  void sendDataPh(User data){                                           //prikaz dostupnih / nedostupnih radnika


        subscription.add(NetworkUtil.getRetrofit().putProfilePhone(mEmail,data)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleRes,this::handleErr));
    }
    private void handleRes(Response response){
        showSnackBarMessage(response.getMessage());
    }


}
