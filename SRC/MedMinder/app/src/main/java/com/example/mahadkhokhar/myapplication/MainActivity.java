package com.example.mahadkhokhar.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import Structures.Medicine;


public class MainActivity extends AppCompatActivity {

    //Used to determine which activity it is coming from
    final int ACTIVITY_NUMBER = 1000;

    //Variables for items from XML file
    private Button drugButton;
    private Button Main_Button_2;

    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Primary Setting
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting id to the buttons
        drugButton = (Button) findViewById(R.id.drugButton);
        Main_Button_2 = (Button) findViewById(R.id.Main_Button_2);

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

        Main_Button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = "x5";
                Medicine medicine = new Medicine (name,"Type", "adfadf", 12,12,1);

                db.addNewMedicine(medicine);
                db.updateMedicineInfo("x5","Type", "adfadf", 12,11,1);



            }
        });





    }
}
