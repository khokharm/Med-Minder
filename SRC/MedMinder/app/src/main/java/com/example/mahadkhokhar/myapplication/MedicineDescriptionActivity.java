package com.example.mahadkhokhar.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    Button delete;
    Button edit;

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

        delete = (Button) findViewById(R.id.delete_medicine_button);
        edit = (Button) findViewById(R.id.edit_medicine_button);

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

        //Button listeners-------------------------------------------------------------------------------------------------
        //Deleting the medication
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Confirming if you want to delete the medication
                AlertDialog.Builder altdial = new AlertDialog.Builder(MedicineDescriptionActivity.this);
                altdial.setMessage("Do you want to delete this medication").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = altdial.create();
                alert.setTitle("Confirm");
                alert.show();

                //Return to new activity
                //Intent i = new Intent( view.getContext(), DisplayMedicineActivity.class);
                //startActivity(i);
            }
        });

        //Deleting the final presentation
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
