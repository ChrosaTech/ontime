package com.chrosatech.ontime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class NotificationActivity extends AppCompatActivity {



    NotificationManager manager;
    Notification myNotication;
    Button btnShow, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initialise();

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        btnShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //API level 11

                Notification.Builder mBuilder =
                        new Notification.Builder(NotificationActivity.this)
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setContentTitle("Lecture")
                                .setContentText("Room")
                                .setAutoCancel(true);

                Intent resultIntent = new Intent(NotificationActivity.this, MainActivity.class);

                /*builder.setAutoCancel(false);
                builder.setTicker("this is ticker text");
                builder.setSubText("This is subtext...");   //API level 16
                builder.setNumber(100);
                builder.build();*/
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(NotificationActivity.this);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                myNotication =   mBuilder.build();
                manager.notify(1, myNotication);

            /*
            //API level 8
            Notification myNotification8 = new Notification(R.drawable.ic_launcher, "this is ticker text 8", System.currentTimeMillis());

            Intent intent2 = new Intent(MainActivity.this, SecActivity.class);
            PendingIntent pendingIntent2 = PendingIntent.getActivity(getApplicationContext(), 2, intent2, 0);
            myNotification8.setLatestEventInfo(getApplicationContext(), "API level 8", "this is api 8 msg", pendingIntent2);
            manager.notify(11, myNotification8);
            */

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                manager.cancel(1);
            }
        });
    }

    private void initialise()
    {
        btnShow = (Button) findViewById(R.id.btnShowNotification);
        btnClear = (Button) findViewById(R.id.btnClearNotification);
    }

}
