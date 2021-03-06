package com.garden.gardenapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements DateFragment.OnDatePass,TimeFragment.OnTimePass  { //interface to get data from frag
    FragmentManager fm = getSupportFragmentManager();
    int year, month, day, hour, minute; //time as instance variables, much easier


    @Override
    public void onDatePass(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public void onTimePass(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

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


    public void showDateDialog(View v) {
        DateFragment dateFragment = new DateFragment();
        // Show DialogFragment
        dateFragment.show(fm, "Dialog Fragment");
    }

    public void showTimeDialog(View v) {
        TimeFragment timeFragment = new TimeFragment();
        // Show DialogFragment
        timeFragment.show(fm, "Dialog Fragment");
    }

    public void setAlarm(View v) {
        //get user input
        EditText editText = (EditText) findViewById(R.id.editReminder);
        String reminder = editText.getText().toString();
        String snoozeString = getString(R.string.snooze_result);


        //the AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //get date and time
        Calendar c = Calendar.getInstance();

        //sets time for alarm
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        //pIntent to launch activity when alarm triggers
        Intent intent = new Intent("com.garden.DisplayNotification"); //(1)From here to DisplayNotification ...
        //DisplayNotification is the activity that is intended to be evoked
        //sometimes you have to use (this,DisplayNotification.class)?
        //when the alarm is triggered

        //assign an ID of 1, and add the text
        intent.putExtra("NotifID", 1);
        System.out.println("main"+reminder);
        intent.putExtra("notification", reminder); //("STRING_I_NEED",strname)
        intent.putExtra("notifyAction", snoozeString); //string for the action button

        PendingIntent displayIntent = PendingIntent.getActivity(
                getBaseContext(), 0,
                intent, 0); //this intent instead of new Intent("com.garden.Reminder")


        //sets alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                c.getTimeInMillis(), displayIntent);


    }

    public void setAlarmNow (View view) {
        /**
         * This is a debugging method that is the same as setAlarm but
         * sends a notification instantly.
         */
        TextView textView = (TextView) findViewById(R.id.hello);
        EditText editText = (EditText) findViewById(R.id.editReminder);
        String reminder = editText.getText().toString();
        textView.setText(reminder);
        String snoozeString = "I'm snoozed";
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent("com.garden.DisplayNotification");
        intent.putExtra("NotifID", 1);
        intent.putExtra("notification", reminder);
        intent.putExtra("notifyAction", snoozeString);
        PendingIntent displayIntent = PendingIntent.getActivity(
                getBaseContext(), 0,
                intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(), displayIntent);
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
