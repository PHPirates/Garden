package com.abbyberkers.remember;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });


    }



//buttons-------------------------------------------------------------------------------------------

    public void setAlarm(View v){
        TimePicker timePicker;
        DatePicker datePicker;
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        //get user input
        EditText editText = (EditText) findViewById(R.id.edit_remember);
        String message = editText.getText().toString();
        String snoozeString = getString(R.string.snooze_result);


        //the AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //get date and time
        Calendar c = Calendar.getInstance();

        //sets time for alarm
        c.set(Calendar.YEAR, datePicker.getYear());
        c.set(Calendar.MONTH, datePicker.getMonth());
        c.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        c.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        c.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        c.set(Calendar.SECOND, 0);
        //pIntent to launch activity when alarm triggers
        Intent intent = new Intent(this, DisplayNotification.class);
        intent.putExtra("NotifID", 1);
        intent.putExtra("notification", message);
        intent.putExtra("snoozeNoti", snoozeString);

        PendingIntent displayIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);

        //sets alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), displayIntent);

    }

        public void setAlarmNow (View view) {
            //debugging method, same as setAlarm but sends notification instantly.

            TextView textView = (TextView) findViewById(R.id.please);
            EditText editText = (EditText) findViewById(R.id.edit_remember);
            String message = editText.getText().toString();
            textView.setText(message);
            String snoozeString = "Snoozed.";

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent("com.abbyberkers.remember.DisplayNotification");
            intent.putExtra("NotifID", 1);
            intent.putExtra("notification", message);
            intent.putExtra("snoozeNoti", snoozeString);
            PendingIntent displayIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), displayIntent);
        }

//    public void createNotification(View view){
//        EditText editText = (EditText) findViewById(R.id.edit_remember);
//        String message = editText.getText().toString();
//
//        Intent intent = new Intent(this, Notified.class);
//        intent.putExtra("notification", message);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
//
//        Intent snoozeIntent = new Intent(this, Notified.class);
//        snoozeIntent.putExtra("snoozeNoti", message);
//        PendingIntent snoozepIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), snoozeIntent, 0);
//
//        Notification notify = new Notification.Builder(this)
//                .setContentTitle(getString(R.string.app_name))
//                .setContentText(message)
//                .setSmallIcon(R.drawable.logo)
//                .setContentIntent(pendingIntent)
//                .addAction(R.drawable.transparant, "Snooze", snoozepIntent)
//                //.setVibrate(new long[]{200, 200, 200, 600})
//                .setPriority(Notification.PRIORITY_MAX)
//                .setAutoCancel(true)
//                .build();
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notify.flags |= Notification.FLAG_AUTO_CANCEL;
//        notificationManager.notify(0, notify);
//
//    }

//end buttons---------------------------------------------------------------------------------------

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

        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this, "settings clicked", Toast.LENGTH_SHORT).show();
            case R.id.action_options:
                Toast.makeText(this, "options clicked", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
