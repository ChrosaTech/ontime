package com.chrosatech.ontime.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chrosatech.ontime.Activities.MainActivity;
import com.chrosatech.ontime.Behaviors.FabBehavior;
import com.chrosatech.ontime.Database.DatabaseContents;
import com.chrosatech.ontime.Helper.OpenerAndHelper;
import com.chrosatech.ontime.Helper.Values;
import com.chrosatech.ontime.R;
import com.chrosatech.ontime.Receivers.MyBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

public class FirstLaunchFragment extends Fragment {

    // private AutoCompleteTextView autoCompleteTextView;
    // String sample1[]={"IT","Cse","Ece","EEE"};
    private Spinner branchSpinner, groupSpinner, yearSpinner,collegeSpinner,tutSpinner,shiftSpinner;
    private SharedPreferences.Editor editor;
    private Boolean exit = false;
    private Snackbar snackbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_launch, container, false);
        snackbar = Snackbar.make(view, getString(R.string.notFound), Snackbar.LENGTH_LONG);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        branchSpinner = (Spinner) view.findViewById(R.id.branch);
        groupSpinner = (Spinner) view.findViewById(R.id.group);
        yearSpinner = (Spinner) view.findViewById(R.id.year);
        collegeSpinner = (Spinner)view.findViewById(R.id.college_spinner);
        tutSpinner = (Spinner)view.findViewById(R.id.tut_spinner);
        shiftSpinner = (Spinner)view.findViewById(R.id.spinner_shift);

        editor = MainActivity.sharedPreferences.edit();

        FloatingActionButton fabSubmit = (FloatingActionButton) view.findViewById(R.id.fabSubmit);
        //fabSubmit.setBackgroundTintList(getResources().getColorStateList(R.color.ColorPrimary));

        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fabSubmit.getLayoutParams();
        p.setBehavior(new FabBehavior());
        fabSubmit.setLayoutParams(p);

        // ArrayAdapter<String> adapter=null;
        // adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.autocomplete,sample1);
        // autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);

        // autoCompleteTextView.setAdapter(adapter);
        // autoCompleteTextView.setThreshold(0);


        //Spinners
        addItemsOnSpinnerBranch();
        addItemsOnSpinnerBatch();
        addListenerOnSpinnerItemSelection();
        addItemsOnSpinnerYear();
        addItemsOnSpinnerTut();
        addItemsOnSpinnerCollege();
        addItemsOnSpinnerShift();
        fabSubmit.setOnClickListener(submitClick);
        return view;
    }

    private void addItemsOnSpinnerShift(){
        List<String> list= new ArrayList<>(2);
        list.add("Morning Shift");
        list.add("Evening Shift");

        ArrayAdapter<String> dataAdapter= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shiftSpinner.setAdapter(dataAdapter);
    }

    private void addItemsOnSpinnerBranch(){
        List<String> list= new ArrayList<>(4);
        list.add("CSE");
        list.add("IT");
        list.add("ECE");
        list.add("EEE");
        ArrayAdapter<String> dataAdapter= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(dataAdapter);
    }

    private void addItemsOnSpinnerBatch(){
        List<String> list= new ArrayList<>(3);
        list.add("P1");
        list.add("P2");
        list.add("P3");
        ArrayAdapter<String> dataAdapter= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(dataAdapter);
    }
    private void addItemsOnSpinnerCollege()
    {
        List<String> list=new ArrayList<>(1);
        list.add("BVCOE");
        ArrayAdapter<String> dataAdapter= new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        collegeSpinner.setAdapter(dataAdapter);
    }

    private void addItemsOnSpinnerTut()
    {
        List<String> list=new ArrayList<>(2);
        list.add("T1");
        list.add("T2");
        ArrayAdapter<String> dataAdapter= new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        tutSpinner.setAdapter(dataAdapter);

    }

    private void addItemsOnSpinnerYear(){
        List<String> list= new ArrayList<>(4);
        list.add("First Year");
        list.add("Second Year");
        list.add("Third Year");
        list.add("Fourth Year");
        ArrayAdapter<String> dataAdapter= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(dataAdapter);
    }

    private void addListenerOnSpinnerItemSelection(){
        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private final View.OnClickListener submitClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int yearInt = getYear();

            //TODO update where clause
            String whereClause = "College = \'" + collegeSpinner.getSelectedItem() +"\' " +
                    "AND YEAR = " + yearInt +
                    " AND Branch = '"+ branchSpinner.getSelectedItem()+
                    "' AND Shift = 'morning' AND Tutorial = '1' AND Practical = '2'";
            // String whereClause = "College = 'BVP' AND YEAR = 3 AND Branch = 'IT' AND Shift = 1 AND Tutorial = '1' AND Practical = '2'";

            DatabaseContents db = new DatabaseContents(getContext());
            Pair<int[], String> selectedData = db.getID(whereClause);
            if (selectedData != null){
                int ids[] = selectedData.first;
                int id = ids[0];
                int altId = ids[1];
                String college = selectedData.second;
                //MainActivity.sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                editor.putBoolean(Values.keyFirstLaunch, false);
                editor.putInt(Values.keyID, id);
                editor.putInt(Values.keyAltID, altId);
                editor.putString(Values.keyCollege, college);
                editor.apply();

                setFirstNotification();

            /*Toast.makeText(FirstLaunchFragment.this, "OnClickListener : " + "\nSpinner 1 : " + String.valueOf(branch.getSelectedItem()) +
                    "\nSpinner 2:" + String.valueOf(group.getSelectedItem()) + yearInt, Toast.LENGTH_SHORT).show();*/

                if (MainActivity.isChangeTimeTable) {
                    MainActivity.isChangeTimeTable = false;
                    OpenerAndHelper.restartApp();
                } else {
                    OpenerAndHelper.openTimeTableFragment();
                }
                //getActivity().getSupportFragmentManager().popBackStack();
            } else {
                snackbar = Snackbar.make(v, getString(R.string.notFound), Snackbar.LENGTH_LONG);
                View view = snackbar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                snackbar.show();
            }

        }
    };

    private void setFirstNotification() {
        MyBroadcastReceiver.setNextAlarm(getContext());
        OpenerAndHelper.enableBootReceiver();
    }

    private int getYear() {
        int yearInt;
        switch (String.valueOf(yearSpinner.getSelectedItem())){
            case "First Year" : yearInt = 1;
                break;
            case "Second Year" : yearInt = 2;
                break;
            case "Third Year" : yearInt = 3;
                break;
            case "Fourth Year" : yearInt = 4;
                break;
            default: yearInt = 0;
        }
        return yearInt;
    }

    public boolean onBackPressed() {

        if (snackbar.isShown())
            snackbar.dismiss();
        if (MainActivity.isChangeTimeTable){
            MainActivity.isChangeTimeTable = false;
            /*TaskStackBuilder.create(getContext()).addParentStack(SettingsActivity.class)
                    .addNextIntent(new Intent(getContext(), SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                    .startActivities();*/
           // OpenerAndHelper.openSettingsActivity();
            //OpenerAndHelper.openSettingsActivity();
            return true;
        }
        if (exit) {
            return true;
            //getActivity().finish(); // finish activity
        } else {
            Toast.makeText(getContext(), "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
            return false;
        }

    }
}