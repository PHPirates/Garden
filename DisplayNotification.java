package com.garden.gardenapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

public class DisplayNotification extends Activity {
    /**
     * This activity is to display a notification only. It is called when
     * the system alarm goes off.
     * @param savedInstanceState What is a bundle?
     */

    //called when activity is first created
    //don't forget to update the manifest!!
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get notification ID passed by MainActivity
        int notifID = getIntent().getExtras().getInt("NotifID");
        //----Oh of course, have to pass on the strings again.....-----
        //initialize strings (reminder is also used for notification text
        String reminderText = getIntent().getStringExtra("notification");
        String snoozeString = getIntent().getStringExtra("notifyAction");
        System.out.println("disp"+reminderText);


        //pIntent to launch activity if user selects notification
        /** making a new Intent has to be done with (this, Reminder.class) instead
         * of ("com.garden.Reminder")... why? (otherwise the reminder text is not shown)
         */
        Intent reminderIntent = new Intent(this, Reminder.class); //(2)... and from here to Reminder ...
        reminderIntent.putExtra("NotifID", notifID);
        //pass on strings again in the intent
        reminderIntent.putExtra("notification", reminderText);
        //intent.putExtra("notifyAction",snoozeString); //---> in different intent

        PendingIntent reminderPIntent = PendingIntent
                .getActivity(this, 0, reminderIntent, 0);

        Intent actionIntent = new Intent(this, Reminder.class);
        actionIntent.putExtra("notifyAction", snoozeString); //("STRING_I_NEED", strName)
        //don't forget to pass the notifID also to the second intent
        actionIntent.putExtra("NotifID",notifID);
        PendingIntent actionPIntent = PendingIntent.getActivity(this,
                (int) System.currentTimeMillis(), actionIntent, 0);


        //create notification
        Notification notif = new Notification.Builder(this) //build the notification
                .setContentTitle(getString(R.string.app_name)) //required
                .setContentText(reminderText) //required
                .setSmallIcon(R.drawable.garden) //required
                .setContentIntent(reminderPIntent)
                //associate pendingIntent with a gesture of NotificationCompat.Builder: click
                .addAction(R.drawable.pixel, "Snooze me", actionPIntent)
                //should be addAction(NotificationCompat.Action action)
                .setAutoCancel(true) //to be dismissed in the Reminder activity
                .setPriority(Notification.PRIORITY_MAX) //to show the action buttons by default
                // .setVibrate(new long[] {200, 600, 200, 600})
                .build();

        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        nm.notify(notifID, notif); //(int id, Notification notification);

        finish(); //because this doesn't have a GUI we don't need it anymore

    }
}
