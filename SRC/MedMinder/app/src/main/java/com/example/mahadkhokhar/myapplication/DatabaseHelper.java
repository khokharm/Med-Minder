package com.example.mahadkhokhar.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Structures.Medicine;

/**
 * Created by Prabhbir Poooni on 2017-11-21
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOGCAT = null;

    //Names for
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = " MEDMINDER ";


    public static final String KEY_ID = " id ";
    public static final String KEY_NAME = " name ";
    public static final String KEY_TYPE = " type ";
    public static final String KEY_DESCRIPTION = " description ";
    public static final String KEY_HOUR = " hour ";
    public static final String KEY_MINUTE = " minute ";

    public DatabaseHelper(Context context) { super(context, DATABASE_NAME , null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        SQLiteDatabase database = this.getWritableDatabase();
        String CREATE_MEDICINE_DETAIL_TABLE = "CREATE TABLE" + DATABASE_NAME + "( "
                + KEY_ID + "INTEGER,"
                + KEY_NAME + "TEXT PRIMARY KEY,"
                + KEY_TYPE + "TEXT,"
                + KEY_DESCRIPTION + "TEXT,"
                + KEY_HOUR + "INTEGER,"
                + KEY_MINUTE + "INTEGER"  +" )";

        db.execSQL(CREATE_MEDICINE_DETAIL_TABLE);
        Log.d(LOGCAT,"Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

    void addNewMedicine(Medicine newMed){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, newMed.getID());
        values.put(KEY_NAME, newMed.getName());
        values.put(KEY_TYPE, newMed.getType());
        values.put(KEY_DESCRIPTION, newMed.getDescription());
        values.put(KEY_HOUR, newMed.getHour());
        values.put(KEY_MINUTE, newMed.getMinute());
        database.insert(DATABASE_NAME, null, values);
        database.close();

    }

    public boolean updateMedicineInfo(String name, String type, String description, int hour, int minute, int id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues args = new ContentValues();

        args.put(KEY_ID, id);
        args.put(KEY_TYPE, type);
        args.put(KEY_DESCRIPTION, description);
        args.put(KEY_HOUR, hour);
        args.put(KEY_MINUTE, minute);

        return db.update(DATABASE_NAME, args, KEY_NAME + "=" + name, null)>0;

    }

    public boolean deleteMedicine(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(DATABASE_NAME, KEY_NAME + "=" + name, null ) >0;
    }


    public List<Medicine> getAllMedicine(){
        List<Medicine> medsList = new ArrayList<Medicine>();
        String selectInfo = "SELECT * FROM" + DATABASE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectInfo, null);

        if(cursor.moveToFirst()){
            do{
                Medicine Med=new Medicine(null,null,null,0,0,0);
                Med.setID(Integer.parseInt(cursor.getString(0)));
                Med.setName(cursor.getString(1));
                Med.setType(cursor.getString(2));
                Med.setHour(Integer.parseInt(cursor.getString(3)));
                Med.setMinute(Integer.parseInt(cursor.getString(4)));
                Med.setDescription(cursor.getString(5));

                medsList.add(Med);

            }while(cursor.moveToNext());
        }

        return medsList;
    }
}
