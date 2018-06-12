package com.example.eri.workers_managing;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.eri.workers_managing.model.Gradiliste;
import com.example.eri.workers_managing.model.Response;
import com.example.eri.workers_managing.network.NetworkUtil;
import com.example.eri.workers_managing.utils.Constants;
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
    private Intent intent_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent_logout = new Intent(this, MainActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_gradilista);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        subscription=new CompositeSubscription();
        addGradiliste=findViewById(R.id.add_gradiliste);

        addGradiliste.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng rijeka = new LatLng(45, 14);
      //  mMap.addMarker(new MarkerOptions().position(rijeka).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rijeka,4.0f));
       // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(rijeka.latitude, rijeka.longitude), 20.0f));



        load();

        // Setting a custom info window adapter for the google map
        MarkerInfoWindowAdapter markerInfoWindowAdapter = new MarkerInfoWindowAdapter(getApplicationContext());
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);


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
                 Intent intent=new Intent(DisplayGradilistaActivity.this,DisplayWorkersActivity.class);
                 Gradiliste gr=(Gradiliste)  marker.getTag();
                 intent.putExtra("search_gr",gr);
                 startActivity(intent);
                  /*  Log.d("+++",marker.getId());
                    //LatLng latlong=marker.getPosition();

                    String[] latlong =  "-34.8799074,174.7565664".split(",");
                   double latitude = Double.parseDouble(latlong[0]);
                   double longitude = Double.parseDouble(latlong[1]);
                   LatLng lo=new LatLng(latitude,longitude);

*/
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
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_grad, menu);

        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_menu:
                logout();

            case R.id.MojProfil:

                Intent intent=new Intent(DisplayGradilistaActivity.this,MyProfile.class);
                startActivity(intent);
            case R.id.Lista_gradilista:
                Intent intent1=new Intent(DisplayGradilistaActivity.this,ListaGradilista.class);
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logout() {

        intent_logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent_logout);
    }
}



