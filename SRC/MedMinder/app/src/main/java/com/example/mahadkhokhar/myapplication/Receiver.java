package com.example.mahadkhokhar.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import Structures.Medicine;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Mahad Khokhar on 2017-11-16.
 */


public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //Used to see if we are in the broadcast system
        Toast.makeText(context, "testing for broadcastReceiver", Toast.LENGTH_LONG).show();

        //Medicine
        Medicine med = null;

        String action = intent.getAction();

        ///Toast.makeText(context, ("Receiver. " + "Broadcast received: " + action), Toast.LENGTH_SHORT).show();


/*        Bundle extras = intent.getExtras();
        if (extras != null){

            if (extras.getString("Tag") != null)
                Toast.makeText(context, "working", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Nothingness", Toast.LENGTH_SHORT).show();
*//*            med = ((Medicine) extras.getSerializable("Medicine"));
            try {
                if (med != null)
                    Toast.makeText(context, med.getName(), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Nothingness", Toast.LENGTH_SHORT).show();*//*
           *//* }
            catch (Exception e){

            }*/


                        //

        //}






        //The Notification Part


        //Creating the Manager
        NotificationManager mNotificationManager =(NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        //Checking if it is oreo because the way you implement notification in oreo is differnt
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            final String ID = "MedicineID";

            NotificationChannel mChannel = new NotificationChannel("MedicineID", "Medicine", NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription("This channel will notify the user to take thier medicine");
            mChannel.setLightColor(Color.RED);
            mChannel.enableLights(true);

            mChannel.enableVibration(true);
            mNotificationManager.createNotificationChannel(mChannel);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.androidicon)
                            .setContentTitle("My notification")
                            .setContentText("Hello World!")
                            .setChannel(ID);

            mNotificationManager.notify(100, mBuilder.build());
        }else{
            //NotificationBuilder
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setSmallIcon(R.drawable.androidicon);
            mBuilder.setContentTitle("My notification");
            mBuilder.setContentText("Hello World!");
            // Builds the notification and issues it.
            mNotificationManager.notify(100, mBuilder.build());
        }

    }
}
