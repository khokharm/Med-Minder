package com.example.mahadkhokhar.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mahad Khokhar on 2017-11-05.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //Names for
    public static final String DATABASE_NAME = "MEDMINDER.db";

    public static final String TABLE_1_NAME = "USER";
    public static final String T1_C1_NAME = "ID";
    public static final String T1_C2_NAME = "First_Name";
    public static final String T1_C3_NAME = "Last_Name";
    private SQLiteDatabase db;



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
        //getWritableDatabase();
        Log.i("create","Main");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("create","In the method");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_1_NAME + "( name CHAR (10));");
                //"  (" + T1_C2_NAME + " CHAR (15) NOT NULL, " +
    //T1_C3_NAME + " CHAR (15) NOT NULL, PRIMARY KEY (" + T1_C2_NAME + ", " + T1_C3_NAME + ") );");
}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_1_NAME);
        onCreate(sqLiteDatabase);
    }
}
