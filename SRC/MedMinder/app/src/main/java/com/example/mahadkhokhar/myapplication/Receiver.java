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


/**
 * This is the main activity. This will be used
 */

/**
 * <h1>The main activity class  </h1>
 *
 * @author Mahad Khokhar
 * Date: Nov 16 2017
 * @version 0
 *
 *<p> </p>
 */
public class Receiver extends BroadcastReceiver {

    /**
     * This method happens when the application recieves the pending intent
     * @param context get the current context
     * @param intent getting the intent and the data from the intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        //Getting the data from intent
        Bundle extras = intent.getExtras();
        //Testing ----------------------------------------------------------------------------------------
/*        if (extras != null) {
            if (extras.getString("MedicineName") != null|| extras.getString("MedicineDescription") != null)
                Toast.makeText(context, (extras.getString("MedicineName")+ extras.getString("MedicineDescription")), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Nothingness", Toast.LENGTH_SHORT).show();

        }*/

        //The Notification Part----------------------------------------------------------------------
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
            mBuilder.setContentTitle(extras.getString("MedicineName") + " Medicine Alert");
            mBuilder.setContentText(extras.getString("MedicineDescription"));
            mBuilder.setPriority(NotificationManager.IMPORTANCE_HIGH);
            mBuilder.setColor(Color.RED);
            // Builds the notification and issues it.
            mNotificationManager.notify(Integer.parseInt(extras.getString("Counter")), mBuilder.build());

        }

    }
}
