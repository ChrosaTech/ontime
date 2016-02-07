package com.chrosatech.ontime.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.chrosatech.ontime.Adapters.ViewPagerAdapter;
import com.chrosatech.ontime.Fragments.FirstLaunchFragment;
import com.chrosatech.ontime.Fragments.TimeTableFragment;
import com.chrosatech.ontime.Helper.OpenerAndHelper;
import com.chrosatech.ontime.R;

public class MainActivity extends AppCompatActivity {

   // Toolbar toolbar;
    TabLayout tabLayout;

    private static final int num_pages = 6;

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private PagerAdapter pagerAdapter;
    private ActionBar actionBar;
    public static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static String firstLaunch = "firstLaunch";
    public static String changeTimeTable = "changeTimeTable";
    /*private float x1;
    private float x2;
    private float MIN_DISTANCE = 10;*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_settings) {
            OpenerAndHelper.openSettingsActivity();
           /* Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);*/
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        OpenerAndHelper.setContext(this);
        OpenerAndHelper.setAppTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

       /* actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.indigo)));
            actionBar.setElevation(0);

        }*/

        sharedPreferences = getSharedPreferences("OnTimePreferences",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(!sharedPreferences.contains(firstLaunch)){
            editor.putBoolean(firstLaunch, true);
            editor.putBoolean(changeTimeTable, false);
            editor.apply();
        }

        if (sharedPreferences.getBoolean(firstLaunch, true) || sharedPreferences.getBoolean(changeTimeTable, true)) {
            Log.d("First Launch","true");
            OpenerAndHelper.openFirstLaunchFragment();
        }else {

            OpenerAndHelper.openTimeTableFragment();
          /*  viewPager = (ViewPager) findViewById(R.id.pager);
            setupViewPager(viewPager);
            viewPager.setOffscreenPageLimit(5);
        *//*tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);*//*

            PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
            tabs.setViewPager(viewPager);

            setCurrentPage();*/
        }

    }


    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
            super.onBackPressed();
        }

        Fragment fragment = OpenerAndHelper.getCurrentFragment();
        if (fragment instanceof FirstLaunchFragment) {
            if(((FirstLaunchFragment) fragment).onBackPressed()){
                finish();
            }
        }else if (fragment instanceof TimeTableFragment){
            finish();
        }
    }

}
