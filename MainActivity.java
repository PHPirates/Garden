package com.garden.gardenapp;

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
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

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

    public void remindMe(View view) {
        //initialize the texView and editText again
        TextView textView = (TextView) findViewById(R.id.hello);
        EditText editText = (EditText) findViewById(R.id.editReminder);
        String reminder = editText.getText().toString();
        textView.setText(reminder);
        String notifyString = "I'm snoozed";

        //inside the notification, the action is defined by a PendingIntent
        Intent intent = new Intent(this, Reminder.class); //intent starts an activity
        intent.putExtra("notification",reminder);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, //PendingIntent contains intent
                (int) System.currentTimeMillis(), intent, 0);

        //Make a new intent for the action button
        Intent actionIntent = new Intent(this, Reminder.class);
        actionIntent.putExtra("notifyAction",notifyString); //("STRING_I_NEED", strName)
        PendingIntent actionpIntent = PendingIntent.getActivity(this,
                (int) System.currentTimeMillis(),actionIntent,0);

        Notification notify = new Notification.Builder(this) //build the notification
                .setContentTitle(getString(R.string.app_name)) //required
                .setContentText(reminder) //required
                .setSmallIcon(R.drawable.garden) //required
                .setContentIntent(pendingIntent)
                //associate pendingIntent with a gesture of NotificationCompat.Builder: click
                .addAction(R.drawable.pixel,"Snooze me",actionpIntent)
                //.addAction(NOTIFY_ACTION)
                //should be addAction(NotificationCompat.Action action)
                //.setVibrate(new long[] {200, 600, 200, 600})
                .build();

        //notification manager?
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
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
