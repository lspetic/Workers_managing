package com.example.eri.workers_managing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.eri.workers_managing.utils.Constants;

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
        setContentView(R.layout.activity_main);
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
        Log.d("++++",prefid);
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void onGroupItemClick(MenuItem item) {
        // One of the group items (using the onClick attribute) was clicked
        // The item parameter passed here indicates which item it is
        // All other menu item clicks are handled by onOptionsItemSelected()
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_search:
              logout();

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
