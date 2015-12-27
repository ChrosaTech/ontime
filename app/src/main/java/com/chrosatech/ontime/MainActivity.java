package com.chrosatech.ontime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    private Boolean exit = false;

    private static final int num_pages = 6;

    private ViewPager viewPager;

    private PagerAdapter pagerAdapter;
    private Button last;
    private Button first;
    private ActionBar actionBar;
    public static SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private String firstLaunch = "firstLaunch";
    /*private float x1;
    private float x2;
    private float MIN_DISTANCE = 10;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.indigo)));
            actionBar.setElevation(0);

        }*/

        sharedpreferences = getPreferences(Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        if(!sharedpreferences.contains(firstLaunch)){
            editor.putBoolean(firstLaunch, true);
            editor.apply();
        }

        if (sharedpreferences.getBoolean(firstLaunch, true)) {
            //the app is being launched for first time, do something
            Intent intent = new Intent(this, LaunchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            // first time task

            // record the fact that the app has been started at least once
          //  editor.putBoolean(firstLaunch, false);
           // editor.apply();
        }

        toolbar = (Toolbar) findViewById(R.id.tabanim_toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(5);
        /*tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);*/

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);

        setCurrentPage();



    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();

        UniversalFragment monday = new UniversalFragment();
        UniversalFragment tuesday = new UniversalFragment();
        UniversalFragment wednesday = new UniversalFragment();
        UniversalFragment thursday = new UniversalFragment();
        UniversalFragment friday = new UniversalFragment();
        UniversalFragment saturday = new UniversalFragment();

        bundle.putString("day", "MONDAY");
        monday.setArguments(new Bundle(bundle));
        bundle.putString("day", "TUESDAY");
        tuesday.setArguments(new Bundle(bundle));
        bundle.putString("day", "WEDNESDAY");
        wednesday.setArguments(new Bundle(bundle));
        bundle.putString("day", "THURSDAY");
        thursday.setArguments(new Bundle(bundle));
        bundle.putString("day", "FRIDAY");
        friday.setArguments(new Bundle(bundle));
        bundle.putString("day", "SATURDAY");
        saturday.setArguments(new Bundle(bundle));


        adapter.addFrag(monday, "MONDAY");
        adapter.addFrag(tuesday, "TUESDAY");
        adapter.addFrag(wednesday, "WEDNESDAY");
        adapter.addFrag(thursday, "THURSDAY");
        adapter.addFrag(friday, "FRIDAY");
        adapter.addFrag(saturday, "SATURDAY");
        viewPager.setAdapter(adapter);
    }

    private void setCurrentPage() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        switch (dayOfTheWeek){
            case "Monday"    : viewPager.setCurrentItem(0);
                break;
            case "Tuesday"   : viewPager.setCurrentItem(1);
                break;
            case "Wednesday" : viewPager.setCurrentItem(2);
                break;
            case "Thursday"  : viewPager.setCurrentItem(3);
                break;
            case "Friday"    : viewPager.setCurrentItem(4);
                break;
            case "Saturday"  : viewPager.setCurrentItem(5);
                break;
            default          : viewPager.setCurrentItem(0);
        }
    }

    /*@Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }*/



/*
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
