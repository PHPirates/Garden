package com.abbyberkers.remember;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_display_notification);

        //get notification ID passed by MainActivity
        int notifID = getIntent().getExtras().getInt("NotifID");

        String message = getIntent().getStringExtra("notification");
        String action_message = getIntent().getStringExtra("snoozeNoti");

        Intent intent = new Intent(this, Notified.class);
        intent.putExtra("NotifID", notifID);
        intent.putExtra("notification", message);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent actionIntent = new Intent(this, Notified.class);
        actionIntent.putExtra("NotifID", notifID);
        actionIntent.putExtra("snoozeNoti", action_message);
        PendingIntent actionPIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), actionIntent,0);

        Notification notif = new Notification.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(message)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.transparant, "Snooze", actionPIntent)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .build();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        nm.notify(notifID, notif);

        finish();

    }
}
