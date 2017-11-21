package com.example.mahadkhokhar.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.*;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import Structures.Medicine;
import Structures.MedicineList;

/**
 * Created by Mahad Khokhar on 2017-11-04.
 */

public class DisplayMedicineActivity extends Activity {

    final int CURRENT_ACTIVITY_NUMBER = 1001;
    final int REFERENCE_ACTIVITY_NUMBER_1 = 1002; //CreateMedicineActivity
    final int REFERENCE_ACTIVITY_NUMBER_2 = 1003; //MedicineDescriptionActivity

    //Variables
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private Button newMedicineButton;
    private AlarmManager alarmManager;

    //Delete this afterwards, used to check for funcationality

    ArrayList<String> stringArray = new ArrayList<String>();

    //Declare the list for the medicine
    private MedicineList medList = new MedicineList();

    //Intents
    PendingIntent pIntent;


    //This methods is used to get Data from the application
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REFERENCE_ACTIVITY_NUMBER_1){

            //Getting data from intents
            Bundle extras = data.getExtras();

            if (extras != null) {
                //Creating new medicine and adding the list of medicines
                Medicine med = ((Medicine) extras.getSerializable("Medicine"));
                medList.add (med);

                //Need to change this to make efficient
                stringArray.add (med.getName());

                //Toast.makeText(this,x,Toast.LENGTH_LONG).show();
            }
        }
        //Statement for the second activity
        else if (resultCode == RESULT_OK && requestCode == REFERENCE_ACTIVITY_NUMBER_2){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Set up
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_all_drug_activity);

        //Getting reference to listview from display_drugs_activity file
        listView = (ListView) findViewById(R.id.listview);

        //Getting reference to the the button
        newMedicineButton = (Button) findViewById(R.id.newMedicine);

        //Setting up Alarm Manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //Adding the clicklistener for the button
        newMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newMedicinePage = new Intent (DisplayMedicineActivity.this, CreateMedicineActivity.class );
                startActivityForResult(newMedicinePage, REFERENCE_ACTIVITY_NUMBER_1);
            }
        });

        //Code for the listview----------------------------------------------------------------
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringArray);

        if (listView != null){
            listView.setAdapter(arrayAdapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplicationContext(), Integer.toString(i),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DisplayMedicineActivity.this, MedicineDescriptionActivity.class);
                intent.putExtra("Medicine", medList.getMedicine(i));
                startActivity(intent);
            }
        });

        //------------------------------------------------------------------------------------------

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override

            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), "Discount should be less than 100",Toast.LENGTH_SHORT).show();
                //medList.remove(i);
                //arrayAdapter.notifyDataSetChanged();

                Toast.makeText(DisplayMedicineActivity.this, "Helded", Toast.LENGTH_SHORT).show();
                Intent test_intent = new Intent(DisplayMedicineActivity.this, Receiver.class);
                final Calendar calendar = Calendar.getInstance();
                pIntent = PendingIntent.getBroadcast(DisplayMedicineActivity.this,100,test_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);

                return false;
            }
        });

        //--------------------------------------------------------------------------------------------------------------
        //Background noticification
/*        Calendar calendar =  Calendar.getInstance();

        calendar.set (Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 53);
        calendar.set (Calendar.SECOND, 0);

        Intent intent = new Intent(this,Receiver.class);
        pIntent = PendingIntent.getBroadcast(this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pIntent);*/

    }

    //The purpose of this method is to update the list which displays the medicine names
    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show();
        arrayAdapter.notifyDataSetChanged();
    }
}
