package com.example.eri.workers_managing;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.eri.workers_managing.model.Gradiliste;
import com.example.eri.workers_managing.model.Response;
import com.example.eri.workers_managing.model.User;
import com.example.eri.workers_managing.network.NetworkUtil;
import com.example.eri.workers_managing.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class User_details extends AppCompatActivity {

    private User user;
    private Gradiliste gradiliste,gradiliste_old;
    private EditText etDate,etTime,etDateFinish,etTimefinish,etGradiliste;
    private Calendar dateCalendar;
    private Button bSave,bCancel,btnPlus;
    private SimpleDateFormat dateFormatter,timeFormatter;
    private CompositeSubscription mSubscriptions;
    private String mToken;
    private SharedPreferences mSharedPref;
    private TextView tvUser;


@Override
protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_details);

    dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    timeFormatter = new SimpleDateFormat("hh-mm", Locale.getDefault());
    mSubscriptions = new CompositeSubscription();

    etDate=findViewById(R.id.etDate);
    etTime=findViewById(R.id.etTime);
    etDateFinish=findViewById(R.id.etDateFinish);
    etTimefinish=findViewById(R.id.etTimeFinish);
    etGradiliste = findViewById(R.id.etGradiliste);
    tvUser=findViewById(R.id.et_user);


    bSave=findViewById(R.id.btnSave);
    bCancel=findViewById(R.id.btnCancel);
    btnPlus=findViewById(R.id.btnDodajGrad);
    mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    mToken = mSharedPref.getString(Constants.TOKEN,"");

    getMyIntent(); // dohvaćanje user klase iz prethodnog activity-a

    getDefaults();  //postavljanje formi na početne vrijednosti korisnika ako takve postoje

    if (user.getGradiliste()!=null) {
        loadGradiliste(); //dohvati objekt gradiliste ako je radnik več na nekom gradilistu

    }

try {
    tvUser.setText(user.getEmail()); //prikaz podataka o korisniku
}catch (Exception   e){
    Log.d("aaaaaa",e.toString());
}





    btnPlus.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(User_details.this,ListaGradilista.class);
            startActivityForResult(intent,1);
        }
    });
    //Click listeneri za datume i vrijeme
    etDate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar newCalendar = Calendar.getInstance();  // current date
            DatePickerDialog datePickerDialog = new DatePickerDialog(User_details.this,
                    new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            dateCalendar = Calendar.getInstance(); // picked date
                            dateCalendar.set(year,monthOfYear,dayOfMonth );
                            etDate.setText(dateFormatter.format(dateCalendar.getTime()));
                        }
                    },
                    newCalendar.get(Calendar.YEAR),
                    newCalendar.get(Calendar.MONTH),
                    newCalendar.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.show();
        }
    });

    etTime.setOnClickListener(new View.OnClickListener(){
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);
        public void onClick(View v) {


            TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (view.isShown()) {
                        myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCalender.set(Calendar.MINUTE, minute);
                        etTime.setText(timeFormatter.format(myCalender.getTime()));

                    }
                }
            };

            TimePickerDialog timePickerDialog = new TimePickerDialog(User_details.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
            timePickerDialog.setTitle("Choose hour:");
            timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            timePickerDialog.show();
        }

    });

    etTimefinish.setOnClickListener(new View.OnClickListener(){
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);
        public void onClick(View v) {


            TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (view.isShown()) {
                        myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCalender.set(Calendar.MINUTE, minute);
                        etTimefinish.setText(timeFormatter.format(myCalender.getTime()));

                    }
                }
            };

            TimePickerDialog timePickerDialog = new TimePickerDialog(User_details.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
            timePickerDialog.setTitle("Choose hour:");
            timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            timePickerDialog.show();
        }

    });

    etDateFinish.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Calendar newCalendar = Calendar.getInstance();  // current date
            DatePickerDialog datePickerDialog = new DatePickerDialog(User_details.this,
                    new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            dateCalendar = Calendar.getInstance(); // picked date
                            dateCalendar.set(year,monthOfYear,dayOfMonth );
                            etDateFinish.setText(dateFormatter.format(dateCalendar.getTime()));
                        }
                    },
                    newCalendar.get(Calendar.YEAR),
                    newCalendar.get(Calendar.MONTH),
                    newCalendar.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.show();
        }

    });

    bSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            user.setStart_job(etDate.getText().toString());
            user.setEnd_job(etDateFinish.getText().toString());
            user.setGradiliste(etGradiliste.getText().toString());



           if (gradiliste_old==null){               //radnik dodan na novo gradiliste

               gradiliste.setBr_radnika(gradiliste.getBr_radnika()+1);
               sendDataToSite(gradiliste);
           }else  if(!gradiliste.getName().equals(gradiliste_old.getName())){   //radnik premjesten na drugo gradiliste

                   gradiliste.setBr_radnika(gradiliste.getBr_radnika()+1);
                   gradiliste_old.setBr_radnika(gradiliste_old.getBr_radnika()+1);
                   sendDataToSite(gradiliste);
                   sendDataToSite(gradiliste_old);
           }                                                                //ako se ime gradilista ne mijenja netreba update-ati bazu


            Log.d("+++put",user.getEnd_job());
            Log.d("+++put",user.getStart_job());
            try {
                Log.d("++++",gradiliste.getBr_radnika().toString());
                sendData(); //slanje podataka u bazu

            }catch(Exception e){
                Log.d("+++ee",e.toString());
            }
        }
    });

    bCancel.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            getDefaults();
        }
    });

}
        public void sendData(){


            mSubscriptions.add(NetworkUtil.getRetrofit(mToken).putJob(user.getEmail(),user)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse,this::handleError));


        }

    public void sendDataToSite(Gradiliste grad){


        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).putSite(grad.getName(),grad)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));


    }

    public void loadGradiliste(){

        mSubscriptions.add(NetworkUtil.getRetrofit().getSiteOne(user.getGradiliste())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handle, this::handleErr));
    }

    private  void handle(Gradiliste gradiliste_old){
        this.gradiliste_old=gradiliste_old;







    }
    private void handleErr(Throwable error){


    }

    private void handleResponse(Response response){
    showSnackBarMessage(response.getMessage());
}


    private void handleError(Throwable error){
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


            if(message !=null)
                Snackbar.make(findViewById(R.id.user_details),message,Snackbar.LENGTH_SHORT).show();


    }
    private void getDefaults(){

    try {
        if(user.getGradiliste()!=null){

            etGradiliste.setText(user.getGradiliste());
            Log.d("+++",user.getGradiliste());

        }
        if (user.getStart_job()!=null)
            etDate.setText(user.getStart_job());
            Log.d("+++",user.getStart_job());

        if (user.getEnd_job()!=null){
            etDateFinish.setText(user.getEnd_job());
            Log.d("+++",user.getStart_job());
        }

    }catch (Exception e){
        Log.d("6++++",e.toString());
    }

    }


private void getMyIntent(){

    try {

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
          //  etGradiliste.setText((String)extras.getSerializable("odabrano_gradliste"));
            user = (User)extras.getSerializable("User_data");
            Log.d("++++", user.getEmail());
        }

    }catch(Exception e){
        Log.d("++error++",e.toString());
    }
}
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
try {

    gradiliste = (Gradiliste) data.getExtras().getSerializable("odabrano_gradiliste");
    etGradiliste.setText(gradiliste.getName());

}catch (Exception e){
    Log.d("+++", e.toString());
}


    }




}

