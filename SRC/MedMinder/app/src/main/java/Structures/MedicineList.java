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


/**
 * <h1>An arraylist of medicine</h1>
 *
 * @author Mahad Khokhar
 * Date: Nov 09 2017
 * @version 0
 *
 *<p> The class will deal with retrieving and storing the information to the list of medicine. The list will
 * also handle any manipulation to the list. There can only be one medicine with a unique name in the list   </p>
 */
public class MedicineList {

    //Name of the medicine list file
    final String file = String.valueOf(R.string.medicineFile);

    /**
     * The list which keeps all the medication
     */
    private static ArrayList<Medicine> medList;

    /**
     * This is the default constructor for the medicine. When the constructor is going to get all the previous stored medicine from the list
     * @param context the context is used to get the medicine resources in the file
     */
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
     * @return it will return the index of where it exist. If it doesn't exist it will give you an error (-1)
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


    /**
     * The method will add the medicine if the medicine already doesn't exist (check by name)
     * @param context the context will be used to get the name of
     * @param medicine the medicine you want added to the list
     * @return true if the add has been successful and false if the add has been unsuccessful
     */
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

    /**
     * The remove will get rid the of the medicine at an index. If the index doesn't exist then it will do nothing
     * @param context the context will reference the file
     * @param index the index which you want to be deleted at
     */
    public void remove (Context context, int index){
        if (index <medList.size() &&index >= 0)
        medList.remove(index);
    }

    /**
     * The remove will the medicine given a name. The method will do nothing
     * @param context the context will reference the file
     * @param name the name of hte medicine you want ot delete
     */
    public void remove (Context context, String name){
        int index = find(name);
        if (index <medList.size() &&index >= 0) {
            medList.remove(index);
            updateFile(context);
        }

    }

    /**
     * return the medicine at the index
     * @param i the index
     * @return the medicine corresponding to the index
     */
    public Medicine getMedicine (int i){
        //Making sure it is between the range
        if (i >= 0 && i < medList.size())
            return medList.get(i);
        else
            return null;
    }

    /**
     * return the medicine given the name
     * @param medName the index
     * @return the medicine corresponding to the name
     */
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

    /**
     *
     * @return The method will return a ArrayList with all the medicine name
     */
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
            return result;
        }
    }

    /**
     * This method is used to update the file
     * @param context used to reference the medicine file
     */
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

    /**
     * The method will update the medicine list
     * @param context used to reference the medicine file
     */
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

    /**
     * The method will be used to sort the medicine alphabetically
     */
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
