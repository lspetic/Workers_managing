package com.example.eri.workers_managing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.eri.workers_managing.model.Response;
import com.example.eri.workers_managing.model.User;
import com.example.eri.workers_managing.network.NetworkUtil;
import com.example.eri.workers_managing.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RadniciGradilsta extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;
    private Button Radnici;
    private Button Gradilista;
    private MenuItem sp;
    private String prefid;
    CompositeSubscription subscription;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radnicigradiliista);
      //  Gradilista=findViewById(R.id.gradilišta);
        //addListenerOnButton();
       // Gradilista.setOnClickListener(view -> logout());
        initSharedPreferences();
        subscription=new CompositeSubscription();
        load();
    }

    public void openRadnici(View view) {
        Intent intent = new Intent(this, DisplayWorkersActivity.class);
        startActivity(intent);    }

    public void openGradilista(View view) {
        Intent intent = new Intent(this, DisplayGradilistaActivity.class);
        startActivity(intent);
       //logout();
        }

    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mEmail = mSharedPreferences.getString(Constants.EMAIL,"");
        prefid = mSharedPreferences.getString(Constants.ID,"");
        Log.d("++++",mEmail);
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_menu:
              logout();

            case R.id.MojProfil:

                Intent intent=new Intent(RadniciGradilsta.this,MyProfile.class);
                startActivity(intent);


            default:
             return super.onOptionsItemSelected(item);
        }
    }
    public void logout() {

        try {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(Constants.EMAIL, "");
            editor.putString(Constants.TOKEN, "");
            editor.putString(Constants.ID, "");
            editor.apply();

        }catch (Exception e){
            Log.d("+++",e.toString());
        }
         finish();
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    }

    //dohvacanje cijelog usera
    private  void load(){                                           //prikaz dostupnih / nedostupnih radnika


        subscription.add(NetworkUtil.getRetrofit().getProfileMy("a@a.com")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handle,this::handleErr));
    }



    private void handle(User user){                      //prihvat odgovora od retrofita,dobijemo listu i postavljamu ju na adapter


        if (user!=null){
            Log.d("+++asd",user.getPhone());

            Log.d("++++u",user.getName());
            Log.d("++++u",user.getCreated_at());

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

       // Snackbar.make(findViewById(R.id.myprofile),message,Snackbar.LENGTH_SHORT).show();

    }

}
