package com.example.eri.workers_managing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.eri.workers_managing.model.Response;
import com.example.eri.workers_managing.model.User;
import com.example.eri.workers_managing.network.NetworkUtil;
import com.example.eri.workers_managing.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;
import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import retrofit2.adapter.rxjava.HttpException;

public class DisplayWorkersActivity extends AppCompatActivity {
    TextView textView;
    String workers = "Erika Fafandel";
    ListView users_list;
    CompositeSubscription subscription;
    ArrayList<User> test;
    User user;
    View popup_menu;
    MenuItem sort_time,sort_av;
    PopupMenu popup;
    private SharedPreferences mSharedPreferences;
    private String prefId;
    private RadniciGradilsta radnici;
    private Intent intent_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.workers_list);
        users_list = findViewById(R.id.listView);



        subscription=new CompositeSubscription();

        sort_time=findViewById(R.id.ch_sort_time);
        sort_av=findViewById(R.id.ch_sort_av);





        user=new User();
        User test2=new User();
        User test3=new User();

        user.setName("Pero");
        user.setEmail("lino@lino.com");

        user.setProffesion("Stolar");

        test2.setName("Mirko");
        test2.setEmail("mirko@mirko.com");
        test2.setProffesion("ubojica");

        test3.setName("Mirko");
        test3.setEmail("mirko@mpazi.com");
        test3.setProffesion("pekar");

        test =new ArrayList<User>();
        test.add(user);
        test.add(test2);
        test.add(test3);
        Log.d("+++",test.get(0).getName());

        load();
        Observable<ArrayList> test;



        initPref();
        intent_logout = new Intent(this, MainActivity.class);
        users_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Dohvat odabrane stvake (objekt Student):
                try {

                User studentToUpdate = (User) users_list.getItemAtPosition(position);


                // Slanje odabranog Student objekta u novu aktivnost (za azuriranje podataka):
                if (studentToUpdate != null) {
                    if(prefId.equals("ivo@admin.com")) {
                        Intent intent = new Intent(
                                DisplayWorkersActivity.this,
                                User_details.class);
                        intent.putExtra("User_data", studentToUpdate);
                        startActivity(intent);
                    }else{



                        Intent intent = new Intent(
                                DisplayWorkersActivity.this,
                                User_spec.class);
                        intent.putExtra("User_data", studentToUpdate);
                        startActivity(intent);
                    }

                }
            }catch(Exception e){
                    Log.d("+++",e.toString());
                }
            }
            });

        }




private void initPref(){
    mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    prefId=mSharedPreferences.getString(Constants.EMAIL,"");

}
    private  void load(){
        subscription.add(NetworkUtil.getRetrofit().getProfile()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handle,this::handleErr));
    }


    private void handleR(User user){
        Log.d("++++",user.getName());
        test.add(user);
        handle(test);
    }
    private void handle(ArrayList<User> user){
        if (user != null && user.size()!=0){
            ProfileAdapter adapter = new ProfileAdapter(this, user);
            users_list.setAdapter(adapter);

        }
    }
    private void handleErr(Throwable error){
        Log.d("***","NetworkError"); //stavit toast poslje
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

        Snackbar.make(findViewById(R.id.workers_list),message,Snackbar.LENGTH_SHORT).show();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filter, menu);


        return super.onCreateOptionsMenu(menu);
    }

    public void onGroupItemClick(MenuItem item) {
        // One of the group items (using the onClick attribute) was clicked
        // The item parameter passed here indicates which item it is
        // All other menu item clicks are handled by onOptionsItemSelected()
    }
    public boolean onOptionsItemSelected(MenuItem item){
        popup_menu=findViewById(R.id.action_search);

        switch (item.getItemId()) {
            case R.id.action_search:
                showPopup(popup_menu);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showPopup(View v) {

        popup = new PopupMenu(DisplayWorkersActivity.this, popup_menu);

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_pupup, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_logout:

                        intent_logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent_logout);
                        return true;

                   case R.id.ch_sort_time:
                        if(item.isChecked()){
                            item.setChecked(false);

                        }else{
                            Log.d("+++","sss");
                            item.setChecked(true);

                        }

                        return true;
                    case R.id.ch_sort_av:
                        item.setChecked(!item.isChecked());

                        return true;

                    default:
                        return false;
                }

            }
        });


        popup.show();
    }



}
