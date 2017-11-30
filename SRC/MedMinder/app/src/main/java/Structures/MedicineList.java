package Structures;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mahadkhokhar.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static java.util.Arrays.sort;


/**
 * Created by Mahad Khokhar on 2017-11-09.
 */

public class MedicineList {

    final String file = String.valueOf(R.string.medicineFile);

    /**
     * The list which keeps all the medication
     */
    private static ArrayList<Medicine> medList;

    public MedicineList (Context context){
        //Create new arraylist
        medList = new ArrayList <Medicine> ();
        //Used to make a new file
        IOHelper io = new IOHelper();
        io.create(context,file);
        //Update the list
        updateList(context);
    }

    /**
     * @param Name Enter the name of the medication
     * @return it will return the index of where it exist. If it doesn't exist it will give you an error
     */
    public int find (String Name){
        for (int i = 0; i <medList.size(); i ++){
            if (medList.get(i).getName().equalsIgnoreCase(Name)){
                return i;
            }
        }
        return -1;//Not Found
    }

    /**
     *
     * @return the size of the medicine list
     */
    public int size (){return medList.size();}



    public boolean add (Context context, Medicine medicine){
        //Searches for if name already exists
        if (find (medicine.getName()) == -1) {
            medList.add(medicine);
            sort();
            updateFile(context);
            return true;
        }else{
            return false;
        }
    }

    /**
     * This method will replace medicine with the right one
     * @param medicine medicine you want to swap in list
     * @return true if sucessful, false if not
     */
    public boolean replace (Context context, Medicine medicine){
        int index = find(medicine.getName());
        if (index != -1){
            medList.remove(index);
            medList.add(medicine);
            updateFile(context);
            return true;
        }else {
            //Not found
            Log.e("MedicineList", "Not Found");
            return false;
        }

    }


    public void remove (Context context, int index){
        if (index <medList.size() &&index >= 0)
        medList.remove(index);
    }

    public void remove (Context context, String name){
        int index = find(name);
        if (index <medList.size() &&index >= 0) {
            medList.remove(index);
            updateFile(context);
        }

    }

    public Medicine getMedicine (int i){
        //Making sure it is between the range
        if (i >= 0 && i < medList.size())
            return medList.get(i);
        else
            return null;
    }

    public Medicine getMedicine (String medName){
        //Find the data structure for the corresponding medicine
        for (int i = 0; i < medList.size(); i ++) {
            if (medList.get(i).getName().equals(medName)){
                return medList.get(i);
            }
        }
        //If nothing is found return null
        return null;
    }

    public ArrayList<String> getMedicineNames (){
        //Making sure there is medicine in the list
        if (medList.size() == 0)
            return new ArrayList<String>();
        //Returning the medicine names
        else {
            sort ();
            ArrayList<String> result = new ArrayList<>();

            for (int i = 0; i < medList.size(); i ++ ){
                result.add(medList.get(i).getName());
            }


/*            String [] result = new String [medList.size()];

            //Get the names
            for (int i = 0; i < medList.size(); i ++ ){
                result[i] =(medList.get(i).getName());
            }

            //Sorting it
            Insertion.sort(result);

            //Converting into arraylist
            ArrayList<String> results = new ArrayList<String>(Arrays.asList(result));
            //Testing
*//*            for (int i = 0; i < results.size(); i ++ ){
                Log.d("toto", results.get(i)); //result[i] =(medList.get(i).getName());
            }*/
            return result;
        }
    }

    public void updateFile(Context context){

        IOHelper io = new IOHelper();
    	String result = "";
        for (int i = 0; i < medList.size(); i ++){
        	result= result + medList.get(i); //Appending to the list 
        	if(i !=  medList.size() - 1 ) {
        		result= result + "-";
        	}
        }
        //Add code to write to file 
        io.writeToFile(context,file,result);
        //System.out.println(result); //Eclipse 
    }
    
    public void updateList(Context context) {

        IOHelper io = new IOHelper();
    	//Add code to read from file 
    	String data = io.read(context,file);

/*                "Facebook,asdf,asdf,1,1,1-"
    			+ "Facebook3,asdf,asdf,1,1,2-"
    			+ "Facebook5,asdf,asdf,1,1,3-"
    			+ "Facebook3,asdf,asdf,1,1,4-"
    			+ "Facebook5,asdf,asdf,1,1,5";*/
    
    	if (data.length() != 0) {
            //Spliting data into medicine
            String[] stringMedList = data.split("-");

            //Arraylist to store the Medication
            ArrayList<Medicine> med = new ArrayList<Medicine>();

            //Converting into medicine object
            for (int i = 0; i < stringMedList.length; i++) {
                med.add(Medicine.convertToMedicineObject(stringMedList[i]));
                //Medicine.convertToMedicineObject(stringMedList[i]);
                Log.d("Medicine", (stringMedList[i]));
            }

            //Overwriting the new medlist with the old one
            this.medList = med;
        }else{
            Log.d("Reading", "(" + data +")" + Integer.toString(data.length()));
            Log.d("Reading", "it went here");
            this.medList =  new ArrayList<Medicine>();
        }
    	  	
    }

    public void sort (){
        Medicine [] result = new Medicine [medList.size()];

        //Get the medicines
        for (int i = 0; i < medList.size(); i ++ ){
            result[i] = medList.get(i);
        }



        //Sorting it
        Insertion.sort(result);



        ArrayList<Medicine> x = new ArrayList<Medicine>();
        //Get the medicines
        for (int i = 0; i < medList.size(); i ++ ){
             x.add(result[i]);
        }

        Log.d("tag", "made it here");

        medList = x;

    }



}
