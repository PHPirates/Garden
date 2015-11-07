package com.abbyberkers.remember;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Notified extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notified);
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



        TextView textView = (TextView) findViewById(R.id.nMessage);
        String notify_text = getIntent().getStringExtra("notification");

        TextView textView1 = (TextView) findViewById(R.id.actionMessage);
        String action_text = getIntent().getStringExtra("snoozeNoti");

        if(!TextUtils.isEmpty(notify_text)){
            textView.setText(notify_text);
        } else {
            textView1.setText(action_text);
        }

    }

}
