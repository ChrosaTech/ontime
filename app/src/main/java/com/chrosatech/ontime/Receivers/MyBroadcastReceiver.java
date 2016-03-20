package com.chrosatech.ontime.Receivers;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.chrosatech.ontime.Activities.MainActivity;
import com.chrosatech.ontime.Database.DatabaseContents;
import com.chrosatech.ontime.Helper.OpenerAndHelper;
import com.chrosatech.ontime.Helper.Values;
import com.chrosatech.ontime.R;

import java.util.Calendar;

/**
 * Created by anshul on 25/1/16.
 */
public class MyBroadcastReceiver extends BroadcastReceiver  {
    /*MainActivity mainActivity=new MainActivity();*/
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
        setNextAlarm(context);
    }


    public static void setNextAlarm(Context context) {

        DatabaseContents db = new DatabaseContents(context);
        Calendar calendar = db.getNextNotificationTime();

        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        /*Toast.makeText(context, "Alarm set in " + 5 + " seconds",
                Toast.LENGTH_SHORT).show();*/
       /* mainActivity.getToast(context);*/
    }


    private void showNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);

        SharedPreferences sharedPreferences = context.getSharedPreferences("OnTimePreferences", Context.MODE_PRIVATE);


      /*  Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.ic_launcher);*/
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();
        TextDrawable textDrawable = TextDrawable.builder()
                .buildRound(String.valueOf((sharedPreferences.getString("Subject", "Subject")).charAt(0)), color);
        /*Context context12 = OpenerAndHelper.getContext();
        Intent intent = new Intent(context12, MyBroadcastReceiver.class);
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
                        /*.setSmallIcon(R.mipmap.ic_launcher)*/
                        .setLargeIcon(OpenerAndHelper.drawableToBitmap(textDrawable, context))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        //Set action for mute
                        .setContentTitle(sharedPreferences.getString("Subject", "Subject"))
                        .setContentText(sharedPreferences.getString("Room", "Room") +
                                " (" + sharedPreferences.getString("ClassType", "ClassType") + ")")
                /*.addAction(R.drawable.mute_notification,"sds",contentIntent)*/
                ;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean isVibrateOn = sharedPref.getBoolean(Values.keyNotificationVibrate, true);
        if (isVibrateOn) {
            mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        }
        String notificationSound = sharedPref.getString(Values.keyNotificationRingtone, "content://settings/system/notification_sound");
        mBuilder.setSound(Uri.parse(notificationSound));
        mBuilder.setAutoCancel(true);


        mBuilder.setContentIntent(contentIntent);
        //mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(123456, mBuilder.build());

    }

}
