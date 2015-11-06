package com.garden.gardenapp;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Reminder extends AppCompatActivity {
    //this is the activity that is shown after you click the notification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /**this activity gets both strings from both intents, so that's why you need
         * to use different intents for the notification and action buttons,
         * so that when you click e.g. the action button the other string is empty.
         */



        //the textview that shows the string the notification passed
        TextView textView = (TextView) findViewById(R.id.reminderText);
        String notifyText = getIntent().getStringExtra("notification");
        textView.setText(notifyText);
        //now the same for the action button from the notification
        TextView snoozeView = (TextView) findViewById(R.id.snoozeText);
        //(3).... and then get String and setText!
        String actionText = getIntent().getStringExtra("notifyAction"); //getString("STRING_I_NEED");
        snoozeView.setText(actionText);

        //This will remove the notification if the action button is pressed
        // instead of the notification, but only when the activity is displayed
        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(getIntent().getExtras().getInt("NotifID"));
        //TODO dismiss notification when snooze is clicked
    }

}
