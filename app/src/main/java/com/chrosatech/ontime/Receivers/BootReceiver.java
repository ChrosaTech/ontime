package com.chrosatech.ontime.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.chrosatech.ontime.Helper.OpenerAndHelper;

/**
 * Created by mayank on 15/2/16.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            MyBroadcastReciever.setNextAlarm(context);
        }
    }
}