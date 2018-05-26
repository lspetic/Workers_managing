package com.example.eri.workers_managing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayWorkersActivity extends AppCompatActivity {
    TextView textView;
    String workers = "Erika Fafandel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_workers);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        // Capture the layout's TextView and set the string as its text
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(workers);
    }
}
