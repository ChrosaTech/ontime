package com.chrosatech.ontime.Receivers;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.chrosatech.ontime.Activities.MainActivity;
import com.chrosatech.ontime.Database.DatabaseContents;
import com.chrosatech.ontime.R;

import java.util.Calendar;

/**
 * Created by anshul on 25/1/16.
 */
public class myBroadcastReciever extends BroadcastReceiver  {
    /*MainActivity mainActivity=new MainActivity();*/
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("OnTimeBroadcast","onreceive");
        Toast.makeText(context,"Time!!1",Toast.LENGTH_SHORT).show();
        showNotification(context);
        //TODO remove this vibrator
        Vibrator vibrator=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);

        setNextAlarm(context);

    }

    private void setNextAlarm(Context context) {

        DatabaseContents db = new DatabaseContents(context);
        Calendar calendar = db.getNextNotificationTime();

        Intent intent = new Intent(context, myBroadcastReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        /*Toast.makeText(context, "Alarm set in " + 5 + " seconds",
                Toast.LENGTH_SHORT).show();*/
       /* mainActivity.getToast(context);*/
    }


    private void showNotification(Context context) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        SharedPreferences sharedPreferences = context.getSharedPreferences("OnTimePreferences", Context.MODE_PRIVATE);

        /*Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;*/
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(sharedPreferences.getString("Subject","Subject"))
                        .setContentText(sharedPreferences.getString("Room", "Room") +
                                " (" + sharedPreferences.getString("ClassType", "ClassType") + ")");
        mBuilder.setContentIntent(contentIntent);
        //mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(123456, mBuilder.build());
    }
}
