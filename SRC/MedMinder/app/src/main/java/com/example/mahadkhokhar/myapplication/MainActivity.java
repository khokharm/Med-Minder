package com.example.mahadkhokhar.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import Structures.IOHelper;
import Structures.Medicine;
import Structures.MedicineList;


public class MainActivity extends AppCompatActivity {

    //Used to determine which activity it is coming from
    final int ACTIVITY_CURERNT = 1000;
    final int ACTIVITY_NEW_MEDICINE = 1001;

    //Variables for items from XML file
    private Button drugButton;
    private Button Main_Button_2;
    private AlarmManager alarmManager;

    //Reference to medList
    MedicineList medList;

    //This methods is used to get Data from the application
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ACTIVITY_NEW_MEDICINE){

            //Getting data from intents
            Bundle extras = data.getExtras();

            if (extras != null) {

                //Creating new medicine and adding the list of medicines
                Medicine med = ((Medicine) extras.getSerializable("Medicine"));

                //Adding to list of medicine
                if (medList.add(getApplicationContext(),med)){

                    //-----Creating the broadcast for the notification------------------------------------------

                    //Creating the calendar for
                    Calendar calendar = Calendar.getInstance();
                    //Toast.makeText(this, Integer.toString(med.getHour()), Toast.LENGTH_SHORT).show();
                    calendar.set(Calendar.HOUR_OF_DAY, med.getHour());
                    //Toast.makeText(this, Integer.toString(med.getMinute()), Toast.LENGTH_SHORT).show();
                    calendar.set(Calendar.MINUTE, med.getMinute());

                    //Intent
                    Intent alarmIntent = new Intent(getApplicationContext(), Receiver.class);

                    alarmIntent.putExtra("MedicineName", med.getName());
                    alarmIntent.putExtra("MedicineDescription", med.getDescription());
                    alarmIntent.putExtra("Counter", Integer.toString(med.getId()));

                    PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(),med.getId(),alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
                    //-------------------------------------------------------------------------------------------------------

                    Toast.makeText(this,med.getName() + " created",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(this,med.getName() + " already exists",Toast.LENGTH_LONG).show();
                }

                //Record to log
                IOHelper io = new IOHelper();
                Log.d("result",  "Main_added: "+ io.read(this,"MedicationFile"));

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Primary Setting
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting id to the buttons
        drugButton = (Button) findViewById(R.id.drugButton);
        Main_Button_2 = (Button) findViewById(R.id.Main_Button_2);

        //Reference to the list of the databases
        medList = new MedicineList(this);

        //Setting up Alarm Manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //Medicine x1 = new Medicine ("Facebook2", "asdf", "asdf",1,1,1);
        //medList.add(this,x1);
        //medList.remove(this,"Facebook2");
        IOHelper io = new IOHelper();
        Log.d("result",  "Main2: "+ io.read(this,String.valueOf(R.string.medicineFile)));

        //Adding the code to switch between activities
        drugButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              Intent i = new Intent(view.getContext(), DisplayMedicineActivity.class);
                                              startActivity(i);
                                          }
                                      }
        );

        //Adding the clicklistener for the button
        Main_Button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newMedicinePage = new Intent (MainActivity.this, CreateMedicineActivity.class );
                //-----------------------------------------------------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                startActivityForResult(newMedicinePage, ACTIVITY_NEW_MEDICINE);
            }
        });




    }
}
