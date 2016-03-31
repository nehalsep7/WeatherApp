package com.example.android.sunshineapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView day = (TextView) findViewById(R.id.dayName);
        TextView max = (TextView)findViewById(R.id.maxView);
        TextView min = (TextView)findViewById(R.id.minView);
        TextView dateMonth = (TextView)findViewById(R.id.dateView);
        TextView weather = (TextView)findViewById(R.id.weather);
        TextView humidity = (TextView)findViewById(R.id.humidityView);
        TextView pressure = (TextView)findViewById(R.id.pressureView);
        TextView wind =(TextView)findViewById(R.id.windView);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        String dayOfWeek = i.getStringExtra("dayOfWeek");
        day.setText(dayOfWeek);
        max.setText(i.getStringExtra("MaxTemp"));
        min.setText(i.getStringExtra("MinTemp"));
        dateMonth.setText(i.getStringExtra("MonthValue") + ", "+i.getStringExtra("date"));
        weather.setText(i.getStringExtra("Main Weather"));
        humidity.setText("Humidity: "+i.getStringExtra("Humidity"));
        pressure.setText("Pressure: " +i.getStringExtra("Pressure"));
        wind.setText("Wind: "+i.getStringExtra("Wind"));


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
