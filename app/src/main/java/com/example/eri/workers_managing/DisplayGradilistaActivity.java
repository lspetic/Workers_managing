package com.example.eri.workers_managing;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.eri.workers_managing.model.Gradiliste;
import com.example.eri.workers_managing.model.Response;
import com.example.eri.workers_managing.network.NetworkUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class DisplayGradilistaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker mark1;
    private CompositeSubscription subscription;
    private FloatingActionButton addGradiliste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_gradilista);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        subscription=new CompositeSubscription();
        addGradiliste=findViewById(R.id.add_gradiliste);

        addGradiliste.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.d("+++10","pls add gradiliste ");
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng rijeka = new LatLng(45, 14);
      //  mMap.addMarker(new MarkerOptions().position(rijeka).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rijeka));



        // Setting a custom info window adapter for the google map
        MarkerInfoWindowAdapter markerInfoWindowAdapter = new MarkerInfoWindowAdapter(getApplicationContext());
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);
        load();

        // Adding and showing marker when the map is touched
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng arg0) {
              /*  mMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(arg0);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0));
                Marker marker = mMap.addMarker(markerOptions);
                marker.showInfoWindow();*/
               ;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {           //postavljanje info prozora kod clicka na marker
            @Override
            public void onInfoWindowClick(Marker marker) {
                    Log.d("+++",marker.getId());
                    //LatLng latlong=marker.getPosition();

                    String[] latlong =  "-34.8799074,174.7565664".split(",");
                   double latitude = Double.parseDouble(latlong[0]);
                   double longitude = Double.parseDouble(latlong[1]);
                   LatLng lo=new LatLng(latitude,longitude);






            }
        });

     /*   mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.equals(mark1)){
                    Log.d("+++","aladin");

                }



                return true;
            }
        });*/
    }
    private void load(){
        try {
            subscription.add(NetworkUtil.getRetrofit().getSite()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handle, this::handleErr));
        }catch (Exception e){
            Log.d("+++",e.toString());
        }
    }

    private  void  handle(ArrayList<Gradiliste> gradiliste){
        Marker m;
        for (Gradiliste object:gradiliste){         //prolazi korz listu objekata tipa Dradiliste i postavlja markere ovisno o lat long od objekta

            Log.d("++++",object.getLatlong());

            String[] latlong=object.getLatlong().split(",");

            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);
            LatLng lo=new LatLng(latitude,longitude);

            m=mMap.addMarker(new MarkerOptions().position(lo).title(object.getName()));
            m.setTag(object);
        }




    }
    private  void handleErr(Throwable error){

    }


}



