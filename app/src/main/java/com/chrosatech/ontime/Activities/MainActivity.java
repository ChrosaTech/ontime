package com.chrosatech.ontime.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.chrosatech.ontime.Fragments.FirstLaunchFragment;
import com.chrosatech.ontime.Helper.OpenerAndHelper;
import com.chrosatech.ontime.Helper.Values;
import com.chrosatech.ontime.R;

public class MainActivity extends AppCompatActivity {

    //private static final int num_pages = 6;

    public static SharedPreferences sharedPreferences;
    //public static boolean isThemeChanged = false;
    public static boolean isChangeTimeTable = false;
    /*private float x1;
    private float x2;
    private float MIN_DISTANCE = 10;*/




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        OpenerAndHelper.setContext(this);
        OpenerAndHelper.setAppTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("OnTimePreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!sharedPreferences.contains(Values.keyFirstLaunch)){
            editor.putBoolean(Values.keyFirstLaunch, true);
            isChangeTimeTable = false;
            editor.apply();
        }

        if (sharedPreferences.getBoolean(Values.keyFirstLaunch, true) || isChangeTimeTable) {
            Log.d("First Launch","true");
            OpenerAndHelper.openFirstLaunchFragment();
        }else {

            OpenerAndHelper.openTimeTableFragment();
        }

    }

   /* @Override
    protected void onResume() {
        super.onResume();
        if (isThemeChanged) {
            isThemeChanged = false;
            recreate();
        }
    }*/


    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
            super.onBackPressed();
        }

        Fragment fragment = OpenerAndHelper.getCurrentFragment();
      /*  if (fragment instanceof FirstLaunchFragment) {
            if(((FirstLaunchFragment) fragment).onBackPressed()){
                finish();
            }
        }*//*else if (fragment instanceof TimeTableFragment){
            finish();
        }*//* else {
            finish();
        }*/
        if (!(fragment instanceof FirstLaunchFragment) || ((FirstLaunchFragment) fragment).onBackPressed()){
            finish();
        }
    }

}
