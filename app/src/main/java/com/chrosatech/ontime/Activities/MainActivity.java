package com.chrosatech.ontime.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.chrosatech.ontime.Fragments.FirstLaunchFragment;
import com.chrosatech.ontime.Fragments.TimeTableFragment;
import com.chrosatech.ontime.R;
import com.chrosatech.ontime.Adapters.ViewPagerAdapter;

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
    private String firstLaunch = "firstLaunch";
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppTheme();
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

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(!sharedPreferences.contains(firstLaunch)){
            editor.putBoolean(firstLaunch, true);
            editor.apply();
        }

        if (sharedPreferences.getBoolean(firstLaunch, true)) {
            Log.d("First Launch","true");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container, new FirstLaunchFragment(), "launchFragment");
            fragmentTransaction.addToBackStack("launchFragment");
            fragmentTransaction.commit();
        }else {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container, new TimeTableFragment(), "timetable");
            fragmentTransaction.addToBackStack("timetable");
            fragmentTransaction.commit();
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

    private void setAppTheme(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String themeColor = sharedPref.getString("example_theme", "");

        switch (themeColor){

            case "Bubblegum Pink"  : setTheme(R.style.PinkTheme);
                break;
            case "Hot Orange" : setTheme(R.style.OrangeTheme);
                break;
            case "Rose Red"    : setTheme(R.style.RedTheme);
                break;
            case "Forest Green"  : setTheme(R.style.GreenTheme);
                break;
            default:
        }

    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
            super.onBackPressed();
        }

        Fragment fragment = getCurrentFragment();
        if (fragment instanceof FirstLaunchFragment) {
            if(((FirstLaunchFragment) fragment).onBackPressed()){
                finish();
            }
        }else if (fragment instanceof TimeTableFragment){
            finish();
        }
    }

    private Fragment getCurrentFragment(){
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return getSupportFragmentManager().findFragmentByTag(tag);
    }
}
