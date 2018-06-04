package com.example.eri.workers_managing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.eri.workers_managing.utils.Constants;

public class RadniciGradilsta extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;
    private Button Radnici;
    private Button Gradilista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //addListenerOnButton();

    }

    public void openRadnici(View view) {
        Intent intent = new Intent(this, DisplayWorkersActivity.class);
        startActivity(intent);    }

    public void openGradilista(View view) {
       // Intent intent = new Intent(this, DisplayGradilistaActivity.class);
       // startActivity(intent);
       logout();
        }

    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mEmail = mSharedPreferences.getString(Constants.EMAIL,"");
    }

    private void logout() {

        try {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(Constants.EMAIL, "");
            editor.putString(Constants.TOKEN, "");
            editor.apply();
            finish();
        }catch (Exception e){
            Log.d("+++",e.toString());
        }
        finish();
      //  Intent intent = new Intent(this, MainActivity.class);
      //  startActivity(intent);
    }

}
