package com.example.mahadkhokhar.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    //Used to determine which activity it is coming from
    final int ACTIVITY_NUMBER = 1000;

    //Variables for items from XML file
    private Button drugButton;
    private Button scheduleButton;

    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Primary Setting
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting id to the buttons
        drugButton = (Button) findViewById(R.id.drugButton);

        //scheduleButton = (Button) findViewById(R.id.scheduleButton);

        //Adding the code to switch between activities
        drugButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent( view.getContext(), DisplayMedicineActivity.class);
                    startActivity(i);
                }
            }
        );

    }
}
