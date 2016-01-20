package com.chrosatech.ontime;

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
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

   // Toolbar toolbar;
    TabLayout tabLayout;

    private static final int num_pages = 6;

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private PagerAdapter pagerAdapter;
    private ActionBar actionBar;
    public static SharedPreferences sharedpreferences;
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
        if (id == R.id.action_settings) {
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

        sharedpreferences = getPreferences(Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        if(!sharedpreferences.contains(firstLaunch)){
            editor.putBoolean(firstLaunch, true);
            editor.apply();
        }

        if (sharedpreferences.getBoolean(firstLaunch, true)) {
            Log.d("First Launch","true");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container, new LaunchFragment(), "launchFragment");
            fragmentTransaction.addToBackStack("launchFragment");
            fragmentTransaction.commit();
        }else {

            viewPager = (ViewPager) findViewById(R.id.pager);
            setupViewPager(viewPager);
            viewPager.setOffscreenPageLimit(5);
        /*tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);*/

            PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
            tabs.setViewPager(viewPager);

            setCurrentPage();
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


    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        DatabaseContents dbContents = new DatabaseContents(this);
        String days[] = dbContents.getWorkingDaysOfWeek();
        int noOfDays = days.length;
        UniversalFragment universalFragment[] = new UniversalFragment[noOfDays];

        for (int i = 0; i<noOfDays; i++){

            universalFragment[i] = new UniversalFragment();
            bundle.putString("day", days[i]);
            universalFragment[i].setArguments(new Bundle(bundle));
            adapter.addFrag(universalFragment[i], days[i]);
        }

        viewPager.setAdapter(adapter);

        /*UniversalFragment monday = new UniversalFragment();
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
        viewPager.setAdapter(adapter);*/
    }

    private void setCurrentPage() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        int flag = 0;

        for (int i = 0; i<adapter.getCount(); i++){
            if (dayOfTheWeek.toUpperCase().equals(adapter.getPageTitle(i))){
                viewPager.setCurrentItem(i);
                flag = 1;
                break;
            }
        }

        if (flag == 0){
            viewPager.setCurrentItem(0);
        }

        /*switch (dayOfTheWeek){
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
        }*/
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

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
            super.onBackPressed();
        }

        Fragment fragment = getCurrentFragment();
        if (fragment instanceof LaunchFragment) {
            if(((LaunchFragment) fragment).onBackPressed()){
                finish();
            }
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
