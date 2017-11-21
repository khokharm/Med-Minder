package Structures;

import java.io.Serializable;

/**
 * Created by Mahad Khokhar on 2017-11-04.
 */

public class Medicine implements Serializable{

    private String name;
    private String type;
    private String description;
    private int minute;
    private int hour;

    public Medicine(String name, String type, String description, int hour, int minute ){
        this.name = name;
        this.type = type;
        this.description = description;
        this.hour = hour;
        this.minute = minute;
    }

    public String getName (){
        return this.name;
    }

    public String getType () {
        return this.type;
    }

    public int getHour() { return this.hour;}

    public int getMinute () {return this.minute;}

    public String getTime12H () {return Integer.toString(hour%12) + ":" + Integer.toString(minute);}

    public String getTime24H () {return Integer.toString(hour) + ":" + Integer.toString(minute);}

    public String getDescription() {return this.description;}

}
