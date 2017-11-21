package Structures;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by Mahad Khokhar on 2017-11-09.
 */

public class MedicineList {

    private static Semaphore x = new Semaphore (1);
    private static ArrayList<Medicine> medList = new ArrayList <Medicine> ();

    public static boolean add (Medicine medicine){

        //x.acquire();
        //Checking if previous medicine exists
        /*for (int i = 0; i < medList.size(); i ++){
            if (medicine.getName().equalsIgnoreCase(medList.get(i).getType())){
                //--------Insert Error message for medicine not being created
                //x.release();
                return false;
            }
        }*/

        //Checking if the medicine
        medList.add(medicine);
       // x.release();
        return true;
    }

    public static void remove (int index){
        if (index <medList.size() &&index >= 0)
        medList.remove(index);
    }

    public static Medicine getMedicine (int i){
        //Making sure it is between the range
        if (i >= 0 && i < medList.size())
            return medList.get(i);
        else
            return null;
    }

    public static Medicine getMedicine (String medName){
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
            return null;
        //Returning the medicine names
        else {
            ArrayList<String> results = new ArrayList<String>() ;
            for (int i = 0; i < medList.size(); i ++ ){
                results.add(medList.get(i).getName());
            }
            return results;
        }
    }





}
