package com.example.eri.workers_managing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.eri.workers_managing.model.Gradiliste;
import com.example.eri.workers_managing.network.NetworkUtil;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ListaGradilista extends AppCompatActivity {

    private CompositeSubscription subscription;
    private ListView site_list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listaview_gradilista);
        subscription=new CompositeSubscription();
        site_list=findViewById(R.id.listView_sites);


        site_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gradiliste item=(Gradiliste) site_list.getItemAtPosition(position);
                Intent intent=new Intent();
                intent.putExtra("odabrano_gradiliste",item.getName());
                setResult(RESULT_OK,intent);
                finish();

                       }
        });


        load();
    }

    private void load(){

            subscription.add(NetworkUtil.getRetrofit().getSite()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handle, this::handleErr));

    }

    private  void  handle(ArrayList<Gradiliste> gradiliste){
        if (gradiliste != null && gradiliste.size()!=0){
            AdapterGradlista adapter = new AdapterGradlista(this, gradiliste);
            site_list.setAdapter(adapter);

        }
        }

    private  void handleErr(Throwable error){

    }



}


