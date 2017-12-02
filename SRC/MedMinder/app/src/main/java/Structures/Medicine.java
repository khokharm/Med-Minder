package Structures;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Mahad Khokhar on 2017-11-04.
 */


/**
 * <h1>The blueprints for medicine class </h1>
 *
 * @author Mahad Khokhar
 * Date: Nov 04 2017
 * @version 0
 *
 *<p> This is the data structure for medicine. Medicine will have name, type, description, the time (hour and minute) and id which is used for the notificaiton id and pending intent id   </p>
 */
public class Medicine implements Serializable, Comparable<Medicine>{

    //Variables------------------------------------------------------------------------------------

    private String name;
    private String type;
    private String description;
    private int minute;
    private int hour;
    private int id;
    private static int counter = 0;

    //Constructors--------------------------------------------------------------------------------

    /**
     * Constructor 1
     * @param name the name of the medicine
     * @param type the type of medicine
     * @param description description of medicine
     * @param hour the hour medicine
     * @param minute the minute when you want to be notified
     * @param id integer ID
     */
    public Medicine(String name, String type, String description, int hour, int minute, int id ){
        this.name = name;
        this.type = type;
        this.description = description;
        this.hour = hour;
        this.minute = minute;
        this.id=id;
    }



    //Getters-------------------------------------------------------------------------------------

    /**
     *
     * @return ID  of the medication
     */
    public int getId (){
        return this.id;
    }

    /**
     *
     * @return get the name of the medication
     */
    public String getName (){
        return this.name;
    }

    /**
     *
     * @return the type of medication
     */
    public String getType () {
        return this.type;
    }

    /**
     *
     * @return the hour which you want to be notified
     */
    public int getHour() {
        return this.hour;
    }

    /**
     *
     * @return the minute which you want to be notified
     */
    public int getMinute () {
        return this.minute;
    }

    /**
     *
     * @return the time in 24 hours standard
     */
    public String getTime24H () {
        String result = "";
        if (hour<10){
            result = "0"+ Integer.toString(hour);
        }else {
            result = Integer.toString(hour);
        }

        result=result+":";

        if (minute<10){
            result=result +"0"+ Integer.toString(minute);
        }else {
            result = result+ Integer.toString(minute);
        }

        return result;
    }

    /**
     *
     * @return the description of the medication
     */
    public String getDescription() {
        return this.description;
    }


    //Setter----------------------------------------------------------

    /**
     *
     * @param id set the new ID
     */
    public void setId (int id){
        this.id = id;
    }

    /**
     *
     * @param name sets the new name
     */
    public void setName (String name){
        this.name=name;
    }

    /**
     *
     * @param type sets the type of medication
     */
    public void setType (String type) {
        this.type=type;
    }


    /**
     *
     * @param hour sets the hour of medication
     */
    public void setHour(int hour) {
        this.hour=hour;
    }

    /**
     *
     * @param minute sets the minute of medication
     */
    public void setMinute (int minute) {
        this.minute= minute;
    }

    /**
     *
     * @param description sets the description of medication
     */
    public void setDescription(String description) {
        this.description=description;
    }

    //Reading and writing methods--------------------------------------------------------------

    /**
     *
     * @return a string object with the description of the object
     */
    public String toString () {
        return this.name + "," + this.type + "," + this.description + "," + this.getHour()+ "," + this.getHour() + "," + this.getId();
    }

    /**
     *
     * @param object Takes a string object and converts it into medicine Object
     * @return A medicine Object corresponding to the String inputted
     */
    public static Medicine convertToMedicineObject (String object) {
        String [] element = object.split(",");
        return new Medicine (element[0], element[1],element[2], Integer.parseInt(element[3].trim()), Integer.parseInt(element[4].trim()), Integer.parseInt(element[5].trim()));
    }

    /**
     *
     * @param medicine medication you want to compare to
     * @return the medication which comes first alphabetically
     */
    @Override
    public int compareTo(@NonNull Medicine medicine) {
        return this.getName().compareTo(medicine.getName());
    }
}