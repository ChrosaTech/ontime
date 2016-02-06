package com.chrosatech.ontime.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.chrosatech.ontime.Adapters.ViewPagerAdapter;
import com.chrosatech.ontime.Database.DatabaseContents;
import com.chrosatech.ontime.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mayank on 21/1/16.
 */
public class TimeTableFragment extends Fragment {

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_time_table, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(5);
        /*tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);*/

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);

        setCurrentPage();

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        Bundle bundle = new Bundle();
        DatabaseContents dbContents = new DatabaseContents(getContext());
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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        Date d = calendar.getTime();
        String dayOfTheWeek = sdf.format(d);
        Boolean flag = false;

        for (int i = 0; i<adapter.getCount(); i++){
            if (dayOfTheWeek.toUpperCase().equals(adapter.getPageTitle(i))){
                viewPager.setCurrentItem(i);
                flag = true;
                break;
            }
        }

        if (!flag){
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

}
