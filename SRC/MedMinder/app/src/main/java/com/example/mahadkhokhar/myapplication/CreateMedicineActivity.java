package com.example.mahadkhokhar.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.io.File;

import Structures.Medicine;

/**
 * Created by Mahad Khokhar on 2017-11-04.
 */

public class CreateMedicineActivity extends Activity {

    final int ACTIVITY_NUMBER = 1002;


    //Items on screen --xml file
    private Button createButton;
    private TimePicker timePicker;
    private EditText medName;
    private Spinner medTypeSpinner;
    private String medType = "";
    private EditText medDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Set for the activity -- Transitioning into the next activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_drug);

        //Find the items in the following activities
        createButton = (Button)findViewById(R.id.Button);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        medName = (EditText) findViewById(R.id.medicineName);
        medTypeSpinner = (Spinner) findViewById(R.id.MedicineType_Spinner);
        medDescription = (EditText) findViewById(R.id.MedicineDescription);

        //Drop Down menu Array Adapter ---------------------------------------------------------------------------------------------------------

        ArrayAdapter<String> SpinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.Medicine_Type));
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        medTypeSpinner.setAdapter(SpinnerAdapter);

        //Button Configurations --------------------------------------------------------------------
        //Set Create Button disable initially
        createButton.setEnabled(false);

        //Checking if file exist if not create it with default value
        counterFileExist();



        //Make sure there is a name for the drug name for the application
        medName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub


                //Sets to true or false
                if (s.toString().equals("")) {
                    createButton.setEnabled(false);
                } else {
                    createButton.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });




        //Spinner Configurations------------------------------------------------------------------
        medTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),getResources().getStringArray(R.array.Medicine_Type)[i],Toast.LENGTH_SHORT).show();
                medType = getResources().getStringArray(R.array.Medicine_Type)[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Intent Configurations --------------------------------------------------------

        createButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Medicine med = new Medicine(medName.getText().toString(), medType, medDescription.getText().toString(), timePicker.getHour(), timePicker.getMinute(),counterAssignment ());
                //Returning the result
                Intent intent = new Intent();
                intent.putExtra ("Medicine", med);
                setResult(RESULT_OK,intent);
                finish();

            }
        });

    }

    public int counterAssignment (){
        String fileName = String.valueOf(R.string.counterFile);
        //Creating File and Getting reference to file
        SharedPreferences filer = getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = filer.edit();

        //Getting the value
        int value = filer.getInt("counter", -1);
        //Used for error
        if (value == -1 ){
            Log.e("CounterFileStatus", "Error with returning counter");
        }

        Log.d("CounterFileStatus", "SharedPreferences Name_of_your_preference : exist" + Integer.toString(value));

        //Increment the value
        value = value + 1;
        Log.d("CounterFileStatus", "CounterFile: " + Integer.toString(value));
        //puting value into file
        editor.putInt("counter",value );
        //Save the value into file
        editor.commit();

        return value;
    }


    public void counterFileExist(){
        String fileName = String.valueOf(R.string.counterFile);

        File f = new File("/data/data/" + getPackageName() +  "/shared_prefs/" + fileName + ".xml");
        if (f.exists()) {
            //Testing for function

            //Used for testing -- Shows the current counter
            SharedPreferences filer = getSharedPreferences(fileName,Context.MODE_PRIVATE);
            int value = filer.getInt("counter", -1);
            if (value == -1 ){
                Log.e("CounterFileStatus", "Error with returning counter");
            }
            Log.d("CounterFileStatus", "SharedPreferences Name_of_your_preference : exist" + Integer.toString(value));
        }
        else{
            Log.d("CounterFileStatus", "Setup default preferences");
            //Creating File and Getting reference to file
            SharedPreferences filer = getSharedPreferences(fileName,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = filer.edit();
            //Puting the default value of the string
            editor.putInt("counter", 1);
            //Saving the file
            editor.commit();
            Log.d("CounterFileStatus", "Made file");
        }

    }

}

