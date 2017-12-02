package com.example.mahadkhokhar.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Structures.IOHelper;
import Structures.Medicine;
import Structures.MedicineList;

/**
 * Created by Mahad Khokhar on 2017-11-04.
 */

/**
 * <h1>The display medicine activity class  </h1>
 *
 * @author Mahad Khokhar
 * Date: Nov 04 2017
 * @version 0
 *
 *<p> This page will control the listing of medicine.  </p>
 */
public class DisplayMedicineActivity extends Activity {

    //Variables
    private ListView listView;
    private ArrayAdapter arrayAdapter;

    //Declare the list for the medicine
    private MedicineList medList;

    //Used to track for return from activity
    final int ACTIVITY_DELETED = 8008;

    /**
     * This method is used for updating the list when the medicine is deleted
     * @param requestCode
     * @param resultCode
     * @param data no data was transfer for this part of application
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ACTIVITY_DELETED) {
            //Update the list
            medList.updateList(getApplicationContext());
            //Used for testing
            IOHelper io = new IOHelper();
            Log.d("result",  "return: "+  io.read(this,String.valueOf(R.string.medicineFile)));
            //Toast.makeText(getApplicationContext(), "return", Toast.LENGTH_SHORT).show();

            //Reseting the arrayAdapter
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, medList.getMedicineNames());

            if (listView != null){
                listView.setAdapter(arrayAdapter);
            }

            arrayAdapter.notifyDataSetChanged();
        }


    }

    ArrayList <String> medicinenames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Set up
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_medication);

        //Linking to the xml files
        listView = (ListView) findViewById(R.id.listview); //Getting reference to listview from display_drugs_activity file


        IOHelper io = new IOHelper();
        Log.d("result",  "AllMedicine: "+ io.read(this,String.valueOf(R.string.medicineFile)));

        //Medicine List
        medList = new MedicineList(this);

        //Used for the clicking the item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(getApplicationContext(), Integer.toString(i),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DisplayMedicineActivity.this, MedicineDescriptionActivity.class);
                //Need to fix this up so that you can sort
                intent.putExtra("Medicine", medList.getMedicine(i));
                startActivityForResult(intent,ACTIVITY_DELETED);
            }
        });


        //------------------------------------------------------------------------------------------

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override

            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });
        //--------------------------------------------------------------------------------------------------------------
    }



    /**
     * The method is used for unpdating the medicine list.
     */
    @Override
    protected void onResume() {
        super.onResume();
        //arrayAdapter.clear();
        medList.updateList(getApplicationContext());

        //Code for the listview----------------------------------------------------------------
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, medList.getMedicineNames());

        if (listView != null){
            listView.setAdapter(arrayAdapter);
        }

        //Toast.makeText(getApplicationContext(), "onResume", Toast.LENGTH_SHORT).show();
    }
}
