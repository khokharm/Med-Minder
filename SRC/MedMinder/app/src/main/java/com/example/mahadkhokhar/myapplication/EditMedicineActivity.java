package com.example.mahadkhokhar.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import Structures.IOHelper;
import Structures.Medicine;

/**
 * Created by Mahad Khokhar on 2017-11-29.
 */


/**
 * <h1>The Edit activity class  </h1>
 *
 * @author Mahad Khokhar
 * Date: Nov 29 2017
 * @version 0
 *
 *<p> The activity will allow you to edit the medicine properties. You cannot edit the name of the selected medicine</p>
 */
public class EditMedicineActivity extends Activity {

    //Items on screen --xml file
    private Button editButton;
    private TimePicker timePicker;
    private EditText medName;
    private Spinner medTypeSpinner;
    private String medType = "";
    private EditText medDescription;

    private Medicine medicine;

    /**
     * This method will be created when the activity is called.
     * It will link the code to xml components
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_medicine);

        //Find the items in the following activities
        editButton = (Button)findViewById(R.id.Button);
        timePicker = (TimePicker) findViewById(R.id.edit_timePicker);
        medName = (EditText) findViewById(R.id.medicineName);
        medTypeSpinner = (Spinner) findViewById(R.id.MedicineType_Spinner);
        medDescription = (EditText) findViewById(R.id.MedicineDescription);

        //Drop Down menu Array Adapter ---------------------------------------------------------------------------------------------------------

        ArrayAdapter<String> SpinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.Medicine_Type));
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        medTypeSpinner.setAdapter(SpinnerAdapter);

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



        //get new extras intent
        Bundle Extras = this.getIntent().getExtras();

        //Change the textviews
        if (Extras != null) {
            medicine = (Medicine) Extras.get("Medicine");
        }

        //Medicine name properties
        medName.setText(medicine.getName()); //setting feild to name of medicine
        medName.setEnabled(false);//Not being able to change the name of the medicine

        //Setting medicine type
        medTypeSpinner.setSelection(findScrollViewIndex(medicine.getType()));

        //Medicine description
        medDescription.setText(medicine.getDescription());

        //Timer Picker Default
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(medicine.getHour());
            timePicker.setMinute(medicine.getMinute());
        }

        //Click button to update
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update
                Medicine med = new Medicine(medName.getText().toString(), medType, medDescription.getText().toString(), timePicker.getHour(), timePicker.getMinute(),medicine.getId());
                /*Log.d ("toto1",medName.getText().toString());
                Log.d ("toto1",medType);
                Log.d ("toto1",medDescription.getText().toString());
                Log.d ("toto1",Integer.toString(timePicker.getHour()));
                Log.d ("toto1",Integer.toString(timePicker.getMinute()));*/

                Intent intent = new Intent();
                intent.putExtra("UpdateMedicine",med);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    /**
     *  This method is used for finding the
     * @param x type of medicine
     * @return the index in the arraylisted in the files
     */
    public int findScrollViewIndex (String x){
        Resources res = getResources();
        String[] types = res.getStringArray(R.array.Medicine_Type);

        for (int i = 0; i < types.length; i ++){
            if (x.equalsIgnoreCase(types[i])){
                return i;
            }
        }

        return 0; //returns default option

    }

}
