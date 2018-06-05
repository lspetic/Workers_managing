package com.example.eri.workers_managing;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.eri.workers_managing.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class User_details extends AppCompatActivity {

    private User user;
    private EditText etDate,etTime,etDateFinish,etTimefinish;
    private Calendar dateCalendar;
    private SimpleDateFormat dateFormatter,timeFormatter;

@Override
protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_details);
    Bundle extras = getIntent().getExtras();
    dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    timeFormatter = new SimpleDateFormat("hh-mm", Locale.getDefault());
    try {

        if (extras != null) {
            user = (User)extras.getSerializable("User_data");
             Log.d("++++", user.getEmail());
        }
    }catch(Exception e){
        Log.d("++error++",e.toString());
    }

    etDate=findViewById(R.id.etDate);
    etTime=findViewById(R.id.etTime);
    etDateFinish=findViewById(R.id.etDateFinish);
    etTimefinish=findViewById(R.id.etTimeFinish);

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





}



}

