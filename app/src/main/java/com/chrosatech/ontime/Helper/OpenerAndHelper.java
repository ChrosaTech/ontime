package com.chrosatech.ontime.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.chrosatech.ontime.Activities.AppCompatPreferenceActivity;
import com.chrosatech.ontime.Activities.MainActivity;
import com.chrosatech.ontime.Activities.SettingsActivity;
import com.chrosatech.ontime.Fragments.FirstLaunchFragment;
import com.chrosatech.ontime.Fragments.TimeTableFragment;
import com.chrosatech.ontime.R;

/**
 * Created by mayank on 7/2/16.
 */
public class OpenerAndHelper {

    static Context context;
    static Activity activity;
    static FragmentManager fragmentManager;

    public static void setContext(Context context1){
        context = context1;
        activity = (Activity) context;
        if (context != null && !(activity instanceof AppCompatPreferenceActivity)){
            fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        }
    }

    public static Context getContext(){
        return context;
    }

 /*   public static void setActivity(Activity activity1){
        activity = activity1;
    }*/

    public static Activity getActivity(){
        return activity;
    }

    public static void setAppTheme(){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String themeColor = sharedPref.getString("theme", "");

        switch (themeColor){

            case "Bubblegum Pink"  : context.setTheme(R.style.PinkTheme);
                break;
            case "Hot Orange" : context.setTheme(R.style.OrangeTheme);
                break;
            case "Rose Red"    : context.setTheme(R.style.RedTheme);
                break;
            case "Forest Green"  : context.setTheme(R.style.GreenTheme);
                break;
            default:
        }

    }
    public static void setBeforeTime(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String timeBefore = sharedPref.getString("notification_before_time", "");
        switch (timeBefore)
        {
            case "5 mins":
                Log.d("a","cj");
                Toast.makeText(getActivity(),"1",Toast.LENGTH_SHORT).show();
                break;
            case "10 mins":
                Toast.makeText(getActivity(),"1",Toast.LENGTH_SHORT).show();
                break;
            default:
        }

    }

    public static void openSettingsActivity(){
        Intent intent = new Intent(context, SettingsActivity.class);
        activity.startActivity(intent);
    }

    public static void openFirstLaunchFragment(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new FirstLaunchFragment(), "launchFragment");
        fragmentTransaction.addToBackStack("launchFragment");
        fragmentTransaction.commit();
    }

    public static void openTimeTableFragment(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new TimeTableFragment(), "timetable");
        fragmentTransaction.addToBackStack("timetable");
        fragmentTransaction.commit();
    }

    public static Fragment getCurrentFragment(){
        if (fragmentManager.getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        return fragmentManager.findFragmentByTag(tag);
    }

    public static void restartApp(){
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(i);
    }
}
