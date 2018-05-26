package com.example.eri.workers_managing;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button Radnici;
    Button Gradilista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //addListenerOnButton();
    }


//    public void addListenerOnButton() {
//
//        Radnici = (Button) findViewById(R.id.radnici);
//        Gradilista= (Button) findViewById(R.id.gradilišta);
//
//        Radnici.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                Intent browserIntent =
//                        new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mkyong.com"));
//                startActivity(browserIntent);
//
//            }
//
//        });
//
//        Gradilista.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                Intent browserIntent =
//                        new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
//                startActivity(browserIntent);
//
//            }
//
//        });
//
//    }

    public void openRadnici(View view) {
        Intent intent = new Intent(this, DisplayWorkersActivity.class);
        startActivity(intent);    }

    public void openGradilista(View view) {
        Intent intent = new Intent(this, DisplayGradilistaActivity.class);
        startActivity(intent);    }

}



