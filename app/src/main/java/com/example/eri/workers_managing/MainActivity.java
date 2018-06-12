package com.example.eri.workers_managing;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private LoginFragment mLoginFragment;
    Button Radnici;
    Button Gradilista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_f);
        Intent intent = getIntent();

        //addListenerOnButton();
        loadFragment();
    }
    private void loadFragment(){

        if (mLoginFragment == null) {

            mLoginFragment = new LoginFragment();
        }
        getFragmentManager().beginTransaction().replace(R.id.fragmentFrame,mLoginFragment,LoginFragment.TAG).commit();
    }



    public void openRadnici(View view) {
        Intent intent = new Intent(this, DisplayWorkersActivity.class);
        startActivity(intent);    }

    public void openGradilista(View view) {
        Intent intent = new Intent(this, DisplayGradilistaActivity.class);
        startActivity(intent);    }

}



