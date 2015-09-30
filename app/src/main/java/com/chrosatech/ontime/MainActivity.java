package com.chrosatech.ontime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;

    private static final int num_pages = 6;

    private ViewPager viewPager;

    private PagerAdapter pagerAdapter;
    private Button last;
    private Button first;
    private ActionBar actionBar;
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

        toolbar = (Toolbar) findViewById(R.id.tabanim_toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);
        /*tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);*/

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);

        setCurrentPage();

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new UniversalFragment("MONDAY"), "MONDAY");
        adapter.addFrag(new UniversalFragment("TUESDAY"), "TUESDAY");
        adapter.addFrag(new UniversalFragment("WEDNESDAY"), "WEDNESDAY");
        adapter.addFrag(new UniversalFragment("THURSDAY"), "THURSDAY");
        adapter.addFrag(new UniversalFragment("FRIDAY"), "FRIDAY");
        adapter.addFrag(new UniversalFragment("SATURDAY"), "SATURDAY");
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
