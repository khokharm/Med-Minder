package com.example.mahadkhokhar.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import Structures.IOHelper;
import Structures.Medicine;
import Structures.MedicineList;

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

    //
    final int ACTIVITY_DELETE = 8008;
    final int ACTIVITY_EDIT = 3003;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == ACTIVITY_EDIT) {
            //Update the list
            Bundle extras = data.getExtras();
            if (extras != null) {
               //Get reference to medication
                Medicine med = (Medicine)extras.getSerializable("UpdateMedicine");
                //Used for testing for correct results
           /*     Log.d ("toto1",med.getName());
                Log.d ("toto1",med.getType());
                Log.d ("toto1",med.getDescription());
                Log.d ("toto1",Integer.toString(med.getHour()));
                Log.d ("toto1",Integer.toString(med.getMinute()));*/

                //Update the mediciine list
                MedicineList medList = new MedicineList(getApplicationContext());
                medList.replace(getApplicationContext(),med );

                //update the current file
                update(med.getName(), med.getType(),med.getDescription(),med.getTime24H());

                //Canceling old alarm
                Intent alarmIntent = new Intent(getApplicationContext(), Receiver.class);
                PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(),med.getId(),alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                pIntent.cancel(); //Cancel the pending intent here

                //Creating a new alaram manager
                //-----Creating the broadcast for the notification------------------------------------------

                //Creating the calendar for
                Calendar calendar = Calendar.getInstance();
                //Toast.makeText(this, Integer.toString(med.getHour()), Toast.LENGTH_SHORT).show();
                calendar.set(Calendar.HOUR_OF_DAY, med.getHour());
                //Toast.makeText(this, Integer.toString(med.getMinute()), Toast.LENGTH_SHORT).show();
                calendar.set(Calendar.MINUTE, med.getMinute());

                //Intent
                alarmIntent = new Intent(getApplicationContext(), Receiver.class);
                alarmIntent.putExtra("MedicineName", med.getName());
                alarmIntent.putExtra("MedicineDescription", med.getDescription());
                alarmIntent.putExtra("Counter", Integer.toString(med.getId()));

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),med.getId(),alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager=(AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }
            //Used for testing
            IOHelper io = new IOHelper();
            Log.d("result",  "UpdateMedicine: "+ io.read(this,String.valueOf(R.string.medicineFile)));


        }
    }

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
            update(medicine.getName(), medicine.getType(), medicine.getDescription(), medicine.getTime24H());
        }

        IOHelper io = new IOHelper();
        Log.d("result",  "MedicineDiscription: "+  io.read(this,String.valueOf(R.string.medicineFile)));

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

                                //Remove the application
                                MedicineList medList = new MedicineList(getApplicationContext());
                                medList.remove(getApplicationContext(),medicine.getName());
                                IOHelper io = new IOHelper();
                                Log.d("result",  "delete: "+ io.read(getApplicationContext(),"MedicationFile"));
                                //Intent intent = new Intent(getApplicationContext(),DisplayMedicineActivity.class);
                                //startActivity(intent);
                                //
                                //Use to reference the pending intent
                                Intent alarmIntent = new Intent(getApplicationContext(), Receiver.class);
                                PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(),medicine.getId(),alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                                pIntent.cancel(); //Cancel the pending intent here

                                Toast.makeText(getApplicationContext(), "Deleted",Toast.LENGTH_SHORT).show();


                                //Return to the previous activity
                                Intent intent = new Intent();
                                intent.putExtra("result",ACTIVITY_DELETE);
                                setResult(RESULT_OK,intent);
                                finish();

                                //return the preivous activity
                                //finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Cancel the dialog box
                                dialog.cancel();
                            }
                        });

                //Properties
                AlertDialog alert = altdial.create();
                alert.setTitle("Confirm");
                alert.show();
            }
        });

        //Editing the medication
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MedicineDescriptionActivity.this,EditMedicineActivity.class);
                intent.putExtra("Medicine",medicine);
                startActivityForResult(intent,ACTIVITY_EDIT);
            }
        });
    }

    public void update(String name, String type, String description, String time){

        medicine_Name.setText("Medicine Name: " + name);
        medicine_Type.setText ("Medicine Type: " + type);
        medicine_Description.setText("Medicine Description: " + description);
        medicine_Time.setText("Time: " + time);

    }
}
