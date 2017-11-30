package Structures;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Mahad Khokhar on 2017-11-04.
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

    public Medicine(String name, String type, String description, int hour, int minute, int id ){
        this.name = name;
        this.type = type;
        this.description = description;
        this.hour = hour;
        this.minute = minute;
        this.id=id;
    }

    public Medicine(String name, String type, String description, int hour, int minute ){
        this.name = name;
        this.type = type;
        this.description = description;
        this.hour = hour;
        this.minute = minute;
        this.id=counter;
        counter++;
    }

    //Getters-------------------------------------------------------------------------------------

    public int getId (){
        return this.id;
    }

    public String getName (){
        return this.name;
    }

    public String getType () {
        return this.type;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute () {
        return this.minute;
    }

    public String getTime12H () {
        return Integer.toString(hour%12) + ":" + Integer.toString(minute);
    }

    public String getTime24H () {
        String result = "";
        if (hour<100){
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

    public String getDescription() {
        return this.description;
    }


    //Setter----------------------------------------------------------

    public void setId (int id){
        this.id = id;
    }

    public void setName (String name){
        this.name=name;
    }

    public void setType (String type) {
        this.type=type;
    }

    public void setHour(int hour) {
        this.hour=hour;
    }

    public void setMinute (int minute) {
        this.minute= minute;
    }

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

    @Override
    public int compareTo(@NonNull Medicine medicine) {
        return this.getName().compareTo(medicine.getName());
    }
}