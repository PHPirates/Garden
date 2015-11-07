package com.garden.gardenapp;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
        TextView mainTextView = (TextView) findViewById(R.id.reminderText);
        String reminderText = getIntent().getStringExtra("notification");
        System.out.println("reminder" + reminderText); //TODO string always empty

        //now the same for the action button from the notification
        TextView snoozeTextView = (TextView) findViewById(R.id.snoozeText);
        //(3).... and then get String and setText!
        String snoozeText = getIntent().getStringExtra("notifyAction"); //getString("STRING_I_NEED");
        if (TextUtils.isEmpty(reminderText)) {
            //if the reminder string is empty it means the snooze button was pressed
            snoozeTextView.setText(snoozeText);
        } else {
            mainTextView.setText(reminderText);
        }
        //This will remove the notification if the action button is pressed
        // instead of the notification, but only when the activity is displayed
        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(getIntent().getExtras().getInt("NotifID"));
    }

}
