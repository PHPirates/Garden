package com.garden.gardenapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //right bottom floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
/* //for normal buttons
    public void settings(View v) { //onclick
        Toast.makeText(this,"button clicked",Toast.LENGTH_LONG).show();
    }
*/

    public void setAlarm(View v) {
        TimePicker timePicker; //TODO the pickers should be in a DialogFragment
        DatePicker datePicker;
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        //get user input
        EditText editText = (EditText) findViewById(R.id.editReminder);
        String reminder = editText.getText().toString();
        String snoozeString = getString(R.string.snooze_result);

        //the AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //get date and time
        Calendar c = Calendar.getInstance();

        //sets time for alarm
        c.set(Calendar.YEAR, datePicker.getYear());
        c.set(Calendar.MONTH, datePicker.getMonth());
        c.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        c.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour()); //getHour requires 23...
        c.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        c.set(Calendar.SECOND, 0);

        //pIntent to launch activity when alarm triggers
        Intent intent = new Intent("com.garden.DisplayNotification"); //(1)From here to DisplayNotification ...
        //DisplayNotification is the activity that is intended to be evoked
        //when the alarm is triggered

        //assign an ID of 1, and add the text
        intent.putExtra("NotifID", 1);
        intent.putExtra("notification", reminder); //("STRING_I_NEED",strnamed)
        intent.putExtra("notifyAction", snoozeString); //string for the action button

        PendingIntent displayIntent = PendingIntent.getActivity(
                getBaseContext(), 0,
                intent, 0); //this intent instead of new Intent("com.garden.Reminder")


        //sets alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                c.getTimeInMillis(), displayIntent);


    }

    public void remindMe (View view) { //to immediately show notification for debugging purposes
        /**
         * Now try that using DisplayNotification and the alarmmanager!
         * (But still without the real delay for debugging purposes.)
         */


        //initialize the texView and editText again
        TextView textView = (TextView) findViewById(R.id.hello);
        //get the inserted text
        EditText editText = (EditText) findViewById(R.id.editReminder);
        String reminder = editText.getText().toString();

        textView.setText(reminder); //set reminder text to that textview
        String snoozeString = "I'm snoozed"; //the string to set to the textview after action button

        //the AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //pIntent to launch activity when alarm triggers
        Intent intent = new Intent("com.garden.DisplayNotification"); //(1)From here to DisplayNotification ...
        //DisplayNotification is the activity that is intended to be evoked
        //when the alarm is triggered

        //assign an ID of 1, and add the text
        //NOTE intents will be split for the different notifButtons in DisplayNotification
        intent.putExtra("NotifID", 1);
        intent.putExtra("notification", reminder); //("STRING_I_NEED",strnamed)
        intent.putExtra("notifyAction", snoozeString); //string for the action button

        PendingIntent displayIntent = PendingIntent.getActivity(
                getBaseContext(), 0,
                intent, 0); //this intent instead of new Intent("com.garden.Reminder")
        //set alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(), displayIntent);
    }

    public void remindMe_old(View view) { //to immediately show notification for debugging purposes

        //initialize the texView and editText again
        TextView textView = (TextView) findViewById(R.id.hello);
        //get the inserted text
        EditText editText = (EditText) findViewById(R.id.editReminder);
        String reminder = editText.getText().toString();

        textView.setText(reminder); //set reminder text to that textview
        String snoozeString = "I'm snoozed"; //the string to set to the textview after action button

        //inside the notification, the action is defined by a PendingIntent
        //Intent intent = new Intent(this, Reminder.class); //intent starts an activity
        Intent intent = new Intent(this, Reminder.class);
        //or "com.garden.DisplayNotification", doesn't seem to matter
        intent.putExtra("notification", reminder);
        //intent.putExtra("notifyAction",snoozeString); //string for when action button pressed
        intent.putExtra("NotifID", 1); // --- added NotifID
        PendingIntent pendingIntent = PendingIntent.getActivity(this, //PendingIntent contains intent
                (int) System.currentTimeMillis(), intent, 0);

        //Make a new intent for the action button //Unnecessary. Just do another putExtra?
        //NO, you need a different intent because they should result in different actions?
        Intent actionIntent = new Intent(this, Reminder.class);
        actionIntent.putExtra("notifyAction", snoozeString); //("STRING_I_NEED", strName)
        PendingIntent actionpIntent = PendingIntent.getActivity(this,
                (int) System.currentTimeMillis(), actionIntent, 0);

      //notification is made in DisplayNotification
        Notification notify = new Notification.Builder(this) //build the notification
                .setContentTitle(getString(R.string.app_name)) //required
                .setContentText(reminder) //required
                .setSmallIcon(R.drawable.garden) //required
                .setContentIntent(pendingIntent)
                        //associate pendingIntent with a gesture of NotificationCompat.Builder: click
                .addAction(R.drawable.pixel, "Snooze me", actionpIntent)
                        //should be addAction(NotificationCompat.Action action)
                        //.setVibrate(new long[] {200, 600, 200, 600})
                .setAutoCancel(true) //autocancel here, the removing in the reminder
                        // activity works, don't know why
                .setPriority(Notification.PRIORITY_MAX) //to show the action buttons by default
                .build();

        //notification manager
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notify); //(int id, Notification notification);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //what happens when you click what button (in the action bar)
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "There are no settings yet!", Toast.LENGTH_LONG).show();
            case R.id.action_about:
                Toast.makeText(this, "About nothing, really.", Toast.LENGTH_LONG).show();
                TextView textView = (TextView) findViewById(R.id.hello);
                textView.setText(getString(R.string.newText));

        }

        return super.onOptionsItemSelected(item);
    }
}
