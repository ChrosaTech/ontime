package com.chrosatech.ontime.Receivers;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.chrosatech.ontime.Activities.MainActivity;
import com.chrosatech.ontime.Database.DatabaseContents;
import com.chrosatech.ontime.Helper.OpenerAndHelper;
import com.chrosatech.ontime.R;

import java.util.Calendar;

/**
 * Created by anshul on 25/1/16.
 */
public class MyBroadcastReciever extends BroadcastReceiver  {
    /*MainActivity mainActivity=new MainActivity();*/
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("OnTimeBroadcast","onreceive");
        Toast.makeText(context,"Time!!1",Toast.LENGTH_SHORT).show();
        showNotification(context);
        //TODO remove this vibrator


        //Vibrator vibrator=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        //vibrator.vibrate(500);

        setNextAlarm(context);





    }


    public static void setNextAlarm(Context context) {

        DatabaseContents db = new DatabaseContents(context);
        Calendar calendar = db.getNextNotificationTime();

        Intent intent = new Intent(context, MyBroadcastReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        /*Toast.makeText(context, "Alarm set in " + 5 + " seconds",
                Toast.LENGTH_SHORT).show();*/
       /* mainActivity.getToast(context);*/
    }


    private void showNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);

        SharedPreferences sharedPreferences = context.getSharedPreferences("OnTimePreferences", Context.MODE_PRIVATE);


        /*Context context12 = OpenerAndHelper.getContext();
        Intent intent = new Intent(context12, MyBroadcastReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager!= null) {
            alarmManager.cancel(pendingIntent);
        }*/

        /*Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;*/
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(sharedPreferences.getString("Subject","Subject"))
                        .setContentText(sharedPreferences.getString("Room", "Room") +
                                " (" + sharedPreferences.getString("ClassType", "ClassType") + ")");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean isVibrateOn = sharedPref.getBoolean("notifications_new_message_vibrate", true);
        if (isVibrateOn) {
            mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        }
        String notificationSound = sharedPref.getString("notifications_new_message_ringtone", "content://settings/system/notification_sound");
        mBuilder.setSound(Uri.parse(notificationSound));
        mBuilder.setAutoCancel(true);
                       /* .addAction(R.drawable.mute_notification,"mute",pendingIntent)*/

        mBuilder.setContentIntent(contentIntent);
        //mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(123456, mBuilder.build());

    }
}
