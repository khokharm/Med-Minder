package com.example.mahadkhokhar.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import Structures.Medicine;

/**
 * Created by Mahad Khokhar on 2017-11-04.
 */

public class MedicineDescriptionActivity extends Activity {

    ////Variables for items from XML file
    TextView medicine_Name;
    TextView medicine_Type;
    TextView medicine_Description;
    TextView medicine_Time;

    //Medicine
    Medicine medicine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_description);

        //linking Variables to the items in the XML File
        medicine_Name = (TextView) findViewById(R.id.Medicine_Name);
        medicine_Type = (TextView) findViewById(R.id.Medicine_Type);
        medicine_Description = (TextView) findViewById(R.id.Medicine_Description);
        medicine_Time = (TextView) findViewById(R.id.Medicine_Time);

        //Check for the information that is attached
        Bundle Extras = this.getIntent().getExtras();

        //Change the textviews
        if (Extras != null){
            medicine = (Medicine) Extras.get("Medicine");
            medicine_Name.setText("Medicine Name: " + medicine.getName());
            medicine_Type.setText ("Medicine Type: " + medicine.getType());
            medicine_Description.setText("Medicine Description: " + medicine.getDescription());
            medicine_Time.setText("Time: " + medicine.getTime24H());
        }



    }
}
