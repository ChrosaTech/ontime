package com.chrosatech.ontime.Fragments;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.chrosatech.ontime.Adapters.ViewPagerAdapter;
import com.chrosatech.ontime.Database.DatabaseContents;
import com.chrosatech.ontime.Helper.OpenerAndHelper;
import com.chrosatech.ontime.Helper.Values;
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

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppBarLayout.LayoutParams params =
                (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        toolbar.setLayoutParams(params);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(5);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);

        setCurrentPage();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        Bundle bundle = new Bundle();
        DatabaseContents dbContents = new DatabaseContents(getContext());
        String days[] = dbContents.getWorkingDaysOfWeek();
        int noOfDays = days.length;
        Values.refreshData = new boolean[noOfDays];
        Values.fragNumber = 0;
        UniversalFragment universalFragment[] = new UniversalFragment[noOfDays];

        for (int i = 0; i<noOfDays; i++){

            universalFragment[i] = new UniversalFragment();
            bundle.putString("day", days[i]);
            universalFragment[i].setArguments(new Bundle(bundle));
            adapter.addFrag(universalFragment[i], days[i]);
        }

        viewPager.setAdapter(adapter);
    }

    private void setCurrentPage() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        Date d = calendar.getTime();
        String dayOfTheWeek = sdf.format(d);
        Boolean flag = false;

        int count = adapter.getCount();
        for (int i = 0; i<count; i++){
            if (dayOfTheWeek.toUpperCase().equals(adapter.getPageTitle(i))){
                viewPager.setCurrentItem(i);
                flag = true;
                break;
            }
        }

        if (!flag){
            viewPager.setCurrentItem(0);
        }
    }

}
