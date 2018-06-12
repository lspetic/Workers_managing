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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radnicigradiliista);
      //  Gradilista=findViewById(R.id.gradilišta);
        //addListenerOnButton();
       // Gradilista.setOnClickListener(view -> logout());
        initSharedPreferences();


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


}
