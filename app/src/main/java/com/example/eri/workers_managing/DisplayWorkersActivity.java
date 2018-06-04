package com.example.eri.workers_managing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eri.workers_managing.model.Response;
import com.example.eri.workers_managing.model.User;
import com.example.eri.workers_managing.network.NetworkUtil;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workers_list);
        users_list = findViewById(R.id.listView);

        subscription=new CompositeSubscription();

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        // Capture the layout's TextView and set the string as its text
       // textView = (TextView) findViewById(R.id.textView);
       // textView.setText(workers);
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
    }
}
